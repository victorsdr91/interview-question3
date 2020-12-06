package com.victorsanchez.exercise.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Superclass object witch can be used to generate new kind of question or reply objects
 */
@MappedSuperclass
public class MessageEntity {

    @Column(name="author")
    @Getter @Setter
    private String author;

    @Column(name="message", nullable=false)
    @Getter @Setter
    private String message;
}
