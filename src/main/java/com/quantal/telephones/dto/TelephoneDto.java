package com.quantal.telephones.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.quantal.telephones.enums.TelephoneNumType;
import com.quantal.telephones.jsonviews.TelephoneViews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by dman on 08/03/2017.
 */
@Data
@AllArgsConstructor
@Builder
public class TelephoneDto {
  @JsonView(TelephoneViews.DefaultTelephoneView.class)
  private Long id;
  @NotNull
  @JsonView(TelephoneViews.DefaultTelephoneView.class)
  private Long companyId;
  @NotNull
  @JsonView(TelephoneViews.DefaultTelephoneView.class)
  private String telephoneNum;
  @JsonView(TelephoneViews.DefaultTelephoneView.class)
  private TelephoneNumType type;

}
