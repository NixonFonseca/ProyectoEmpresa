/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author PC_22
 */
public class Reserva {
    private Habitacion habitacion;
    private Huesped huesped;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double costoTotal;

    public Reserva(Habitacion habitacion, Huesped huesped, LocalDate fechaInicio, LocalDate fechaFin, double costoTotal) {
        this.habitacion = habitacion;
        this.huesped = huesped;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costoTotal = costoTotal;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getCostoTotal() {
        return costoTotal;
    }
    public double calcularCostoTotal(){
        long dias = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
        return  dias * habitacion.getPrecioNoche();
    }

    @Override
    public int hashCode() {
      return Objects.hash(habitacion,huesped,fechaInicio,fechaFin);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reserva other = (Reserva) obj;
        if (!Objects.equals(this.habitacion, other.habitacion)) {
            return false;
        }
        if (!Objects.equals(this.huesped, other.huesped)) {
            return false;
        }
        return Objects.equals(this.fechaFin, other.fechaFin);
    }

    
    @Override
    public String toString() {
        return "Reserva{" + "habitacion=" + habitacion + ", huesped=" + huesped + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", costoTotal=" + costoTotal + '}';
    }
    
}
