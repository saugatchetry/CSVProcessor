package JwtTokensTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DummyJwtGenerator {

    public static void main(String[] args) throws Exception {
        int n = 10; // Number of tokens to generate
        List<TokenInfo> tokenInfoList = generateDummyTokens(n);
        tokenInfoList.forEach(info -> System.out.println(info.jwtToken));

        writeTokensToCsv(tokenInfoList, "tokens.csv");
        System.out.println("Tokens written to tokens.csv");
    }

    public static List<TokenInfo> generateDummyTokens(int count) throws Exception {
        List<TokenInfo> tokens = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Random random = new Random();

        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes());

        // Variability options
        String[] scopes = {"read:all", "write:limited", "admin", "read:limited"};
        String[] clientIds = {"web-client", "mobile-client", "api-client"};
        String[] domains = {"company.com", "example.org", "test.net"};
        String[] auth1 = {"action:read"};
        String[] auth2 = {"action:write"};
        String[] auth3 = {"action:delete", "action:read"};
        String[][] authOptions = {auth1, auth2, auth3};

        for (int i = 0; i < count; i++) {
            Map<String, Object> payload = new LinkedHashMap<>();

            String userid = generateUserId(random);
            String win = generateWin(random);

            // Populate payload with variations
            payload.put("scope", scopes[random.nextInt(scopes.length)]);
            payload.put("client_id", clientIds[random.nextInt(clientIds.length)]);
            payload.put("guid", UUID.randomUUID().toString());
            payload.put("iss", "pingfed");
            payload.put("jti", "jti-" + UUID.randomUUID());
            payload.put("aud", "aud-123");
            payload.put("sub", "user-sub");
            payload.put("upn", "user" + i + "@" + domains[random.nextInt(domains.length)]);
            payload.put("nbf", "1600000000");
            payload.put("iat", "1600000000");
            payload.put("exp", "1700000000");
            payload.put("userid", userid);
            payload.put("win", win);
            payload.put("authorization_details", authOptions[random.nextInt(authOptions.length)]);

            String payloadJson = objectMapper.writeValueAsString(payload);
            String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes());

            String jwt = encodedHeader + "." + encodedPayload + ".signature-placeholder";
            tokens.add(new TokenInfo(userid, win, jwt));
        }

        return tokens;
    }

    public static void writeTokensToCsv(List<TokenInfo> tokens, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"UserID", "WIN", "JWT"});
            for (TokenInfo token : tokens) {
                writer.writeNext(new String[]{token.userid, token.win, token.jwtToken});
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    private static String generateUserId(Random random) {
        return new StringBuilder()
                .append(getRandomLetter(random))                      // Letter
                .append(random.nextInt(10))                           // Digit
                .append(getRandomLetter(random))                      // Letter
                .append(String.format("%03d", random.nextInt(1000)))  // 3-digit number
                .append(getRandomLetter(random))                      // Letter
                .toString();
    }

    private static String generateWin(Random random) {
        return "2" + String.format("%08d", random.nextInt(100_000_000));
    }

    private static char getRandomLetter(Random random) {
        return (char) ('a' + random.nextInt(26));
    }

    // Data holder class
    public static class TokenInfo {
        public String userid;
        public String win;
        public String jwtToken;

        public TokenInfo(String userid, String win, String jwtToken) {
            this.userid = userid;
            this.win = win;
            this.jwtToken = jwtToken;
        }
    }
}
