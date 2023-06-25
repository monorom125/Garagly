package com.rom.garagely.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>(
    var items: ArrayList<T> = ArrayList()
) : RecyclerView.Adapter<VH>() {

    protected var action: ((item: T, position: Int) -> Unit)? = null

    var recyclerView: RecyclerView? = null

    private var parentHeight: Int = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun set(data: T) {
        items.clear()
        items.add(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun set(data: List<T>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    open fun add(data: T) {
        items.add(data)
        notifyItemInserted(items.lastIndex)
    }

    open fun add(data: T, position: Int) {
        items.add(position, data)
        notifyItemInserted(position)
    }

    open fun add(data: List<T>) {
        val startIndex = items.size
        items.addAll(data)
        notifyItemRangeInserted(startIndex, data.size)
    }

    open fun add(data: List<T>, startIndex: Int) {
        items.addAll(startIndex, data)
        notifyItemRangeInserted(startIndex, data.size)
    }

    open fun update(position: Int, item: T) {
        items[position] = item
        notifyItemChanged(position)
    }

    open fun update(item: T) {
        val position = items.indexOf(item)
        update(position, item)
    }

    open fun updateAll(data: List<T>) {
        items.clear()
        items.addAll(data)
        notifyItemRangeChanged(0, itemCount)
    }

    open fun removeFrom(startIndex: Int, endIndex: Int = items.lastIndex) {
        for (index in endIndex downTo startIndex) {
            items.removeAt(index)
        }
        notifyItemRangeRemoved(startIndex, (endIndex - startIndex) + 1)
    }

    open fun removeAt(position: Int) {
        if (position < 0 || position == 0 && itemCount == 0) return
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun removeItem(item: T) {
        val position = items.indexOf(item)
        removeAt(position)
    }

    open fun removeFirst() {
        removeItem(items.first())
    }

    open fun removeLast() {
        removeItem(items.last())
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun removeItem(items: List<T>) {
        if (items.size > 1) {
            this.items.removeAll(items.toSet())
            notifyDataSetChanged()
        } else if (items.size == 1) {
            removeItem(items.first())
        }
    }

    open fun addOrUpdate(item: T) {
        if (items.contains(item)) {
            update(item)
        } else {
            add(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val item = items[from]
        items.removeAt(from)
        items.add(to, item)
    }

    fun notifyFirstItemChange() {
        notifyItemChanged(0)
    }

    fun notifyLastItemChanged() {
        notifyItemChanged(items.lastIndex)
    }

    fun setDelegate(action: ((item: T, position: Int) -> Unit)?) {
        this.action = action
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    protected fun getParentHeight(completion: (Int) -> Unit) {
        if (parentHeight > 0) {
            completion.invoke(parentHeight)
        } else {
            recyclerView?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    parentHeight = recyclerView?.height ?: 0
                    completion.invoke(parentHeight)
                    recyclerView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateItemHolder(LayoutInflater.from(parent.context), parent, viewType)
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        onBindItemHolder(holder, position, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    protected abstract fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): VH

    protected abstract fun onBindItemHolder(holder: VH, position: Int, context: Context)


}