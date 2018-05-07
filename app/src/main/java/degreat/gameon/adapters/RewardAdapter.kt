package degreat.gameon.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import degreat.gameon.R
import degreat.gameon.models.Reward
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_reward.view.*

class RewardAdapter : RecyclerView.Adapter<RewardAdapter.RViewHolder>() {

    private val rewards = ArrayList<Reward>()

    fun set(rs: ArrayList<Reward>) {
        rewards.clear()
        rewards.addAll(rs)

        rewards.sortWith(Comparator { r1, r2 -> r2.on.compareTo(r1.on) })

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = rewards.size


    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.bind(rewards[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val contentView = layoutInflater.inflate(R.layout.view_reward, parent, false)

        return RViewHolder(contentView)
    }


    class RViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(reward: Reward) {
            containerView.reward.text = reward.reward
            containerView.participant_name.text = reward.name
        }
    }
}