package com.quantal.telephones.facades;

import com.quantal.javashared.util.TestUtil;
import com.quantal.telephones.dto.TelephoneDto;
import com.quantal.telephones.enums.TelephoneNumType;
import com.quantal.telephones.models.Telephone;
import com.quantal.telephones.services.api.GiphyApiService;
import com.quantal.telephones.services.interfaces.TelephonesService;
import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dman on 25/03/2017.
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(UserManagementFacade.class)
@SpringBootTest
//@AutoConfigureMockMvc
public class TelephonesFacadeTest {

    @MockBean
    private GiphyApiService giphyApiService;
    @MockBean
    private TelephonesService telephonesService;


    @Autowired
   // @InjectMocks
    private TelephonesFacade telephonesFacade;

    @Before
    public void setUp(){
     //microserviceFacade = new UserManagementFacade(telephonesService, giphyApiService);
    }

    @Test
    public void shouldUpdateUserWithPartialData() throws Exception {

    }

}
