package com.examplek.agecal

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvDateController : TextView? = null

    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonDatePicker: Button = findViewById(R.id.buttonDatePicker)

        tvDateController = findViewById(R.id.tvDate);

        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        buttonDatePicker.setOnClickListener{
            openCalendar()
        }
    }

    private fun openCalendar(){
        val myCalendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance()
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd =DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ view,year,month,day ->

                Toast.makeText(this, "DatePicker ", Toast.LENGTH_SHORT).show()
                val dateString: String = "$day/${month+1}/$year"
                tvDateController?.text  = dateString
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(dateString)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time/60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentInMinutes = currentDate.time/60000
                        val ageInMinutes = currentInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text = "$ageInMinutes"
                    }
                }

            },year,month,day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}