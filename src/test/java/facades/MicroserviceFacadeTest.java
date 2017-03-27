package facades;

import com.quantal.quantalmicroservicetemplate.MicroserviceApplication;
import com.quantal.quantalmicroservicetemplate.dto.ResponseDTO;
import com.quantal.quantalmicroservicetemplate.dto.MicroserviceDto;
import com.quantal.quantalmicroservicetemplate.enums.Gender;
import com.quantal.quantalmicroservicetemplate.models.MicroserviceModel;
import com.quantal.quantalmicroservicetemplate.services.api.GiphyApiService;
import com.quantal.quantalmicroservicetemplate.services.interfaces.MicroserviceService;
import com.quantal.quantalmicroservicetemplate.facades.MicroserviceFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@SpringBootTest(classes = {MicroserviceApplication.class})
@AutoConfigureMockMvc
public class MicroserviceFacadeTest {

    @MockBean
    private GiphyApiService giphyApiService;
    @MockBean
    private MicroserviceService microserviceService;


    @Autowired
   // @InjectMocks
    private MicroserviceFacade microserviceFacade;

    @Before
    public void setUp(){
     //microserviceFacade = new UserManagementFacade(microserviceService, giphyApiService);
    }

    @Test
    public void shouldUpdateUserWithPartialData() throws Exception {

        MicroserviceModel persistedModel = new MicroserviceModel();
        MicroserviceModel updateModel = new MicroserviceModel();
        MicroserviceDto updateDto = new MicroserviceDto();

        String persistedModelFirstName =  "persistedDtoFirstName";
        String persistedModelLastName = "persistedDtoLastName";
        String persistedModelEmail = "persistedModel@quant.com";
        String persistedModelPassword = "persistedDtoPassword";
        String updateDtoFirstName = "updatedFirstName";
        String updateDtoLastName = "updatedLastName";
        String updateDtoEmail = "updateModel@quant.com";
        Long id = 1L;

        persistedModel.setId(id);
        persistedModel.setFirstName(persistedModelFirstName);
        persistedModel.setLastName(persistedModelLastName);
        persistedModel.setEmail(persistedModelEmail);
        persistedModel.setGender(Gender.M);
        persistedModel.setPassword(persistedModelPassword);

        updateModel.setId(id);
        updateModel.setFirstName(updateDtoFirstName);
        updateModel.setLastName(updateDtoLastName);
        updateModel.setEmail(updateDtoEmail);

        updateDto.setId(id);
        updateDto.setFirstName(updateDtoFirstName);
        updateDto.setLastName(updateDtoLastName);
        updateDto.setEmail(updateDtoEmail);

        given(this.microserviceService
                .findOneByEmail(updateModel.getEmail()))
                .willReturn(persistedModel);

        given(this.microserviceService
                .saveOrUpdate(eq(updateModel)))
                .willReturn(persistedModel);

        MicroserviceDto result = (MicroserviceDto)((ResponseDTO) microserviceFacade.update(updateDto).getBody()).getData();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getFirstName()).isEqualTo(updateDtoFirstName);
        assertThat(result.getLastName()).isEqualTo(updateDtoLastName);
        assertThat(result.getEmail()).isEqualTo(updateDtoEmail);
        assertThat(result.getPassword()).isEqualTo(persistedModelPassword);
        assertThat(result.getGender()).isEqualTo(Gender.M);

        verify(microserviceService, times(1)).findOneByEmail(updateModel.getEmail());
        verify(microserviceService, times(1)).saveOrUpdate(eq(updateModel));
    }

}
