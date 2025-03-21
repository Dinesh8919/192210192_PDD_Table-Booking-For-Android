import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tablebooking.Booking
import com.example.tablebooking.R
import com.example.tablebooking.bookiinglistdata

class BookingListAdapter(
    private val bookingList: List<Booking> // List of bookings
) : RecyclerView.Adapter<BookingListAdapter.BookingViewHolder>() {

    // ViewHolder for the booking item
    inner class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookingIdTV: TextView = itemView.findViewById(R.id.bookingIdTV)
        val tableNoTV: TextView = itemView.findViewById(R.id.tableNoTV)
        val bookingDateTV: TextView = itemView.findViewById(R.id.bookingDateTV)
        val bookingTimeTV: TextView = itemView.findViewById(R.id.bookingTimeTV)
        val amountTV: TextView = itemView.findViewById(R.id.amountTV)
        val userNameTV: TextView = itemView.findViewById(R.id.userNameTV)
        val emailTV: TextView = itemView.findViewById(R.id.emailTV)
    }

    // Inflate the item layout and create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_rv_booking_list, parent, false)
        return BookingViewHolder(itemView)
    }

    // Bind data to the views in each item
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val currentItem = bookingList[position]

        holder.bookingIdTV.text = "Booking ID: ${currentItem.booking_id}"
        holder.tableNoTV.text = "Table No: ${currentItem.table_no}"
        holder.bookingDateTV.text = "Date: ${currentItem.booking_date}"
        holder.bookingTimeTV.text = "Time: ${currentItem.starting_time} - ${currentItem.ending_time}"
        holder.amountTV.text = "Amount: $${currentItem.amount}"
        holder.userNameTV.text = "User: ${currentItem.name}"
        holder.emailTV.text = "Email: ${currentItem.email}"
    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return bookingList.size
    }
}