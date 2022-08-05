package com.remindme.glance.model

import javax.inject.Inject

data class Task @Inject constructor(val id: Long?, val title: String)
