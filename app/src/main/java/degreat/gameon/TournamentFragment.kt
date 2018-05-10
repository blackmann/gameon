package degreat.gameon

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import degreat.gameon.adapters.TournamentAdapter
import degreat.gameon.models.Tournament
import kotlinx.android.synthetic.main.dialog_add_tournament.view.*
import kotlinx.android.synthetic.main.fragment_tournament.*

class TournamentFragment : Fragment(), TournamentAdapter.OnTournamentAction {
    override fun onDelete(t: Tournament) {
        viewModel.deleteTournament(t)
    }

    private val adapter = TournamentAdapter().apply {
        setOnTournamentAction(this@TournamentFragment)
    }

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
        items.adapter = adapter

        add.setOnClickListener { showAddTournamentDialog() }

        viewModel.tournaments.observe(this, Observer { adapter.set(it ?: arrayListOf()) })
    }


    private fun showAddTournamentDialog() {
        if (context == null) return

        val contentView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_add_tournament, null, false)

        val dialog = AlertDialog.Builder(context!!)
                .setView(contentView)
                .setPositiveButton("Create", { d, _ ->
                    val title = contentView.tournament_title.text.toString()
                    if (!title.trim().isEmpty()) {
                        viewModel.addTournament(Tournament(title))
                    }
                })
                .create()

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTournaments()
    }
}