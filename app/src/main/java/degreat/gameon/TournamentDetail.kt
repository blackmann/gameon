package degreat.gameon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import degreat.gameon.adapters.ParticipantAdapter
import degreat.gameon.models.DB
import degreat.gameon.models.Participant
import degreat.gameon.models.Tournament
import kotlinx.android.synthetic.main.activity_tournament.*
import kotlinx.android.synthetic.main.dialog_add_participant.view.*

class TournamentDetail : AppCompatActivity() {

    private val adapter = ParticipantAdapter()

    private val db = DB()

    companion object {
        fun start(context: Context, id: String) {
            val intent = Intent(context, TournamentDetail::class.java)
            intent.putExtra("tournament_id", id)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        score_board.adapter = adapter

        add_participant.setOnClickListener { showAddParticipantDialog() }
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
                        val tid = intent.getStringExtra("tournament_id")
                        db.addParticipant(tid, Participant(name))
                        adapter.set(getTournamentFromIntent()?.participants
                                ?: return@setPositiveButton)
                    }
                })
                .create()

        dialog.show()
    }

    private fun getTournamentFromIntent(): Tournament? {
        return db.getTournament(intent.getStringExtra("tournament_id"))
    }

    override fun onResume() {
        super.onResume()
        val tournament = getTournamentFromIntent()

        if (tournament == null) finish()

        title = tournament!!.title

        adapter.set(tournament.participants.toList())
    }
}