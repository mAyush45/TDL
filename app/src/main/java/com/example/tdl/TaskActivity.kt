package com.example.tdl

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.tdl.databinding.ActivityTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "todo.db"

class TaskActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var database: AppDatabase

    lateinit var myCalendar: Calendar
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    private val labels = arrayListOf("Low", "Medium", "High")
    val db by lazy {
        AppDatabase.getDatabase(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTaskBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.dateEdit.setOnClickListener(this)
        binding.timeEdit.setOnClickListener(this)
        binding.saveTask.setOnClickListener {

            if (binding.titleInLay.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.taskInLay.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.spinnerCategory.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                var title = binding.titleInLay.toString()
                var subtitle = binding.taskInLay.toString()
                var priority = binding.spinnerCategory.selectedItem.toString()
                var date = myCalendar.time.date.toString()
                var time = myCalendar.time.time.toString()
                GlobalScope.launch{
                    db.todoDao().insertTask(TodoModel(title, subtitle, priority, date, time))
                    db.todoDao().getTask().toString()
                }
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        setUpSpinner()
    }

    private fun setUpSpinner() {
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels)
        labels.sort()
        binding.spinnerCategory.adapter = adapter

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dateEdit -> {
                setDateListener()
            }
            R.id.timeEdit -> {
                setTimeListener()
            }

        }
    }


    private fun setDateListener() {
        myCalendar = Calendar.getInstance()
        dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()
            }

        val datePickerDialog = DatePickerDialog(
            this,
            dateSetListener,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        // Mon, 5 Jan 2022
        val myformat = "EEE, d MMM yyyy"
        val simpleDateFormat = SimpleDateFormat(myformat)
        binding.dateEdit.setText(simpleDateFormat.format(myCalendar.time))

        binding.timeInLay.visibility = View.VISIBLE

    }

    private fun setTimeListener() {
        myCalendar = Calendar.getInstance()
        timeSetListener =
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, min: Int ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, min)

                updateTime()
            }

        val timePickerDialog = TimePickerDialog(
            this,
            timeSetListener,
            myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()

    }

    private fun updateTime() {
        val myformat = "HH: mm: ss"
        val simpleDateFormat = SimpleDateFormat(myformat)
        binding.timeEdit.setText(simpleDateFormat.format(myCalendar.time))

    }

}


