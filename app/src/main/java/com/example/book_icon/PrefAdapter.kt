package com.example.book_icon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.book_icon.ui.book.PrefLatLon


class PrefAdapter(
    private var checkedData: ArrayList<PrefLatLon>,
    private val checkedListener: PrefViewHolder.ConfirmChecked
) : RecyclerView.Adapter<PrefViewHolder>() {

    override fun getItemCount(): Int = checkedData.size

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrefViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PrefViewHolder(inflater.inflate(R.layout.pref_name_cb, parent, false), checkedListener)
    }

    override fun onBindViewHolder(holder: PrefViewHolder, position: Int) {
        holder.getPrefLatLonPosition(checkedData.get(position))
    }


}