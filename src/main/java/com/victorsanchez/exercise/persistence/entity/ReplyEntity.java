package com.victorsanchez.exercise.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Object to store Replies on the storage
 */
@Data
@Entity
@Table(name="TBL_Replies")
public class ReplyEntity extends MessageEntity {
    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private Long questionId;
}
