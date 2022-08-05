package com.todotask.mapper

import com.todotask.model.AlarmInterval.DAILY
import com.todotask.model.AlarmInterval.HOURLY
import com.todotask.model.AlarmInterval.MONTHLY
import com.todotask.model.AlarmInterval.NEVER
import com.todotask.model.AlarmInterval.WEEKLY
import com.todotask.model.AlarmInterval.YEARLY
import javax.inject.Inject
import com.remindme.domain.model.AlarmInterval as DomainInterval
import com.todotask.model.AlarmInterval as ViewDataInterval

/**
 * Maps Alarm Interval between Domain and View.
 */
class AlarmIntervalMapper @Inject constructor() {

    /**
     * Maps Alarm Interval from View Data to Domain.
     *
     * @param alarmInterval the object to be converted
     *
     * @return the converted object
     */
    fun toDomain(alarmInterval: ViewDataInterval): DomainInterval? =
        when (alarmInterval) {
            HOURLY -> DomainInterval.HOURLY
            DAILY -> DomainInterval.DAILY
            WEEKLY -> DomainInterval.WEEKLY
            MONTHLY -> DomainInterval.MONTHLY
            YEARLY -> DomainInterval.YEARLY
            NEVER -> null
        }

    /**
     * Maps Alarm Interval from Domain to View Data.
     *
     * @param alarmInterval the object to be converted
     *
     * @return the converted object
     */
    fun toViewData(alarmInterval: DomainInterval?): ViewDataInterval =
        when (alarmInterval) {
            DomainInterval.HOURLY -> HOURLY
            DomainInterval.DAILY -> DAILY
            DomainInterval.WEEKLY -> WEEKLY
            DomainInterval.MONTHLY -> MONTHLY
            DomainInterval.YEARLY -> YEARLY
            null -> NEVER
        }
}
