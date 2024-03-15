package com.example.book_icon

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.example.book_icon.ui.report.ReportFragment
import dagger.hilt.android.AndroidEntryPoint
import com.example.book_icon.ui.book.BookFragment as BookFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var toolbar: Toolbar
    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private var toolbarString: String = ""
    private var position: Int = 0
    private var historyList: MutableList<HistoryData> = mutableListOf()

    var observedNowLocationValue: NowLocation? = null

    var latitude: Double? = null
    var longitude: Double? = null

    val nowLocation: MutableLiveData<NowLocation> by lazy {
        MutableLiveData<NowLocation>()
    }
    init{
        nowLocation.value = NowLocation(latitude, longitude)
    }

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->

                when (item.itemId) {

                    R.id.fragment_book -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, BookFragment())
                            .commit()
                        if (toolbarString.isEmpty()) {
                            textView.text = "本アイコンの画面"
                        } else {
                            textView.text = toolbarString
                        }
                        position = 0
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.fragment_report -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, ReportFragment())
                            .commit()
                        if (toolbarString.isEmpty()) {
                            textView.text = "レポートアイコンの画面"
                        } else {
                            textView.text = toolbarString
                        }
                        position = 1
                        return@OnNavigationItemSelectedListener true
                    }

                }

                false
            }

        toolbar = findViewById(R.id.toolbar)
        editText = findViewById(R.id.editTextText)
        textView = findViewById(R.id.textView)

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        // 初期表示
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, BookFragment())
            .commit()

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {

            if (editText.text.toString() == "") {
                AlertDialog.Builder(this)
                    .setTitle("エラー")
                    .setMessage("EditTextに文字を入力してください")
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            } else {
                toolbarString = editText.text.toString()
                textView.text = toolbarString
            }

        }

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            if (editText.text.toString() == "") {
                when(position) {
                    0 -> textView.text = "本アイコンの画面"
                    1 -> textView.text = "レポートアイコンの画面"
                }
            } else {
                editText.text.clear()
                when(position) {
                    0 -> textView.text = "本アイコンの画面"
                    1 -> textView.text = "レポートアイコンの画面"
                }
                toolbarString = ""
            }
        }

        nowLocation.observe(this) {
            observedNowLocationValue = it
        }

    }


    fun addHistoryData(historyData: HistoryData) {
        historyList.add(historyData)
    }


    fun getHistoryData(): MutableList<HistoryData> {
        return historyList
    }


    //画面がタッチされた時に反応するやつ
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // InputMethodManagerを取得
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //背景のlinearLayoutを取得
        val constraintLayout = findViewById<ConstraintLayout>(R.id.container)
        // キーボードを閉じる
        inputMethodManager.hideSoftInputFromWindow(
            constraintLayout.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        return false
    }

}