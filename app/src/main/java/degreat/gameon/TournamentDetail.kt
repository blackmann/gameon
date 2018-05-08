package degreat.gameon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import degreat.gameon.adapters.ParticipantAdapter
import degreat.gameon.models.DB
import degreat.gameon.models.Participant
import degreat.gameon.models.Tournament
import kotlinx.android.synthetic.main.activity_tournament.*
import kotlinx.android.synthetic.main.dialog_add_participant.view.*
import kotlinx.android.synthetic.main.view_participant_menu.*
import kotlinx.android.synthetic.main.view_participant_menu.view.*

class TournamentDetail : AppCompatActivity(), ParticipantAdapter.ParticipantSelect {

    override fun onSelect(p: Participant) {
        selectedParticipant = p
        showActions()
    }

    private val adapter = ParticipantAdapter()

    private val db = DB()

    private var selectedParticipant: Participant? = null

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

        adapter.setOnSelect(this)

        score_board.adapter = adapter

        add_participant.setOnClickListener { showAddParticipantDialog() }

        actions.done.setOnClickListener {
            hideActions()
            adapter.arrange()
        }
        actions.delete.setOnClickListener { deleteParticipant() }
        actions.plus_one.setOnClickListener { plus(1) }
        actions.minus_one.setOnClickListener { plus(-1) }

        // by default hide actions
        hideActions()
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
        assemble()
    }

    private fun assemble() {
        val tournament = getTournamentFromIntent()

        if (tournament == null) finish()

        title = tournament!!.title

        adapter.set(tournament.participants.toList())
    }

    private fun showActions() {
        actions.visibility = View.VISIBLE
    }

    private fun hideActions() {
        actions.visibility = View.GONE
    }

    private fun plus(value: Int) {
        if (selectedParticipant == null) {
            return
        }
        when (value) {
            1 -> db.increasePoints(selectedParticipant!!)
            -1 -> db.decreasePoints(selectedParticipant!!)
        }

        adapter.notifyDataSetChanged()
    }

    private fun rename() {

    }

    private fun deleteParticipant() {
        if (selectedParticipant == null) {
            return
        }

        db.deleteParticipant(selectedParticipant!!)
        assemble()

        hideActions()
    }
}