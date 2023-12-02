package com.example.book_icon.ui.report

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.book_icon.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReportFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
        // 以降は「onCreateView」の中には処理は書かなくても大丈夫。
        // 以降の処理 (TextViewに「改行」を入れて「現在の日時」を表示する処理) は「onViewCreated」内に書き込む。
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = LocalDateTime.now()
        val dtformat = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分 E曜日")
        val fdate = dtformat.format(date)
        val textViewReport: TextView = view.findViewById(R.id.text_report)

        textViewReport.text = "こちらはボトムバー、右のボタンのタブページです。\n 現在の日時は $fdate です。"
    }

}