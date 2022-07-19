package com.remindme.repository.mapper

import com.remindme.repository.model.AlarmInterval.*
import com.remindme.domain.model.AlarmInterval as DomainInterval
import com.remindme.repository.model.AlarmInterval as RepoInterval

/**
 * Maps Alarm Interval between Repository and Domain.
 */
class AlarmIntervalMapper {

    /**
     * Maps Alarm Interval from Repo to Domain.
     *
     * @param alarmInterval the object to be converted
     *
     * @return the converted object
     */
    fun toDomain(alarmInterval: RepoInterval): DomainInterval =
        when (alarmInterval) {
            HOURLY -> DomainInterval.HOURLY
            DAILY -> DomainInterval.DAILY
            WEEKLY -> DomainInterval.WEEKLY
            MONTHLY -> DomainInterval.MONTHLY
            YEARLY -> DomainInterval.YEARLY
            NEVER -> TODO()
        }

    /**
     * Maps Alarm Interval from Domain to Repo.
     *
     * @param alarmInterval the object to be converted
     *
     * @return the converted object
     */
    fun toRepo(alarmInterval: DomainInterval): RepoInterval =
        when (alarmInterval) {
            DomainInterval.HOURLY -> HOURLY
            DomainInterval.DAILY -> DAILY
            DomainInterval.WEEKLY -> WEEKLY
            DomainInterval.MONTHLY -> MONTHLY
            DomainInterval.YEARLY -> YEARLY
        }
}
