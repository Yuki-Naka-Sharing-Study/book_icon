package com.example.book_icon

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var prefHistoryNameText: TextView = itemView.findViewById(R.id.pref_history_name)
    private var latText: TextView = itemView.findViewById(R.id.temp_history_text)
    private var lonText: TextView = itemView.findViewById(R.id.weather_history_text)

    //【MY仮説】「ViewHolder」の中で「interface」を使う。
    interface something2 {
        //fun something3(something4: Something5)
    }

//    fun getHistory(something4: Something5) {
//
//        // 左辺は「自分が名付けたTextView.text」で、右辺は「今までリクエストしてきた履歴」
//        prefHistoryNameText.text = prefLatLon.pref
//        latText.text = prefLatLon.pref
//        lonText.text = prefLatLon.pref
//
//    }

}