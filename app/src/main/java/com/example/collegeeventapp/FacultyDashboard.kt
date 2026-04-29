package com.example.collegeeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth

class FacultyDashboard : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var description: EditText
    lateinit var date: EditText
    lateinit var typeSpinner: Spinner
    lateinit var firstPoints: EditText
    lateinit var secondPoints: EditText
    lateinit var thirdPoints: EditText
    lateinit var participationPoints: EditText
    lateinit var addEventBtn: Button
    lateinit var logoutBtn: Button

    lateinit var listView: ListView
    lateinit var eventList: ArrayList<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_dashboard)

        // 🔥 INIT VIEWS (AFTER setContentView)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        date = findViewById(R.id.date)
        typeSpinner = findViewById(R.id.typeSpinner)
        firstPoints = findViewById(R.id.firstPoints)
        secondPoints = findViewById(R.id.secondPoints)
        thirdPoints = findViewById(R.id.thirdPoints)
        participationPoints = findViewById(R.id.participationPoints)
        addEventBtn = findViewById(R.id.addEventBtn)
        logoutBtn = findViewById(R.id.logoutBtn)

        listView = findViewById(R.id.eventList)
        eventList = ArrayList()

        // 🔥 LOGOUT FIX
        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val types = arrayOf("Ranking", "Participation")
        typeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            types
        )

        val db = FirebaseDatabase.getInstance().getReference("events")

        addEventBtn.setOnClickListener {

            val id = db.push().key!!
            val facultyId = FirebaseAuth.getInstance().currentUser!!.uid

            val event = Event(
                id,
                title.text.toString(),
                description.text.toString(),
                "BCA",
                date.text.toString(),
                typeSpinner.selectedItem.toString(),
                firstPoints.text.toString(),
                secondPoints.text.toString(),
                thirdPoints.text.toString(),
                participationPoints.text.toString(),
                facultyId
            )

            db.child(id).setValue(event)
            Toast.makeText(this, "Event Created", Toast.LENGTH_SHORT).show()
        }

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                eventList.clear()

                val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

                for (data in snapshot.children) {
                    val event = data.getValue(Event::class.java)
                    if (event != null && event.facultyId == currentUserId) {
                        eventList.add(event)
                    }
                }

                listView.adapter = ArrayAdapter(
                    this@FacultyDashboard,
                    android.R.layout.simple_list_item_1,
                    eventList.map { it.title }
                )
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            val event = eventList[position]

            val intent = Intent(this, ParticipantsActivity::class.java)
            intent.putExtra("eventId", event.id)
            startActivity(intent)
        }

        val leaderboardBtn = findViewById<Button>(R.id.leaderboardBtn)
        leaderboardBtn.setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }
    }
}