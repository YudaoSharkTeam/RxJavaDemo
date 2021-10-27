package com.mahen.rxjavademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observer
import org.reactivestreams.Subscriber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}