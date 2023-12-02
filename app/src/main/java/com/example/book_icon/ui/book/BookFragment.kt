package com.example.book_icon.ui.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.book_icon.R
import org.json.JSONObject


class BookFragment : Fragment() {

    private var lat:String = ""
    private var lon:String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)

        return view
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewBook: TextView = view.findViewById(R.id.book_text_view)
        textViewBook.text = "こちらはボトムバー、左のボタンのタブページです。\nこの下はスピナーの実装を入れてます。"

        val tempWeatherTextView = view.findViewById<TextView>(R.id.temp_weather_text_view)
        val queue = Volley.newRequestQueue(requireContext())

        val prefItems: ArrayList<String> = arrayListOf()
        for (i in PrefLatLon.values())  {
            prefItems.add(i.pref)
        }

        val prefLatLonAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, prefItems)

        val prefSpinner: Spinner = view.findViewById(R.id.pref_spinner)
        prefSpinner.adapter = prefLatLonAdapter

        prefSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val PrefLat = PrefLatLon.values().get(position).prefLat
                val PrefLon = PrefLatLon.values().get(position).prefLon

                lat = PrefLat
                lon = PrefLon
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val requestButton: Button = view.findViewById(R.id.request_button)
        requestButton.setOnClickListener {

            val url =
                "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current_weather=true"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->

                    val currentWeather: JSONObject = response.getJSONObject("current_weather")
                    currentWeather.get("temperature")
                    val temperature: Double = currentWeather.getDouble("temperature")

                    val weathercode: Int = currentWeather.getInt("weathercode")

                    tempWeatherTextView.text =
                        "平均気温は${temperature}°C \n 今日の天気は${getCode(weathercode)}です。"
                },
                { tempWeatherTextView.text = "平均気温と今日の天気を表示することに失敗しました。" })
            queue.add(jsonObjectRequest)
        }

    }

    fun getCode(weathercode: Int): String {
        var code: String = "悪天候"
        when (weathercode) {
            0 -> code = "晴れ"
            1 -> code = "晴れのち曇り"
            2 -> code = "晴れのち曇り"
            3 -> code = "晴れのち曇り"
        }
        return code
    }

}

enum class PrefLatLon(val pref: String, val prefLat: String, val prefLon: String){
    TOKYO("東京","35.01833", "139.5986"),
    OSAKA("大阪","34.62278", "135.5111"),
    KYOTO("京都","35.25194", "135.4458"),
    FUKUOKA("福岡","33.5225", "130.6681"),
    HOKKAIDO("北海道","43.46722", "142.8278"),
    OKINAWA("沖縄","25.77111", "126.64")
}