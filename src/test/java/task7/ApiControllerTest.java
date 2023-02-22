package task7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import task7.dto.PostDataJson;
import task7.serviceTest.MeterGroupService;
import task7.serviceTest.MeterReadingService;
import task7.serviceTest.MeterService;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@ContextConfiguration
@TestExecutionListeners({
     DependencyInjectionTestExecutionListener.class,
     DbUnitTestExecutionListener.class})
@DatabaseSetup("meterDataTest.xml")
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MeterService meterService;
    @Autowired
    private MeterGroupService meterGroupService;
    @Autowired
    private MeterReadingService meterReadingService;

    public ApiControllerTest() {
    }

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
    }

    @Test
    public void postReading() throws JsonProcessingException, Exception {
        PostDataJson postDataJson = new PostDataJson(
             10L, "type10", "group10", new Timestamp(2023, 1, 1, 1, 30, 0, 0), 15);

        ResultActions response = mockMvc.perform(post("/api/v1.0/example")
             .contentType(MediaType.APPLICATION_JSON_VALUE)
             .content(objectMapper.writeValueAsString(postDataJson)));

        response.andExpect(status().isOk())
             .andDo(print());
    }

    @Test
}
