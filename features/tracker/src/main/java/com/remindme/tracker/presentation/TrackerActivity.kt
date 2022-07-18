package com.remindme.tracker.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.remindme.designsystem.RemindMeTheme
//import com.remindme.tracker.di.injectDynamicFeature
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TrackerActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        injectDynamicFeature()

        setContent {
            RemindMeTheme {
                TrackerSection(onUpPress = { finish() })
            }
        }
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        SplitCompat.installActivity(context)
    }
}
