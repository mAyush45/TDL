package com.example.tdl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import kotlin.random.Random

class TodoAdapter(val list: List<TodoModel>):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount() =list.size

    class TodoViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(todoModel: TodoModel) {
            with(itemView){
                val colors = resources.getIntArray(R.array.material_colors)
                val randomColor= colors[Random.nextInt(colors.size)]
                var v:View= findViewById(R.id.viewColorTag)
                v.setBackgroundColor(randomColor)
                findViewById<TextView>(R.id.textShowTitle).text=todoModel.title
                findViewById<TextView>(R.id.textShowTask).text=todoModel.description
                findViewById<TextView>(R.id.textShowCategory).text=todoModel.category
//                updateTime(todoModel.time)
//                updateDate(todoModel.date)
                findViewById<TextView>(R.id.textShowDate).text=todoModel.date
                findViewById<TextView>(R.id.textShowTime).text=todoModel.time


            }
        }

//        private fun updateTime(time:Long) {
//            val myformat = "h:mm a"
//            val simpleDateFormat = SimpleDateFormat(myformat)
//            itemView.textShowTime.text=simpleDateFormat.format(Date(time))
//        }
//
//        private fun updateDate(time:Long) {
//            val myformat = "EEE, d MMM yyyy"
//            val simpleDateFormat = SimpleDateFormat(myformat)
//            itemView.textShowDate.text=simpleDateFormat.format(Date(time))
//        }

    }
}