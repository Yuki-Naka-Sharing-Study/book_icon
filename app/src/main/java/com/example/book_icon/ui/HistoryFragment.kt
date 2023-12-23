package com.example.book_icon.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.book_icon.R
import com.example.book_icon.ui.book.PrefLatLon


class HistoryFragment : Fragment() {

    //③ 「RecyclerView」の作成。
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyLayoutManager: RecyclerView.LayoutManager
    private lateinit var historyAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyLayoutManager = LinearLayoutManager(requireContext())
        // historyAdapter = HistoryAdapter()

        //② 「(Mutable) List」の作成。「(Mutable) List」と「RecyclerView」はセットで使う。
        //画面に表示するのは「選択した各都道府県 (pref)」「気温 (temperature)」「天気 (weathercode)」
        val historyList: MutableList<PrefLatLon> = mutableListOf()

        for (item in historyList) {

            //historyList.add("何かの変数")
        }

    }


}