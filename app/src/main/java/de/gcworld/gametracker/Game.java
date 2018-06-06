package de.gcworld.gametracker;


import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Game {
    @Id
    public long id;

    String title;
    String platform;
    boolean started, storycompleted, allachievements, m100percent, owned, want;
    Date addedDate, startedDate, completedDate, achievementsDate, m100percentDate;

    public Game(){
    }

    public Game(String title, String platform, boolean started, boolean storycompleted, boolean allachievements, boolean m100percent, boolean owned, boolean want){
        this.title = title;
        this.platform = platform;
        this.started = started;
        this.storycompleted = storycompleted;
        this.allachievements = allachievements;
        this.m100percent = m100percent;
        this.owned = owned;
        this.want = want;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean getOwned() {
        return this.owned;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }
    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

}
