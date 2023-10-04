package com.sad.t5t1.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Match_Result")
public class Match_Result {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    String Opponent;
    String FileId;


    public Match_Result() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOpponent() {
        return Opponent;
    }
    public void setOpponent(String opponent) {
        Opponent = opponent;
    }
    public String getFileId() {
        return FileId;
    }
    public void setFileId(String fileId) {
        FileId = fileId;
    }
}
