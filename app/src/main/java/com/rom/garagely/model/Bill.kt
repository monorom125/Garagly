import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.rom.garagely.constant.Constant
import com.rom.garagely.model.BaseModel
import com.rom.garagely.model.Car
import com.rom.garagely.model.Discount
import com.rom.garagely.model.Key
import com.rom.garagely.model.Order
import com.rom.garagely.model.Sell
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import java.util.Date
import java.util.UUID

@Parcelize
data class Bill(
    override var id: String = UUID.randomUUID().toString(),
    var account_id: String? = null,
    var sell: Sell? = null,
    var discount: Discount? = null,
    var payDate: Date = Date(),
    var amount: Double? = 0.00,
    var change_amount: Double? = 0.00,
    var status: Status = Status.New

) : Parcelable, BaseModel() {


    override val pathName: String
        @Exclude get() = Constant.BILL

    @IgnoredOnParcel

    val orders
        @Exclude get() = sell?.orders ?: listOf()

//    @IgnoredOnParcel
//    val totalDiscount: Double
////        @Exclude get() = orders.sumOf { it.discountAmount }

    enum class Status {
        New, Paid
    }
}