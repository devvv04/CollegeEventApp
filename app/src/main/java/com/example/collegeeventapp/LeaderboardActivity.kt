package com.example.collegeeventapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class LeaderboardActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var list: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        listView = findViewById(R.id.listView)
        list = ArrayList()

        val db = FirebaseDatabase.getInstance().getReference("users")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()

                for (data in snapshot.children) {



                    // 🔥 ONLY STUDENTS
                    val role = data.child("role").value?.toString()?.lowercase() ?: ""

                    if (role == "student") {

                        val email = data.child("email").value?.toString() ?: "Unknown"
                        val points = data.child("totalPoints").value?.toString()?.toIntOrNull() ?: 0

                        list.add("$email - $points pts")
                    }
                }

                // 🔥 SORT DESCENDING
                list.sortByDescending {
                    it.substringAfter("- ").substringBefore(" pts").toInt()
                }

                listView.adapter = ArrayAdapter(
                    this@LeaderboardActivity,
                    android.R.layout.simple_list_item_1,
                    list
                )
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}