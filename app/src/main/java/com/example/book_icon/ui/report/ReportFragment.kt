package com.example.book_icon.ui.report

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.*
import androidx.fragment.app.Fragment
import com.example.book_icon.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ReportFragment : Fragment() {

    private lateinit var gpsImageView: ImageView
    private lateinit var textViewLat: TextView
    private lateinit var textViewLon: TextView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude: String = ""
    private var longitude: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient = getFusedLocationProviderClient(requireContext())

        val date = LocalDateTime.now()
        val dtformat = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分 E曜日")
        val fdate = dtformat.format(date)
        val textViewReport: TextView = view.findViewById(R.id.text_report)

        textViewReport.text = "こちらはボトムバー、右のボタンのタブページです。\n 現在の日時は $fdate です。"

        // 取得した現在地の緯度
        textViewLat = view.findViewById<TextView>(R.id.latitude_text_view)
        // 取得した現在地の経度
        textViewLon = view.findViewById<TextView>(R.id.longitude_text_view)

        gpsImageView = view.findViewById(R.id.gps_image_view)
        gpsImageView.setOnClickListener {
            // 『現在地を取得しますか？はい/いいえ』といったダイアログ
            //「現在の緯度は○○」と「現在の経度は○○」といった文字列を 「TextView」に表示する。
            AlertDialog.Builder(requireContext())
                .setMessage("現在地を取得しますか？")
                .setPositiveButton("はい") { dialog, which -> getLocation()}
                .setNegativeButton("いいえ") { dialog, which -> }
                .show()
        }
    }

    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
            return
        }
        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {

            val latTextView = "緯度: "+ it.latitude.toString()
            val lonTextView = "経度: "+ it.longitude.toString()

            textViewLat.text = latTextView
            textViewLon.text = lonTextView

            latitude = it.latitude.toString()
            longitude = it.longitude.toString()

            //【新しい課題の要件】
            // 「Spinner」に「GPS」という項目を追加 →「GPS」という項目を選択 →「GPSの値」を使ってリクエストを走らせることができたらOK。

            //【MY仮説】以下の処理的なコードを書いて「latitude」「longitude」を「BookFragment」に渡す？

//            val mainActivity = activity as MainActivity?
//            if (mainActivity != null) {
//                mainActivity.addGpsData(gpsData)
//            }

        }
    }


}