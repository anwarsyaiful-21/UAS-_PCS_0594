package pcs.uas_0594.design.event

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pcs.uas_0594.R
import pcs.uas_0594.data.model.Event
import java.text.SimpleDateFormat

class EventAdapter(private val context: Context, private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = events.size
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tv_skor: TextView = view.findViewById(R.id.tv_skor)
        val tv_date: TextView = view.findViewById(R.id.tv_date)
        val tv_home: TextView = view.findViewById(R.id.tv_home)
        val tv_away: TextView = view.findViewById(R.id.tv_away)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = events[position]

        if(data.scoreHome.isNullOrEmpty() && data.scoreAway.isNullOrEmpty()){
            holder.tv_skor.text = "VS"
        }else{
            holder.tv_skor.text = data.scoreHome+" VS "+data.scoreAway
        }
        var tanggalBaru = SimpleDateFormat("EEE, d MMM yyyy")
                .format(SimpleDateFormat("yyyy-MM-dd")
                        .parse(data.eventDate))
        holder.tv_date.text = tanggalBaru
        holder.tv_home.text = data.teamHome
        holder.tv_away.text = data.teamAway
        holder.itemView.setOnClickListener { listener(data) }
    }

}
