package com.natansin.flashcardsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class intoanim : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intoanim)


        val animationView = findViewById<LottieAnimationView>(R.id.animation_view)

        animationView.setAnimation(R.raw.anim1)

        animationView.playAnimation()

        Handler().postDelayed({
            val intent = Intent(this, activity_choose_option::class.java)
            startActivity(intent)
            finish() // Finaliza a atividade atual após abrir a ChooseActivity
        }, 4000) // 4000 milissegundos = 4 segundos
    }
}
