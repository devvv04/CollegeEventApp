package com.example.collegeeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StudentDashboard : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var eventList: ArrayList<Event>
    lateinit var db: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        logoutBtn = findViewById(R.id.logoutBtn)

        // 🔥 LOGOUT FIX
        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val leaderboardBtn = findViewById<Button>(R.id.leaderboardBtn)
        leaderboardBtn.setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }

        listView = findViewById(R.id.eventList)
        eventList = ArrayList()
        db = FirebaseDatabase.getInstance().getReference("events")
        auth = FirebaseAuth.getInstance()

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()

                for (data in snapshot.children) {
                    val event = data.getValue(Event::class.java)
                    if (event != null) {
                        eventList.add(event)
                    }
                }

                val adapter = object : ArrayAdapter<Event>(
                    this@StudentDashboard,
                    R.layout.event_item,
                    eventList
                ) {
                    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {

                        val view = layoutInflater.inflate(R.layout.event_item, null)

                        val e = eventList[position]

                        view.findViewById<TextView>(R.id.title).text = e.title
                        view.findViewById<TextView>(R.id.desc).text = e.description
                        view.findViewById<TextView>(R.id.date).text = e.date

                        val btn = view.findViewById<Button>(R.id.participateBtn)
                        val userId = auth.currentUser!!.uid

                        val ref = FirebaseDatabase.getInstance()
                            .getReference("participants")
                            .child(e.id)
                            .child(userId)

                        ref.get().addOnSuccessListener {
                            if (it.exists()) {
                                btn.text = "Participated"
                                btn.isEnabled = false
                            }
                        }

                        btn.setOnClickListener {
                            ref.get().addOnSuccessListener {
                                if (it.exists()) {
                                    Toast.makeText(this@StudentDashboard, "Already Participated", Toast.LENGTH_SHORT).show()
                                } else {
                                    val map = HashMap<String, Any>()
                                    map["status"] = "joined"
                                    map["points"] = 0

                                    ref.setValue(map)
                                    Toast.makeText(this@StudentDashboard, "Participated Successfully", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        return view
                    }
                }

                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}