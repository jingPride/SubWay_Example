package com.example.subwayexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import java.lang.Float

class MainActivity : AppCompatActivity() {

    lateinit var mScaleGestureDetector : ScaleGestureDetector
    lateinit var mImageView : ImageView

    var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mImageView = findViewById(R.id.imageView);
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        mImageView.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
            mScaleGestureDetector.onTouchEvent(motionEvent);
            return@OnTouchListener true
        })

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector?.onTouchEvent(event)

        Log.d("태그", "" + event?.getX() + ", " + event?.getY())


        return true
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener(){
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            mScaleFactor *= mScaleGestureDetector.scaleFactor

            mScaleFactor = Float.max(0.5f, Float.min(mScaleFactor, 2.0f))

            mImageView.scaleX = mScaleFactor
            mImageView.scaleY = mScaleFactor

            return true
        }
    }
}