package tarea12;

import java.util.Scanner;

public class MenuPrincipal {

    private static Scanner sc = new Scanner(System.in);
    private static MetodosSQL metodo = new MetodosSQL();

    public static void main(String[] args) {
        int respuesta;
        
        do {
            mostrarMenu();
            respuesta = sc.nextInt();
            switch (respuesta) {
                case 1:
                    metodo.insertarGrupo();
                    break;
                case 2:
                    metodo.insertarAlumno();
                    break;
                case 3:
                    metodo.mostrarAlumnosConGrupo();
                    break;
                case 4:
                    metodo.guardarAlumnosEnTXT();
                    break;
                case 5:
                    metodo.leerTxtGuardarBD();
                    break;
                case 6:
                    metodo.modificarNombreConPK();
                    break;
                case 7:
                    metodo.eliminarAlumnoConPK();
                    break;
                case 8:
                    metodo.eliminarAlumnosDelGrupo();
                    break;
                case 9:
                    metodo.guardarGruposEnXML();
                    break;
                case 10:
                    metodo.leerXmlInsertarBD();
                    break;
                case 11:
                    metodo.guardarGruposEnJSON();
                    break;
                case 12:
                    metodo.leerJsonInsertarBD();
                    break;
                case 0:
                    System.out.println("Has salido");
                    metodo.cerrarConexion();
                    break;
            }
        } while (respuesta != 0);
    }

    public static void mostrarMenu() {
    	System.out.println();
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Insertar Grupo");
        System.out.println("2. Insertar Alumno");
        System.out.println("3. Mostrar Alumnos con su Grupo");
        System.out.println("4. Guardar Alumnos en archivo TXT");
        System.out.println("5. Leer archivo TXT y guardar en BD");
        System.out.println("6. Modificar Nombre de Alumno por PK");
        System.out.println("7. Eliminar Alumno por PK");
        System.out.println("8. Eliminar Alumnos de un Grupo");
        System.out.println("9. Guardar Grupos en archivo XML");
        System.out.println("10. Leer archivo XML e Insertar en BD");
        System.out.println("11. Guardar Grupos en archivo JSON");
        System.out.println("12. Leer archivo JSON e Insertar en BD");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
        System.out.println();
    }
}
