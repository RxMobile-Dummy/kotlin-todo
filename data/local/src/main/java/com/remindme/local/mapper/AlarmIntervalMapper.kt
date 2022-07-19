package com.remindme.local.mapper

import com.remindme.local.model.AlarmInterval.*
import com.remindme.local.model.AlarmInterval as LocalInterval
import com.remindme.repository.model.AlarmInterval as RepoInterval

/**
 * Maps Alarm Interval between Repository and Domain.
 */
class AlarmIntervalMapper {

    /**
     * Maps Alarm Interval from Local to Repo.
     *
     * @param alarmInterval the object to be converted
     *
     * @return the converted object
     */
    fun toRepo(alarmInterval: LocalInterval): RepoInterval =
        when (alarmInterval) {
            HOURLY -> RepoInterval.HOURLY
            DAILY -> RepoInterval.DAILY
            WEEKLY -> RepoInterval.WEEKLY
            MONTHLY -> RepoInterval.MONTHLY
            YEARLY -> RepoInterval.YEARLY
            NEVER -> TODO()
        }

    /**
     * Maps Alarm Interval from Repo to Local.
     *
     * @param alarmInterval the object to be converted
     *
     * @return the converted object
     */
    fun toLocal(alarmInterval: RepoInterval): LocalInterval =
        when (alarmInterval) {
            RepoInterval.HOURLY -> HOURLY
            RepoInterval.DAILY -> DAILY
            RepoInterval.WEEKLY -> WEEKLY
            RepoInterval.MONTHLY -> MONTHLY
            RepoInterval.YEARLY -> YEARLY
            com.remindme.repository.model.AlarmInterval.NEVER -> TODO()
        }
}
