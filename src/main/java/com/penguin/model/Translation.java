package com.penguin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "translations")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sentence1_id")
    private Sentence sentence1;

    @OneToOne
    @JoinColumn(name = "sentence2_id")
    private Sentence sentence2;

    @Column(name = "comments")
    private String comments;

    public Translation() {
    }

    public Translation(Sentence sentence1, Sentence sentence2) {
        this.sentence1 = sentence1;
        this.sentence2 = sentence2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sentence getSentence1() {
        return sentence1;
    }

    public void setSentence1(Sentence sentence1) {
        this.sentence1 = sentence1;
    }

    public Sentence getSentence2() {
        return sentence2;
    }

    public void setSentence2(Sentence sentence2) {
        this.sentence2 = sentence2;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
