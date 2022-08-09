package com.remindme.category_task.presentation.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.remindme.category_task.R
import com.remindme.category_task.presentation.list.CategoryListViewModelImpl
import com.remindme.categoryapi.model.Category
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.designsystem.RemindMeTheme
import com.remindme.designsystem.components.RemindMeDialog
import com.remindme.designsystem.components.RemindMeInputTextField
import com.remindme.designsystem.components.DialogArguments
import com.remindme.designsystem.components.RemindMeToolbar
import kotlinx.coroutines.delay

/**
 * RemindMe Category Bottom Sheet.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryBottomSheet(onUpPress: () -> Unit,
                        category: Category?,
                        //onHideBottomSheet: () -> Unit,
navController: NavController
    ) {
    Scaffold(
        topBar = { RemindMeToolbar(onUpPress = onUpPress) },
        content = {   val colorList = CategoryColors.values().map { it.value }

            val editCategory = category ?: Category(
                name = "",
                color = CategoryColors.values()[0].value.toArgb()
            )

            var bottomContent by rememberSaveable(category) {
                mutableStateOf(CategoryBottomSheetState(editCategory))
            }

            CategorySheetLoader(
                colorList = colorList,
                bottomSheetState = bottomContent,
                onHideBottomSheet = {
                    //  onHideBottomSheet()
                    bottomContent = CategoryBottomSheetState(editCategory)
                },
                navController = navController
            ) }
    )

}

@Composable
private fun CategorySheetLoader(
    addViewModel: CategoryAddViewModel = hiltViewModel(),
    editViewModel: CategoryEditViewModel = hiltViewModel(),
    bottomSheetState: CategoryBottomSheetState,
    colorList: List<Color>,
    onHideBottomSheet: () -> Unit,
    navController: NavController
) {

    val onSaveCategory: (CategoryBottomSheetState) -> Unit = if (bottomSheetState.isEditing()) {
        { editCategory -> editViewModel.updateCategory(editCategory.toCategory()) }
    } else {
        { newCategory -> addViewModel.addCategory(newCategory.toCategory())

        }
    }

    CategorySheetContent(
        colorList = colorList,
        state = bottomSheetState,
        onCategoryChange = { updatedState ->
            onSaveCategory(updatedState)
            onHideBottomSheet()
        },
        onCategoryRemove = { category ->
            editViewModel.deleteCategory(category)
            onHideBottomSheet()
        },
        navController = navController
    )
}

@Composable
@Suppress("MagicNumber")
private fun CategorySheetContent(
    state: CategoryBottomSheetState,
    colorList: List<Color>,
    onCategoryChange: (CategoryBottomSheetState) -> Unit,
    onCategoryRemove: (Category) -> Unit,
    navController:NavController
) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceAround) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            var openDialog by rememberSaveable { mutableStateOf(false) }
            val focusRequester = remember { FocusRequester() }

            LaunchedEffect(Unit) {
                delay(300)
                focusRequester.requestFocus()
            }

            RemoveCategoryDialog(
                categoryName = state.name,
                isDialogOpen = openDialog,
                onCloseDialog = { openDialog = false },
                onActionConfirm = { onCategoryRemove(state.toCategory()) }
            )

            RemindMeInputTextField(
                label = stringResource(id = R.string.category_add_label),
                text = state.name,
                onTextChange = { state.name = it },
                modifier = Modifier
                    .weight(5F)
                    .focusRequester(focusRequester)
            )
            if (state.isEditing()) {
                IconButton(
                    onClick = { openDialog = true },
                    modifier = Modifier
                        .height(64.dp)
                        .weight(1F)
                ) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                }
            }
        }
        Spacer(Modifier.height(50.dp))
        CategoryColorSelector(
            colorList = colorList,
            value = Color(state.color),
            onColorChange = { state.color = it.toArgb() }
        )
        Spacer(Modifier.height(50.dp))

        CategorySaveButton(
            currentColor = Color(state.color),
            onClick = {
                onCategoryChange(state)
                navController.popBackStack()

            }
        )
    }
}

@Composable
private fun CategoryColorSelector(
    colorList: List<Color>,
    value: Color,
    onColorChange: (Color) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        items(
            items = colorList,
            itemContent = { color ->
                val optionSelected = color == value
                CategoryColorItem(color, optionSelected, onClick = { onColorChange(color) })
            }
        )
    }
}

@Composable
private fun RemoveCategoryDialog(
    categoryName: String,
    isDialogOpen: Boolean,
    onCloseDialog: () -> Unit,
    onActionConfirm: () -> Unit
) {
    val arguments = DialogArguments(
        title = stringResource(id = R.string.category_dialog_remove_title),
        text = stringResource(id = R.string.category_dialog_remove_text, categoryName),
        confirmText = stringResource(id = R.string.category_dialog_remove_confirm),
        dismissText = stringResource(id = R.string.category_dialog_remove_cancel),
        onConfirmAction = {
            onActionConfirm()
            onCloseDialog()
        }
    )
    RemindMeDialog(
        arguments = arguments,
        isDialogOpen = isDialogOpen,
        onDismissRequest = onCloseDialog
    )
}

@Composable
private fun CategorySaveButton(currentColor: Color, onClick: () -> Unit) {
    val colorState = animateColorAsState(targetValue = currentColor)
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = colorState.value),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = stringResource(id = R.string.category_sheet_save),
            color = MaterialTheme.colors.background
        )
    }
}

@Composable
private fun CategoryColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color = color)
                .selectable(
                    role = Role.RadioButton,
                    selected = isSelected,
                    onClick = onClick
                )
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.background)
            )
        }
    }
}

@Suppress("UndocumentedPublicFunction")
@Composable
fun CategorySheetContentPreview(navController: NavController) {
    RemindMeTheme {
        Surface(modifier = Modifier.height(256.dp)) {
            val category = Category(id = 1L, name = "Movies", color = android.graphics.Color.YELLOW)
            val state = CategoryBottomSheetState(category)
            CategorySheetContent(
                state = state,
                colorList = CategoryColors.values().map { it.value },
                onCategoryChange = {},
                onCategoryRemove = {},
                navController = navController
            )
        }
    }
}
