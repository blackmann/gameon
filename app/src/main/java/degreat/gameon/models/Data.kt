package degreat.gameon.models

import java.util.*
import kotlin.collections.ArrayList

class Tournament(val title: String,
                 val startOn: Date = Date(),
                 val participants: ArrayList<Participant> = ArrayList())

class Participant(val name: String,
                  var points: Int = 0)

class Reward(val name: String,
             val reward: String)