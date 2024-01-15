package com.example.book_icon.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.book_icon.HistoryAdapter
import com.example.book_icon.MainActivity
import com.example.book_icon.R


class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity?
        if (mainActivity != null) {
            mainActivity.getHistoryData()
        }

        if (mainActivity != null) {
            historyAdapter = HistoryAdapter(mainActivity.getHistoryData())
            historyLayoutManager = LinearLayoutManager(requireContext())
        }

        historyRecyclerView = view.findViewById<RecyclerView?>(R.id.history_recycler_view).also {
            it.adapter = historyAdapter
            it.layoutManager = historyLayoutManager
        }

        (historyLayoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
        historyRecyclerView.layoutManager = historyLayoutManager

        // 以下の処理はリストに区切り線を入れる処理
        val dividerItemDecoration =
            DividerItemDecoration(requireContext() , LinearLayoutManager(requireContext()).getOrientation())
        historyRecyclerView.addItemDecoration(dividerItemDecoration)

    }

}