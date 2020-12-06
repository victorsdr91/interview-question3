package com.victorsanchez.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsanchez.exercise.persistence.QuestionController;
import com.victorsanchez.exercise.dto.QuestionDTO;
import com.victorsanchez.exercise.persistence.entity.QuestionEntity;
import com.victorsanchez.exercise.persistence.QuestionService;
import com.victorsanchez.exercise.persistence.entity.ReplyEntity;

import com.victorsanchez.exercise.utils.QuestionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

/**
 * TestSuit for testing the QuestionController methods
 */
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService service;

    private static QuestionDTO mockedQuestionDto;
    private static QuestionEntity mockedQuestion;
    private static List<QuestionDTO> mockedQuestionList;
    private static ReplyEntity mockedReply;

    private final static Long MOCK_ID = 1L;
    private final static Long MOCK_REPLY_ID = 2L;

    @BeforeAll
    public static void init() {
        mockedQuestionDto = new QuestionDTO();
        mockedQuestionDto.setId(MOCK_ID);

        mockedReply = new ReplyEntity();
        mockedReply.setQuestionId(MOCK_ID);
        mockedReply.setId(MOCK_REPLY_ID);

        mockedQuestionDto.setReplies(new ArrayList<>());

        ((ArrayList) mockedQuestionDto.getReplies()).add(mockedReply);

        mockedQuestion = QuestionUtils.parse(mockedQuestionDto);

        mockedQuestionList = new ArrayList<>();
        mockedQuestionList.add(mockedQuestionDto);
        mockedQuestionList.add(mockedQuestionDto);
    }


    /**
     * Scenario: Making a POST Call to /questions path with a JSON of a question answers with the same object and 201 Status
     * @throws Exception
     */
    @Test
    public void createQuestionReturnsTheCreatedQuestion() throws Exception {
        when(service.createQuestion(mockedQuestion)).thenReturn(mockedQuestionDto);

        this.mockMvc.perform(post("/questions")
                    .content(asJsonString(mockedQuestion))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    /**
     * Scenario: Making a POST Call to /questions/{id}/reply path with a JSON of a reply answers with the same object and 201 Status
     * @throws Exception
     */
    @Test
    public void createReplyReturnsTheReply() throws Exception {
        when(service.createReplyToQuestion(mockedReply)).thenReturn(mockedReply);

        this.mockMvc.perform(post("/questions/".concat(String.valueOf(MOCK_ID)).concat("/reply"))
                .content(asJsonString(mockedReply))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionId").exists());
    }

    /**
     * Scenario: Making a GET Call to /questions returns a JSON object with the list of questions stored.
     * Can be empty.
     * @throws Exception
     */
    @Test
    public void getAllQuestionsReturnsAListOfQuestions() throws Exception {
        when(service.getAllQuestions()).thenReturn(mockedQuestionList);

        this.mockMvc.perform(get("/questions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(mockedQuestionList)));
    }

    /**
     * Scenario: Making a GET Call to /questions/{id} returns a JSON object with the requested question.
     * @throws Exception
     */
    @Test
    public void givenAQuestionIdReturnsTheQuestion() throws Exception {
        when(service.getQuestionById(MOCK_ID)).thenReturn(mockedQuestionDto);

        this.mockMvc.perform(get("/questions/".concat(String.valueOf(MOCK_ID))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(mockedQuestionDto)));
    }

    /**
     * Method to parse an object to JSON format
     * @param obj - Object to be parsed
     * @return String with JSON format
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
