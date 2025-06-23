package JwtTokensTest;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

public class JwtUserDetailsUtil {
    private final Map<String, Object> claims;

    public JwtUserDetailsUtil(JwtAuthenticationToken authToken) {
        this.claims = authToken.getTokenAttributes();
    }

    public String getUserId() {
        return (String) claims.get("userid");
    }
    public String getUserWIN() {
        return (String) claims.get("win");
    }
}
