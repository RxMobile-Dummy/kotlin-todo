package com.todotask.mapper

import android.graphics.Color
import com.remindme.categoryapi.model.Category as ViewCategory
import com.remindme.domain.model.Category as DomainCategory

class CategoryMapper {

    fun toView(domainCategory: DomainCategory): ViewCategory =
        ViewCategory(
            id = domainCategory.id,
            name = domainCategory.name,
            color = Color.parseColor(domainCategory.color)
        )
}
