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
public class Envio {

    private String direccionEnvio;
    private String metodoEnvio;
    private double costoEnvio;

    public Envio(String direccionEnvio, String metodoEnvio, double costoEnvio) {
        this.direccionEnvio = direccionEnvio;
        this.metodoEnvio = metodoEnvio;
        this.costoEnvio = costoEnvio;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public String getMetodoEnvio() {
        return metodoEnvio;
    }

    public double getCostoEnvio() {
        return costoEnvio;
    }

    public void procesarEnvio() {
        System.out.println("Enviado a: " + direccionEnvio + "Tipo Envio: " + metodoEnvio);
        System.out.println("Costo envio: $" + costoEnvio );
    }
    public static Envio crearEnvio(Cliente cliente){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite el metodo de envio (Normal, Rapido): ");
        String metodoEnvio = sc.nextLine();
        System.out.println("Digite el costo del envio: ");
        double costoEnvio = sc.nextDouble();
        return new Envio(cliente.getDireccion(), metodoEnvio,costoEnvio  );
    }

}
