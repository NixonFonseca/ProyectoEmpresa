/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.Scanner;

/**
 *
 * @author PC_22
 */
public class Pago {
    protected  double monto;

    public Pago(double monto) {
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }
    public void realizarPago(){
        System.out.println("Pago realizar por $ " + monto);
    }
    public static Pago seleccionarMetodoPago(double monto){
        Scanner sc = new Scanner(System.in);
        System.out.println("Seleccione el metodo de pago: Tarjeta(T), PayPal(P), Efectivi(E)");
        String metodo = sc.nextLine();
        if (metodo.equalsIgnoreCase("T")) {
            System.out.println("Ingrese el numero de la tarjeta: ");
            String numeroTarjeta = sc.nextLine();
            System.out.println("Ingrese la Fecha de expiracion: ");
            String fechaExpiracion = sc.nextLine();
            return new PagoTarjeta(monto, numeroTarjeta, fechaExpiracion);
        }else if (metodo.equalsIgnoreCase("P")) {
            System.out.println("Ingrese el correo de PayPal: ");
            String correoElectronico = sc.nextLine();
            return new PagoPaypal(monto, correoElectronico);
            
        }else if (metodo.equalsIgnoreCase("E")) {
            return new PagoEfectivo(monto);
        
        }else{
            System.out.println("Metodo de pago no válido");
            return null;
        }
    }
}
