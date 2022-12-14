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
import com.example.win23.databinding.FragmentAddBetBinding
import com.example.win23.model.DatabaseModel
import com.example.win23.room.DatabaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBetFragment : DialogFragment() {

    private lateinit var binding: FragmentAddBetBinding
    private lateinit var databaseRoom: DatabaseRoom

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRoom = DatabaseRoom.getDatabase(requireContext())
        val editTextName = binding.editTextNameBet
        val editTextOdd = binding.editTextOddBet
        val editTextSum = binding.editTextSumBet
        binding.buttonAddBetValidate.setOnClickListener {
            if (editTextName.text.isNotEmpty() && editTextOdd.text.isNotEmpty() && editTextSum.text.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    databaseRoom.bettingDao().insert(
                        DatabaseModel(
                            null,
                            databaseRoom.bettingDao().getAll().size,
                            editTextName.text.toString(),
                            editTextOdd.text.toString(),
                            editTextSum.text.toString(),
                            "wait",
                            getPrefBank()!!
                        )
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        val currentBank =
                            getPrefBank()!!.toDouble() - editTextSum.text.toString().toDouble()
                        setPrefBank(currentBank.toString())
                        requireActivity().startActivity(Intent(context, MainActivity::class.java))
                    }
                }
            }
        }
    }

    private fun getPrefBank(): String? {
        val sharedPreference = requireActivity().getSharedPreferences("bank", Context.MODE_PRIVATE)
        return sharedPreference.getString("bank", "")
    }

    private fun setPrefBank(sum: String) {
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum)
        }
    }


}