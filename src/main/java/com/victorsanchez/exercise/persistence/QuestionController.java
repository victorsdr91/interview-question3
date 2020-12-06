package com.victorsanchez.exercise.persistence;

import com.victorsanchez.exercise.dto.QuestionDTO;
import com.victorsanchez.exercise.persistence.entity.QuestionEntity;
import com.victorsanchez.exercise.persistence.entity.ReplyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Controller that serves different APIs to be consumed
 *  GET /questions - returns a list of questions stored
 *  POST /questions - creates a new question
 *  GET /questions/{id} - returns the requested question
 *  POST /questions/{id}/reply - creates a reply for the question id
 */
@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionService service;

    /**
     * Returns the list of the questions stored
     * @return List of QuestionDTO which can be empty
     */
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> list = service.getAllQuestions();
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Given a Question ID as param, returns the question if founded
     * @param id of the question to be returned
     * @return JSON info with the question information and 200 Status
     * @throws RecordNotFoundException if the question is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        QuestionDTO dto = service.getQuestionById(id);
        return new ResponseEntity<>(dto, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Given a question, sends it to the service layer to store it.
     * @param question Question Entity object to be stored
     * @return QuestionDTO if is correctly stored and 201 Status
     */
    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionEntity question) {
        QuestionDTO updated = service.createQuestion(question);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Given a question id and a reply object to be stored, sends it to the service layer to store it.
     * @param questionId - ID of the parent question
     * @param reply - ReplyEntity with the information to be stored
     * @return ReplyEntity object stored and 201 Status
     * @throws RecordNotFoundException is thrown when the question attached to the reply is not found
     */
    @PostMapping("/{id}/reply")
    public ResponseEntity<ReplyEntity> createReply(@PathVariable("id") Long questionId, @RequestBody ReplyEntity reply)
            throws RecordNotFoundException {
        reply.setQuestionId(questionId);
        ReplyEntity updated = service.createReplyToQuestion(reply);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.CREATED);
    }


}
