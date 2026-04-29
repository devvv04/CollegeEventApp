package com.example.collegeeventapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ParticipantsActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var list: ArrayList<String>
    lateinit var userIds: ArrayList<String>

    lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants)

        listView = findViewById(R.id.listView)

        list = ArrayList()
        userIds = ArrayList()

        // 🔥 Get eventId from previous screen
        eventId = intent.getStringExtra("eventId") ?: ""

        val db = FirebaseDatabase.getInstance()
            .getReference("participants")
            .child(eventId)
        val currentUserId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseDatabase.getInstance()
            .getReference("events")
            .child(eventId)
            .get()
            .addOnSuccessListener {

                val facultyId = it.child("facultyId").value?.toString()

                if (facultyId != currentUserId) {
                    Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

        // 🔥 Load participants
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()
                userIds.clear()

                for (data in snapshot.children) {

                    val userId = data.key!!

                    userIds.add(userId)

                    // 🔥 Fetch email from users table
                    FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(userId)
                        .child("email")
                        .get()
                        .addOnSuccessListener { snapshot ->

                            val email = snapshot.value?.toString() ?: "Unknown"

                            list.add(email)

                            // refresh list after adding
                            listView.adapter = ArrayAdapter(
                                this@ParticipantsActivity,
                                android.R.layout.simple_list_item_1,
                                list
                            )
                        }
                }

                listView.adapter = ArrayAdapter(
                    this@ParticipantsActivity,
                    android.R.layout.simple_list_item_1,
                    list
                )
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // 🔥 CLICK → SELECT WINNER
        listView.setOnItemClickListener { _, _, position, _ ->

            val selectedUserId = userIds[position]

            val options = arrayOf("1st Prize", "2nd Prize", "3rd Prize", "Participation")

            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Select Result")

            builder.setItems(options) { _, which ->

                // 🔥 Get event data to fetch points
                FirebaseDatabase.getInstance()
                    .getReference("events")
                    .child(eventId)
                    .get()
                    .addOnSuccessListener { snapshot ->

                        val first = snapshot.child("firstPoints").value.toString().toIntOrNull() ?: 0
                        val second = snapshot.child("secondPoints").value.toString().toIntOrNull() ?: 0
                        val third = snapshot.child("thirdPoints").value.toString().toIntOrNull() ?: 0
                        val participation = snapshot.child("participationPoints").value.toString().toIntOrNull() ?: 0

                        val points = when (which) {
                            0 -> first
                            1 -> second
                            2 -> third
                            else -> participation
                        }

                        val result = options[which]

                        // ✅ Update participant data
                        FirebaseDatabase.getInstance()
                            .getReference("participants")
                            .child(eventId)
                            .child(selectedUserId)
                            .child("result")
                            .setValue(result)

                        FirebaseDatabase.getInstance()
                            .getReference("participants")
                            .child(eventId)
                            .child(selectedUserId)
                            .child("points")
                            .setValue(points)

                        // ✅ Update leaderboard
                        val userRef = FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(selectedUserId)

                        userRef.child("totalPoints").get().addOnSuccessListener {

                            val current = it.value?.toString()?.toIntOrNull() ?: 0

                            userRef.child("totalPoints").setValue(current + points)

                            Toast.makeText(
                                this,
                                "$result Assigned (+$points pts)",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

            builder.show()
        }

    }
}