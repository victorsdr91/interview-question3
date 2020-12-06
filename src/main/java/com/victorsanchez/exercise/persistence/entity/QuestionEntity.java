package com.victorsanchez.exercise.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


/**
 * Entity that defines a Question Object that can be stored. Extends from MessageEntity to inherit the author and message attributes
 */
@ToString
@Data
@Entity
@Table(name="TBL_Questions")
public class QuestionEntity extends MessageEntity {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @OneToMany
    @JoinColumn(name="questionId")
    @Getter @Setter
    private List<ReplyEntity> replies = new ArrayList<>();

}
