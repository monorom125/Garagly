import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<T : ViewBinding>(protected val binding: T) :
    RecyclerView.ViewHolder(binding.root) {

    protected val context: Context
        get() = binding.root.context

    protected fun getString(resId: Int): String {
        return context.getString(resId)
    }

    protected fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    protected fun getColorStateList(resId: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, resId)
    }

    protected fun getDimensionPixelOffset(resId: Int): Int {
        return context.resources.getDimensionPixelOffset(resId)
    }

    protected fun getDimension(resId: Int): Float {
        return context.resources.getDimension(resId)
    }

    protected fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    protected fun applyFont(viewGroup: ViewGroup, fontRes: Int) {
        viewGroup.children.forEach { child ->
            if (child is TextView) {
                child.typeface = ResourcesCompat.getFont(context, fontRes)
            } else if (child is ViewGroup) {
                applyFont(child, fontRes)
            }
        }
    }
}