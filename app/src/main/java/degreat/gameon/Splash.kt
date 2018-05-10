package degreat.gameon

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity

class Splash: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object: CountDownTimer(1000, 1000) {
            override fun onFinish() {
                startActivity(Intent(this@Splash, Home::class.java))
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
            }

        } .start()
    }
}