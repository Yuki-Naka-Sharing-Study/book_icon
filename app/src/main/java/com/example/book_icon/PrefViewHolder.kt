package com.example.book_icon

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.book_icon.ui.book.PrefLatLon


class PrefViewHolder(itemView: View, private val listener: ConfirmChecked) : RecyclerView.ViewHolder(itemView) {

    private var prefCheckBox: CheckBox = itemView.findViewById(R.id.pref_checkbox)
    private var prefNameText: TextView = itemView.findViewById(R.id.pref_name)

    interface ConfirmChecked {
        fun confirmedChecked(prefLatLon: PrefLatLon, isChecked: Boolean)
    }

    fun getPrefLatLonPosition(prefLatLon: PrefLatLon) {

        prefCheckBox.setOnCheckedChangeListener { _, isChecked ->
            listener.confirmedChecked(prefLatLon, isChecked)
        }

        prefNameText.text = prefLatLon.pref

    }


}