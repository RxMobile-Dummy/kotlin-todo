package com.incidentcontrolroom.incidentcommander.firebase

data class FCMModel(
    var notificationId:Int=0,
    var title: String = "",
    var description: String = "",
    var incidentName: String = ""
)