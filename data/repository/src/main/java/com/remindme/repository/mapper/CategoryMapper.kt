package com.remindme.repository.mapper

import com.remindme.domain.model.Category as DomainCategory
import com.remindme.repository.model.Category as RepoCategory

/**
 * Maps Category between Repository and Domain.
 */
class CategoryMapper {

    /**
     * Maps Category from Repo to Domain.
     *
     * @param repoCategory the Category to be converted.
     *
     * @return the converted Category
     */
    fun toDomain(repoCategory: RepoCategory): DomainCategory =
        DomainCategory(
            id = repoCategory.id,
            name = repoCategory.name,
            color = repoCategory.color
        )

    /**
     * Maps Category from Repo to Domain.
     *
     * @param repoCategoryList the list of Category to be converted.
     *
     * @return the converted list of Category
     */
    fun toDomain(repoCategoryList: List<RepoCategory>): List<DomainCategory> =
        repoCategoryList.map { toDomain(it) }

    /**
     * Maps Category from Domain to Repo.
     *
     * @param localCategory the Category to be converted.
     *
     * @return the converted Category
     */
    fun toRepo(localCategory: DomainCategory): RepoCategory =
        RepoCategory(
            id = localCategory.id,
            name = localCategory.name,
            color = localCategory.color
        )

    /**
     * Maps Category from Domain to Repo.
     *
     * @param localCategoryList the list of Category to be converted.
     *
     * @return the converted list of Category
     */
    fun toRepo(localCategoryList: List<DomainCategory>): List<RepoCategory> =
        localCategoryList.map { toRepo(it) }
}
