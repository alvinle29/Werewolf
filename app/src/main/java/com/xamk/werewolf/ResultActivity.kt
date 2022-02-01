package com.xamk.werewolf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xamk.werewolf.adapter.ListAdapter

class ResultActivity : AppCompatActivity() {

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val names: ArrayList<String>? = intent.getStringArrayListExtra("name")
        val characters: ArrayList<String>? = intent.getStringArrayListExtra("character")
        val name = ArrayList<String>()
        val character = ArrayList<String>()

        val n = intent.getIntExtra("n",0)
        for (i in 0 until n) {
            names?.get(i)?.let { name.add(it) }
            characters?.get(i)?.let { character.add(it) }
        }

        recyclerView2 = findViewById(R.id.recyclerView2)
        recyclerView2.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        adapter = ListAdapter(name, character)
        recyclerView2.adapter = adapter

    }
}
