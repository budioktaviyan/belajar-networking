package id.kotlin.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.kotlin.networking.MainAdapter.MainViewHolder
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(private val results: List<Result>) : Adapter<MainViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
    MainViewHolder(
      LayoutInflater.from(parent.context).inflate(
        R.layout.item_main,
        parent,
        false
      )
    )

  override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
    holder.bind(results[holder.adapterPosition])
  }

  override fun getItemCount(): Int = results.size

  inner class MainViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(result: Result) {
      with(itemView) {
        tv_title.text = result.title
        tv_overview.text = result.overview
      }
    }
  }
}