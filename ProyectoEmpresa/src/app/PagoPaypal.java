/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author PC_22
 */
class PagoPaypal extends Pago {

    String correoElectronico;

    public PagoPaypal(double monto,String correoElectronico) {
        super(monto);
        this.correoElectronico = correoElectronico;
    }

    @Override
    public void realizarPago() {
        System.out.println("Pago de $" + monto + " Realizado con Paypal: " + correoElectronico);
    }
}
