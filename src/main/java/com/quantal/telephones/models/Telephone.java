package com.quantal.telephones.models;

import com.quantal.telephones.enums.TelephoneNumType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by dman on 07/03/2017.
 */

@Entity
@Table(name = "telephone_numbers")
@Data
@ToString
@EqualsAndHashCode (of = {"id"})
public class Telephone implements Serializable{

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "telephone_num")
  private String telephoneNum;

  @Column(name = "company_id")
  private String companyId;

  private TelephoneNumType type;
}
