package degreat.gameon.models;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Reward extends RealmObject {

    public String name = "";
    public String reward = "";
    public Date on = new Date();

    @PrimaryKey
    public String id = UUID.randomUUID().toString();

    public Reward() {

    }

    public Reward(String name, String reward) {
        this.name = name;
        this.reward = reward;
    }
}
