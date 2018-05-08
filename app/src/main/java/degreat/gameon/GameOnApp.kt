package degreat.gameon

import android.app.Application
import io.realm.Realm

class GameOnApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}