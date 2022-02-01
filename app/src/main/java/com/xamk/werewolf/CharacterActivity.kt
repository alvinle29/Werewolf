package com.xamk.werewolf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_character.*
import java.util.*
import kotlin.collections.ArrayList

class CharacterActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{

    private var characters = ArrayList<String>()
    private var spinner: Spinner? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var pos1: Int = 0
    private var characterList = arrayOf(
        "Werewolf",
        "Hunter",
        "Villager",
        "Bodyguard",
        "Seer",
        "Witch",
        "Cupid",
        "Wolf Cub",
        "Doppelganger",
        "Fool",
        "Lycan",
        "Lone Wolf",
        "The Apprentice Seer",
        "Alpha Werewolf",
        "Vengeful Ghost"
    )

    private val SL = IntArray(characterList.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        val names: ArrayList<String>? = intent.getStringArrayListExtra("name")

        val etSL = findViewById<EditText>(R.id.etSL)
        val btnGo = findViewById<Button>(R.id.btnGo)

        val d = intent.getIntExtra("d",0)

        //val txtRes = findViewById<TextView>(R.id.txtRes)

        spinner = findViewById(R.id.character_spinner)
        arrayAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item, characterList)
        spinner?.adapter = arrayAdapter

        spinner?.onItemSelectedListener = this

        var timer = Timer()
        val DELAY:Long = 500
        etSL.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                Log.e("TAG","timer start")
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        //do something
                        if (etSL.text.toString() != ""){
                            SL[pos1] = etSL.text.toString().toInt()
                            txtRes.setText("Đã nhập ${SL.sum()}/$d nhân vật")
                        }
                    }
                }, DELAY)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("TAG","timer cancel ")
                timer.cancel() //Terminates this timer,discarding any currently scheduled tasks.
                timer.purge() //Removes all cancelled tasks from this timer's task queue.
            }
        })

        btnGo.setOnClickListener{

            val mIntent = Intent(this@CharacterActivity,RandomActivity::class.java)
            val k = SL.sum()

            if ( k != d)
                Toast.makeText(this, "Số người chơi và nhân vật phải bằng nhau", Toast.LENGTH_SHORT).show();
            else {
                for (i in SL.indices)
                    if (SL[i] != 0)
                        for (j in 1..SL[i])
                            characters.add(characterList[i])

                //txtRes.text = names?.get(1).toString()
                mIntent.putExtra("c_number", k)
                mIntent.putExtra("s_number", d)
                mIntent.putExtra("name", names)
                mIntent.putExtra("character", characters)
                startActivity(mIntent)
            }
        }

        btnReset.setOnClickListener{
            etSL.setText("0")
            for (i in SL.indices)
                SL[i] = 0
        }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val etSL = findViewById<EditText>(R.id.etSL)

        if (etSL.text.toString() != "")
            etSL.setText(SL[position].toString())
        else
            etSL.setText("0")

        pos1 = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}