package org.emarva.workbase.Shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.emarva.workbase.Shared.Base64Coder;

/**
 * @author Lord Bytes
 */
public class Security {
    private static final char[] Hexadecimal = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    public static String base64Codificar(String cadena) {
        return Base64Coder.encodeString(cadena);
    }
    
    public static String base64Decodificar(String cadena) {
        return Base64Coder.decodeString(cadena);
    }
    
    public static String generarMD5(String cadena) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(cadena.getBytes());
            StringBuilder sb = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int low = (int)(bytes[i] & 0x0f);
                int high = (int)((bytes[i] & 0xf0) >> 4);
                sb.append(Hexadecimal[high]);
                sb.append(Hexadecimal[low]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
