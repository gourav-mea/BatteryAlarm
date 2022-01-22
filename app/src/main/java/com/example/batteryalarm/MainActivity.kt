package com.example.batteryalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.BatteryManager
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var intbatterylevel =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receiver: BroadcastReceiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.apply {
                    intbatterylevel = this.getIntExtra(BatteryManager.EXTRA_LEVEL,0)
                    var limitlevel :String = findViewById<EditText>(R.id.battery_alarm_level).text.toString()
                    var x=100
                    try {
                        x = limitlevel.toInt()
                    } catch (e: NumberFormatException) {
                        x=100
                    }
                    if(intbatterylevel>= x && flag==1){
                        if(!ringtone.isPlaying) {
                            ringtone.play()
                        }
                    }
                }
            }
        }
        ringtone= defaultRingtone
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver,filter)
    }

    private lateinit var ringtone: Ringtone
    private var flag =0
    fun setalarm(view: android.view.View) {
        flag =1
        Toast.makeText(this,"Alarm has been set !!",Toast.LENGTH_LONG).show()
    }
    fun stopalarm(view: android.view.View) {
        flag=0
        ringtone.stop()
    }
}

val Context.defaultRingtone:Ringtone
    get() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        return RingtoneManager.getRingtone(this, uri)
    }
