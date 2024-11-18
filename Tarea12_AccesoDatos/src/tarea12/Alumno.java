package tarea12;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * La clase Alumno representa a un alumno con atributos como NIA, nombre, apellidos,
 * género, fecha de nacimiento, ciclo, curso y grupo.
 * Esta clase permite almacenar los datos básicos de un alumno.
 *
 *
 * @author Aleksandar Aleksandrov
 */
public class Alumno {

    private int nia;
    private String nombre;
    private String apellidos;
    private char genero;

    private Date fNacimiento;
    
    private String ciclo;
    private String curso;
    private int grupo;
	

    /**
     * Constructor que inicializa todos los atributos del alumno.
     *
     * @param nia Número de identificación del alumno.
     * @param nombre Nombre del alumno.
     * @param apellidos Apellidos del alumno.
     * @param genero Género del alumno ('M' para masculino, 'F' para femenino).
     * @param fNacimiento Fecha de nacimiento del alumno.
     * @param ciclo Ciclo en el que está matriculado el alumno.
     * @param curso Curso en el que está matriculado el alumno.
     * @param grupo Grupo al que pertenece el alumno.
     */
    public Alumno(int nia, String nombre, String apellidos, char genero, Date fNacimiento,
                  String ciclo, String curso, int grupo) {
        this.nia = nia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.ciclo = ciclo;
        this.curso = curso;
        this.grupo = grupo;
        this.genero = genero;
        this.fNacimiento = fNacimiento;
    }

    /**
     * Constructor por defecto que crea un Alumno sin atributos.
     */
    public Alumno() {
    }

    /**
     * Devuelve el NIA del alumno.
     *
     * @return El número de identificación del alumno.
     */
    public int getNia() {
        return nia;
    }

    /**
     * Establece el NIA del alumno.
     *
     * @param nia El número de identificación del alumno.
     */
    public void setNia(int nia) {
        this.nia = nia;
    }

    /**
     * Devuelve el nombre del alumno.
     *
     * @return El nombre del alumno.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del alumno.
     *
     * @param nombre El nombre del alumno.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve los apellidos del alumno.
     *
     * @return Los apellidos del alumno.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del alumno.
     *
     * @param apellidos Los apellidos del alumno.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Devuelve el ciclo del alumno.
     *
     * @return El ciclo del alumno.
     */
    public String getCiclo() {
        return ciclo;
    }

    /**
     * Establece el ciclo del alumno.
     *
     * @param ciclo El ciclo del alumno.
     */
    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * Devuelve el curso del alumno.
     *
     * @return El curso del alumno.
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Establece el curso del alumno.
     *
     * @param curso El curso del alumno.
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Devuelve el grupo del alumno.
     *
     * @return El grupo del alumno.
     */
    public int getGrupo() {
        return grupo;
    }

    /**
     * Establece el grupo del alumno.
     *
     * @param grupo El grupo del alumno.
     */
    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    /**
     * Devuelve el género del alumno.
     *
     * @return El género del alumno.
     */
    public char getGenero() {
        return genero;
    }

    /**
     * Establece el género del alumno.
     *
     * @param genero El género del alumno.
     */
    public void setGenero(char genero) {
        this.genero = genero;
    }

    /**
     * Devuelve la fecha de nacimiento del alumno.
     *
     * @return La fecha de nacimiento del alumno.
     */
    public Date getfNacimiento() {
        return fNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del alumno.
     *
     * @param fNacimiento La fecha de nacimiento del alumno.
     */
    public void setfNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    /**
     * Método que permite crear alumnos pidiendo al usuario los datos correspondientes.
     * 
     *
     * @param sc El objeto Scanner utilizado para leer la entrada del usuario.
     * @param i El número del alumno.
     * @throws ParseException Si ocurre un error en el formato de la fecha de nacimiento.
     */
	public void cargarAlumno(Scanner sc, int i) throws ParseException {

    	System.out.println("Dime el nia del alumno " + i);
        setNia(sc.nextInt());

        System.out.println("Dime el nombre del alumno " + i);
        setNombre(sc.next());

        sc.nextLine();
        System.out.println("Dime los apellidos del alumno " + i);
        setApellidos(sc.nextLine());


        System.out.println("Dime el genero del alumno " + i);
        setGenero(sc.next().charAt(0));
        sc.nextLine();

        System.out.println("Dime la fecha de nacimiento del alumno " + i);
        String fechaFormatoTexto = sc.nextLine();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        setfNacimiento(formato.parse(fechaFormatoTexto));

        System.out.println("Dime el ciclo del alumno " + i);
        setCiclo(sc.next());

        System.out.println("Dime el curso del alumno " + i);
        setCurso(sc.next());

        System.out.println("Dime el grupo del alumno " + i);
        setGrupo(sc.nextInt());

    }
}
