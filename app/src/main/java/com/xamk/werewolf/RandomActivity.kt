package com.xamk.werewolf

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class RandomActivity : AppCompatActivity() {

    private var pos = 0
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)

        val btnGet = findViewById<Button>(R.id.btnGet)
        val txtRes = findViewById<TextView>(R.id.txtv)
        val txtvNV = findViewById<TextView>(R.id.txtv1)

        val numberC = intent.getIntExtra("c_number", 0)
        val numberS = intent.getIntExtra("s_number", 0)

        val names: ArrayList<String>? = intent.getStringArrayListExtra("name")
        val characters: ArrayList<String>? = intent.getStringArrayListExtra("character")

        var times = Random.nextInt(99999) % 100 + 1
        while (times != 0) {
            for (i in numberC - 1 downTo 0) {
                 val id = Random.nextInt(i + 1)
                 val a = characters?.get(id)
                 characters?.set(id, characters[i])
                if (a != null) {
                    characters.set(i, a)
                }
            }
            for (i in numberS - 1 downTo 0) {
                val id = Random.nextInt(i + 1)
                val a = names?.get(id)
                names?.set(id, names[i])
                if (a != null) {
                    names.set(i, a)
                }
            }
            times -= 1
        }

        mediaPlayer = MediaPlayer.create(this@RandomActivity, R.raw.xoso)
        mediaPlayer!!.start()

        btnGet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (pos == numberC) {
                    mediaPlayer!!.release()
                    val mIntent = Intent(this@RandomActivity, ResultActivity::class.java)
                    mIntent.putExtra("n", numberC)
                    mIntent.putExtra("name", names)
                    mIntent.putExtra("character", characters)
                    startActivity(mIntent)
                }
                else if (txtRes.text.toString().equals("")) {
                    txtRes.text = names?.get(pos).toString()
                    txtvNV.text = characters?.get(pos).toString()
                    pos++
                }
                else {
                    txtRes.text = ""
                    txtvNV.text = ""
                }
            }
        })
    }

}