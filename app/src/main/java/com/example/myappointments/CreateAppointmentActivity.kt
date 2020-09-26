package com.example.myappointments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.card_view_step_one.*
import kotlinx.android.synthetic.main.card_view_step_two.*
import kotlinx.android.synthetic.main.card_view_step_three.*
import java.util.*

class CreateAppointmentActivity : AppCompatActivity() {

    private val SelectedCalendar = Calendar.getInstance()
    // Variable que puede guardar un radiobutton e inicialmente tendrá un valor null
    private var selectedRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        btnNext.setOnClickListener{
            if (etDescription.text.toString().length<3){
                etDescription.error = getString(R.string.validate_create_appointment_description)
            } else {
                cvStep1.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
        }

        btnNext2.setOnClickListener{
            showAppointmentDataToConfirm()
            cvStep2.visibility = View.GONE
            cvStep3.visibility = View.VISIBLE
        }



        btnConfirmAppointment.setOnClickListener{
            Toast.makeText(this,"Cita registrada correctamente",Toast.LENGTH_LONG).show()
            finish()
        }

        val specialtyOptiones = arrayOf("Specialty A","Specialty B","Specialty C")
        spinnerSpecialties.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,specialtyOptiones)

        val doctorOptions = arrayOf("Doctor A","Doctor B","Doctor C")
        spinnerDoctors.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,doctorOptions)

    }

    private fun showAppointmentDataToConfirm() {
        tvConfirmDescription.text = etDescription.text.toString()
        tvConfirmSpecialty.text = spinnerSpecialties.selectedItem.toString()
        val SelectedRadioBtnId = radioGroupType.checkedRadioButtonId
        var SelectedRadioType = radioGroupType.findViewById<RadioButton>(SelectedRadioBtnId)
        tvConfirmType.text = SelectedRadioType.text.toString()

        tvConfirmDoctorName.text = spinnerDoctors.selectedItem.toString()
        tvConfirmDate.text = etScheduledDate.text.toString()
        tvConfirmTime.text = selectedRadioButton?.text.toString()

    }

    fun onClickScheduledDate(v: View?){
        val year = SelectedCalendar.get(Calendar.YEAR)
        val month = SelectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = SelectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            //Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            SelectedCalendar.set(y,m,d)
            etScheduledDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    m.twoDigits(),
                    d.twoDigits()
                )
            )
            displayRadioButtons()
        }
        //Aca se crea el dialog de la fecha
        //min date
        //max date
        val datePickerDialog = DatePickerDialog(this,listener,year,month,dayOfMonth)
        val datePicker = datePickerDialog.datePicker
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH,1)
        datePicker.minDate = calendar.timeInMillis // +1
        calendar.add(Calendar.DAY_OF_MONTH,29)
        datePicker.maxDate = calendar.timeInMillis // +30
        datePickerDialog.show()

    }

    private fun displayRadioButtons() {
        //radioGroup.clearCheck()
        //radioGroup.removeAllViews()
        // no tener ninguna seleccion
        selectedRadioButton = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        val hours = arrayOf("3:00 PM","3:30 PM","4:00 PM","4:30 PM")
        var goToLeft = true

        hours.forEach{
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it

            radioButton.setOnClickListener{ view ->
                //Desmarcar el elemento (check) seleccionado | para que la línea no marque error la variable selectedRadioButton debe ser distinto de null
                //El signo de interrogación soluciona el problema como si fuera un terneario
                //Si la variable es distinto de null (.isChecked = false)
                //Si la variable es null (no se hace nada)
                selectedRadioButton?.isChecked = false
                //Guardamos el valor sobre el cual se hizo click
                selectedRadioButton = view as RadioButton?
                //Hacemos que el NUEVO VALOR muestre seleccionado
                selectedRadioButton?.isChecked = true
            }

            if (goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft
        }

    }

    private fun Int.twoDigits()
        = if (this>=10) this.toString() else "0$this"

    override fun onBackPressed() {
        when {
            cvStep3.visibility == View.VISIBLE -> {
                cvStep3.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
            cvStep2.visibility == View.VISIBLE -> {
                cvStep2.visibility = View.GONE
                cvStep1.visibility = View.VISIBLE
            }
            cvStep1.visibility == View.VISIBLE -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.dialog_create_appointment_exit_title))
                builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
                builder.setPositiveButton(getString(R.string.dialog_create_appointment_exit_positive_btn)) { _, _ ->
                    finish()
                }

                builder.setNegativeButton(getString(R.string.dialog_create_appointment_exit_negative_btn)) { dialog, _ ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }


    }
}