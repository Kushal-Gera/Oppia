package kushal.application.recommender.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kushal.application.recommender.R
import kushal.application.recommender.ViewHolders.Home_viewHolder


class Home_adapter(val list: Array<Int>) : RecyclerView.Adapter<Home_viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Home_viewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_rec_view, parent, false)
        return Home_viewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Home_viewHolder, position: Int) {
        holder.image.setImageResource(list[position])
    }


}