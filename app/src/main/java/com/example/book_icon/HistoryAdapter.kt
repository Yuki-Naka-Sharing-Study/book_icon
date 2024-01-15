package com.example.book_icon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class HistoryAdapter(historyList: MutableList<HistoryData>): RecyclerView.Adapter<HistoryViewHolder>() {

    private val _historyList: MutableList<HistoryData> = historyList

    override fun getItemCount(): Int = _historyList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(inflater.inflate(R.layout.history_result, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.getHistory(_historyList.get(position))
    }

}