package com.xamk.werewolf

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.*
import androidx.room.Room
import com.xamk.werewolf.adapter.PlayerAdapter
import com.xamk.werewolf.model.PlayerDatabase
import com.xamk.werewolf.model.PlayerItem

class MainActivity :
    AppCompatActivity(),
    PlayerDialogFragment.AddDialogListener{

    // Shopping List items
    private var player: MutableList<PlayerItem> = ArrayList()
    // Shopping List adapter
    private lateinit var adapter: PlayerAdapter
    // RecyclerView
    private lateinit var recyclerView: RecyclerView
    // Shopping List Room database
    private lateinit var db: PlayerDatabase
    //Button
    private lateinit var btnGetData: Button

    // Add a new shopping list item to db
    override fun onDialogPositiveClick(item: PlayerItem) {
        // Create a Handler Object
        val handler = Handler(Looper.getMainLooper(), Handler.Callback {
            // Toast message
            Toast.makeText(
                applicationContext,
                it.data.getString("message"),
                Toast.LENGTH_SHORT
            ).show()
            // Notify adapter data change
            adapter.update(player)
            true
        })
        // Create a new Thread to insert data to database
        Thread(Runnable {
            // insert and get autoincrement id of the item
            val id = db.playerDao().insert(item)
            // add to view
            item.id = id.toInt()
            player.add(item)
            val message = Message.obtain()
            message.data.putString("message","Item added to db!")
            handler.sendMessage(message)
        }).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        btnGetData = findViewById(R.id.btnGetData)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        adapter = PlayerAdapter(player)
        recyclerView.adapter = adapter

        // Create database and get instance
        db = Room.databaseBuilder(
            applicationContext,
            PlayerDatabase::class.java,
            "hs_db"
        ).build()

        // load items
        loadPlayerList()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            // create and show dialog
            val dialog = PlayerDialogFragment()
            dialog.show(supportFragmentManager, "AskNewItemDialogFragment")
        }

        initSwipe()

        btnGetData.setOnClickListener{

            val name = ArrayList<String>()
            var d = 0
            for (item in player) {
                if (item.isSelected) {
                    d += 1
                    name.add(item.name)
                }
            }
            val mIntent = Intent(this@MainActivity,CharacterActivity::class.java )
            mIntent.putExtra("d", d)
            mIntent.putExtra("name", name)
            startActivity(mIntent)
        }
    }

    private fun loadPlayerList() {
        // Create a new Handler object to display a message in UI
        val handler = Handler(Looper.getMainLooper()) {
            // Toast message
            Toast.makeText(
                applicationContext,
                it.data.getString("message"),
                Toast.LENGTH_SHORT
            ).show()
            // Notify adapter data change
            adapter.update(player)
            true
        }

        // Create a new Thread to insert data to database
        Thread {
            player = db.playerDao().getAll()
            val message = Message.obtain()
            if (player.size > 0)
                message.data.putString("message", "Data read from db!")
            else
                message.data.putString("message", "Player list is empty!")
            handler.sendMessage(message)
        }.start()
    }

    private fun initSwipe() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            // Swiped
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                // Create a Handler Object
                val handler = Handler(Looper.getMainLooper()) {
                    // Toast message
                    Toast.makeText(
                        applicationContext,
                        it.data.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                    // Notify adapter data change
                    adapter.update(player)
                    true
                }
                // Get remove item id
                var id = player[position].id

                // Remove from UI list
                player.removeAt(position)

                // Remove from db
                Thread {
                    db.playerDao().delete(id)
                    val message = Message.obtain()
                    message.data.putString("message", "Item deleted from db!")
                    handler.sendMessage(message)
                }.start()
            }

            override fun onMove(
                p0: RecyclerView,
                p1: RecyclerView.ViewHolder,
                p2: RecyclerView.ViewHolder)
                    : Boolean {
                return true
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}