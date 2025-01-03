package com.example.recordkeeper.cycling

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.recordkeeper.databinding.ActivityEditCyclingRecordBinding
import java.util.Calendar

class EditCyclingRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCyclingRecordBinding
    private lateinit var distance: String
    private val preferences by lazy { getSharedPreferences("Cycling", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCyclingRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener { saveRecord() }
        binding.btnClearRecord.setOnClickListener { clearRecord() }

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
                    binding.edittextDate.setText(formattedDate)
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
        binding.edittextRecord.text = Editable.Factory.getInstance()
            .newEditable(preferences.getString("$distance record", ""))

        binding.edittextDate.text = Editable.Factory.getInstance()
            .newEditable(preferences.getString("$distance date", ""))
    }

    private fun clearRecord() {
        preferences.edit {
            remove("$distance record")
            remove("$distance date")
        }
        finish()
    }

    private fun saveRecord() {
        val record = binding.edittextRecord.text.toString()
        val date = binding.edittextDate.text.toString()

        preferences.edit {
            putString("$distance record", record)
            putString("$distance date", date)
        }
        finish()
    }
}