package com.example.collegeeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RoleActivity : AppCompatActivity() {

    lateinit var studentBtn: Button
    lateinit var facultyBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)

        studentBtn = findViewById(R.id.studentBtn)
        facultyBtn = findViewById(R.id.facultyBtn)

        studentBtn.setOnClickListener {
            startActivity(Intent(this, StudentDashboard::class.java))
        }

        facultyBtn.setOnClickListener {
            startActivity(Intent(this, FacultyDashboard::class.java))
        }
    }
}