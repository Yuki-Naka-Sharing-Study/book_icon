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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.book_icon.HistoryData
import com.example.book_icon.MainActivity
import com.example.book_icon.PrefAdapter
import com.example.book_icon.PrefViewHolder
import com.example.book_icon.R
import com.example.book_icon.ui.HistoryFragment
import org.json.JSONObject


@Suppress("CAST_NEVER_SUCCEEDS")
class BookFragment : Fragment() {

    private lateinit var prefRecyclerView: RecyclerView
    private lateinit var prefLayoutManager: RecyclerView.LayoutManager
    private lateinit var prefAdapter: RecyclerView.Adapter<*>

    private lateinit var selectedPrefLatLon: ArrayList<PrefLatLon>

    private var prefName: String = ""
    private var lat: String = ""
    private var lon: String = ""

    private lateinit var choicedPrefLatLon: PrefLatLon

    private var count: Int = 0

    private lateinit var tempWeatherTextView: TextView
    private lateinit var requestButton: Button
    private lateinit var resetButton: Button
    private lateinit var historyButton: Button

    private lateinit var queue: RequestQueue


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
        return view
    }


    @SuppressLint("SetTextI18n", "ResourceType", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedPrefLatLon = arrayListOf()
        prefLayoutManager = LinearLayoutManager(requireContext())

        var prefArrayList = ArrayList<PrefLatLon>()

        for (value in PrefLatLon.values()) {

            if (value != PrefLatLon.NOSELECT) {
                if (value != PrefLatLon.SELECT) {
                    prefArrayList.add(value)
                }
            }

        }

        prefAdapter = PrefAdapter(prefArrayList,

            object : PrefViewHolder.ConfirmChecked {

                //以下、「prefCheckBox」が「押された」ときに反応する
                override fun confirmedChecked(prefLatLon: PrefLatLon, isChecked: Boolean) {

                    if (isChecked == true) {

                        if (selectedPrefLatLon.contains(prefLatLon)) {
                            selectedPrefLatLon.remove(prefLatLon)
                            return
                        } else {
                            selectedPrefLatLon.add(prefLatLon)
                        }

                        //【「isChecked」が「false」のとき】
                    } else {

                        if (selectedPrefLatLon.contains(prefLatLon)) {
                            selectedPrefLatLon.remove(prefLatLon)
                            tempWeatherTextView.setText("")
                            return
                        } else {
                            selectedPrefLatLon.add(prefLatLon)
                            tempWeatherTextView.setText("")
                        }

                    }

                }

            })

        prefRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        prefRecyclerView.adapter = prefAdapter

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        prefRecyclerView.layoutManager = layoutManager

        val textViewBook: TextView = view.findViewById(R.id.book_text_view)
        textViewBook.text = "こちらはボトムバー、左のボタンのタブページです。\nこの下はスピナーの実装を入れてます。"

        tempWeatherTextView = view.findViewById<TextView>(R.id.temp_weather_text_view)
        requestButton = view.findViewById(R.id.request_button)
        resetButton = view.findViewById(R.id.reset_button)
        historyButton = view.findViewById(R.id.history_button)

        queue = Volley.newRequestQueue(requireContext())

        val prefItems: ArrayList<String> = arrayListOf()

        for (i in PrefLatLon.values()) {
            prefItems.add(i.pref)
        }

        val prefLatLonAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                prefItems
            )

        val prefSpinner: Spinner = view.findViewById(R.id.pref_spinner)
        prefSpinner.adapter = prefLatLonAdapter

        prefSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    if (PrefLatLon.values().get(position).pref == "select") {

                        prefRecyclerView.visibility = View.VISIBLE

                        count = 1


                    } else if (PrefLatLon.values().get(position).pref == "GPS") {

                        prefRecyclerView.visibility = View.INVISIBLE
                        lat
                        lon

                        // spinnerで表示される単一の都道府県を選択したときの処理
                    } else {

                        prefRecyclerView.visibility = View.INVISIBLE

                        lat = PrefLatLon.values().get(position).prefLat
                        lon = PrefLatLon.values().get(position).prefLon
                        choicedPrefLatLon = PrefLatLon.values().get(position)

                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        requestButton.setOnClickListener {

            //【単一】の都道府県を選択した場合、以下の処理を走らせる
            //【単一】の場合は複数回リクエストボタンをタップしても複数回分表示されない
            if (selectedPrefLatLon.isEmpty()) {

                if (count == 0 || count == 1) {
                    requestButton.isEnabled = false
                }

                if (lat == "" && lon == "" && prefName == "") {
                    requestApi(choicedPrefLatLon)
                } else {
                    requestApi(choicedPrefLatLon)
                }

            } else {

                for (i in selectedPrefLatLon) {

                    if (count == 0 || count == 1) {
                        requestButton.isEnabled = false
                    }
                    requestApi(i)

                }
            }

        }

        resetButton.setOnClickListener {
            tempWeatherTextView.text = ""
        }

        historyButton.setOnClickListener {

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.container, HistoryFragment())
                addToBackStack(null)
                commit()
            }

        }

    }

    @SuppressLint("SetTextI18n")
    fun requestApi(choicedPrefLatLon: PrefLatLon) {
        //「never used」になっている「prefLatLon」をどこでどう使うべきか？　そもそも使わなくてもいいのか？
        val url =
            "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current_weather=true"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response

                ->

                val currentWeather: JSONObject =
                    response.getJSONObject("current_weather")
                currentWeather.get("temperature")
                val temperature: Double = currentWeather.getDouble("temperature")
                val weathercode: Int = currentWeather.getInt("weathercode")

                val historyData = HistoryData(choicedPrefLatLon.pref, temperature.toString(), getCode(weathercode))
                val mainActivity = activity as MainActivity?
                if (mainActivity != null) {
                    mainActivity.addHistoryData(historyData)
                }

                tempWeatherTextView.append(
                    "平均気温は${temperature}°C \n 今日の天気は${getCode(weathercode)}です。"
                )

                if (count == 0 || count == 1) {
                    requestButton.isEnabled = true
                }

                count = 1
            },

            {
                tempWeatherTextView.text = "平均気温と今日の天気を表示することに失敗しました。"

                if (count == 0 || count == 1) {
                    requestButton.isEnabled = true
                }

                count = 1
            }

        )

        queue.add(jsonObjectRequest)

    }


    fun getCode(weathercode: Int): String {
        var code: String = "悪天候"
        // 元々のコード
        when (weathercode) {
            0 -> code = "晴れ"
            1 -> code = "晴れのち曇り"
            2 -> code = "晴れのち曇り"
            3 -> code = "晴れのち曇り"
        }
        return code
    }

}

enum class PrefLatLon(val pref: String, val prefLat: String, val prefLon: String) {
    NOSELECT("", "", ""),
    TOKYO("東京", "35.01833", "139.5986"),
    OSAKA("大阪", "34.62278", "135.5111"),
    KYOTO("京都", "35.25194", "135.4458"),
    FUKUOKA("福岡", "33.5225", "130.6681"),
    HOKKAIDO("北海道", "43.46722", "142.8278"),
    OKINAWA("沖縄", "25.77111", "126.64"),
    SELECT("SELECT", "", ""),
    GPS("GPS", "" ,"")
}