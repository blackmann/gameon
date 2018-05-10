package degreat.gameon

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import degreat.gameon.arch.SingleLiveEvent
import degreat.gameon.models.DB
import degreat.gameon.models.Reward
import degreat.gameon.models.Tournament
import kotlinx.android.synthetic.main.activity_main.*

class Home : AppCompatActivity() {

    private val tournamentFragment = TournamentFragment()
    private val rewardFragment = RewardFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pages.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment =
                    when (position) {
                        0 -> tournamentFragment
                        else -> rewardFragment
                    }

            override fun getCount(): Int = 2
        }

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tournament -> showPage(0)
                R.id.reward -> showPage(1)
            }

            return@setOnNavigationItemSelectedListener true
        }


    }

    private fun showPage(page: Int) {
        pages.currentItem = page

        when (page) {
            0 -> title = "Tournaments"
            1 -> title = "Rewards"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> startActivity(Intent(this, About::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    class HomeVM : ViewModel() {

        private val db = DB()

        // events
        val showLoadingRewards = SingleLiveEvent<Boolean>()
        val showLoadingTs = SingleLiveEvent<Boolean>()

        val tournaments = MutableLiveData<ArrayList<Tournament>>()
        val rewards = MutableLiveData<ArrayList<Reward>>()

        fun fetchTournaments() {
            showLoadingTs.value = true
            tournaments.value = db.fetchTournaments()
            showLoadingTs.value = false
        }

        fun fetchRewards() {
            showLoadingRewards.value = true
            rewards.value = db.fetchRewards()
            showLoadingRewards.value = false
        }

        fun addTournament(t: Tournament) {
            db.save(t)
            tournaments.value = db.tournaments
        }

        fun addReward(r: Reward) {
            db.save(r)
            rewards.value = db.rewards
        }
    }
}