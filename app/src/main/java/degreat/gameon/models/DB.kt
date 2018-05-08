package degreat.gameon.models

import io.realm.Realm


class DB {
    val tournaments = ArrayList<Tournament>()
    val rewards = ArrayList<Reward>()

    private val realm = Realm.getDefaultInstance()

    fun fetchTournaments(): ArrayList<Tournament> {
        tournaments.clear()
        val res = realm.where(Tournament::class.java)
                .findAll()

        tournaments.addAll(res.toList())

        return tournaments
    }

    fun fetchRewards(): ArrayList<Reward> {
        rewards.clear()
        val res = realm.where(Reward::class.java)
                .findAll()

        rewards.addAll(res.toList())

        return rewards
    }

    fun save(t: Tournament) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(t)
        realm.commitTransaction()

        if (!tournaments.any { it.id == t.id }) {
            tournaments.add(t)
        }
    }

    fun save(r: Reward) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(r)
        realm.commitTransaction()

        if (!rewards.any { it.id == r.id }) {
            rewards.add(r)
        }
    }

    fun rename(p: Participant, nn: String) {
        realm.beginTransaction()
        p.name = nn
        realm.copyToRealmOrUpdate(p)
        realm.commitTransaction()
    }

    fun getTournament(id: String): Tournament? {
        val t = tournaments.find { it.id == id }
        return t ?: realm.where(Tournament::class.java)
                .equalTo("id", id).findFirst()
    }

    fun addParticipant(t: String, p: Participant) {
        val tournament = getTournament(t)
        if (tournament != null) {
            realm.beginTransaction()
            tournament.participants.add(p)
            realm.commitTransaction()
        }
    }

    fun increasePoints(p: Participant) {
        realm.beginTransaction()
        p.points.increment(1)
        realm.commitTransaction()
    }

    fun decreasePoints(p: Participant) {
        realm.beginTransaction()
        p.points.decrement(1)
        realm.commitTransaction()
    }

    fun deleteParticipant(p: Participant) {
        realm.beginTransaction()
        p.deleteFromRealm()

        realm.commitTransaction()
    }
}