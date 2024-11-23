package tarea12;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Alumno {

    private int nia;
    private String nombre;
    private String apellidos;
    private char genero;

    private Date fNacimiento;
    
    private String ciclo;
    private String curso;
    private int grupo;

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

    public Alumno() {
    }

    public int getNia() {
        return nia;
    }

    public void setNia(int nia) {
        this.nia = nia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public Date getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

	public void cargarAlumno(Scanner sc, int i) throws ParseException {

    	System.out.println("Dime el nia del alumno " + i);
        setNia(sc.nextInt());
        sc.nextLine();
        
        System.out.println("Dime el nombre del alumno " + i);
        setNombre(sc.nextLine());

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
        setCiclo(sc.nextLine());

        System.out.println("Dime el curso del alumno " + i);
        setCurso(sc.nextLine());

        System.out.println("Dime el grupo del alumno " + i);
        setGrupo(sc.nextInt());
        sc.nextLine();
    }
}
