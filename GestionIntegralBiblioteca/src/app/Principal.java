/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Principal {

    private static Biblioteca biblioteca = new Biblioteca();
    private static Logger logger = new FileLogger();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Usuario bibliotecario1 = new Usuario("1", "admin", "bibliotecario", "admin@biblioteca.com");
    Usuario lector1 = new Usuario("2", "lector1", "lector", "lector1@biblioteca.com");

    public static void main(String[] args) {
        // Cargar datos iniciales (puedes personalizar esto)
        cargarDatosIniciales();

        while (true) {
            String[] options = {"Iniciar sesión", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Sistema de Gestión de Biblioteca",
                    "Menú Principal", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options, options[0]);

            if (opcion == 1) {
                System.out.println("Saliendo...");
                break;
            } else if (opcion == 0) {
                autenticarUsuario();
            }
        }
    }

    private static void cargarDatosIniciales() {
        // Agregar usuarios iniciales
        biblioteca.agregarUsuario(new Usuario("u1", "pass1", "lector", "Juan Pérez"));
        biblioteca.agregarUsuario(new Usuario("u2", "pass2", "bibliotecario", "Ana Gómez"));
     
        // Agregar libros iniciales
        biblioteca.agregarLibro(new Libro("9783", "Cien años de soledad", "Gabriel García Márquez", "Novela", 5, true),
                new Usuario("u2", "pass2", "bibliotecario", "Ana Gómez"));

        biblioteca.agregarLibro(new Libro("9780", "El código Da Vinci", "Dan Brown", "Thriller", 3, true),
                new Usuario("u2", "pass2", "bibliotecario", "Ana Gómez"));
    }

    private static void autenticarUsuario() {
        String id = JOptionPane.showInputDialog("Ingrese ID de usuario:");
        String password = JOptionPane.showInputDialog("Ingrese contraseña:");

        Usuario usuario = biblioteca.autenticar(id, password);
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Autenticación fallida.");
            logger.logError("Falló la autenticación para ID de usuario: " + id);
            return;
        }

        JOptionPane.showMessageDialog(null, "Bienvenido, " + usuario.getNombre() + "!");
        if ("bibliotecario".equals(usuario.getTipoUsuario())) {
            menuBibliotecario(usuario);
        } else {
            menuLector(usuario);
        }
    }

    private static void menuBibliotecario(Usuario bibliotecario) {
        while (true) {
            String[] Usuario = {"Agregar usuario", "Eliminar Usuario", "Ver prestamos", "Salir"};
            String[] options = {"Agregar libro", "Eliminar libro", "Modificar libro", "Usuarios", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Menú Bibliotecario",
                    "Opciones", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options, options[0]);

            if (opcion == 4) {
                break;  // Salir del menú de bibliotecario
            }

            switch (opcion) {
                case 0:  // Agregar libro
                    agregarLibro(bibliotecario);
                    break;
                case 1:  // Eliminar libro
                    eliminarLibro(bibliotecario);
                    break;
                case 2:  // Modificar libro
                    modificarLibro(bibliotecario);
                    break;
                case 3:

                    int usuario = JOptionPane.showOptionDialog(null, "Menú Bibliotecario",
                            "Opciones", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                            Usuario, Usuario[0]);
                    if (usuario == 3) {
                        break;
                    }
                    switch (usuario) {
                        case 0:
                            biblioteca.agregarUsuario(bibliotecario);
                            break;
                        case 1:
                            biblioteca.eliminarUsuario(bibliotecario.getId());
                            break;
                        case 2:
                            biblioteca.mostrarUsuariosConPrestamos();
                            break;
                        default:

                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }
        }
    }

    private static void menuLector(Usuario lector) {
        while (true) {
            String[] options = {"Consultar catálogo", "Solicitar préstamo", "Devolver préstamo", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Menú Lector",
                    "Opciones", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options, options[0]);

            if (opcion == 3) {
                break;  // Salir del menú de lector
            }

            switch (opcion) {
                case 0:  // Consultar catálogo
                    consultarCatalogo();
                    break;
                case 1:  // Solicitar préstamo
                    solicitarPrestamo(lector);
                    break;
                case 2:  // Devolver préstamo
                    devolverPrestamo();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }
        }
    }

    private static void agregarLibro(Usuario bibliotecario) {
        String isbn = JOptionPane.showInputDialog("Ingrese ISBN del libro:");
        String titulo = JOptionPane.showInputDialog("Ingrese título del libro:");
        String autor = JOptionPane.showInputDialog("Ingrese autor del libro:");
        String genero = JOptionPane.showInputDialog("Ingrese género del libro:");
        int numeroCopias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese número de copias:"));

        Libro libro = new Libro(isbn, titulo, autor, genero, numeroCopias, true);
        biblioteca.agregarLibro(libro, bibliotecario);
    }

    private static void eliminarLibro(Usuario bibliotecario) {
        String isbn = JOptionPane.showInputDialog("Ingrese ISBN del libro a eliminar:");
        biblioteca.eliminarLibro(isbn, bibliotecario);   
    }

    private static void modificarLibro(Usuario bibliotecario) {
        String isbn = JOptionPane.showInputDialog("Ingrese ISBN del libro a modificar:");
        String nuevoTitulo = JOptionPane.showInputDialog("Ingrese nuevo título:");
        int nuevoNumeroCopias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese nuevo número de copias:"));

        biblioteca.modificarLibro(isbn, nuevoTitulo, nuevoNumeroCopias, bibliotecario);
    }

    private static void consultarCatalogo() {
        List<Libro> catalogo = biblioteca.getCatalogo();  // Método getCatalogo() debe estar en Biblioteca

        StringBuilder sb = new StringBuilder();
        for (Libro libro : catalogo) {
            sb.append("ISBN: ").append(libro.getIsbn()).append("\n")
                    .append(", Título: ").append(libro.getTitulo()).append("\n")
                    .append(", Autor: ").append(libro.getAutor()).append("\n")
                    .append(", Género: ").append(libro.getGenero()).append("\n")
                    .append(", Disponibles: ").append(libro.getNumeroCopias()).append("\n")
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Catálogo de Libros", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void solicitarPrestamo(Usuario lector) {
        String isbn = JOptionPane.showInputDialog("Ingrese ISBN del libro para solicitar préstamo:");
        Libro libro = biblioteca.buscarLibroPorIsbn(isbn);  // Método buscarLibroPorIsbn() debe estar en Biblioteca

        if (libro != null) {
            biblioteca.solicitarPrestamo(libro, lector);
        } else {
            JOptionPane.showMessageDialog(null, "Libro no encontrado.");
        }
    }

    private static void devolverPrestamo() {
        String isbn = JOptionPane.showInputDialog("Ingrese ISBN del libro para devolver:");
        // Aquí se asume que tienes una manera de encontrar el préstamo asociado por ISBN.
        Prestamo prestamo = biblioteca.buscarPrestamoPorIsbn(isbn);  // Método buscarPrestamoPorIsbn() debe estar en Biblioteca

        if (prestamo != null) {
            biblioteca.devolverPrestamo(prestamo);
        } else {
            JOptionPane.showMessageDialog(null, "Préstamo no encontrado.");
        }
    }
}
