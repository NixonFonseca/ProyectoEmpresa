/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author PC_22
 */
public class PagoEfectivo extends Pago{

    public PagoEfectivo(double monto) {
        super(monto);
    }
     @Override
    public void realizarPago() {
        System.out.println("Pago de $" + monto + " Realizado En efectivo: "  );
    }
}
