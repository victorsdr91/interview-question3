package com.victorsanchez.exercise.persistence;

import com.victorsanchez.exercise.dto.QuestionDTO;
import com.victorsanchez.exercise.persistence.entity.QuestionEntity;
import com.victorsanchez.exercise.persistence.entity.ReplyEntity;
import com.victorsanchez.exercise.persistence.repository.QuestionRepository;
import com.victorsanchez.exercise.persistence.repository.ReplyRepository;
import com.victorsanchez.exercise.utils.QuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer of the Application which manage and stores the data of the application
 */
@Service
public class QuestionService {
     
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ReplyRepository replyRepository;

    /**
     * Returns a list of QuestionDTO that can be empty
     * @return List of QuestionDTO
     */
    public List<QuestionDTO> getAllQuestions()
    {
        List<QuestionDTO> questionList = new ArrayList<>();
        for(QuestionEntity question : questionRepository.findAll()) {
            questionList.add(QuestionUtils.parse(question));
        }
        return questionList;
    }

    /**
     * Given a question ID returns the requested QuestionDTO object
     * @param id - ID of the requested QuestionEntity
     * @return QuestionDTO
     * @throws RecordNotFoundException when the QuestionEntity linked is not found on the storage
     */
    public QuestionDTO getQuestionById(Long id) throws RecordNotFoundException
    {
        Optional<QuestionEntity> question = questionRepository.findById(id);
         
        if(question.isPresent()) {
            return QuestionUtils.parse(question.get());
        } else {
            throw new RecordNotFoundException("No question record exist for given id");
        }
    }

    /**
     * Given a QuestionEntity, persist it on the storage and returns a QuestionDTO object
     * @param entity - QuestionEntity to persist
     * @return QuestionDTO with the info of the stored QuestionEntity
     */
    public QuestionDTO createQuestion(QuestionEntity entity) {
        entity = questionRepository.save(entity);

        return QuestionUtils.parse(entity);
    }

    /**
     * Given a ReplyEntity, persist it on the storage and adds the reply to the linked Question which is also uptaded
     * @param entity - ReplyEntity to persist
     * @return ReplyEntity persisted on the storage
     * @throws RecordNotFoundException when the QuestionEntity linked is not found on the storage
     */
    public ReplyEntity createReplyToQuestion(ReplyEntity entity) throws RecordNotFoundException
    {
        entity = replyRepository.save(entity);
        Optional<QuestionEntity> question = questionRepository.findById(entity.getQuestionId());
        if(question.isPresent()) {
            QuestionEntity requestedQuestion = question.get();
            List<ReplyEntity> replyList = requestedQuestion.getReplies();
            replyList.add(entity);

            questionRepository.save(requestedQuestion);
        } else {
            throw new RecordNotFoundException("No question record exist for given id");
        }

        return entity;
    }

}
