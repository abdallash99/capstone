package com.capstone.project.result;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Result implements Serializable {
    @EmbeddedId
    private IdClass id;
    private String result;
    private int Score;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", result='" + result + '\'' +
                ", Score=" + Score +
                '}';
    }

    public IdClass getId() {
        return id;
    }

    public Result() {
    }

    public Result(IdClass id, String result,int score) {
        this.id = id;
        this.result = result;
    }

    public void setId(IdClass id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
