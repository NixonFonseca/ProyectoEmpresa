/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author PC_22
 */
public class RegistroPrestamo {

    private List<Libro> listaLibro = new ArrayList<>();
    private List<Prestamo> listaPrestamo = new ArrayList<>();

    public String entrada(String texto) {
        return JOptionPane.showInputDialog(texto);
    }

    public void agregarLibro() {
        String nombre = entrada("Nombre libro: ");
        for (Libro libro : listaLibro) {
            if (libro.getNombre().equalsIgnoreCase(nombre)) {

                JOptionPane.showMessageDialog(null, "Libro ya existe");
                return;
            }
        }
        String edicion = entrada("Edicion del libro: ");
        String categoria = entrada("Ingrese la categoria del libro: ");
        int numEjemplares = Integer.parseInt(entrada("Numero de Ejemplares: "));
        Libro libro = new Libro(nombre, edicion, categoria, numEjemplares);
        listaLibro.add(libro);
        JOptionPane.showMessageDialog(null, "Se agrego el libro correctamente");

    }

    public void registraPrestamo() {
        if (listaLibro.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros disponibles");
            return;
        }
        String nombreLibro = entrada("INgrese el numero para el prestamo: ");
        Libro libroPrestado = null;
        for (Libro librop : listaLibro) {
            if (librop.getNombre().equalsIgnoreCase(nombreLibro)) {
                libroPrestado = librop;
                break;
            }
        }
        if (libroPrestado == null) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado");
            return;
        }
        if (libroPrestado.getNumElemplares() <= 0) {
            JOptionPane.showMessageDialog(null, "No hay ejemplares disponibles");
            return;

        }
        String nombreUsuario = entrada("Ingrese el nombre del usuario: ");
        String celular = entrada("Ingrese numero de celular: ");
        String direccion = entrada("Ingrese la direccion del Usuario: ");
        Prestamo prestamo = new Prestamo(libroPrestado, nombreUsuario, celular, direccion);
        listaPrestamo.add(prestamo);
        JOptionPane.showMessageDialog(null, "Prestamo Registrado Exitosamnete. \n fecha de devolucion:" + prestamo.getFechaDevolucion());

    }

    public void mostrarlibros() {
        if (listaLibro.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡¡¡NO hay libros en la lista!!!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < listaLibro.size(); i++) {
            stringBuilder.append("Libro").append(i + 1).append("\n");
            stringBuilder.append("Nombre: ").append(listaLibro.get(i).getNombre()).append("\n");
            stringBuilder.append("Edicion: ").append(listaLibro.get(i).getEdicion()).append("\n");
            stringBuilder.append("Categoria: ").append(listaLibro.get(i).getCategoria()).append("\n");
            stringBuilder.append("NumEjemplares").append(listaLibro.get(i).getNumElemplares()).append("\n");

        }
        JOptionPane.showMessageDialog(null, stringBuilder.toString());
    }

    public void buscarLibroPorNombre() {
        String nombre = entrada("Ingrese el nombre del libro a buscar: ");
        boolean encontrado = false;
        StringBuilder string = new StringBuilder();
        for (Libro libro : listaLibro) {
            if (libro.getNombre().equalsIgnoreCase(nombre)) {
                string.append("Nombre: ").append(libro.getNombre()).append("\n");
                string.append("Edicion: ").append(libro.getEdicion()).append("\n");
                string.append("Categoria: ").append(libro.getCategoria()).append("\n");
                string.append("NumEjemplares").append(libro.getNumElemplares()).append("\n");
            }
        }
        if (encontrado) {
            JOptionPane.showMessageDialog(null, "Libro econtrado: \n" + string);
        } else {
            JOptionPane.showMessageDialog(null, "Libro no econtrado : \n" + string);

        }

    }

    public void eliminarLibroPorNombre() {
        String nombre = entrada("Ingrese el nombre a eliminar:");
        boolean eliminado = listaLibro.removeIf(libro -> libro.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Libro elimidado exitosamnete:");
        } else {
            JOptionPane.showMessageDialog(null, "Libro no encontrado:");

        }
    }

    public void limpiarLista() {
        listaLibro.clear();
        JOptionPane.showMessageDialog(null, "Lista de libros limpiada");
    }

    public void generarReporte() {
        if (true) {
            JOptionPane.showMessageDialog(null, "no hay lobrod en la lista");
            return;
        }
        StringBuilder reporte = new StringBuilder();
        reporte.append("Reporte de inventario libros: \n");
        reporte.append("----------------------------- \n");
        reporte.append("Total Titulos \n").append(listaLibro.size()).append("\n");

        ArrayList<String> categorias = new ArrayList<>();
        for (Libro libro : listaLibro) {
            if (!categorias.contains(libro.getCategoria())) {
                categorias.add(libro.getCategoria());
            }
        }
        for (String categoria : categorias) {
            int count = 0;
            int totalEjemplares = 0;
            for (Libro libro : listaLibro) {
                if (libro.getCategoria().equalsIgnoreCase(categoria)) {
                    count++;
                    totalEjemplares += libro.getNumElemplares();
                }
            }
            reporte.append("Categoria: ").append(categoria).append("-")
                    .append(count).append("     Titulos     ").append(totalEjemplares).append("   Ejemplares  ");
        }
        JOptionPane.showMessageDialog(null, reporte.toString());
    }

    public void generarReportePrestamo() {
        if (listaPrestamo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay prestamos registrados ");
        }
        StringBuilder reporte = new StringBuilder();

        reporte.append("Reporte detallado de libros pretados: \n");
        reporte.append("----------------------------- \n");
        reporte.append("Total Titulos \n").append(listaLibro.size()).append("\n");

        for (Prestamo prestamos : listaPrestamo) {
            reporte.append("Libro: ").append(prestamos.getLibro().getNombre()).append("\n");
            reporte.append("Usuario: ").append(prestamos.getNombreUsuario()).append("\n");
            reporte.append("Celular: ").append(prestamos.getCelular()).append("\n");
            reporte.append("Direccion: ").append(prestamos.getDireccion()).append("\n");
            reporte.append("Fecha Prestamo: ").append(prestamos.getFechaPrestamo()).append("\n");
            reporte.append("Fecha Delovolucion: ").append(prestamos.getFechaDevolucion()).append("\n");
            reporte.append("Estado: ").append(prestamos.isAtrasado() ? "Atrasado" : " En plazo ").append("\n\n");

        }
        JOptionPane.showMessageDialog(null, reporte.toString());
    }

    public void verificarPrestamo() {
        if (listaPrestamo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "!!No hay prestamos registrados");
            return;
        }

        StringBuilder prestamosAtrasados = new StringBuilder();
        boolean hayAtrasos = false;

        for (Prestamo prestamo : listaPrestamo) {
            if (prestamo.isAtrasado()) {
                prestamosAtrasados.append("Usuario: ").append(prestamo.getNombreUsuario()).append("\n");
                prestamosAtrasados.append("Libro: ").append(prestamo.getLibro().getNombre()).append("\n");
                prestamosAtrasados.append("Fecha Devolucion: ").append(prestamo.getFechaDevolucion()).append("\n\n");

            }

        }
        if (hayAtrasos) {
            JOptionPane.showMessageDialog(null, "Préstamos atrasados: \n " + prestamosAtrasados);
        } else {
            JOptionPane.showMessageDialog(null, "!!No hay prestamos atrasados!!!");

        }

    }
}
