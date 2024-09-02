/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author PC_22
 */
public class FileLogger implements Logger {
    private static final String OPERACIONES_LOG_FILE = "logs/operaciones.log";
    private static final String ERRORES_LOG_FILE = "logs/errores.log";

    @Override
    public void logOperacion(String mensaje) {
        try (PrintWriter out = new PrintWriter(new FileWriter(OPERACIONES_LOG_FILE, true))) {
            out.println(mensaje);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log de operaciones: " + e.getMessage());
        }
    }

    @Override
    public void logError(String mensaje) {
        try (PrintWriter out = new PrintWriter(new FileWriter(ERRORES_LOG_FILE, true))) {
            out.println(mensaje);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log de errores: " + e.getMessage());
        }
    }
}
