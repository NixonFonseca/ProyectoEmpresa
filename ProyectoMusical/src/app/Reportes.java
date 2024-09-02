/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author PC_22
 */
public class Reportes {

    private List<Gestion> listaIntrumentos = new ArrayList<>();
    private List<Prestamo> listaPrestamoIn = new ArrayList<>();

    public String entrada(String texto) {
        return JOptionPane.showInputDialog(texto);
    }

    public void agregarInstrumento() {
        String instrumento = entrada("Nombre Instrumeto: ");
        for (Gestion instru : listaIntrumentos) {
            if (instru.getinstrumeto().equalsIgnoreCase(instrumento)) {

                JOptionPane.showMessageDialog(null, "Libro ya existe");
                return;
            }
        }
        String estado = entrada("Estado del instrumento (Nuevo,Usado): ");
        String grupo = entrada("Ingrese el grupo del Instrumento(Cuerdas ,Cuerdas Frotadas , Vientos o Maderas , Metales ,Percusión): ");
        int cantidad = Integer.parseInt(entrada("Numero de Ejemplares: "));
        Gestion libro = new Gestion(instrumento, grupo, estado, cantidad);
        listaIntrumentos.add(libro);
        JOptionPane.showMessageDialog(null, "Se agrego el libro correctamente");
    }
   

    public void mostrarInstrumentos() {
        if (listaIntrumentos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡¡¡NO hay Instrumentos en la lista!!!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < listaIntrumentos.size(); i++) {
            for (Gestion Intrumento : listaIntrumentos) {
                if (Intrumento.getGrupo().equalsIgnoreCase("Cuerdas")) {
                    stringBuilder.append(" Grupo Cuerdas ").append("\n");
                    stringBuilder.append("Instrumento").append(i + 1).append("\n");
                    stringBuilder.append("Nombre: ").append(listaIntrumentos.get(i).getinstrumeto()).append("\n");
                    stringBuilder.append("Edicion: ").append(listaIntrumentos.get(i).getEstado()).append("\n");
                    stringBuilder.append("Categoria: ").append(listaIntrumentos.get(i).getCantidad()).append("\n\n");
                } else if (Intrumento.getGrupo().equalsIgnoreCase("Frotadas")) {
                    stringBuilder.append(" Grupo Cuerdas Frotadas ").append("\n");
                    stringBuilder.append("Instrumento").append(i + 1).append("\n");
                    stringBuilder.append("Nombre: ").append(listaIntrumentos.get(i).getinstrumeto()).append("\n");
                    stringBuilder.append("Edicion: ").append(listaIntrumentos.get(i).getEstado()).append("\n");
                    stringBuilder.append("Categoria: ").append(listaIntrumentos.get(i).getCantidad()).append("\n\n");
                } else if (Intrumento.getGrupo().equalsIgnoreCase("Vientos")) {
                    stringBuilder.append(" Grupo Vientos ").append("\n");
                    stringBuilder.append("Instrumento").append(i + 1).append("\n");
                    stringBuilder.append("Nombre: ").append(listaIntrumentos.get(i).getinstrumeto()).append("\n");
                    stringBuilder.append("Edicion: ").append(listaIntrumentos.get(i).getEstado()).append("\n");
                    stringBuilder.append("Categoria: ").append(listaIntrumentos.get(i).getCantidad()).append("\n\n");
                } else if (Intrumento.getGrupo().equalsIgnoreCase("Metales")) {
                    stringBuilder.append(" Grupo Metales ").append("\n");
                    stringBuilder.append("Instrumento").append(i + 1).append("\n");
                    stringBuilder.append("Nombre: ").append(listaIntrumentos.get(i).getinstrumeto()).append("\n");
                    stringBuilder.append("Edicion: ").append(listaIntrumentos.get(i).getEstado()).append("\n");
                    stringBuilder.append("Categoria: ").append(listaIntrumentos.get(i).getCantidad()).append("\n\n");
                } else if (Intrumento.getGrupo().equalsIgnoreCase("Percusion")) {
                    stringBuilder.append(" Grupo Percusion ").append("\n");
                    stringBuilder.append("Instrumento").append(i + 1).append("\n");
                    stringBuilder.append("Nombre: ").append(listaIntrumentos.get(i).getinstrumeto()).append("\n");
                    stringBuilder.append("Edicion: ").append(listaIntrumentos.get(i).getEstado()).append("\n");
                    stringBuilder.append("Categoria: ").append(listaIntrumentos.get(i).getCantidad()).append("\n\n");
                }
            }

        }
        JOptionPane.showMessageDialog(null, stringBuilder.toString());
    }

    public void generarReportePrestamo() {
        if (listaPrestamoIn.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay prestamos registrados ");
        }
        StringBuilder reporte = new StringBuilder();

        reporte.append("Reporte detallado de libros pretados: \n");
        reporte.append("----------------------------- \n");
        reporte.append("Total Titulos \n").append(listaIntrumentos.size()).append("\n");

        for (Prestamo prestamos : listaPrestamoIn) {
            reporte.append("Instrumento: ").append(prestamos.getinstrumeto()).append("\n");
            reporte.append("Usuario: ").append(prestamos.getNombreUsuario()).append("\n");
            reporte.append("Celular: ").append(prestamos.getCelular()).append("\n");
            reporte.append("Fecha Prestamo: ").append(prestamos.getFechaPrestamo()).append("\n");
            reporte.append("Fecha Delovolucion: ").append(prestamos.getFechaDevolucion()).append("\n");
            reporte.append("Estado: ").append(prestamos.isAtrasado() ? "Atrasado" : " En plazo ").append("\n\n");

        }
        JOptionPane.showMessageDialog(null, reporte.toString());
    }

    public void verificarPrestamo() {
        if (listaPrestamoIn.isEmpty()) {
            JOptionPane.showMessageDialog(null, "!!No hay prestamos registrados");
            return;
        }

        StringBuilder prestamosAtrasados = new StringBuilder();
        boolean hayAtrasos = false;

        for (Prestamo prestamo : listaPrestamoIn) {
            if (prestamo.isAtrasado()) {
                prestamosAtrasados.append("Usuario: ").append(prestamo.getNombreUsuario()).append("\n");
                prestamosAtrasados.append("Instrumento: ").append(prestamo.getinstrumeto()).append("\n");
                prestamosAtrasados.append("Fecha Devolucion: ").append(prestamo.getFechaDevolucion()).append("\n\n");

            }

        }
        if (hayAtrasos) {
            JOptionPane.showMessageDialog(null, "Préstamos atrasados: \n " + prestamosAtrasados);
        } else {
            JOptionPane.showMessageDialog(null, "!!No hay prestamos atrasados!!!");

        }

    }

    public void eliminarIntrumento() {
        String nombre = entrada("Ingrese el Instrumeto que desea eliminar:");
        boolean eliminado = listaIntrumentos.removeIf(Gestion -> Gestion.getinstrumeto().equalsIgnoreCase(nombre));
        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Instrumento elimidado exitosamnete:");
        } else {
            JOptionPane.showMessageDialog(null, "Intrumento no encontrado:");

        }
    }
    public void actualizarEstado(){
        
    }
    public void actualizarCantidad(){
        
    }

    public void registraPrestamo() {
        if (listaIntrumentos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay instrumentos disponibles");
            return;
        }
        String seleccion;

        do {
            String[] opcion = {
                "Menu Principal\n",
                 "Cuerdas",
                 "Cuerdas Frotadas",
                 "Vientos o Maderas",
                 "Metales",
                 "Percusion",
                 "Salir"};
            seleccion = (String) JOptionPane.showInputDialog(null, "Seleceione el gruopo de instrumetos", "Opciones", JOptionPane.QUESTION_MESSAGE, null, opcion, opcion[0]);
            
          if (seleccion != null && !seleccion.equals("Salir")) {
            // Llamar a recorreLista con la opción seleccionada
            recorreLista(seleccion);
        }
    } while (seleccion == null || !"Salir".equals(seleccion));
}

    public void recorreLista(String seleccion) {
        String nombreInstrumeto = entrada("Ingrese el instrumento para el prestamo: ");
        Gestion instrumetoPrestado = null;
        JOptionPane.showMessageDialog(null,"Nombre del instrumento: " + nombreInstrumeto);
        JOptionPane.showMessageDialog(null,"Grupo: " + seleccion);
        
        for (Gestion instrumento : listaIntrumentos) {
             JOptionPane.showMessageDialog(null,"Instrumento en la lista: " + instrumento.getinstrumeto());
        JOptionPane.showMessageDialog(null,"Grupo del instrumento: " + instrumento.getGrupo());
            if ( instrumento.getinstrumeto().equalsIgnoreCase(nombreInstrumeto) && instrumento.getGrupo().equalsIgnoreCase(seleccion) ) {
                instrumetoPrestado = instrumento;
                break;
            }
        }
        if (instrumetoPrestado == null) {
            JOptionPane.showMessageDialog(null, "Instrumento no encontrado");
            return;
        }
        if (instrumetoPrestado.getCantidad() <= 0) {
            JOptionPane.showMessageDialog(null, "No hay Instrumentos disponibles");
            return;

        }
        String nombreUsuario = entrada("Ingrese el nombre del usuario: ");
        int celular = Integer.parseInt(entrada("Ingrese numero de celular: "));
        Prestamo prestamo = new Prestamo(nombreInstrumeto, nombreUsuario, celular);
        listaPrestamoIn.add(prestamo);
        JOptionPane.showMessageDialog(null, "Prestamo Registrado Exitosamnete. \n fecha de devolucion:" + prestamo.getFechaDevolucion());
    }
}
