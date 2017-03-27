package com.quantal.quantalmicroservicetemplate.facades;


import com.quantal.quantalmicroservicetemplate.dto.ResponseDTO;
import com.quantal.quantalmicroservicetemplate.objectmapper.OrikaBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractBaseFacade {

  @Autowired
  @Qualifier("orikaBeanMapper")
  protected OrikaBeanMapper mapper;

  @Autowired
  @Qualifier("nullSkippingOrikaBeanMapper")
  protected OrikaBeanMapper nullSkippingMapper;


  /**
   * Converts Dto to a ResponseDto and with the supplied Http Status and headears
   * @param reponseDTOData - The type of the Dto
   * @param httpStatus  - The Http status
   * @param httpHeaders - the http headers
   * @param <TResponseDTOData>
   * @return
   */
  public static <TResponseDTOData> ResponseEntity<?> toRESTResponse(TResponseDTOData reponseDTOData, HttpStatus httpStatus, HttpHeaders httpHeaders){

    ResponseEntity<ResponseDTO<TResponseDTOData>> response;
    ResponseDTO<TResponseDTOData> responseDTO = new ResponseDTO<>(reponseDTOData);
    if (httpHeaders != null){
      response = new ResponseEntity<>(responseDTO, httpHeaders, httpStatus);
    } else {
      response = new ResponseEntity<>(responseDTO, httpStatus);
    }
    return response;
  }
  /**
   * Converts Dto to a ResponseDto and with the supplied Http Status
   * @param reponseDTOData - The type of the Dto
   * @param httpStatus  - The Http status
   * @param <TResponseDTOData>
   * @return
   */
  public static <TResponseDTOData> ResponseEntity<?> toRESTResponse(TResponseDTOData reponseDTOData, HttpStatus httpStatus){
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE,  MediaType.APPLICATION_JSON_VALUE);
    return toRESTResponse(reponseDTOData,httpStatus,headers);
  }


  /**
   * Converts Dto to a ResponseDto and with a Http Status code of 200
   * @param reponseDTOData - The type of the Dto
   * @param <TResponseDTOData>
   * @return
   */
  public static <TResponseDTOData> ResponseEntity<?> toRESTResponse(TResponseDTOData reponseDTOData){
    return toRESTResponse(reponseDTOData,HttpStatus.OK);
  }

  /**
   * Maps a DTO to a model given the type (class) of the model
   * @param source - the source model to map from i.e. The DTO
   * @param clazz  - The closs (type) of the destination model
   * @param <TDTO> - The type of the source object i.e. the DTO
   * @param <TModel> - The type of the model
   * @return
   */
  public  <TDTO, TModel> TModel toModel(TDTO source, Class<TModel> clazz){
    TModel model = mapper.map(source, clazz);
    model = mapper.map(source, clazz);

    return model;
  }


  /**
   * Maps a DTO to a model given an instance of the model
   * @param source - the source model to map from i.e. The DTO
   * @param model  - The the destination model
   * @param mapNulls - if true, then null values will copied to destination model. If
   *                 false, null values will be skipped
   * @param <TDTO> - The type of the source object i.e. the DTO
   * @param <TModel> - The type of the model
   * @return
   */
  public  <TDTO, TModel> TModel toModel(TDTO source, TModel model, boolean mapNulls){


    if (mapNulls){
      mapper.map(source, model);
    } else {
      nullSkippingMapper.map(source, model);
    }
    return model;
  }

  /**
   * Maps a model to a DTO given the type (class) of the DTO
   * @param source - the source model to map from i.e. The DTO
   * @param clazz  - The closs (type) of the destination model
   * @param <TDTO> - The type of the source object i.e. the DTO
   * @param <TModel> - The type of the model
   * @return
   */

  public  <TModel, TDTO> TDTO toDto(TModel source, Class<TDTO> clazz){

    TDTO dto = mapper.map(source, clazz);
    return dto;
  }

  /**
   * Maps a model to a DTO given an instance of the dto
   * @param source - the source model to map from i.e. The DTO
   * @param <TDTO> - The type of the source object i.e. the DTO
   * @param <TModel> - The type of the model
   * @return
   */

  public  <TModel, TDTO> TDTO toDto(TModel source, TDTO dto){

    mapper.map(source, dto);
    return dto;
  }
}