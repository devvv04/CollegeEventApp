# 📱 Smart College Event Management System

🚀 **Android Application using Firebase**

---

## 👨‍💻 Team Members

- **DEV VERMA** (25/SCA/MCAN/029)  
- **RAGHAV JAITLY** (25/SCA/MCAN/036)  
- **KRISHNA SHARMA** (25/SCA/MCAN/043)  

---

## 📌 Project Overview

The **Smart College Event Management System** is an Android application designed to streamline and digitize the management of college events.

This system enables:

- 👨‍🏫 Faculty to create and manage events  
- 🎓 Students to explore and participate in events  
- 🏆 Faculty to assign results and points  
- 📊 Automatic real-time leaderboard generation  

The app leverages **Firebase** for real-time database updates, authentication, and seamless data handling.

---

## ❗ Problem Statement

Traditional college event systems face several challenges:

- ❌ No centralized platform  
- ❌ Manual participation & result tracking  
- ❌ No performance tracking system  
- ❌ Difficult event record management  

---

## ✅ Solution

This project solves the above issues by providing:

- 📱 A centralized Android application  
- ⚡ Real-time event updates  
- 🧠 Automated result and point system  
- 📈 Dynamic leaderboard generation  

---

## 🛠️ Tech Stack

| Component        | Technology Used              |
|----------------|----------------------------|
| Frontend        | Android (XML + Kotlin)     |
| Backend         | Firebase Realtime Database |
| Authentication  | Firebase Authentication    |
| IDE             | Android Studio             |

---

## 🧩 Modules

### 🎓 Student Module
- Register / Login  
- View all events  
- Participate in events  
- Prevent duplicate participation  
- View leaderboard  

---

### 👨‍🏫 Faculty Module
- Register / Login  
- Create events  
- Define event type:
  - Ranking (1st, 2nd, 3rd)
  - Participation  
- Assign points  
- View participants  
- Select winners  

---

### 🏆 Leaderboard Module
- Displays student rankings  
- Sorts based on total points  
- Updates in real-time  

---

### 🔐 Authentication Module
- Firebase-based login system  
- Role-based access control (Student / Faculty)  

---

## 🔄 System Workflow

### 🟢 Step 1: User Registration
- User registers as **Student** or **Faculty**  
- Data stored in Firebase  

### 🔵 Step 2: Login
- Firebase authentication  
- Redirect based on role  

### 🟡 Step 3: Event Creation (Faculty)
- Faculty creates event  
- Adds title, date, points, type  
- Stored in Firebase  

### 🟣 Step 4: Event Participation (Student)
- Student views event  
- Clicks **Participate**  
- Data saved in `participants`  

### 🔴 Step 5: Winner Selection (Faculty)
- Faculty views participants  
- Assigns:
  - 🥇 1st Prize  
  - 🥈 2nd Prize  
  - 🥉 3rd Prize  
  - 🎖 Participation  
- Points assigned dynamically  

### 🟢 Step 6: Leaderboard Update
- Points updated in user profile  
- Leaderboard auto-sorted  

---


---

## 🎯 Features

- ✅ Role-based login (Student / Faculty)  
- ✅ Real-time event updates  
- ✅ Duplicate participation prevention  
- ✅ Dynamic leaderboard  
- ✅ Firebase integration  
- ✅ Event-based point system  

---

## 📌 Conclusion

This project successfully demonstrates a **real-time, scalable, and secure event management system**.

It:
- Reduces manual effort  
- Improves transparency  
- Enhances student engagement  
- Provides structured event tracking  

---

## 🚀 Future Enhancements

- 📜 Certificate generation system  
- 🏫 Department-based filtering  
- 🎨 UI upgrade using RecyclerView  
- 🔔 Push notifications  
- 🛠️ Admin panel  

---

## ⭐ Acknowledgement

We would like to thank our faculty and institution for supporting this project.

---

## 📬 Contact

For queries or collaboration:

📧 devv6687@gmail.com

---
