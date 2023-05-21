package com.umer.simplefakebank.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.umer.simplefakebank.configuration.BankConstants.*;;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "Create new user.", description = "Required information to create a new user.")
public class CreateUserDTO {

    @JsonProperty("user_name")
    @NotNull
    @ApiModelProperty(notes = "Username.")
    private String username;

    @JsonProperty("email")
    @NotNull
    @ApiModelProperty(notes = "Email.")
    private String email;

    @JsonProperty("password")
    @NotNull
    @ApiModelProperty(notes = "Password.")
    private String password;

}
