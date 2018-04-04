package com.quantal.telephones.facades;

import com.quantal.javashared.annotations.logger.InjectLogger;
import com.quantal.javashared.dto.LogEvent;
import com.quantal.javashared.dto.LogTraceId;
import com.quantal.javashared.exceptions.NotFoundException;
import com.quantal.javashared.facades.AbstractBaseFacade;
import com.quantal.javashared.logger.QuantalLogger;
import com.quantal.javashared.objectmapper.NullSkippingOrikaBeanMapper;
import com.quantal.javashared.objectmapper.OrikaBeanMapper;
import com.quantal.javashared.services.interfaces.MessageService;
import com.quantal.telephones.constants.Events;
import com.quantal.telephones.constants.MessageCodes;
import com.quantal.telephones.dto.TelephoneDto;
import com.quantal.telephones.models.Telephone;
import com.quantal.telephones.services.api.GiphyApiService;
import com.quantal.telephones.services.interfaces.TelephonesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by dman on 08/03/2017.
 */
@Service
public class TelephonesFacade extends AbstractBaseFacade {

  private TelephonesService telephonesService;
  private final GiphyApiService giphyApiService;
  private MessageService messageService;

  //private Logger logger = LoggerFactory.getLogger(this.getClass())
  @InjectLogger
  private QuantalLogger logger;

  @Autowired
  public TelephonesFacade(TelephonesService telephonesService,
                          GiphyApiService giphyApiService,
                          MessageService messageService,
                          OrikaBeanMapper orikaBeanMapper,
                          NullSkippingOrikaBeanMapper nullSkippingOrikaBeanMapper) {
    super(orikaBeanMapper, nullSkippingOrikaBeanMapper);
    this.telephonesService = telephonesService;
    this.giphyApiService = giphyApiService;
    this.messageService = messageService;
  }

  public CompletableFuture<ResponseEntity<?>> saveOrUpdateTelephone(TelephoneDto telephoneDto){

    logger.debug("Saving user:", new LogEvent(Events.TELEPHONE_CREATE), telephoneDto);
    Telephone telephoneModelToCreate = toModel(telephoneDto, Telephone.class);
    return telephonesService.saveOrUpdate(telephoneModelToCreate)
    .thenApply((created) -> {
      TelephoneDto createdDto = toDto(created, TelephoneDto.class);
      logger.debug("Finished saving user:", new LogEvent("TELEPHONE_CREATE"), createdDto);
      return toRESTResponse(createdDto, messageService.getMessage(MessageCodes.ENTITY_CREATED, new String[]{Telephone.class.getSimpleName()}), HttpStatus.CREATED);
    });


  }

  public CompletableFuture<ResponseEntity<?>>  update(TelephoneDto telephoneDto){

    Telephone telephoneToUpdate = toModel(telephoneDto, Telephone.class);
            return telephonesService.update(telephoneDto.getId(), telephoneToUpdate, true).thenApply(telephoneUpdated -> {
              TelephoneDto updatedDto = toDto(telephoneUpdated, TelephoneDto.class);
              return toRESTResponse(updatedDto, messageService.getMessage(MessageCodes.ENTITY_UPDATED, new String[]{Telephone.class.getSimpleName()}));
            });

  }

  public CompletableFuture<ResponseEntity<?>>  findTelephoneById(long id){

    return telephonesService.findOne(id).thenApply(foundTelephone -> {
      TelephoneDto telephoneDto = toDto(foundTelephone, TelephoneDto.class);
      if(telephoneDto == null) {
        String errMsg = messageService.getMessage(MessageCodes.NOT_FOUND, new String[]{Telephone.class.getSimpleName()});
        throw new NotFoundException(errMsg);
      }
      return toRESTResponse(telephoneDto, messageService.getMessage(MessageCodes.ENTITY_READ, new String[]{Telephone.class.getSimpleName()}));
    });

  }

  public CompletableFuture<String> getFunnyCat(){
    logger.debug("Getting funny cat", new LogEvent("FUNNY_CAT"),new LogTraceId("TRACE_ID"));
    //String result = "";
    //String result = giphyApiService.getGiphy("funny+cat", "dc6zaTOxFJmzC");
    CompletableFuture<String> result = giphyApiService.getGiphy("funny+cat", "dc6zaTOxFJmzC").thenApply((res) -> {
      logger.debug("finished getting funny cat", new LogEvent("FUNNY_CAT"));
      return res;
    });
    return result;
  }
}
