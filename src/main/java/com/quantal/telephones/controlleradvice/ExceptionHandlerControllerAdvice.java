package com.quantal.telephones.controlleradvice;

import com.quantal.javashared.exceptions.NotFoundException;
import com.quantal.telephones.constants.MessageCodes;
import com.quantal.javashared.services.interfaces.MessageService;
import com.quantal.javashared.util.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.HttpException;

import static com.quantal.javashared.facades.AbstractBaseFacade.toRESTResponse;

/**
 * Created by dman on 08/07/2017.
 */
@RestController
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private Logger logger  = LogManager.getLogger();

    private MessageService messageService;

    @Autowired
    public ExceptionHandlerControllerAdvice(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleThrowable(final Throwable ex) {

        logger.error("Unexpected error", ex);
        ResponseEntity responseEntity = toRESTResponse(null,
                messageService.getMessage(MessageCodes.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
        Exception businessEx = CommonUtils.extractBusinessException(ex);
        if (ex.getCause() instanceof NotFoundException) {
            responseEntity = toRESTResponse(null, ex.getMessage(), HttpStatus.NOT_FOUND);
            return responseEntity;
        } else if (businessEx instanceof HttpException) {
            HttpStatus status = HttpStatus.valueOf(((HttpException) businessEx).code());
            responseEntity = toRESTResponse(null, businessEx.getMessage(), status);
        }
        logger.error(ex);
        return responseEntity;
    }


}
