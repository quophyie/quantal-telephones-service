package com.quantal.telephones.services.implementations;

import com.quantal.javashared.objectmapper.NullSkippingOrikaBeanMapper;
import com.quantal.javashared.objectmapper.OrikaBeanMapper;
import com.quantal.javashared.services.implementations.AbstractRepositoryServiceAsync;
import com.quantal.telephones.models.Telephone;
import com.quantal.telephones.repositories.TelephonesRepository;
import com.quantal.telephones.services.interfaces.TelephonesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class TelephonesServiceImpl extends AbstractRepositoryServiceAsync<Telephone, Long> implements TelephonesService {

  private TelephonesRepository telephonesRepository;

  private OrikaBeanMapper orikaBeanMapper;
  private NullSkippingOrikaBeanMapper nullSkippingOrikaBeanMapper;


  @Autowired
  public TelephonesServiceImpl(TelephonesRepository telephonesRepository, OrikaBeanMapper orikaBeanMapper, NullSkippingOrikaBeanMapper nullSkippingOrikaBeanMapper){
   super(telephonesRepository, orikaBeanMapper, nullSkippingOrikaBeanMapper);
    this.telephonesRepository = telephonesRepository;
    this.orikaBeanMapper = orikaBeanMapper;
    this.nullSkippingOrikaBeanMapper = nullSkippingOrikaBeanMapper;
  }

}
