package com.umer.simplefakebank.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "New user created response.", description = "The new user information.")
public class ResponseUserDTO {

    @JsonProperty("user_id")
    @ApiModelProperty(notes = "The Id of the user.")
    private Long id;

    @JsonProperty("user_name")
    @ApiModelProperty(notes = "The name of the user.")
    private String username;

    @JsonProperty("user_email")
    @ApiModelProperty(notes = "The email of the user.")
    private String email;
}