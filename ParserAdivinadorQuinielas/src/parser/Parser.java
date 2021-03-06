package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JProgressBar;

import caseCreator.CaseCreator;

import parserClasificacion.ParserClasificacion;
import parserResultados.ParserResultados;
import utils.StringUtils;

public class Parser {

	public static void main(String[] args) {
		parsearClasificaciones();

		parsearResultados();

		// Se crea el fichero de los casos juntando los parses de
		// clasificaciones y resultados
		CaseCreator creator = new CaseCreator();
		creator.juntarInfo();
	}
	
	public static void parse(JProgressBar pb) {

		pb.setString("Recuperando clasificaciones");
		pb.setValue(33);
		parsearClasificaciones();

		pb.setString("Recuperando resultados");
		pb.setValue(66);
		parsearResultados();

		// Se crea el fichero de los casos juntando los parses de
		// clasificaciones y resultados
		pb.setString("Creando base de casos");
		pb.setValue(80);
		CaseCreator creator = new CaseCreator();
		creator.juntarInfo();	
		pb.setString("");
		pb.setValue(100);
	}

	/**
	 * Lee de la web la información de las clasificaciones
	 */
	private static void parsearClasificaciones() {
		// Se coge el separador dependiendo del sistema en el que estemos
		// Si es Windows '\' y si es Linux '/'
		String separator = System.getProperty("file.separator");

		try {
			ParserClasificacion parserC = new ParserClasificacion();
			// Se parsean los datos de las clasificaciones
			for (int temporada = 107; temporada < 111; temporada++) {
				System.out.print("Extrayendo la información de la temporada "
						+ temporada + " [Clasificaciones]: |");

				FileWriter file = new FileWriter("Ficheros" + separator
						+ "ClasificacionesTemp" + temporada, false);
				PrintWriter writer = new PrintWriter(file);

				parserC.resetFile(writer);

				parserC
						.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t="
								+ temporada + "&j=1");
				parserC.setJornada(StringUtils.arreglarTildes(parserC.getJornada()));
				parserC.escribirPrimeraClasificacion(writer);

				int jornada = 1;
				// Mientras haya exito parseando las clasificaciones almacenamos
				// y guardamos en el fichero.
				while (parserC
						.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t="
								+ temporada + "&j=" + jornada)
						&& jornada < 38) {
					System.out.print("-");
					parserC.setJornada(StringUtils.arreglarTildes(parserC.getJornada()));
					parserC.writeInfo(writer);
					parserC.writeSeparator(writer);
					jornada++;
				}

				System.out.println("|");

				writer.close();
				file.close();
			}
			System.out.println("Clasificaciones parseadas");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lee de la web la información de los resultados
	 */
	private static void parsearResultados() {
		// Se cogue el separador dependiendo del sistema en el que estemos
		// Si es Windows '\' y si es Linux '/'
		String separator = System.getProperty("file.separator");

		ParserResultados p = new ParserResultados();
		try {
			// Se parsean los datos de los resultados
			for (int temporada = 107; temporada < 111; temporada++) {
				System.out.print("Extrayendo la información de la temporada "
						+ temporada + " [Resultados]: |");

				FileWriter file = new FileWriter("Ficheros" + separator
						+ "ResultadosTemp" + temporada, false);
				PrintWriter writer = new PrintWriter(file);

				p.parse("http://www.lfp.es/?tabid=154&Controltype=cal&g=1&t="
						+ temporada);
				p.writeInfo(writer);

				System.out.println('|');

				writer.close();
				file.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Resultados parseados");
	}
}