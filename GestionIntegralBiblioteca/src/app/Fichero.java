/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author PC_22
 */
public class Fichero {

    public void hacerCopiaSeguridad(String archivoFuente, String archivoDestino) throws IOException {
        try (InputStream in = new FileInputStream(archivoFuente); OutputStream out = new FileOutputStream(archivoDestino)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    public void restaurarArchivo(String archivoFuente, String archivoDestino) throws IOException {
        hacerCopiaSeguridad(archivoFuente, archivoDestino);
    }

    public void crearArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        try {
            if (archivo.createNewFile()) {
                JOptionPane.showMessageDialog(null, "Archivo Creado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No Archivo Creado");

        }
    }

    public void escribirArchivo(String nombreArchivo, Libro infoU) {
        try (FileWriter filew = new FileWriter(nombreArchivo, true); BufferedWriter bufferR = new BufferedWriter(filew); PrintWriter mostrar = new PrintWriter(bufferR)) {
            mostrar.print(infoU);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No Archivo Creado");

        }

    }
}
