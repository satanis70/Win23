package com.example.win23

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.win23.databinding.FragmentStatisticsBinding
import com.example.win23.model.DatabaseModel
import com.example.win23.room.DatabaseRoom
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var databaseRoom: DatabaseRoom
    private var betList = ArrayList<DatabaseModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chart = binding.chart
        databaseRoom = DatabaseRoom.getDatabase(requireContext())
        CoroutineScope(Dispatchers.IO).launch{
            betList.addAll(databaseRoom.bettingDao().getAll())
            CoroutineScope(Dispatchers.Main).launch {
                val chartList = ArrayList<Entry>()
                for (i in betList.indices){
                    chartList.add(Entry(i.toFloat(), betList[i].bankCapital.toFloat()))
                    chartList.add(Entry(betList.size.toFloat(), getPrefBank()!!.toFloat()))
                    val lineDataset = LineDataSet(chartList, "")
                    lineDataset.lineWidth = 6f
                    lineDataset.color = Color.RED
                    lineDataset.setDrawCircles(true)
                    lineDataset.setDrawCircleHole(false)
                    val arrayIline = ArrayList<ILineDataSet>()
                    arrayIline.add(lineDataset)
                    val lineData = LineData(arrayIline)
                    chart.setBackgroundColor(Color.LTGRAY)
                    chart.axisLeft.textColor = Color.RED
                    chart.axisLeft.textSize = 16F
                    chart.axisRight.textColor = Color.RED
                    chart.axisRight.textSize = 16F
                    chart.data = lineData
                    chart.invalidate()
                }
            }
        }
    }

    private fun getPrefBank(): String? {
        val sharedPreference = requireActivity().getSharedPreferences("bank", Context.MODE_PRIVATE)
        return sharedPreference.getString("bank", "")
    }
}