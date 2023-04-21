package com.rom.garagely.common


import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

fun <T, VH : RecyclerView.ViewHolder> RecyclerView.init(
    adapter: BaseRecyclerViewAdapter<T, VH>,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context),
    layoutAnimation: LayoutAnimationController? = null,
    itemAnimator: RecyclerView.ItemAnimator? = DefaultItemAnimator(),
    itemDecoration: RecyclerView.ItemDecoration? = null
) {
    this.adapter = adapter
    this.layoutManager = layoutManager
    this.layoutAnimation = layoutAnimation
    this.itemAnimator = itemAnimator
    itemDecoration?.let {
        this.addItemDecoration(it)
    }
}

fun horizontalLinearLayoutManager(context: Context): LinearLayoutManager {
    return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
}

@SuppressLint("ClickableViewAccessibility")
fun RecyclerView.setEmptyBackgroundTap(onEmptySpaceClick: () -> Unit) {
    var recyclerViewMoveHit = false
    var recyclerViewScrollX = 0.0f
    var recyclerViewScrollY = 0.0f
    // handle empty space click
    this.setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            recyclerViewScrollX = event.x
            recyclerViewScrollY = event.y
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            recyclerViewMoveHit =
                (recyclerViewScrollX != event.x) || (recyclerViewScrollY != event.y)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            if (!recyclerViewMoveHit) {
                onEmptySpaceClick.invoke()
            }
            recyclerViewMoveHit = false
        }
        false
    }
}