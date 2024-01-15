package com.example.book_icon

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class HistoryViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private var prefHistoryNameText: TextView = itemView.findViewById(R.id.pref_history_name)
    private var tempHistoryText: TextView = itemView.findViewById(R.id.temp_history_text)
    private var weatherHistoryText: TextView = itemView.findViewById(R.id.weather_history_text)

    fun getHistory(historyData: HistoryData) {
        prefHistoryNameText.text = historyData.prefNameHistoryData
        tempHistoryText.text = historyData.tempHistoryData
        weatherHistoryText.text = historyData.weatherHistoryData
    }

}