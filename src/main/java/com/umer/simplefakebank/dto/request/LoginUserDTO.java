package com.umer.simplefakebank.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Login User Request", description = "DTO for user login")
public class LoginUserDTO {

    @JsonProperty("email")
    @NotNull
    @NotBlank(message = "email is mandatory")
    @ApiModelProperty(value = "email", required = true)
    private String email;

    @JsonProperty("password")
    @NotNull
    @NotBlank(message = "Password is mandatory")
    @ApiModelProperty(value = "Password", required = true)
    private String password;

}
