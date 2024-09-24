package ibanez.pppb1.tugas5pppb1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ibanez.pppb1.tugas5pppb1.databinding.HomePageBinding

class PageHome : AppCompatActivity() {
    private lateinit var binding: HomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val namaPenumpang = intent.getStringExtra(MainActivity.EXTRA_NAMA)
        val selectedDate = intent.getStringExtra(MainActivity.EXTRA_DATE)
        val selectedTime = intent.getStringExtra(MainActivity.EXTRA_TIME)
        val selectedDestination = intent.getStringExtra(MainActivity.EXTRA_DESTINATION)

        binding.txtNama.text = "Nama     : $namaPenumpang"
        binding.txtTanggal.text = "Tanggal   : $selectedDate"
        binding.txtJam.text = "Jam         : $selectedTime"
        binding.txtTujuan.text = "Tujuan     : $selectedDestination"
    }
}

