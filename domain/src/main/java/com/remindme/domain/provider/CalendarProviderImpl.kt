package com.remindme.domain.provider

import java.util.Calendar

/**
 * Provide the date and time to be used on the task use cases, respecting the Inversion of Control.
 */
class CalendarProviderImpl : CalendarProvider {

    /**
     * Gets the current [Calendar].
     *
     * @return the current [Calendar]
     */
    override fun getCurrentCalendar(): Calendar = Calendar.getInstance()
}
