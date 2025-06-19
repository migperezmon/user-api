package cl.ntt.userapi.user_api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cl.ntt.userapi.user_api.validations.ValidationGroups.OnCreateUser;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnUpdateUser;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneRequest {

	@JsonProperty("id")
	private UUID id;

	@JsonProperty("number")
	@Pattern(regexp = "\\d+", message = "El campo 'number' debe contener solo dígitos", groups = {
			OnCreateUser.class, OnUpdateUser.class })
	private String numero;

	@Pattern(regexp = "\\d+", message = "El campo 'city_code' debe contener solo dígitos", groups = {
			OnCreateUser.class, OnUpdateUser.class })
	@JsonProperty("city_code")
	private String codigoCiudad;

	@Pattern(regexp = "\\d+", message = "El campo 'country_code' debe contener solo dígitos", groups = {
			OnCreateUser.class, OnUpdateUser.class })
	@JsonProperty("country_code")
	private String codigoPais;

}
