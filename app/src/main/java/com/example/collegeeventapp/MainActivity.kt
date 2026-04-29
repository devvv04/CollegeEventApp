package com.example.collegeeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var roleSpinner: Spinner
    private lateinit var deptSpinner: Spinner
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)
        registerBtn = findViewById(R.id.registerBtn)
        roleSpinner = findViewById(R.id.roleSpinner)
        deptSpinner = findViewById(R.id.deptSpinner)

        auth = FirebaseAuth.getInstance()

        // 🔥 CLEAR FIELDS EVERY TIME SCREEN OPENS
        email.setText("")
        password.setText("")

        val roles = arrayOf("Student", "Faculty")
        val departments = arrayOf("BCA", "BTech", "Dental", "Law", "Arts")

        roleSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
        deptSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, departments)

        // LOGIN
        loginBtn.setOnClickListener {

            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            auth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        val userId = auth.currentUser!!.uid

                        FirebaseDatabase.getInstance().getReference("users")
                            .child(userId)
                            .get()
                            .addOnSuccessListener { snapshot ->

                                val role = snapshot.child("role").value.toString()

                                if (role == "Student") {
                                    startActivity(Intent(this, StudentDashboard::class.java))
                                } else {
                                    startActivity(Intent(this, FacultyDashboard::class.java))
                                }

                                finish() // 🔥 prevent back navigation
                            }

                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }

        // REGISTER
        registerBtn.setOnClickListener {

            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            val role = roleSpinner.selectedItem.toString()
            val dept = deptSpinner.selectedItem.toString()

            auth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        val userId = auth.currentUser!!.uid

                        val userMap = HashMap<String, String>()
                        userMap["email"] = userEmail
                        userMap["role"] = role
                        userMap["department"] = dept

                        FirebaseDatabase.getInstance().getReference("users")
                            .child(userId)
                            .setValue(userMap)

                        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}