package com.example.win23

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.example.win23.databinding.FragmentSettingsBinding
import com.example.win23.room.DatabaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentSettings : Fragment() {

    private lateinit var databaseRoom: DatabaseRoom
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRoom = DatabaseRoom.getDatabase(requireContext())
        binding.clearDataButton.setOnClickListener {
            clearPrefBank()
            CoroutineScope(Dispatchers.IO).launch{
                databaseRoom.bettingDao().deleteDatabase()
                CoroutineScope(Dispatchers.Main).launch {
                    requireActivity().startActivity(Intent(context, MainActivity::class.java))
                }
            }
        }
    }

    private fun clearPrefBank() {
        val sharedPrefCapital =
            requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            remove("bank")
        }
    }
}