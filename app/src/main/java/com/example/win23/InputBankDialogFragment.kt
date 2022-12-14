package com.example.win23

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import com.example.win23.databinding.FragmentInputBankDialogBinding

class InputBankDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentInputBankDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputBankDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextBank = binding.editTextBank
        binding.buttonValidate.setOnClickListener {
            if (editTextBank.text.isNotEmpty()){
                setPrefBank(editTextBank.text.toString())
                requireActivity().startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }

    private fun setPrefBank(sum:String){
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum)
        }
        val sharedPrefCapitalFirst = requireContext().getSharedPreferences("totalBank", Context.MODE_PRIVATE)
        sharedPrefCapitalFirst.edit {
            putString("totalBank", sum)
        }
    }
    override fun onStop() {
        super.onStop()
        requireActivity().startActivity(Intent(context, MainActivity::class.java))
    }
}