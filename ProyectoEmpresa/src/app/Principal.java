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
public class Principal {

    public static void main(String[] args) {
        Cliente cliente = Cliente.crearCliente();
        System.out.println("Clinete Crado. ¿Que desea hacer?");
        System.out.println("1. Actualizar Cliente ");
        System.out.println("2. Eliminar Cliente ");
        System.out.println("3. Continuar sin cambios ");
        Scanner sc = new Scanner(System.in);
        int opcionClinete = sc.nextInt();
        if (opcionClinete == 1) {
            cliente.actualizarCliente();
        }else if (opcionClinete == 2) {
            cliente.eliminarCliente();
        }
        
        Orden orden = Orden.crearOrden(cliente);
        orden.mostrarOrden();
        
        Pago pago = Pago.seleccionarMetodoPago(orden.calcularTotal());
        if (pago != null) {
            pago.realizarPago();
            
            Envio envio = Envio.crearEnvio(cliente);
            envio.procesarEnvio();
            Factura factura = new Factura(cliente,orden.getProductos(),orden.getCantidades(),orden.calcularTotal(),envio);
            factura.imprimirFactura();
        }else{
            System.out.println("El pago no se puedo realizar");
        }

    }
}
