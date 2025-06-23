package JwtTokensTest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityTokenDto {

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("client_id")
    private String client_id;

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("iss")
    private String iss;

    @JsonProperty("jti")
    private String jti;

    @JsonProperty("aud")
    private String aud;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("upn")
    private String upn;

    @JsonProperty("nbf")
    private String nbf;

    @JsonProperty("iat")
    private String iat;

    @JsonProperty("userid")
    private String userid;

    @JsonProperty("win")
    private String win;

    @JsonProperty("exp")
    private String exp;

    @JsonProperty("authorization_details")
    private String[] authorization_details;

}
