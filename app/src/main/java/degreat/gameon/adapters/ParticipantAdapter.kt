package degreat.gameon.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import degreat.gameon.R
import degreat.gameon.models.Participant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_participant.view.*

class ParticipantAdapter: RecyclerView.Adapter<ParticipantAdapter.PViewHolder>() {

    private val participants = ArrayList<Participant>()

    fun set(ps: List<Participant>) {
        participants.clear()
        participants.addAll(ps)

        participants.sortWith(Comparator { t1, t2 -> t1.getPoints() - t2.getPoints()})

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = participants.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val contentView = inflater.inflate(R.layout.view_participant, parent, false)

        return PViewHolder(contentView)
    }

    override fun onBindViewHolder(holder: PViewHolder, position: Int) {
        holder.bind(participants[position], position+1)
    }

    class PViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(participant: Participant, position: Int) {
            containerView.participant_name.text = "$position      ${participant.name}"
            containerView.points.text = participant.points.get().toString()
        }
    }
}