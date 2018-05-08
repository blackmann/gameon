package degreat.gameon.models;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tournament extends RealmObject {

    public String title = "";
    public Date startOn = new Date();
    public RealmList<Participant> participants = new RealmList<>();

    @PrimaryKey
    public String id = UUID.randomUUID().toString();

    public Tournament() {

    }

    public Tournament(String title) {
        this.title = title;
    }
}
