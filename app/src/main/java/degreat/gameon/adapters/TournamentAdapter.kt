package degreat.gameon.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import degreat.gameon.R
import degreat.gameon.models.Tournament
import degreat.gameon.TournamentDetail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_tournament.*
import java.util.*

class TournamentAdapter : RecyclerView.Adapter<TournamentAdapter.TViewHolder>() {

    private val tournaments = ArrayList<Tournament>()

    fun set(t: ArrayList<Tournament>) {
        tournaments.clear()
        tournaments.addAll(t)
        tournaments.sortWith(Comparator { t1, t2 -> t2.startOn.compareTo(t1.startOn) })

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TViewHolder, position: Int) {
        holder.bind(tournaments[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.view_tournament, parent, false)

        return TViewHolder(containerView)
    }

    override fun getItemCount(): Int = tournaments.size

    class TViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(tournament: Tournament) {
            tournament_title.text = "${tournament.title} •"

            containerView.setOnClickListener {
                containerView.context.startActivity(Intent(containerView.context,
                        TournamentDetail::class.java))
            }
        }
    }
}