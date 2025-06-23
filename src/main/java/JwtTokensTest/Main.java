package JwtTokensTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        AuthTokenUtils authTokenUtils = new AuthTokenUtils();
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6InJlYWQ6YWxsIiwiY2xpZW50X2lkIjoidGVzdC1jbGllbnQiLCJndWlkIjoiNjk0NDEyOTAtMGMyNS00MWIxLWE0ZTYtMjhmOGQwYThiZTFmIiwiaXNzIjoicGluZ2ZlZCIsImp0aSI6Imp0aS0zNGM0MGY5OC04MmYwLTRlMzUtYTBkNC02YWU5OTNjZGRlM2YiLCJhdWQiOiJhdWQtMTIzIiwic3ViIjoidXNlci1zdWIiLCJ1cG4iOiJ1c2VyMEBjb21wYW55LmNvbSIsIm5iZiI6IjE2MDAwMDAwMDAiLCJpYXQiOiIxNjAwMDAwMDAwIiwiZXhwIjoiMTcwMDAwMDAwMCIsInVzZXJpZCI6InY1aTk5NnciLCJ3aW4iOiIyNDgzMTg2NzgiLCJhdXRob3JpemF0aW9uX2RldGFpbHMiOlsiYWN0aW9uOnJlYWQiLCJhY3Rpb246d3JpdGUiXX0.signature-placeholder";
        SecurityTokenDto extractedToken = authTokenUtils.extractToken(token);
        System.out.println("WIN = "+extractedToken.getWin());
        System.out.println("USERID = "+extractedToken.getUserid());

        JwtAuthenticationToken authToken = createAuthTokenFromTokenString(token);

        String userId = new JwtUserDetailsUtil(authToken).getUserId();
        String win = new JwtUserDetailsUtil(authToken).getUserWIN();
        System.out.println("API User = "+userId);
        System.out.println("WIN = "+win);

    }

    private static JwtAuthenticationToken createAuthTokenFromTokenString(String token) throws Exception {
        String[] chunks = token.split("\\.");
        if (chunks.length < 2) throw new IllegalArgumentException("Invalid token");

        // Decode Base64URL payload
        byte[] decoded = Base64.getUrlDecoder().decode(chunks[1]);
        String payloadJson = new String(decoded);
        Map<String, Object> claims = new ObjectMapper().readValue(payloadJson, Map.class);

        // Create Jwt and wrap in JwtAuthenticationToken
        Jwt jwt = new Jwt(
                token,
                Instant.now(),                         // issued at
                Instant.now().plusSeconds(3600),       // expires at
                Map.of("alg", "none"),                 // header
                claims                                 // payload claims
        );
        return new JwtAuthenticationToken(jwt);
    }
}
