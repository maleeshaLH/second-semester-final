package lk.ijse.backend.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class AppUtil {
    public static String toBase64(byte [] profilePic){
        return Base64.getEncoder().encodeToString(profilePic);
    }
}
