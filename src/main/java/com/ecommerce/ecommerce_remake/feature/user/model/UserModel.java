package com.ecommerce.ecommerce_remake.feature.user.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.marker.CreateInfo;
import com.ecommerce.ecommerce_remake.common.marker.UpdateInfo;
import com.ecommerce.ecommerce_remake.feature.user.enums.Gender;
import com.ecommerce.ecommerce_remake.validator.ConfirmPasswordValid;
import com.ecommerce.ecommerce_remake.validator.PasswordLengthValid;
import com.ecommerce.ecommerce_remake.validator.UniqueEmailValid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfirmPasswordValid
@JsonInclude(NON_DEFAULT)
public class UserModel {
    @Null(groups = CreateInfo.class)
    @NotNull(groups = UpdateInfo.class)
    private Long userId;

    @UniqueEmailValid
    @NotBlank(message = "{email.required}")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @PasswordLengthValid
    @NotBlank(message = "{password.not.null}")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{confirm.password.required}")
    private String confirmPassword;

    @NotBlank(message = "{name.required}")
    private String name;

    private String picture;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
