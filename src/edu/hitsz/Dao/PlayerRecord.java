package edu.hitsz.Dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerRecord implements Serializable {
    private int score;
    private String userName;
    private String localDateTime;



    public PlayerRecord(){
        this.userName = "testusername";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        localDateTime = LocalDateTime.now().format(formatter);
    }
    public PlayerRecord(String userName, int score){
        this.userName = userName;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        localDateTime = LocalDateTime.now().format(formatter);
        this.setScore(score);
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }
}
