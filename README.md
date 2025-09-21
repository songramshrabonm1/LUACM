# 🎓 LUACM (Leading University ACM Helper App)

LUACM is an Android application built for CSE students of Leading University.  
It helps students with academic and co-curricular resources such as previous semester questions, classroom availability, learning videos, contest upsolves, and more.  

---

## Demo Video As a Admin 
https://github.com/user-attachments/assets/bc887511-34fb-4559-a500-da03e0095f84

---


## ✨ Features

- 🔍 **Previous Semester Questions**  
  Students can search and access previous semester question papers (uploaded by Admin).  


<image src = "previousQstn.png" width = '200'>
<image src = "qstn.png" width = '200'>






- 🎥 **Video Module**  
  Admin/Teacher/Seniors can upload Google Drive video links, and the videos are played inside the app using **ExoPlayer**.  
  Perfect for helping new students start their programming journey.  


- 🖥️ **Upsolved Classes**  
  Contest problem-solving discussions and solutions (Codeforces, CodeChef, etc.). 


<image src = 'home.png' width = '200' >
<image src = 'home2.png' width = '200'>
<image src = "module.png" width = '200'>
<image src = "moduleClass.png" width = '200'>
<image src = "exoplayer.png" width = '200'>

- 📘 **Practice Problem Integration**  
  Admin can provide **vJudge problem lists** (with links and passwords if needed).  
  Students can directly practice problems related to the topics they just learned from videos.  

<image src = 'problemsolve.png' width = '200'>
<image src = 'codeforcesss.png' width = '200'>
<image src = 'rating.png' width = '100'>




- 🏫 **Classroom Availability**  
  Students can instantly check which classrooms are free or occupied at a given time — making it easier to find free space for practice or group study.  



- 📅 **Routine Upload**  
  CR/Admin can upload and update class routines.  

<image src = 'freeclass.png' width = '200'>
<image src = 'freeclass copy.png' width = '200'>


- 🔐 **Authentication System**  
  Implemented using **Firebase Authentication**.  
  - Students must verify email after signup.  
  - Only verified users can log in and access app features.  

<image src = 'loginn.png' width = "200">
<image src = 'signupp.png' width = "200">

- 👨‍💼 **Role-based Access**  
  - **Admin**: Full control (upload videos, vJudge lists, questions, routines, add students, assign CR).  
  - **CR**: Can upload routines, classroom status, and previous year questions.  
  - 
  **Student**: After verification, can access all sections.  


<image src = 'account.png' width = "200">
<image src = 'accountlist.png' width = "100">
<br>


---

## 🛠️ Tech Stack

- **Language:** Java (Android Studio)  
- **Backend:** Firebase Authentication  
- **Database/Storage:** Firebase (Auth) + Google Drive (for videos)  
- **Video Player:** ExoPlayer  
- **Other Tools:** Git, GitHub  

<image src = "image-1.png">
---

## ⚡ Challenges Faced

- Firebase no longer provides free storage.  
  ✅ Solution: Instead of storing videos directly, videos are uploaded to **Google Drive** and played inside the app with ExoPlayer.  

---


