package com.example.book_icon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    private val historyData: ArrayList<String>
    //【MY仮説】いあkの１行は間違っていないはず。上の<>の中に何を入れるべきか？
): RecyclerView.Adapter<HistoryViewHolder>() {

    override fun getItemCount(): Int = historyData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(inflater.inflate(R.layout.history_result, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        //【理解していること】ここで引数「holder」を使うことは理解できている。
        //【MY仮説】「HistoryHolder」の関数を使う。
        //holder.getHistory(historyData.get(position))
    }

}