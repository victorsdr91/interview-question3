package com.victorsanchez.exercise.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfer Object to work with between web layer and service layer
 */
@ToString
@Data
public class QuestionDTO {

    @Getter @Setter
    private String author;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private Object replies = 0;
}
