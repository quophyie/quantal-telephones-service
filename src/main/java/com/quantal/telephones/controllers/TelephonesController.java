package com.quantal.telephones.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantal.javashared.annotations.logger.InjectLogger;
import com.quantal.javashared.dto.LogTraceId;
import com.quantal.javashared.jsonviews.DefaultJsonView;
import com.quantal.telephones.constants.Events;
import com.quantal.telephones.dto.TelephoneDto;
import com.quantal.telephones.facades.TelephonesFacade;
import com.quantal.javashared.controller.BaseControllerAsync;
import com.quantal.javashared.dto.LogEvent;
import com.quantal.javashared.logger.QuantalLogger;
import com.quantal.telephones.jsonviews.TelephoneViews;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.quantal.javashared.constants.CommonConstants.SUB_EVENT_KEY;

/**
 * Created by dman on 08/03/2017.
 */

@RestController
@RequestMapping("/telephones")
public class TelephonesController extends BaseControllerAsync {

  private TelephonesFacade telephonesFacade;
  //private Logger logger = LoggerFactory.getLogger(this.getClass());
  @InjectLogger
  private QuantalLogger logger;

  @Autowired
  ObjectMapper objectMapper;


  @Autowired
  public TelephonesController(TelephonesFacade telephonesFacade) {
    this.telephonesFacade = telephonesFacade;
  }

 // @JsonView(TelephoneViews.DefaultTelephoneView.class)
  @PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CompletableFuture<?> createTelephone(@RequestBody TelephoneDto telephoneDto){
    MDC.put(SUB_EVENT_KEY, Events.TELEPHONE_CREATE);
    return telephonesFacade.saveOrUpdateTelephone(telephoneDto)
            .thenApply( responseEntity -> applyJsonView(responseEntity, TelephoneViews.DefaultTelephoneView.class, objectMapper));
  }

    @PatchMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<?> updateTelephone(@RequestBody TelephoneDto telephoneDto){
        MDC.put(SUB_EVENT_KEY, Events.TELEPHONE_UPDATE);
        return telephonesFacade.update(telephoneDto)
                .thenApply( responseEntity -> applyJsonView(responseEntity, TelephoneViews.DefaultTelephoneView.class, objectMapper));
    }

    @GetMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<?> getTelephone(@PathVariable("id") long id){
        MDC.put(SUB_EVENT_KEY, Events.TELEPHONE_RETRIEVE);
        return telephonesFacade.findTelephoneById(id)
                .thenApply( responseEntity -> applyJsonView(responseEntity, TelephoneViews.DefaultTelephoneView.class, objectMapper));
    }

  @GetMapping(value="")
  public CompletableFuture<String> getFunnyCatAsync(){
    logger.debug("started to  get funny cat ...", new LogEvent("TELEPHONE_CREATE"), new LogTraceId(UUID.randomUUID().toString()));
    return telephonesFacade.getFunnyCat();
  }


}
