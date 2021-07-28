package com.example.subwayexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.lang.Float

class SubwayMap : AppCompatActivity() {

    lateinit var mScaleGestureDetector : ScaleGestureDetector
    lateinit var subwaymap : ImageView  // subwaymap
    lateinit var slidePanel : SlidingUpPanelLayout

    var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subway_map)

        subwaymap = findViewById(R.id.imageView);
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        slidePanel = findViewById(R.id.main_frame)
        slidePanel.addPanelSlideListener(PanelEventListener())

        subwaymap.setOnTouchListener(View.OnTouchListener{ view, motionEvent ->
            mScaleGestureDetector.onTouchEvent(motionEvent);
            return@OnTouchListener true
        })

        subwaymap.setOnClickListener{
            Toast.makeText(applicationContext, "Click Listener", Toast.LENGTH_SHORT).show()
            val state = slidePanel.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
            // 열린 상태일 경우 닫기
            else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector?.onTouchEvent(event)
        return true
    }

    // pinch zoom
    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener(){
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            mScaleFactor *= mScaleGestureDetector.scaleFactor

            mScaleFactor = Float.max(0.1f, Float.min(mScaleFactor, 5.0f))

            subwaymap.scaleX = mScaleFactor
            subwaymap.scaleY = mScaleFactor

            return true
        }
    }

    // sliding event
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        var slideOffset_t = findViewById<TextView>(R.id.tv_slideOffset)
        var toggle = findViewById<TextView>(R.id.btn_toggle)

        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: kotlin.Float) {
            slideOffset_t.text = slideOffset.toString()
        }

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
            if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                toggle.text = "열기"
            } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                toggle.text = "닫기"
            }
        }

    }
}