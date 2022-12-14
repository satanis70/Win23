package com.example.win23

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.win23.adapters.BetAdapter
import com.example.win23.databinding.FragmentHomeBinding
import com.example.win23.model.DatabaseModel
import com.example.win23.room.DatabaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), BetAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseRoom: DatabaseRoom
    private var betList = ArrayList<DatabaseModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRoom = DatabaseRoom.getDatabase(requireContext())
        if (getPrefBank()!!.isEmpty()) {
            val showAddDialog = InputBankDialogFragment()
            showAddDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        }
        binding.textViewBank.text = getPrefBank()
        addBet()
        setDataRecycler()
    }

    private fun setDataRecycler() {
        betList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            betList.addAll(databaseRoom.bettingDao().getAll())
            launch(Dispatchers.Main) {
                val recyclerView = binding.recyclerView
                val adapter = BetAdapter(betList, this@HomeFragment)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }
        }

    }

    private fun addBet() {
        binding.buttonAddBet.setOnClickListener {
            val showAddDialog = AddBetFragment()
            showAddDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
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

    @SuppressLint("SetTextI18n")
    override fun onClick(
        position: Int,
        databaseModel: DatabaseModel,
        status: String,
        textViewStatus: TextView,
        textViewResult: TextView
    ) {
        val amount = betList[position].betAmount
        val odd = betList[position].betOdd
        val textViewBank = binding.textViewBank
        if (status == "win") {
            val res = odd.toDouble() * amount.toDouble()
            setPrefBank((getPrefBank()!!.toDouble() + res).toString())
            textViewResult.text = res.toString()
            textViewStatus.text = status
            textViewBank.text = getPrefBank()
            CoroutineScope(Dispatchers.IO).launch {
                databaseRoom.bettingDao().update("win", position)
                setDataRecycler()
            }
        } else if (status == "loss") {
            textViewStatus.text = status
            textViewResult.text = (amount.toDouble() * -1).toString()
            textViewBank.text = getPrefBank()
            CoroutineScope(Dispatchers.IO).launch {
                databaseRoom.bettingDao().update("loss", position)
                setDataRecycler()
            }
        } else if (status == "delete") {
            CoroutineScope(Dispatchers.IO).launch {
                betList = databaseRoom.bettingDao().getAll() as ArrayList<DatabaseModel>
                launch(Dispatchers.Main) {
                    if (betList[position].betStatus == "loss") {
                        var capWinLoss = getPrefBank()!!.toDouble()
                        capWinLoss += betList[position].betAmount.toDouble()
                        setPrefBank(capWinLoss.toString())
                        textViewBank.text = "$capWinLoss"
                        launch(Dispatchers.IO) {
                            databaseRoom.bettingDao().delete(betList[position])
                            betList = databaseRoom.bettingDao().getAll() as ArrayList<DatabaseModel>
                            launch(Dispatchers.Main) {
                                setDataRecycler()
                            }
                        }
                    } else if (betList[position].betStatus == "win") {
                        var capWinDel = getPrefBank()!!.toDouble()
                        capWinDel -= betList[position].betAmount.toDouble()
                        setPrefBank(capWinDel.toString())
                        textViewBank.text = "$capWinDel"
                        CoroutineScope(Dispatchers.IO).launch {
                            databaseRoom.bettingDao().delete(betList[position])
                            betList = databaseRoom.bettingDao().getAll() as ArrayList<DatabaseModel>
                            launch(Dispatchers.Main) {
                                setDataRecycler()
                            }
                        }
                    } else {
                        var capWinLoss = getPrefBank()!!.toDouble()
                        capWinLoss += betList[position].betAmount.toDouble()
                        setPrefBank(capWinLoss.toString())
                        textViewBank.text = "$capWinLoss"
                        launch(Dispatchers.IO) {
                            databaseRoom.bettingDao().delete(betList[position])
                            betList =
                                databaseRoom.bettingDao().getAll() as ArrayList<DatabaseModel>
                            launch(Dispatchers.Main) {
                                setDataRecycler()
                            }
                        }
                    }
                }
            }
        }
    }
}
