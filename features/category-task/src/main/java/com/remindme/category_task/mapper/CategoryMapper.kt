package com.remindme.category_task.mapper

import android.graphics.Color
import com.remindme.core.extension.toStringColor
import javax.inject.Inject
import com.remindme.categoryapi.model.Category as ViewCategory
import com.remindme.domain.model.Category as DomainCategory

 class CategoryMapper @Inject constructor() {

    fun toView(domainCategoryList: List<DomainCategory>): List<ViewCategory> =
        domainCategoryList.map { toView(it) }

    private fun toView(domainCategory: DomainCategory): ViewCategory =
        ViewCategory(
            id = domainCategory.id,
            name = domainCategory.name,
            color = Color.parseColor(domainCategory.color)
        )

    fun toDomain(viewCategory: ViewCategory): DomainCategory =
        DomainCategory(
            id = viewCategory.id,
            name = viewCategory.name,
            color = viewCategory.color.toStringColor()
        )
}
