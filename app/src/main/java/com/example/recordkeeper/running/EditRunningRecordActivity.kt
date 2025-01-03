package com.example.recordkeeper.running

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.recordkeeper.databinding.ActivityEditRunningRecordBinding
import java.util.Calendar

class EditRunningRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditRunningRecordBinding
    private lateinit var distance: String
    private val appPreferences by lazy { getSharedPreferences("Running", Context.MODE_PRIVATE) }

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRunningRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener { saveRecord() }
        binding.buttonClearRecord.setOnClickListener { clearRecord() }

        distance = intent.getStringExtra("Distance")!!
        title = "$distance Record"

        pickDate()
    }

    @SuppressLint("DefaultLocale")
    private fun pickDate() {
        binding.textInputDate.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    binding.editTextDate.setText(formattedDate)
                },
                year, month, day
            )
            datePicker.show()
        }
    }

    override fun onResume() {
        super.onResume()
        displayRecord()
    }

    private fun displayRecord() {
        binding.editTextRecord.text = Editable.Factory.getInstance()
            .newEditable(appPreferences.getString("$distance record", ""))
        binding.editTextDate.text = Editable.Factory.getInstance()
            .newEditable(appPreferences.getString("$distance date", ""))
    }

    private fun clearRecord() {
        appPreferences.edit {
            remove("$distance record")
            remove("$distance date")
        }
        finish()
    }

    private fun saveRecord() {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()

        appPreferences.edit {
            putString("$distance record", record)
            putString("$distance date", date)
        }
        finish()
    }
}