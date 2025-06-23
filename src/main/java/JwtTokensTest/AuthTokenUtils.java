package JwtTokensTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class AuthTokenUtils {
    public SecurityTokenDto extractToken (String token) throws Exception {

        if(token != null) {
            try {
                String[] chunks = token.split("\\.");
                if(chunks.length < 2) {
                    throw new IllegalArgumentException("Invalid token format: Not enough parts");
                }

                var decodedPayload = Base64.getUrlDecoder().decode(chunks[1]);
                return new ObjectMapper().readValue(decodedPayload, SecurityTokenDto.class);
            } catch (Exception e) {
                System.out.println("Something failed "+e.getMessage());
            }
        }

        return new SecurityTokenDto();
    }
}
