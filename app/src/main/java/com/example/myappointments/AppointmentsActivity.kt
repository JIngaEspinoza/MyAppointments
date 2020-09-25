package com.example.myappointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappointments.model.Appointment
import kotlinx.android.synthetic.main.activity_appointments.*

class AppointmentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointments = ArrayList<Appointment>()
        appointments.add(Appointment(1,"Médido Test", "12/12/2018", "3:00 PM"))
        appointments.add(Appointment(2,"Médido BB", "12/12/2018", "3:00 PM"))
        appointments.add(Appointment(3,"Médido CC", "12/12/2018", "3:00 PM"))

        rvAppointments.layoutManager = LinearLayoutManager(this) //GridLayoutManager
        rvAppointments.adapter = AppointmentAdapter(appointments)


    }
}