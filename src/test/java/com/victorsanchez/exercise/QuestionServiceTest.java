package com.victorsanchez.exercise;


import com.victorsanchez.exercise.persistence.RecordNotFoundException;
import com.victorsanchez.exercise.dto.QuestionDTO;
import com.victorsanchez.exercise.persistence.entity.QuestionEntity;
import com.victorsanchez.exercise.persistence.repository.QuestionRepository;
import com.victorsanchez.exercise.persistence.QuestionService;
import com.victorsanchez.exercise.utils.QuestionUtils;
import com.victorsanchez.exercise.persistence.entity.ReplyEntity;
import com.victorsanchez.exercise.persistence.repository.ReplyRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * TestSuit for testing the QuestionService methods
 */
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionService service;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    ReplyRepository replyRepository;

    private static QuestionEntity mockedQuestion;
    private static QuestionDTO mockedQuestionDto;
    private static List<QuestionEntity> mockedQuestionList;
    private static ReplyEntity mockedReply;

    private final static Long MOCK_ID = 1L;
    private final static Long MOCK_REPLY_ID = 2L;

    @BeforeAll
    public static void init() {
        mockedQuestion = new QuestionEntity();
        mockedQuestion.setId(MOCK_ID);

        mockedQuestionDto = QuestionUtils.parse(mockedQuestion);

        mockedReply = new ReplyEntity();
        mockedReply.setQuestionId(MOCK_ID);
        mockedReply.setId(MOCK_REPLY_ID);

        mockedQuestion.getReplies().add(mockedReply);

        mockedQuestionList = new ArrayList<>();
        mockedQuestionList.add(mockedQuestion);
        mockedQuestionList.add(mockedQuestion);
    }

    /**
     * Scenario: getAllQuestions method returns a non-null list of questions
     */
    @Test
    public void getAllQuestionsReturnsAListOfQuestions() {
        when(questionRepository.findAll()).thenReturn(mockedQuestionList);
        Assertions.assertNotNull(service.getAllQuestions());
    }

    /**
     * Scenario: Given a question ID, getQuestionById method returns the requested question
     */
    @Test
    public void getQuestionByIdReturnsTheRequestedQuestion() throws RecordNotFoundException {
        when(questionRepository.findById(MOCK_ID)).thenReturn(Optional.ofNullable(mockedQuestion));
        Assertions.assertEquals(service.getQuestionById(MOCK_ID).getId(), mockedQuestion.getId());
    }

    /**
     * Scenario: Given a question ID, getQuestionById method throws a RecordNotFoundException
     * if the question is not found
     */
    @Test
    public void getQuestionByIdThrowsRecordNotFoundException() {
        Assertions.assertThrows(RecordNotFoundException.class, () -> service.getQuestionById(MOCK_ID));
    }

    /**
     * Scenario: Given a QuestionEntity object, createQuestion method stores the question and returns a DTO with the same ID
     */
    @Test
    public void createQuestionReturnsTheCreatedQuestion(){
        when(questionRepository.save(mockedQuestion)).thenReturn(mockedQuestion);
        Assertions.assertEquals(service.createQuestion(mockedQuestion).getId(), mockedQuestionDto.getId());
    }

    /**
     * Scenario: Given ReplyEntity object, createReply method stores the reply and returns the same object
     */
    @Test
    public void createReplyReturnsTheCreatedReply() throws RecordNotFoundException {
        when(replyRepository.save(mockedReply)).thenReturn(mockedReply);
        when(questionRepository.findById(mockedReply.getQuestionId())).thenReturn(Optional.ofNullable(mockedQuestion));
        Assertions.assertEquals(service.createReplyToQuestion(mockedReply), mockedReply);
    }

    /**
     * Scenario: Given a reply, createReply method throws a RecordNotFoundException
     * if the question attached to the reply is not found
     */
    @Test
    public void createReplyThrowsRecordNotFoundException() {
        when(replyRepository.save(mockedReply)).thenReturn(mockedReply);
        Assertions.assertThrows(RecordNotFoundException.class, () -> service.createReplyToQuestion(mockedReply));
    }
}
