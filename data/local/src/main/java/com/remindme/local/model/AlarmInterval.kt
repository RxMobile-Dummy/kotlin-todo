package com.remindme.local.model

import javax.inject.Inject

/**
 * Represents the interval between repeating intervals.
 *
 * @property id the id representation of the interval
 */
@Suppress("MagicNumber")
enum class AlarmInterval(val id: Int?) {

    /**
     * Represents no alarm interval.
     */
    NEVER(0),

    /**
     * Represents a interval of 1 hour.
     */
    HOURLY(1),

    /**
     * Represents a interval of 1 day.
     */
    DAILY(2),

    /**
     * Represents a interval of 1 week.
     */
    WEEKLY(3),

    /**
     * Represents a interval of 1 month.
     */
    MONTHLY(4),

    /**
     * Represents a interval of 1 year.
     */
    YEARLY(5)
}