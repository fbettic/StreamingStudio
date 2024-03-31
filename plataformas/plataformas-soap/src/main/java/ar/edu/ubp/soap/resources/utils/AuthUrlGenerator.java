package ar.edu.ubp.soap.resources.utils;

import java.util.Base64;
import java.util.UUID;

public class AuthUrlGenerator {

    private static String generateUniqueToken() {
        UUID uuid = UUID.randomUUID();
        byte[] uuidBytes = toByteArray(uuid);
        String base64Encoded = Base64.getUrlEncoder().encodeToString(uuidBytes);
        return base64Encoded;
    }
    
    private static byte[] toByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];
        
        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }
        
        return buffer;
    }

    public static String generateUuid(){
        return generateUniqueToken();
    }


    public static String generateAuthUrl(String urlType, String uuid) {
        String baseUrl = "http://localhost:4201/";
        String endpoint = urlType == "LOGIN" ? "login" : "signup";
        return baseUrl + endpoint + "?uuid=" + uuid;
    }

}
