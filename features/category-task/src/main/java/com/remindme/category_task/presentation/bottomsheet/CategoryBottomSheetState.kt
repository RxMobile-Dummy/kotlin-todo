package com.remindme.category.presentation.bottomsheet

import android.os.Parcelable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.remindme.categoryapi.model.Category
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Stable
@Parcelize
internal class CategoryBottomSheetState @Inject constructor(
    private val category: Category
) : Parcelable {

    @IgnoredOnParcel
    var id by mutableStateOf(category.id)

    @IgnoredOnParcel
    var name by mutableStateOf(category.name)

    @IgnoredOnParcel
    var color by mutableStateOf(category.color)

    fun isEditing(): Boolean =
        id > 0L

    fun toCategory(): Category =
        Category(
            id = id,
            name = name,
            color = color
        )
}
