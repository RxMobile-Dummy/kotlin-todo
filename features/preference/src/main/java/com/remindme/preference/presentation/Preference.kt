package com.remindme.preference.presentation

//import org.koin.androidx.compose.getViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.remindme.core.extension.getVersionName
import com.remindme.core.extension.openUrl
import com.remindme.designsystem.RemindMeTheme
import com.remindme.domain.usecase.preferences.LoadAppTheme
import com.remindme.domain.usecase.preferences.UpdateAppTheme
import com.remindme.preference.AppThemeOptionsMapper
import com.remindme.preference.R
import com.remindme.preference.model.AppThemeOptions
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import java.util.*
import java.util.concurrent.Future
import kotlin.time.Duration.Companion.seconds

/**
 * RemindMe Preference Section.
 *
 * @param modifier the decorator
 * @param onAboutClick function to be called when the about item is clicked
 * @param onTrackerClick function to be called when the tracker item is clicked
 */
@Composable
fun PreferenceSection(
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit,
    onTrackerClick: () -> Unit,
    theme: AppThemeOptions

) {

        PreferenceLoader(
            modifier = modifier,
            onAboutClick = onAboutClick,
            onTrackerClick = onTrackerClick,
            theme= theme
        )


}

@Composable
private fun PreferenceLoader(
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit,
    onTrackerClick: () -> Unit,
    theme: AppThemeOptions,
   viewModel:PreferenceViewModel = hiltViewModel()
) {
//    PreferenceContent(
//            modifier = modifier,
//            onAboutClick = onAboutClick,
//            onTrackerClick = onTrackerClick,
//            theme = theme,
//           onThemeUpdate = {}
//        )
    val theme by remember(viewModel) {
        viewModel.loadCurrentTheme()
    }.collectAsState(initial = AppThemeOptions.SYSTEM)

    theme.let {
        PreferenceContent(
            modifier = modifier,
            onAboutClick = onAboutClick,
            onTrackerClick = onTrackerClick,
            theme = theme,
           onThemeUpdate = viewModel::updateTheme
        )
    }
}


@Composable
internal fun PreferenceContent(
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit,
    onTrackerClick: () -> Unit,
    theme: AppThemeOptions,
    onThemeUpdate: (AppThemeOptions) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        PreferenceTitle(title = stringResource(id = R.string.preference_title_features))
        TrackerItem(onTrackerClick)
        Separator()
        PreferenceTitle(title = stringResource(id = R.string.preference_title_settings))
        ThemeItem(currentTheme = theme, onThemeUpdate = onThemeUpdate)
        AboutItem(onAboutClick)
        VersionItem()
    }
}

@Composable
private fun PreferenceTitle(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 16.dp)
            .height(32.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
private fun TrackerItem(onTrackerClick: () -> Unit) {
    PreferenceItem(
        title = stringResource(id = R.string.preference_title_tracker),
        onItemClick = onTrackerClick
    )
}

@Composable
@Suppress("MagicNumber")
private fun VersionItem() {
    val title = stringResource(id = R.string.preference_title_version)
    val context = LocalContext.current
    val version = context.getVersionName()
    var numberOfClicks by remember { mutableStateOf(0) }
    val onClick = {
        if (++numberOfClicks == 7) {
            context.openUrl(EasterEggUrl)
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { numberOfClicks }
            .filter { it > 0 }
            .collectLatest {
                delay(1_000)
                numberOfClicks = 0
            }
    }

    PreferenceItem(title = title, description = version, onItemClick = onClick)
}

@Composable
private fun AboutItem(onAboutClick: () -> Unit) {
    PreferenceItem(
        title = stringResource(id = R.string.preference_title_about),
        onItemClick = onAboutClick
    )
}

@Composable
private fun ThemeItem(
    currentTheme: AppThemeOptions,
    onThemeUpdate: (AppThemeOptions) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    PreferenceItem(
        title = stringResource(id = R.string.preference_title_app_theme),
        description = stringResource(id = currentTheme.titleRes),
        onItemClick = { isDialogOpen = true }
    )

    AppThemeDialog(
        isDialogOpen = isDialogOpen,
        onDismissRequest = { isDialogOpen = false },
        currentTheme = currentTheme,
        onThemeUpdate = onThemeUpdate
    )
}

@Composable
private fun Separator() {
    Spacer(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .height(1.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.onSecondary.copy(alpha = 0.7F))
    )
}

private const val EasterEggUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"

@Suppress("UndocumentedPublicFunction")
@Preview
@Composable
fun PreferencePreview() {
    RemindMeTheme {
       // PreferenceSection(onAboutClick = {}, onTrackerClick = {}, theme = )
    }
}
