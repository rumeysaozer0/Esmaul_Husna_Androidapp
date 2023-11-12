package com.arbolesyazilim.esmaulhusna

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.arbolesyazilim.esmaulhusna.Constants
import com.arbolesyazilim.esmaulhusna.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val args: SecondFragmentArgs by navArgs()
    private lateinit var sharedPreferences: SharedPreferences
    private var zikir = 0
    private var targetZikirCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        // Tıklanan öğenin değerini al
        val selectedName = args.selectedName
        val constantsName = Constants.getNames().find { it.id == selectedName.id }

        if (constantsName != null) {
            val nameArabic = constantsName.nameArabic
            val name = constantsName.name
            val meaning = constantsName.meaning
            val intention = constantsName.intention
            targetZikirCount = constantsName.number

            binding.arabicName.text = nameArabic
            binding.name.text = name
            binding.description.text = meaning
            binding.intention.text = "Niyet: $intention"
            binding.zikirSayisi.text = "Zikir Sayısı: $targetZikirCount"
        } else {
            // Hata durumu
        }

        binding.zikirButton2.visibility = View.INVISIBLE

        // SharedPreferences'ten kaydedilen zikir sayısını al
        zikir = sharedPreferences.getInt("zikir_${selectedName.id}", 0)
        binding.zikirNumber.text = zikir.toString()

        binding.zikirButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Zikir1 butonuna dokunulduğunda
                    zikir++
                    binding.zikirNumber.text = zikir.toString()
                    binding.zikirButton2.visibility = View.VISIBLE
                    binding.zikirButton.visibility = View.INVISIBLE
                    if (zikir == targetZikirCount) {
                        vibrateDevice()
                        showAlertDialog()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    // El çekildiğinde
                    binding.zikirButton.visibility = View.VISIBLE
                    binding.zikirButton2.visibility = View.INVISIBLE
                }
            }
            true
        }

        binding.reset.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Sıfırlamak İstediğinize Emin Misiniz?")
            alertDialogBuilder.setNegativeButton("Hayır") { dialogInterface: DialogInterface, _: Int ->
                // Hayır seçeneği seçildiğinde yapılacak işlemler
                dialogInterface.dismiss()
            }

            alertDialogBuilder.setPositiveButton("Evet") { _, _ ->
                // Evet seçeneği seçildiğinde yapılacak işlemler
                // Örneğin: Zikir sayısını sıfırlama
                zikir = 0
                binding.zikirNumber.text = zikir.toString()
            }

            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    override fun onStop() {
        super.onStop()

        // Zikir sayısını SharedPreferences'e kaydet
        with(sharedPreferences.edit()) {
            putInt("zikir_${args.selectedName.id}", zikir)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun vibrateDevice() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Hedef Zikir Sayısına Ulaşıldı")
        alertDialogBuilder.setMessage("$targetZikirCount zikir sayısına ulaştınız.")
        alertDialogBuilder.setPositiveButton("Tamam") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
