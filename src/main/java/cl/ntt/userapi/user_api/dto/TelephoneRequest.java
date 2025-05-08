package cl.ntt.userapi.user_api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class TelephoneRequest {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("numero")
    private String numero;
    @JsonProperty("codigoCiudad")
    private String codigoCiudad;
    @JsonProperty("codigoPais")
    private String codigoPais;

}
