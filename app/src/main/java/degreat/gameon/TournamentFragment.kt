package degreat.gameon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import degreat.gameon.adapters.TournamentAdapter
import degreat.gameon.models.Tournament
import kotlinx.android.synthetic.main.dialog_add_tournament.view.*
import kotlinx.android.synthetic.main.fragment_tournament.*

class TournamentFragment : Fragment() {

    private val adapter = TournamentAdapter()
    private val ts = arrayListOf(Tournament("Big Match 3"), Tournament("Big Match 5"))

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tournament, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        items.adapter = adapter

        adapter.set(ts)

        add.setOnClickListener { showAddTournamentDialog() }
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
                        ts.add(Tournament(title))
                        adapter.set(ts)
                    }
                })
                .create()

        dialog.show()
    }
}