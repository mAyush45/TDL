package com.example.tdl

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdl.databinding.ActivityHistoryBinding
import com.example.tdl.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val list= arrayListOf<TodoModel>()
    val adapter=TodoAdapter(list)

    val db by lazy {
        AppDatabase.getDatabase(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.toolbarRV.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=this@MainActivity.adapter
        }


            db.todoDao().getTask().observe(this@MainActivity) {
                if (!it.isNullOrEmpty()) {
                    list.clear()
                    list.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }



        binding.newTaskBtn.setOnClickListener{
            startActivity(Intent(this, TaskActivity::class.java))
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.history ->{
                startActivity(Intent(this, HistoryActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }
}