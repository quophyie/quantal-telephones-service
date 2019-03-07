package com.quantal.telephones.facades;

import com.quantal.telephones.services.api.GiphyApiService;
import com.quantal.telephones.services.interfaces.TelephonesService;
import de.invesdwin.instrument.DynamicInstrumentationLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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

    @BeforeClass
    public static void beforeClass(){
        //Starts the aspectj weaver so that we can weave the compile time aspects
        DynamicInstrumentationLoader.waitForInitialized(); //dynamically attach java agent to jvm if not already present
        DynamicInstrumentationLoader.initLoadTimeWeavingContext(); //weave all classes before they are loaded as beans
    }

    @Before
    public void setUp(){
     //microserviceFacade = new UserManagementFacade(telephonesService, giphyApiService);
    }

    @Test
    public void shouldUpdateUserWithPartialData() throws Exception {

    }

}
