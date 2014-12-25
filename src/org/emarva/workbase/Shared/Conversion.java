package org.emarva.workbase.Shared;

import java.math.BigDecimal;

/**
 * @author Lord Bytes
 */
public class Conversion {
    public static double redondear(double numero, int decimales) {
	double resultado;
        BigDecimal res;
        res = new BigDecimal(numero).setScale(decimales, BigDecimal.ROUND_UP);
        resultado = res.doubleValue();
        return resultado; 
    }
    
    public static String binarioAHex(byte caracter) {             
        return Integer.toHexString(caracter);        
    }
    
    /*public static String hex2ASCII(String valor) {
        return "";
    }*/
    
    public static int hex2Dec(String valor) {
        return Integer.parseInt(valor, 16);
    }  
    
    public static String byte2Hex(byte numero) {
        /*
         * Como funciona
         * numero = -24
         * (numero & 0xff) equivale en binario a ((1110 1000 & 1111 1111) = 1110 1000) = 232 (Decimal)
         * luego (232) + 0x100 = 332
         * luego 332 se pasa a base 16 = 14C
         * luego le extraigo el 1 = 4C
         */
        return Integer.toString((numero & 0xff) + 0x100, 16).substring(1).toUpperCase();
    }
    
    public static int byte2Dec(byte numero) {
        String valor = byte2Hex(numero);
        return hex2Dec(valor);
    }
    
    public static String byte2String(byte numero) {
        return String.valueOf(byte2Dec(numero));
    }
    
    public static String texto2Html(String texto) {
        char[] caracteres = texto.toCharArray();        
        StringBuilder sb = new StringBuilder();
        for (char caracter : caracteres) {
            if (caracter == 'á') {
                sb.append("&aacute;");
            } else if (caracter == 'é') {
                sb.append("&eacute;");
            } else if (caracter == 'í') {
                sb.append("&iacute;");
            } else if (caracter == 'ó') {
                sb.append("&oacute;");
            } else if (caracter == 'ú') {
                sb.append("&uacute;");
            } else if (caracter == 'Á') {
                sb.append("&Aacute;");
            } else if (caracter == 'É') {
                sb.append("&Eacute;");
            } else if (caracter == 'Í') {
                sb.append("&Iacute;");
            } else if (caracter == 'Ó') {
                sb.append("&Oacute;");
            } else if (caracter == 'Ú') {
                sb.append("&Uacute;");
            } else if (caracter == 'ñ') {
                sb.append("&ntilde;");                
            } else if (caracter == 'Ñ') {
                sb.append("&Ntilde;");                
            } else {
                sb.append(caracter);
            }
        }
        return sb.toString();
    }
}
