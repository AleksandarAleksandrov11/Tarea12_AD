package tarea12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MetodosSQL {

	private static Scanner sc = new Scanner(System.in);
	private static final String ruta = "jdbc:mysql://localhost/Alumnos09_2";
	private static final String usuario = "root";
	private static final String contraseña = "manager";
	private static Connection conexion;
	private static Statement sentencia;
	private static ResultSet resul;
	private static BufferedWriter bfw;
	private static BufferedReader bfr;

	public MetodosSQL() {
		establecerConexion();
	}

	public void establecerConexion() {
		try {
			conexion = DriverManager.getConnection(ruta, usuario, contraseña);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertarGrupo() {

		try {
			sentencia = conexion.createStatement();
			System.out.println("Dime el nombre del grupo a añadir");
			String nombreGrupo = sc.next();

			String sql = "INSERT INTO grupos (nombre_grupo) VALUES ('" + nombreGrupo + "')";

			int filasAfectadas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filasAfectadas);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void insertarAlumno() {

		try {
			Alumno a = new Alumno();
			a.cargarAlumno(sc, 1);

			sentencia = conexion.createStatement();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fechaFormatoSQL = sdf.format(a.getfNacimiento());
			String sql = "INSERT INTO alumnos (NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo) "
					+ "VALUES (" + a.getNia() + ", '" + a.getNombre() + "', '" + a.getApellidos() + "', '"
					+ a.getGenero() + "', '" + fechaFormatoSQL + "', '" + a.getCiclo() + "', " + a.getCurso() + ", "
					+ a.getGrupo() + ")";

			int filasAfectadas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filasAfectadas);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void mostrarAlumnosConGrupo() {

		try {
			sentencia = conexion.createStatement();
			String sql = "SELECT a.NIA, a.nombre, a.apellidos, a.genero, a.fecha_nacimiento, a.ciclo, a.curso, g.id_grupo, g.nombre_grupo "
					+ "FROM alumnos a " + "LEFT JOIN grupos g ON a.id_grupo = g.id_grupo";

			resul = sentencia.executeQuery(sql);

			while (resul.next()) {
				System.out.printf(
						"NIA: %d, Nombre: %s, Apellidos: %s, Genero: %s, Fecha_Nacimiento: %s, Ciclo: %s, Curso: %s, NumeroGrupo: %s, NombreGrupo: %s%n",
						resul.getInt("NIA"), resul.getString("nombre"), resul.getString("apellidos"),
						resul.getString("genero"), resul.getString("fecha_nacimiento"), resul.getString("ciclo"),
						resul.getString("curso"), resul.getString("id_grupo"), resul.getString("nombre_grupo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				resul.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void guardarAlumnosEnTXT() {

		try {
			sentencia = conexion.createStatement();

			bfw = new BufferedWriter(new FileWriter(new File("alumnosGuardados.txt")));

			String sql = "SELECT a.NIA, a.nombre, a.apellidos, a.genero, a.fecha_nacimiento, a.ciclo, a.curso, g.id_grupo, g.nombre_grupo "
					+ "FROM alumnos a " + "LEFT JOIN grupos g ON a.id_grupo = g.id_grupo";

			resul = sentencia.executeQuery(sql);

			while (resul.next()) {
				String datos = String.format("%d, %s, %s, %s, %s, %s, %s, %s, %s%n", resul.getInt("NIA"),
						resul.getString("nombre"), resul.getString("apellidos"), resul.getString("genero"),
						resul.getString("fecha_nacimiento"), resul.getString("ciclo"), resul.getString("curso"),
						resul.getString("id_grupo"), resul.getString("nombre_grupo"));
				bfw.write(datos);
				bfw.flush();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				bfw.close();
				resul.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void leerTxtGuardarBD() {

		try {
			sc.nextLine();
			System.out.println("Dime la ruta del archivo txt");
			String rutaArchivo = sc.next();

			sentencia = conexion.createStatement();

			bfr = new BufferedReader(new FileReader(new File(rutaArchivo)));

			String linea;

			while ((linea = bfr.readLine()) != null) {

				String[] datos = linea.split(", ");

				String sql = "INSERT INTO alumnos (NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo) "
						+ "VALUES ('" + datos[0] + "', '" + datos[1] + "', '" + datos[2] + "', '" + datos[3] + "', '"
						+ datos[4] + "', '" + datos[5] + "', '" + datos[6] + "', '" + datos[7] + "')";

				int filasAfectadas = sentencia.executeUpdate(sql);
				System.out.println("Filas afectadas: " + filasAfectadas);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				bfr.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void modificarNombreConPK() {

		try {

			System.out.println("Dime la PK del alumno");
			int pk = sc.nextInt();

			System.out.println("Dime el nuevo nombre del alumno");
			String nuevoNombre = sc.next();

			sentencia = conexion.createStatement();

			String sql = "UPDATE alumnos SET nombre = '" + nuevoNombre + "' WHERE NIA = '" + pk + "'";

			int filasAfectadas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filasAfectadas);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void eliminarAlumnoConPK() {

		try {

			System.out.println("Dime la PK del alumno a eliminar");
			int pk = sc.nextInt();

			sentencia = conexion.createStatement();

			String sql = "DELETE FROM alumnos WHERE NIA = '" + pk + "'";

			int filasAfectadas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filasAfectadas);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void mostrarGrupos() {

		try {
			sentencia = conexion.createStatement();

			String sql = "SELECT id_grupo, nombre_grupo FROM grupos";

			resul = sentencia.executeQuery(sql);

			System.out.println("Grupos disponibles: ");
			while (resul.next()) {

				System.out.printf("ID_GRUPO: %d, NOMBRE_GRUPO: %s%n", resul.getInt("id_grupo"),
						resul.getString("nombre_grupo"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				resul.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void eliminarAlumnosDelGrupo() {

		mostrarGrupos();

		try {

			System.out.println("Dime el id del grupo de donde quieras eliminar los alumnos");
			int id = sc.nextInt();

			sentencia = conexion.createStatement();

			String sql = "DELETE FROM alumnos WHERE id_grupo = '" + id + "'";

			int filasAfectadas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filasAfectadas);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				resul.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void guardarGruposEnXML() {
		Statement sentenciaAlumnos = null;
		ResultSet resulAlumnos = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation imp = builder.getDOMImplementation();
			Document doc = imp.createDocument(null, "Grupos", null);
			doc.setXmlVersion("1.0");

			sentencia = conexion.createStatement();

			String sql = "SELECT id_grupo, nombre_grupo FROM grupos";

			resul = sentencia.executeQuery(sql);

			while (resul.next()) {

				Element grupo = doc.createElement("grupo");
				doc.getDocumentElement().appendChild(grupo);

				Element id = doc.createElement("id_grupo");
				Text textoId = doc.createTextNode(resul.getString("id_grupo"));
				id.appendChild(textoId);
				grupo.appendChild(id);

				Element nombre = doc.createElement("nombre_grupo");
				Text textoNombre = doc.createTextNode(resul.getString("nombre_grupo"));
				nombre.appendChild(textoNombre);
				grupo.appendChild(nombre);

				sentenciaAlumnos = conexion.createStatement();

				String sqlAlumnos = "SELECT NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo FROM alumnos "
						+ "WHERE id_grupo = '" + resul.getInt("id_grupo") + "'";

				resulAlumnos = sentenciaAlumnos.executeQuery(sqlAlumnos);

				while (resulAlumnos.next()) {

					Element alumno = doc.createElement("alumno");
					alumno.setAttribute("NIA", resulAlumnos.getString("NIA"));
					alumno.setAttribute("nombre", resulAlumnos.getString("nombre"));
					alumno.setAttribute("apellidos", resulAlumnos.getString("apellidos"));
					alumno.setAttribute("genero", resulAlumnos.getString("genero"));
					alumno.setAttribute("fecha_nacimiento", resulAlumnos.getString("fecha_nacimiento"));
					alumno.setAttribute("ciclo", resulAlumnos.getString("ciclo"));
					alumno.setAttribute("curso", resulAlumnos.getString("curso"));
					alumno.setAttribute("id_grupo", resulAlumnos.getString("id_grupo"));
					grupo.appendChild(alumno);
				}

			}

			DOMSource source = new DOMSource(doc);
			StreamResult res = new StreamResult(new File("gruposGuardados.xml"));
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.transform(source, res);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				resul.close();
				sentenciaAlumnos.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void leerXmlInsertarBD() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("gruposLeer.xml"));
			doc.getDocumentElement().normalize();

			sentencia = conexion.createStatement();

			NodeList grupos = doc.getElementsByTagName("grupo");

			// Por cada grupo encontrado en el XML:
			for (int i = 0; i < grupos.getLength(); i++) {
				Element grupo = (Element) grupos.item(i);

				// Guardamos en variables los valores del id y del nombre.
				String idGrupo = grupo.getElementsByTagName("id_grupo").item(0).getTextContent();
				String nombreGrupo = grupo.getElementsByTagName("nombre_grupo").item(0).getTextContent();

				// Insertamos los grupos primero en la base de datos.
				String sqlGrupo = "INSERT INTO grupos (id_grupo, nombre_grupo) VALUES ('" + idGrupo + "', '"
						+ nombreGrupo + "')";
				int filasAfectadas = sentencia.executeUpdate(sqlGrupo);
				System.out.println("Filas afectadas: " + filasAfectadas);

				NodeList alumnos = grupo.getElementsByTagName("alumno");

				// Por cada alumno encontrado en el XML:
				for (int j = 0; j < alumnos.getLength(); j++) {
					Element alumno = (Element) alumnos.item(j);

					// Guardamos cada atributo del alumno en variables.
					String nia = alumno.getAttribute("NIA");
					String nombre = alumno.getAttribute("nombre");
					String apellidos = alumno.getAttribute("apellidos");
					String genero = alumno.getAttribute("genero");
					String fechaNacimiento = alumno.getAttribute("fecha_nacimiento");
					String ciclo = alumno.getAttribute("ciclo");
					String curso = alumno.getAttribute("curso");

					// Insertamos los alumnos en la base de datos.
					String sqlAlumno = "INSERT INTO alumnos (NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo) VALUES "
							+ "('" + nia + "', '" + nombre + "', '" + apellidos + "', '" + genero + "', '"
							+ fechaNacimiento + "', '" + ciclo + "', '" + curso + "', '" + idGrupo + "')";
					int filasAfectadas2 = sentencia.executeUpdate(sqlAlumno);
					System.out.println("Filas afectadas: " + filasAfectadas2);
				}
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void guardarGruposEnJSON() {
		Statement sentenciaAlumnos = null;
		ResultSet resulAlumnos = null;
		Grupo grupo;
		ArrayList<Alumno> alumnos = null;
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();;
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();

		try(FileWriter writer = new FileWriter(new File("gruposGuardados.json"));) {
			
			
			sentencia = conexion.createStatement();

			String sql = "SELECT id_grupo, nombre_grupo FROM grupos";

			resul = sentencia.executeQuery(sql);

			while (resul.next()) {

				sentenciaAlumnos = conexion.createStatement();
				String sqlAlumnos = "SELECT NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo FROM alumnos "
						+ "WHERE id_grupo = '" + resul.getInt("id_grupo") + "'";
				resulAlumnos = sentenciaAlumnos.executeQuery(sqlAlumnos);
				
				alumnos = new ArrayList<Alumno>();
						
				while (resulAlumnos.next()) {
					alumnos.add(new Alumno(resulAlumnos.getInt("NIA"), resulAlumnos.getString("nombre"), resulAlumnos.getString("apellidos"),
							resulAlumnos.getString("genero").charAt(0), resulAlumnos.getDate("fecha_nacimiento"), resulAlumnos.getString("ciclo"),
							resulAlumnos.getString("curso"), resulAlumnos.getInt("id_grupo")));
				}
				
				grupo = new Grupo(resul.getInt("id_grupo"), resul.getString("nombre_grupo"), alumnos);
				grupos.add(grupo);

			}
			
			gson.toJson(grupos,writer);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				sentencia.close();
				resul.close();
				sentenciaAlumnos.close();
				resulAlumnos.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void leerJsonInsertarBD() {
		
		Statement sentenciaAlumnos = null;		
		
		try(FileReader reader = new FileReader(new File("gruposLeer.json"));) {
			
			JsonElement jsonElement = JsonParser.parseReader(reader);
			
			JsonArray gruposArray = jsonElement.getAsJsonArray();
			
			for(JsonElement elementoGrupo : gruposArray) {
				
				JsonObject grupoObjeto = elementoGrupo.getAsJsonObject();
				
				int idGrupo = grupoObjeto.get("id_grupo").getAsInt();
				String nombreGrupo = grupoObjeto.get("nombre_grupo").getAsString();
				
				sentencia = conexion.createStatement();
				
				String sql = "INSERT INTO grupos (id_grupo, nombre_grupo) VALUES ('"+idGrupo+"','"+nombreGrupo+"')";
				
				int filasAfectadas = sentencia.executeUpdate(sql);
				System.out.println("Filas afectadas: "+ filasAfectadas);
				
				JsonArray alumnos = grupoObjeto.getAsJsonArray("alumnos");
				
				for(JsonElement alumno : alumnos) {
					
					JsonObject alumnoInsertar = alumno.getAsJsonObject();
					
					int NIA = alumnoInsertar.get("nia").getAsInt();
					String nombre = alumnoInsertar.get("nombre").getAsString();
					String apellidos = alumnoInsertar.get("apellidos").getAsString();
					char genero = alumnoInsertar.get("genero").getAsString().charAt(0);
					String fecha = alumnoInsertar.get("fNacimiento").getAsString();
					String ciclo = alumnoInsertar.get("ciclo").getAsString();
					String curso = alumnoInsertar.get("curso").getAsString();
					int grupo = alumnoInsertar.get("grupo").getAsInt();
					
					sentenciaAlumnos = conexion.createStatement();
					
					String sqlAlumno = "INSERT INTO alumnos (NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo) VALUES "
							+ "('" + NIA + "', '" + nombre + "', '" + apellidos + "', '" + genero + "', '"
							+ fecha + "', '" + ciclo + "', '" + curso + "', '" + grupo + "')";
					
					int filasAfectadas2 = sentencia.executeUpdate(sqlAlumno);
					System.out.println("Filas afectadas: "+ filasAfectadas2);
	
				}
	
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
