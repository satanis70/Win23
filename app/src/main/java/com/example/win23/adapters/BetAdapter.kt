package com.example.win23.adapters

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.win23.R
import com.example.win23.model.DatabaseModel

class BetAdapter(
    val betList: List<DatabaseModel>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BetAdapter.Holder>() {

    inner class Holder(itemView: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.textView_nameBet)
        val tvBetAmount = itemView.findViewById<TextView>(R.id.textView_betAmount)
        val tvStatus = itemView.findViewById<TextView>(R.id.textView_status)
        val tvResult = itemView.findViewById<TextView>(R.id.textView_result)
        val buttonWin = itemView.findViewById<AppCompatImageButton>(R.id.win_button)
        val buttonLoss = itemView.findViewById<AppCompatImageButton>(R.id.loss_button)
        val buttonDelete = itemView.findViewById<AppCompatImageButton>(R.id.delete_button)
        val layout = itemView.findViewById<ConstraintLayout>(R.id.constraint_item_recycler)
        fun bind(name: String, amount: String, odd: String, status: String, capital: String) {
            tvName.text = name
            tvBetAmount.text = amount
            tvStatus.text = status
            when (status) {
                "win" -> {
                    buttonLoss.visibility = View.INVISIBLE
                    buttonWin.visibility = View.INVISIBLE
                    layout.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.round_layout_win,
                        null
                    )
                    tvResult.text = (amount.toDouble()*odd.toDouble()).toString()
                }
                "loss" -> {
                    buttonLoss.visibility = View.INVISIBLE
                    buttonWin.visibility = View.INVISIBLE
                    layout.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.round_layout_loss,
                        null
                    )
                    tvResult.text = (amount.toDouble()*-1).toString()
                }
                else -> {
                    layout.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.round_layout,
                        null
                    )
                }
            }
            buttonWin.setOnClickListener {
                onItemClickListener.onClick(
                    adapterPosition, DatabaseModel(
                        adapterPosition,
                        adapterPosition,
                        name,
                        odd,
                        amount,
                        status,
                        capital
                    ),
                    "win",
                    tvStatus,
                    tvResult
                )
                buttonLoss.visibility = View.INVISIBLE
                buttonWin.visibility = View.INVISIBLE
            }
            buttonLoss.setOnClickListener {
                onItemClickListener.onClick(
                    adapterPosition, DatabaseModel(
                        adapterPosition,
                        adapterPosition,
                        name,
                        odd,
                        amount,
                        status,
                        capital
                    ),
                    "loss",
                    tvStatus,
                    tvResult
                )
                buttonLoss.visibility = View.INVISIBLE
                buttonWin.visibility = View.INVISIBLE
            }
            buttonDelete.setOnClickListener {
                onItemClickListener.onClick(
                    adapterPosition, DatabaseModel(
                        adapterPosition,
                        adapterPosition,
                        name,
                        odd,
                        amount,
                        status,
                        capital
                    ),
                    "delete",
                    tvStatus,
                    tvResult
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return Holder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(
            betList[position].betName,
            betList[position].betAmount,
            betList[position].betOdd,
            betList[position].betStatus,
            betList[position].bankCapital
        )
    }

    override fun getItemCount(): Int {
        return betList.size
    }

    interface OnItemClickListener {
        fun onClick(
            position: Int,
            databaseModel: DatabaseModel,
            status: String,
            textViewStatus: TextView,
            textViewResult: TextView
        ) {
        }
    }
}