package degreat.gameon

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import degreat.gameon.adapters.ParticipantAdapter
import degreat.gameon.models.Participant
import kotlinx.android.synthetic.main.activity_tournament.*
import kotlinx.android.synthetic.main.dialog_add_participant.view.*

class TournamentDetail : AppCompatActivity() {

    private val adapter = ParticipantAdapter()
    private val ps = ArrayList<Participant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        score_board.adapter = adapter

        add_participant.setOnClickListener { showAddParticipantDialog() }

        // TODO set tournament name as title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddParticipantDialog() {
        val contentView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_add_participant, null, false)

        val dialog = AlertDialog.Builder(this)
                .setView(contentView)
                .setPositiveButton("Add", { _, _ ->
                    val name = contentView.participant_name.text.toString()
                    if (!name.isEmpty()) {
                        ps.add(Participant(name))
                        adapter.set(ps)
                    }
                })
                .create()

        dialog.show()
    }
}