package com.quantal.telephones.controllers;

import com.quantal.javashared.dto.CommonLogFields;
import com.quantal.javashared.dto.LoggerConfig;
import com.quantal.javashared.dto.ResponseDto;
import com.quantal.javashared.logger.QuantalLoggerFactory;
import com.quantal.javashared.services.interfaces.MessageService;
import com.quantal.javashared.util.TestUtil;
import com.quantal.telephones.controlleradvice.ExceptionHandlerControllerAdvice;
import com.quantal.telephones.dto.TelephoneDto;
import com.quantal.telephones.enums.TelephoneNumType;
import com.quantal.telephones.facades.TelephonesFacade;
import com.quantal.telephones.models.Telephone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonPartMatches;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by dman on 24/03/2017.
 */

@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(MicroserviceController.class)
//@DataJpaTest
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MicroserviceApplication.class)
public class TelephonesControllerTests {

   // @Autowired
    private MockMvc mvc;

    private final String TELEPHONE_NUM = "+233 244 65 89 09";

    private final long ID = 1234L;


    private TelephonesController telephonesController;

    @MockBean
    private TelephonesFacade telephonesFacade;


    @MockBean
    private MessageService messageService;
    @Before
    public void setUp() {

        telephonesController = new TelephonesController(telephonesFacade);

        ReflectionTestUtils.setField(telephonesController, "logger", QuantalLoggerFactory.getLogger(TelephonesController.class, LoggerConfig.builder()
                .commonLogFields(new CommonLogFields())
                .build()));
        ExceptionHandlerControllerAdvice exceptionHandlerControllerAdvice = new ExceptionHandlerControllerAdvice(messageService);
        this.mvc = MockMvcBuilders.standaloneSetup(telephonesController)
                .setControllerAdvice(exceptionHandlerControllerAdvice)
                .build();
    }



    @Test
    public void shouldGetGiphyGivenQuery() throws Exception {

        String jsonResult = "{\"data\":[{\"type\":\"gif\",\"id\":\"jTnGaiuxvvDNK\",\"slug\":\"funny-cat-jTnGaiuxvvDNK\",\"url\":\"http:\\/\\/giphy.com\\/gifs\\/funny-cat-jTnGaiuxvvDNK\",\"bitly_gif_url\":\"http:\\/\\/gph.is\\/2cKVPVQ\"}]}";
        given(this.telephonesFacade.getFunnyCat())
                .willReturn(CompletableFuture.completedFuture(jsonResult));


        MvcResult asyscResult = this.mvc.perform(get("/telephones").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        mvc.perform(asyncDispatch(asyscResult))
            .andExpect(status().isOk())
            .andExpect(
                json()
                    .node("data")
                    .isArray())
            .andExpect(
                json()
                    .node("data")
                    .matches(hasItem(jsonPartMatches("type", equalTo("gif")))));


    }

    @Test
    public  void shouldCreateNewTelephoneNumberGivenValidTelephineData() throws Exception {

        TelephoneDto telephoneDto = TelephoneDto.builder()
                .telephoneNum(TELEPHONE_NUM)
                .type(TelephoneNumType.Mobile)
                .build();
        given(this.telephonesFacade.saveOrUpdateTelephone(telephoneDto))
        .willAnswer(invocationOnMock -> {
            TelephoneDto savedTelephoneDto = TelephoneDto.builder()
                    .telephoneNum(TELEPHONE_NUM)
                    .id(ID)
                    .type(TelephoneNumType.Mobile)
                    .build();


            ResponseDto<TelephoneDto> telephoneRespone = new ResponseDto<>(savedTelephoneDto);



            return CompletableFuture.completedFuture(new ResponseEntity(telephoneRespone, HttpStatus.CREATED));
        });


        MvcResult asyncResult = this.mvc.perform(post("/telephones")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtil.convertObjectToJsonString(telephoneDto))
        ).andReturn();

        mvc.perform(asyncDispatch(asyncResult))
                .andExpect(status().isCreated())
                .andExpect(
                        json()
                                .node("data")
                                .isObject())
                .andExpect(
                        json()
                                .node("data.telephoneNum").isEqualTo(TELEPHONE_NUM))
                .andExpect( json()
                        .node("data.type").isEqualTo(TelephoneNumType.Mobile.toString()));
    }

    @Test
    public  void shouldRetrieveTelephoneNumberGivenValidTelephoneId() throws Exception {


        given(this.telephonesFacade.findTelephoneById(ID))
                .willAnswer(invocationOnMock -> {
                    TelephoneDto telephoneDto = TelephoneDto.builder()
                            .telephoneNum(TELEPHONE_NUM)
                            .id(ID)
                            .type(TelephoneNumType.Mobile)
                            .build();
                    ResponseDto<TelephoneDto> telephoneRespone = new ResponseDto<>(telephoneDto);
                    return CompletableFuture.completedFuture(new ResponseEntity(telephoneRespone, HttpStatus.CREATED));
                });


        MvcResult asyncResult = this.mvc.perform(get("/telephones/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        mvc.perform(asyncDispatch(asyncResult))
                .andExpect(status().isCreated())
                .andExpect(
                        json()
                                .node("data")
                                .isObject())
                .andExpect(
                        json()
                                .node("data.telephoneNum").isEqualTo(TELEPHONE_NUM))
                .andExpect( json()
                        .node("data.type").isEqualTo(TelephoneNumType.Mobile.toString()));
    }


    @Test
    public  void shouldUpdateTelephoneNumberGivenValidTelephoneId() throws Exception {

        TelephoneDto telephoneDto = TelephoneDto.builder()
                .telephoneNum(TELEPHONE_NUM)
                .type(TelephoneNumType.Mobile)
                .build();
        given(this.telephonesFacade.update(telephoneDto))
                .willAnswer(invocationOnMock -> {
                    TelephoneDto updatedTelephone = TelephoneDto.builder()
                            .telephoneNum(TELEPHONE_NUM)
                            .id(ID)
                            .type(TelephoneNumType.Mobile)
                            .build();


                    ResponseDto<TelephoneDto> telephoneRespone = new ResponseDto<>(updatedTelephone);



                    return CompletableFuture.completedFuture(new ResponseEntity(telephoneRespone, HttpStatus.CREATED));
                });


        MvcResult asyncResult = this.mvc.perform(patch("/telephones/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtil.convertObjectToJsonString(telephoneDto))
        ).andReturn();

        mvc.perform(asyncDispatch(asyncResult))
                .andExpect(status().isCreated())
                .andExpect(
                        json()
                                .node("data")
                                .isObject())
                .andExpect(
                        json()
                                .node("data.telephoneNum").isEqualTo(TELEPHONE_NUM))
                .andExpect( json()
                        .node("data.type").isEqualTo(TelephoneNumType.Mobile.toString()));
    }
}

