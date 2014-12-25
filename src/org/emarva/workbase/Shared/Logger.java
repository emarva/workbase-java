package org.emarva.workbase.Shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lord Bytes
 */           
public class Logger {
    public enum TipoEntradaLogger {
        Advertencia,
        Error,
        Informacion,
        Ninguno
    }
    
    /**
     * Campos
     */
    private static String archivoLogger = "";
    private static boolean anteponerFecha = false;
    private static boolean anteponerHora = false;
    private static int largoLinea = 40;
    private static String fuente = "";
    private static long tamanoMaximoArchivo = 2097152; // Predeterminado 2 MB

    /**
     * Propiedades
     */
    public static String getArchivoLog() {
        return archivoLogger;
    }
    
    public static void setArchivoLog(String valor) {
        archivoLogger = valor;
    }

    public static boolean getAnteponerFecha() {
        return anteponerFecha;
    }
    
    public static void setAnteponerFecha(boolean valor) {
        anteponerFecha = valor;
    }

    public static boolean getAnteponerHora() {
        return anteponerHora;
    }
    
    public static void setAnteponerHora(boolean valor) {
        anteponerHora = valor;
    }

    public static int getLargoLinea() {
        return largoLinea;
    }
    
    public static void setLargoLinea(int valor) {
        largoLinea = valor;
    }

    public static String getFuente() {
        return fuente;
    }
    
    public static void setFuente(String valor) {
        fuente = valor;
    }
    
    public static long getTamanoMaximoArchivo() {
        return tamanoMaximoArchivo;
    }
    
    public static void setTamanoMaximoArchivo(long valor) {
        tamanoMaximoArchivo = valor;
    }

    /**
     * Metodos
     */ 
    private static boolean ArchivoExiste() {
        File archivo = new File(archivoLogger);
        if (archivo.exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    private static boolean VerificarTamanoArchivo() {
        boolean eliminado = false;
        if (ArchivoExiste()) {
            File archivo = new File(archivoLogger);
            if (archivo.length() > tamanoMaximoArchivo) {
                if (archivo.delete()) { 
                    eliminado = true;
                }
            }
        }
        return eliminado;
    }
    
    private static void AgregarLinea(String cadena, TipoEntradaLogger tipo, boolean linea)
    {
        FileWriter archivo = null;
        PrintWriter pw = null;
        try
        {
            // Si no se define el nombre del archivo Log, se lanza una excepcion
            if (archivoLogger.isEmpty()) {
                throw new FileNotFoundException();
            }

            // Repito la cadena
            if (linea) {
                String caracteres = "";
                for (int i = 0; i < largoLinea; i++) {
                    caracteres += cadena;
                }
                cadena = caracteres;
            }

            // Verifico si hay que anteponer la fecha y hora
            if (cadena.length() != 0) {          
                String anteponer = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaHora = new Date();
                if (anteponerFecha) {
                    anteponer = "[" + sdf.format(fechaHora);
                    sdf.applyPattern("HH:mm:ss");
                }
                if (anteponerHora && anteponer.length() != 0) {
                    anteponer += " " + sdf.format(fechaHora) + "]";
                } else if (!anteponerHora && anteponer.length() != 0) {
                    anteponer += "]";
                } else if (anteponerHora) {
                    anteponer = "[" + sdf.format(fechaHora) + "]";
                }                
                // Agrego la aplicacion, solo si el parametro aplicacion no esta vacio
                if (!fuente.isEmpty()) {
                    anteponer += "[" + fuente + "]";
                }
                // Agrego el tipo de entrada
                switch (tipo) {
                    case Advertencia: anteponer += "[WARNING]"; break;
                    case Error: anteponer += "[ERROR]"; break;
                    case Informacion: anteponer += "[INFO]"; break;
                }
                cadena = anteponer + " " + cadena;
            }

            if (!ArchivoExiste()) {
                archivo = new FileWriter(archivoLogger, false);                
            }
            else {
                if (!VerificarTamanoArchivo()) {
                    archivo = new FileWriter(archivoLogger, true);
                } else {
                    archivo = new FileWriter(archivoLogger, false);
                }
            }
            pw = new PrintWriter(archivo);
            pw.println(cadena);
            pw.close();
            archivo.close();
        }
        catch (Exception ex) { ex.printStackTrace(); }
        finally {
            try {
                if (null != archivo) {
                    archivo.close();
                }
            } catch (Exception ex2) {}
        }
    }

    public static void AgregarCadena(String cadena, TipoEntradaLogger tipo)
    {
        AgregarLinea(cadena, tipo, false);
    }

    public static void AgregarCadena(String cadena)
    {
        AgregarLinea(cadena, TipoEntradaLogger.Ninguno, false);
    }

    public static void AgregarLineaEnBlanco()
    {
        AgregarLinea("", TipoEntradaLogger.Ninguno, true);
    }

    public static void AgregarLineaDoble()
    {
        AgregarLinea("=", TipoEntradaLogger.Ninguno, true);
    }

    public static void AgregarLineaSimple()
    {
        AgregarLinea("-", TipoEntradaLogger.Ninguno, true);
    }
}
