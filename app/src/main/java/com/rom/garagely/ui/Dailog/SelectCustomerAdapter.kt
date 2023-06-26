
import BaseViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rom.garagely.R
import com.rom.garagely.databinding.SelectCustomerListItemBinding
import com.rom.garagely.model.Client
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

class SelectCustomerAdapter(private val delegate: Delegate) :
    BaseRecyclerViewAdapter<Client, SelectCustomerAdapter.ItemViewHolder>() {

    var selectedCustomer: Client? = null

    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(SelectCustomerListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindItemHolder(holder: ItemViewHolder, position: Int, context: Context) {
        holder.bind(items[position])
    }

    inner class ItemViewHolder(itemBinding: SelectCustomerListItemBinding) :
        BaseViewHolder<SelectCustomerListItemBinding>(itemBinding) {

        fun bind(customer: Client) {
            with(binding) {
                if (customer.id == selectedCustomer?.id) {
                    root.setBackgroundColor(getColor(R.color.line_divider_view))
                } else {
                    root.setBackgroundColor(getColor(R.color.white))
                }
                textViewCustomerName.text = customer.first_name + customer.last_name
                textViewCustomerNumber.text = customer.phone_number ?: ""
                loadGuestImage(customer)
                root.setOnClickListener {
                    delegate.onCustomerSelected(customer)
                    root.setBackgroundColor(getColor(R.color.line_divider_view))
                    val newPosition = selectedCustomer?.let {
                        items.indexOf(it)
                    }
                    selectedCustomer = customer
                    newPosition?.let {
                        notifyItemChanged(it)
                    }
                }
            }
        }

        private fun loadGuestImage(customer: Client) {
            with(binding.imageViewCustomer) {
                Glide.with(this).load(customer.image).placeholder(R.drawable.ic_image_guest)
                    .circleCrop().into(this)
            }
        }
    }

    interface Delegate {
        fun onCustomerSelected(customer: Client)
    }

}