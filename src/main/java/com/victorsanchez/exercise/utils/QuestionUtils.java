package com.victorsanchez.exercise.utils;

import com.victorsanchez.exercise.dto.QuestionDTO;
import com.victorsanchez.exercise.persistence.entity.QuestionEntity;
import com.victorsanchez.exercise.persistence.entity.ReplyEntity;

import java.util.List;
import java.util.Optional;

/**
 * Utils class which contains methods that can be used for parsing between question data transfer objects to question entities and vice versa
 */
public class QuestionUtils {

    /**
     * Given a QuestionDTO object, parses it to a QuestionEntity object
     * @param questionDto - QuestionDTO object to be parsed
     * @return QuestionEntity
     */
    public static QuestionEntity parse (QuestionDTO questionDto) {
        QuestionEntity questionEntity= new QuestionEntity();
        Optional<Long> id = Optional.ofNullable(questionDto.getId());
        if(id.isPresent()) {
            questionEntity.setId(questionDto.getId());
        }
        questionEntity.setId(questionDto.getId());
        questionEntity.setAuthor(questionDto.getAuthor());
        questionEntity.setMessage(questionDto.getMessage());
        if(questionDto instanceof List){
            questionEntity.setReplies((List<ReplyEntity>)questionDto.getReplies());
        }

        return questionEntity;
    }

    /**
     * Given a QuestionEntity object, parses it to a QuestionDTO object
     * @param questionEntity - QuestionEntity object to be parsed
     * @return QuestionDTO
     */
    public static QuestionDTO parse (QuestionEntity questionEntity) {
        QuestionDTO questionDto= new QuestionDTO();
        Optional<Long> id = Optional.ofNullable(questionEntity.getId());
        if(id.isPresent()) {
            questionDto.setId(questionEntity.getId());
        }
        questionDto.setAuthor(questionEntity.getAuthor());
        questionDto.setMessage(questionEntity.getMessage());
        if(questionEntity.getReplies().size() != 0) {
            List<ReplyEntity> replies = questionEntity.getReplies();
            questionDto.setReplies(replies);
        }

        return questionDto;
    }

}
