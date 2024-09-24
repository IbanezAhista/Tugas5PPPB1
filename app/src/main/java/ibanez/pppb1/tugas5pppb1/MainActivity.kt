package ibanez.pppb1.tugas5pppb1

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import ibanez.pppb1.tugas5pppb1.databinding.ActivityMainBinding
import ibanez.pppb1.tugas5pppb1.databinding.ConfirmPageBinding
import ibanez.pppb1.tugas5pppb1.databinding.HomePageBinding
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity(),  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var Tujuan: Array<String>
    var selectedDate: String? = null
    var selectedTime: String? = null
    var selectedDestination: String? = null

    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_TIME = "extra_time"
        const val EXTRA_DESTINATION = "extra_destination"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnShowTimePicker = binding.btnShowTimePicker
        val btnShowCalendar = binding.btnShowCalendar
        Tujuan = resources.getStringArray(R.array.Tujuan)
        with(binding) {
            val adapterTujuan = ArrayAdapter(this@MainActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Tujuan)
            adapterTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTujuan.adapter = adapterTujuan

            spinnerTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: android.view.View,
                    position: Int,
                    id: Long
                ) {
                    selectedDestination = Tujuan[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    selectedDestination = null
                }
            }

            btnBeliTiket.setOnClickListener {
                val confirmPage = ConfirmPage()
                confirmPage.show(supportFragmentManager, "confirmPage")
            }

            btnShowCalendar.setOnClickListener {
                val datePicker = DatePicker()
                datePicker.show(supportFragmentManager, "datePicker")
            }

            btnShowTimePicker.setOnClickListener{
                val timePicker=TimePicker()
                timePicker.show(supportFragmentManager,"timePicker")
            }
        }
    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        binding.btnShowCalendar.text = selectedDate
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        selectedTime = String.format(Locale.UK, "%02d:%02d", hourOfDay, minute)
        binding.btnShowTimePicker.text = selectedTime
    }
}

class DatePicker: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(),
            activity as DatePickerDialog.OnDateSetListener,
            year,
            monthOfYear,
            dayOfMonth
        )
    }
}

class TimePicker: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(
            requireActivity(),
            activity as TimePickerDialog.OnTimeSetListener,
            hourOfDay,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }
}

class ConfirmPage : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val binding = ConfirmPageBinding.inflate(inflater)

        with(binding) {
            val namaPenumpang = (activity as MainActivity).binding.edtUsername.text.toString()
            btnLanjutkan.setOnClickListener {
                val intent = Intent(requireActivity(), PageHome::class.java)
                intent.putExtra(MainActivity.EXTRA_NAMA, namaPenumpang)
                intent.putExtra(MainActivity.EXTRA_DATE, (activity as MainActivity).selectedDate)
                intent.putExtra(MainActivity.EXTRA_TIME, (activity as MainActivity).selectedTime)
                intent.putExtra(MainActivity.EXTRA_DESTINATION, (activity as MainActivity).selectedDestination)
                startActivity(intent)
                dismiss()
            }

            btnBatal.setOnClickListener {
                dismiss()
            }
        }

        builder.setView(binding.root)
        return builder.create()
    }
}
