package degreat.gameon.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marlonlom.utilities.timeago.TimeAgo
import degreat.gameon.R
import degreat.gameon.TournamentDetail
import degreat.gameon.models.Tournament
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_tournament.view.*
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

    inner class TViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(tournament: Tournament) {
            containerView.tournament_title.text = "${tournament.title} •"
            containerView.ago.text = "  ${TimeAgo.using(tournament.startOn.time)}"

            containerView.setOnClickListener {
                TournamentDetail.start(containerView.context, tournament.id)
            }
        }
    }
}