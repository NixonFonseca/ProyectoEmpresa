/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author PC_22
 */
public class Hotel {

    private List<Habitacion> habitaciones;
    private List<Huesped> huespedes;
    private List<Reserva> reservas;

    public Hotel() {
        habitaciones = new ArrayList<>();
        huespedes = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public String entrada(String texto) {
        return JOptionPane.showInputDialog(texto);
    }

    public void agregarHabitaciones() {
        int numero = Integer.parseInt(entrada("Digite el numero de habitacion:"));
        String tipo = entrada("Digite el tipo de habitacion (individual, doble):");
        double precioNoche = Double.parseDouble(entrada("Digite el numero de habitacion:"));

        Habitacion habitacion = new Habitacion(numero, tipo, precioNoche);
        habitaciones.add(habitacion);
        JOptionPane.showMessageDialog(null, "Habitacion agregada correctamente");

    }

    public void registrarHuesped() {
        String nombre = entrada("Digite el nombre del huesped:");
        String documento = entrada("Digite el documento del huesped:");
        String correoElectronico = entrada("Digite el correo del huesped:");
        String telefono = entrada("Digite el telefono del huesped:");

        Huesped huesped = new Huesped(nombre, documento, correoElectronico, telefono);
        huespedes.add(huesped);
        JOptionPane.showMessageDialog(null, "Huesped registrado correctamente");

    }

    public void realizarReserva() {
        int numHabitacion = Integer.parseInt(JOptionPane.showInputDialog("Digite el numero de la habitacion"));
        String documentoHu = entrada("Digite el documento del huesped");
        LocalDate fechaInicio = LocalDate.parse(entrada("Digie la fecha de inicio:"));
        LocalDate fechaFin = LocalDate.parse(entrada("Digie la fecha de fin:"));
        Habitacion habitacion = habitaciones.stream()
                .filter(h -> h.getNumero() == numHabitacion)
                .findFirst()
                .orElse(null);
        Huesped huesped = huespedes.stream()
                .filter(h -> h.getDocumento().equalsIgnoreCase(documentoHu))
                .findFirst()
                .orElse(null);

        if (habitacion != null && huesped != null) {
            Reserva reserva = new Reserva(habitacion, huesped, fechaInicio, fechaFin, numHabitacion);
            reservas.add(reserva);
            JOptionPane.showMessageDialog(null, "Reversa exitosa" + reserva);
        } else {
            JOptionPane.showMessageDialog(null, "Error, habitacion o huesped no encontrado");
        }

    }

    public void buscarHabitacionesDisponibles() {
        LocalDate fechaInicio = LocalDate.parse(entrada("Digite la fecha de inicio"));
        LocalDate fechaFin = LocalDate.parse(entrada("Digite la fecha de Fin"));
        List<Habitacion> disponibles = habitaciones.stream()
                .filter(habitacion -> reservas.stream().noneMatch(
                reserva -> reserva.getHabitacion().equals(habitacion)
                && (reserva.getFechaInicio().isBefore(fechaFin)
                && reserva.getFechaFin().isAfter(fechaInicio))))
                .collect(Collectors.toList());
        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encuentra habitaciones");
        }else{
            StringBuilder mensaje = new StringBuilder("Habitaciones disponibles:\n");
            for (Habitacion h : disponibles) {
                mensaje.append(h).append("\n");
                
            }
            JOptionPane.showMessageDialog(null, mensaje.toString());
        }
    }

    public void ListReservas() {
            if (reservas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay reserbas resgistradas");
        }else  {
            StringBuilder mensaje = new StringBuilder("Reservas: \n");
                for (Reserva r : reservas) {
                    mensaje.append(r).append("\n");
                }
                JOptionPane.showMessageDialog(null, mensaje.toString());
        }
    }

}
