package com.penguin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sentences")
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mandarin_sentence")
    private String mandarinSentence;

    public Sentence() {
    }

    public Sentence(String mandarinSentence) {
        this.mandarinSentence = mandarinSentence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMandarinSentence() {
        return mandarinSentence;
    }

    public void setMandarinSentence(String mandarinSentence) {
        this.mandarinSentence = mandarinSentence;
    }

}
