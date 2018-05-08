package degreat.gameon.models;

import java.util.UUID;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Participant extends RealmObject {

    public String name = "";
    public final MutableRealmInteger points = MutableRealmInteger.valueOf(0);

    @PrimaryKey
    public String id = UUID.randomUUID().toString();

    public Participant() {

    }

    public Participant(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points.get().intValue();
    }
}
