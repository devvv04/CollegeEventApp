package com.example.collegeeventapp

data class Event(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var department: String = "",
    var date: String = "",
    var type: String = "", // "Ranking" or "Participation"
    var firstPoints: String = "",
    var secondPoints: String = "",
    var thirdPoints: String = "",
    var participationPoints: String = "",
    var facultyId: String = ""
)