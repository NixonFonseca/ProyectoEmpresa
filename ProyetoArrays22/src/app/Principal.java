package app;

import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {
        RegistroPrestamo registro = new RegistroPrestamo();

        byte opcion;

        do {
            opcion = Byte.parseByte(JOptionPane.showInputDialog(
                    "Menu Principal\n" 
                    + "1.Agregar Libro\n"
                    + "2.Listar Libros\n"
                    + "3.Buscar Libro por Nombre\n"
                    + "4.Eliminar Libro por Nombre\n"
                    + "5.Limpiar Lista de Libros\n"
                    + "6.Generar Reporte Detallado de Libros\n"
                    + "7.Registrar Préstamo\n"
                    + "8.Verificar Préstamo\n"
                    + "9.Generar Reporte de libros Prestados\n"
                    +"10.Salir"
            ));
            switch (opcion) {
                case 1:
                    registro.agregarLibro();
                    break;
                    case 2:
                        registro.mostrarlibros();
                    
                    break;
                    case 3:
                        registro.buscarLibroPorNombre();
                        
                    
                    break;
                    case 4:
                    registro.eliminarLibroPorNombre();
                    break;
                    case 5:
                        registro.limpiarLista();
                    
                    break;
                    case 6:
                        registro.generarReporte();
                    
                    break;
                    case 7:
                    registro.registraPrestamo();
                    break;
                    case 8:
                    registro.verificarPrestamo();
                    break;
                    case 9:
                    registro.generarReportePrestamo();
                    break;
                    case 10:
                        JOptionPane.showMessageDialog(null, "Gracias por Utilizar el sistema");
                    
                    break;
                    
                default:
JOptionPane.showMessageDialog(null, "Opcion invalidad");
            }
        }while (opcion != 10); {            
            
        }
    }
}
