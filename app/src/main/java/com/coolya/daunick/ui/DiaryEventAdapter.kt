package com.coolya.daunick.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coolya.daunick.R
import com.coolya.daunick.data.entities.DiaryEvent
import com.coolya.daunick.ext.toStringDate
import kotlinx.android.synthetic.main.item_diary_event.view.*

class DiaryEventAdapter : PagedListAdapter<DiaryEvent, DiaryEventViewHolder>(async) {


    var onDeleteClick: ((diaryEvent: DiaryEvent) -> Unit)? = null
    var onSelectClick: ((diaryEvent: DiaryEvent) -> Unit)? = null

    private companion object async : DiffUtil.ItemCallback<DiaryEvent>() {
        override fun areItemsTheSame(oldItem: DiaryEvent, newItem: DiaryEvent): Boolean =
            oldItem.dairyId == newItem.dairyId

        override fun areContentsTheSame(oldItem: DiaryEvent, newItem: DiaryEvent): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryEventViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.item_diary_event, parent, false)
            .run { DiaryEventViewHolder(this) }

    override fun onBindViewHolder(holder: DiaryEventViewHolder, position: Int){
        getItem(holder.adapterPosition)?.let{
            holder.bind(it)
            if(onDeleteClick!=null){
                holder.bindDelete(it, onDeleteClick!!)
            }
            if(onSelectClick!=null){
                holder.bindOnSelect(it, onSelectClick!!)
            }
        }


    }


}

class DiaryEventViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    fun bind(item: DiaryEvent) = with(view){
        date.text = item.time.toStringDate()
        emo.text = item.emo
        scale.text = item.scale
        thoughts.text = item.thoughts
        event.text = item.event
    }

    inline fun bindDelete(diaryEvent: DiaryEvent, crossinline onDeleteClick: (diaryEvent: DiaryEvent) -> Unit): Unit = with(view){
        delete.setOnClickListener { onDeleteClick(diaryEvent) }
    }

    inline fun bindOnSelect(diaryEvent: DiaryEvent, crossinline onSelectClick: (diaryEvent: DiaryEvent) -> Unit) = with(view){
        setOnClickListener { onSelectClick(diaryEvent) }
    }


}