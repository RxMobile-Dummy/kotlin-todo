package com.remindme.ui.theme.presentation.home

import android.os.Parcelable
import com.remindme.categoryapi.model.Category
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

   sealed class SheetContentState  {

    @Parcelize
    object Empty : SheetContentState(), Parcelable

    @Parcelize
    object TaskListSheet : SheetContentState(), Parcelable

    @Parcelize
    data class CategorySheet(val category: Category?) : SheetContentState(), Parcelable
}
