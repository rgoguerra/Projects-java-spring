import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class application {

	private static final String FILENAME = "./fuzzy-search.txt";
	private static final int TAMINCREMENTO = 2;

	public static void main(String args[]) {

		int index = 0;
		
		//args = new String[] {"list"};

		if (args.length == 0) {
			printMessage();
			System.exit(0);
		}

		index = validaComando(args[0]);

		if (index == 0) {
			printMessage();
			System.exit(0);

		} else if (index == 1) {
			list();
		} else if (index == 2) {
			if (args.length == 2) {
				addPersona(args[1]);
			} else {
				printMessage();
				System.exit(0);
			}
		} else if (index == 3) {
			if (args.length == 2) {
				searchInfo(args[1]);
			} else {
				printMessage();
				System.exit(0);
			}

		}

		
	}

	/**
     * Valida la entrada de la linea de comandos 
     * 
     * @param      params   primer parametro de la linea de comando, que contine la instrucción a realizar
     * @return     el número que representa el comando validado            
     */
	public static int validaComando(String argumento) {
		int nValido = 0;
		String token = argumento.trim();

		if (token.equals("list")) {
			nValido = 1;
		}
		if (token.equals("add")) {
			nValido = 2;
		}
		if (token.equals("fuzzy-search")) {
			nValido = 3;
		}

		return nValido;

	}
	
	/**
     * Agrega elementos dentro del archivo con
     * formato JSON que representan un Arreglo.
     * 
     * El segundo parametro de la linea de comandos contiene la información del
     * elementos que será agragado al archivo.
     *
     * @param      params   segundo parametro de la linea de comando, que contiene el registro.
     * @exception  Exception  si el/la 
     *             {@code File} manejo de apertura de archivo de texto
     *             
     */
	public static void addPersona(String sTexto) {
		JSONObject json = new JSONObject();
		String sLlave;
		String sValor;

		String[] arrOfStr = sTexto.split(":", 2);

		sLlave = arrOfStr[0].substring(arrOfStr[0].indexOf("{") + 1, arrOfStr[0].length());
		// System.out.println(sLlave.toString());

		sValor = arrOfStr[1].substring(0, arrOfStr[1].indexOf("}"));
		// System.out.println(sValor.toString());

		sTexto.indexOf("{");
		json.put(sLlave, sValor);

		File file = new File(FILENAME);

		try {

			if (!file.exists()) {

				JSONArray nameList = new JSONArray();
				nameList.add(json);

				file.createNewFile();

				FileWriter jsonFileWriter = new FileWriter(file.getAbsoluteFile());
				jsonFileWriter.write(nameList.toJSONString());

				jsonFileWriter.flush();
				jsonFileWriter.close();

			} else {

				JSONParser jsonParser = new JSONParser();

				FileReader reader = new FileReader(FILENAME);
				Object obj = jsonParser.parse(reader);

				JSONArray nameReadList = (JSONArray) obj;
				nameReadList.add(json);

				FileWriter jsonFileWriter = new FileWriter(file.getAbsoluteFile());
				jsonFileWriter.write(nameReadList.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
			}

			System.out.println("Usuario agregado");
			
			json = null;
			file = null;
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

	}

	/**
     * Muestra el contenido de los elementos dentro archivo con formato JSON
     * y los ordena alfabeticamente.
     * 
     * 
     * @exception  Exception  si el/la
     *             {@code File} manejo de apertura de archivo de texto
     *             
     */
	public static void list() {

		JSONParser parser = new JSONParser();
		List<JSONObject> records = new ArrayList<JSONObject>();

		File file = new File(FILENAME);

		if (!file.exists()) {
			System.out.println("[]");
			System.exit(0);
		}

		try {
			FileReader fileReader = new FileReader(file);
			JSONArray jsonArray = (JSONArray) parser.parse(fileReader);

			JSONArray jsonArr = (JSONArray) jsonArray;

			JSONArray sortedJsonArray = new JSONArray();

			List<JSONObject> jsonValues = new ArrayList<JSONObject>();
			for (int j = 0; j < jsonArr.size(); j++) {
				jsonValues.add((JSONObject) jsonArr.get(j));
			}
			Collections.sort(jsonValues, new Comparator<JSONObject>() {

				private static final String KEY_NAME = "name";

				@Override
				public int compare(JSONObject a, JSONObject b) {
					String valA = new String();
					String valB = new String();

					try {
						valA = (String) a.get(KEY_NAME);
						valB = (String) b.get(KEY_NAME);
					} catch (Exception e) {

					}

					return valA.compareTo(valB);

				}
			});

			for (int k = 0; k < jsonArr.size(); k++) {
				sortedJsonArray.add(jsonValues.get(k));
			}

			Iterator<?> v = sortedJsonArray.iterator();

			while (v.hasNext()) {
				JSONObject obj = (JSONObject) v.next();
				System.out.println(obj.toJSONString());
			}

			jsonArr = null;
			fileReader = null;
			file = null;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
     * Realiza la búsqueda de información dentro del archivo con
     * formato JSON de un arreglo se elementos.
     * 
     * El segundo parametro de la linea de comaandos se verifica mediente una expresión regular
     *
     * @param      params   segundo parametro de la linea de comando, que contiene la expresion de búsqueda
     * @exception  Exception  si el/la 
     *             {@code File} manejo de apertura de archivo de texto
     *             
     */
	public static void searchInfo(String params) {

		String[] arrOfStr = params.split(":", 2);

		String sLlave = arrOfStr[0].substring(arrOfStr[0].indexOf("{") + 1, arrOfStr[0].length());

		if (!sLlave.equals("search")) {
			System.out.println("No esta bien formado el commando de búsqueda");
			System.exit(0);
		}

		String sBusqueda = arrOfStr[1].substring(0, arrOfStr[1].indexOf("}"));
		// System.out.println(sBusqueda);

		JSONParser parser = new JSONParser();
		List<JSONObject> records = new ArrayList<JSONObject>();

		File file = new File(FILENAME);
		FileReader fileReader;
		int nCoincide = 0;

		if (!file.exists()) {
			System.out.println("[]");
			System.exit(0);
		}

		try {
			fileReader = new FileReader(file);

			JSONArray jsonArr = (JSONArray) (JSONArray) parser.parse(fileReader);

			JSONArray searchJsonArray = new JSONArray();

			List<JSONObject> jsonValues = new ArrayList<JSONObject>();
			for (int j = 0; j < jsonArr.size(); j++) {

				JSONObject obj = (JSONObject) jsonArr.get(j);

				String valA = new String();
				valA = (String) obj.get("name");

				int tamExpresion = sBusqueda.length();
				int incremento = 0;
				int tamInc = TAMINCREMENTO;
				
				int sigInc = tamInc;
				String nvExpr = ".*(" + sBusqueda + ").*";
				if (tamExpresion > tamInc) {

					for (int k = 0; k < tamExpresion; k += tamInc) {
						if ((sigInc) > tamExpresion) {
							break;
						}
						nvExpr = nvExpr + " | .*(" + sBusqueda.substring(incremento, sigInc) + ").*";
						sigInc = sigInc + tamInc;

						incremento = incremento + tamInc;
					}

				}

				Pattern pattern = Pattern.compile(nvExpr, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(valA);
				if (matcher.find()) {
					System.out.println(obj.toJSONString());
					nCoincide++;
				}

			}

			if (nCoincide == 0) {
				System.out.println("Sin coincidencias");
			}
			jsonArr = null;
			fileReader = null;
			file = null;
			

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		
	}

	/**
     * Muestra en la consola de salida los posibles comandos de operación
     *         
     */
	public static void printMessage() {
		System.out.println("Utilice: ./applition [opciones] [parametros]");
		System.out.println("");
		System.out.println("ejemplos: ./applition list ");
		System.out.println("                           Muestra los elementos");
		System.out.println("");
		System.out.println("ejemplos: ./applition add {“name”:“Juan Antonio Perez”} ");
		System.out.println("                           Agrega un nevo elemento");
		System.out.println("");
		System.out.println("ejemplos: ./application fuzzy-search {“search”:“Alver”} ");
		System.out.println("                           Busca dentro del contenido");

	}
   
   
  
}