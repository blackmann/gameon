package degreat.gameon

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import degreat.gameon.adapters.RewardAdapter
import degreat.gameon.models.Reward
import kotlinx.android.synthetic.main.dialog_add_reward.view.*
import kotlinx.android.synthetic.main.fragment_tournament.*

class RewardFragment : Fragment() {

    private val adapter = RewardAdapter()

    private val viewModel by lazy {
        ViewModelProviders.of(activity as Home)
                .get(Home.HomeVM::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tournament, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add.setOnClickListener { showAddTournamentDialog() }

        items.adapter = adapter

        viewModel.rewards.observe(this, Observer { adapter.set(it ?: arrayListOf()) })
    }

    private fun showAddTournamentDialog() {
        if (context == null) return

        val contentView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_add_reward, null, false)

        val dialog = AlertDialog.Builder(context!!)
                .setView(contentView)
                .setPositiveButton("Add", { d, _ ->
                    val title = contentView.participant_name.text.toString()
                    val reward = contentView.reward.text.toString()
                    if (!title.trim().isEmpty() && !reward.trim().isEmpty()) {
                        viewModel.addReward(Reward(title, reward))
                    }
                })
                .create()

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchRewards()
    }
}