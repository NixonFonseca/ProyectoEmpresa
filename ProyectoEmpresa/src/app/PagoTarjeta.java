/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author PC_22
 */
 class PagoTarjeta extends Pago {

    private String numeroTarjeta;
    private String fechaExpedicion;

    public PagoTarjeta(double monto,String numeroTarjeta, String fechaExpedicion) {
        super(monto);
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpedicion = fechaExpedicion;
    }
@Override
    public void realizarPago() {
    System.out.println("Pago de $" + monto + " Realizado con Tarjeta: "+ numeroTarjeta);
    
    }

}
