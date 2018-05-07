package degreat.gameon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
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
        pages.setCurrentItem(page, false)

        when (page) {
            0 -> title = "Tournaments"
            1 -> title = "Rewards"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }
}