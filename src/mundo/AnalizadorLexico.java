/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Teoría de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Diseño original por: Leonardo A. Hernández R. - Agosto 2008 - Marzo 2009
 * Modificado y usado por: Claudia E. Quiceno R- Julio 2021
 * 
 * Modificado y usado por: Braian Camilo Piedrahita Rodriguez, Esteban Sanchez Carranza
 * Daniel Ceballos Giraldo y Angy Paola Tabares Acevedo - Abril 2022
 * 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package mundo;

import java.util.ArrayList;

/**
 * Clase que modela un analizador léxico
 */

public class AnalizadorLexico {

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Extrae los tokens de un código fuente dado
	 * 
	 * @param cod - código al cual se le van a extraer los tokens - !=null
	 * @return vector con los tokens
	 */
	public ArrayList extraerTokens(String cod) {
		Token token;
		ArrayList vectorTokens = new ArrayList();

		// El primer token se extrae a partir de posición cero
		int i = 0;

		// Ciclo para extraer todos los tokens
		while (i < cod.length()) {
			// Extrae el token de la posición i
			token = extraerSiguienteToken(cod, i);
			vectorTokens.add(token);
			i = token.darIndiceSiguiente();
		}
		return vectorTokens;
	}

	/**
	 * Extrae el token de la cadena cod a partir de la posición i, basándose en el
	 * Autómata
	 * 
	 * @param cod - código al cual se le va a extraer un token - codigo!=null
	 * @param i   - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	public Token extraerSiguienteToken(String cod, int i) {
		Token token;

		// Intenta extraer un entero
		token = extraerEntero(cod, i);

		if (token != null)
			return token;

		// Intenta extraer un float
		token = extraerFloat(cod, i);

		if (token != null)
			return token;

		// Intenta extraer una cadena
		token = extraerCadena(cod, i);

		if (token != null)
			return token;

		// Intenta extraer un caracter
		token = extraerCaracter(cod, i);

		if (token != null)
			return token;

		// Intenta extraer la palabra para enteros
		token = extraerPalabraEnteros(cod, i);

		if (token != null)
			return token;

		// Intenta extraer la palabra para reales
		token = extraerPalabraReales(cod, i);

		if (token != null)
			return token;

		// Intenta extraer un operador aditivo
		token = extraerOperadorAritmetico(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un operador relacional
		token = extraerOperadorRelacional(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un operador logico
		token = extraerOperadorLogico(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un operador de asignación
		token = extraerOperadorAsignacion(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el simbolo de abrir
		token = extraerSimboloAbrir(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el simbolo de cerrar
		token = extraerSimboloCerrar(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un separador
		token = extraerSeparador(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada para caracteres
		token = extraerReservadaCaracter(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada para cadenas
		token = extraerReservadaCadena(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada para cadenas
		token = extraerReservadaHilo(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada para cadenas
		token = extraerReservadaBreak(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un terminal
		token = extraerTerminal(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerIdentificador(cod, i);
		if (token != null)
			return token;

		// Intenta extraer la palabra reservada para bucle
		token = extraerExpresionBucle(cod, i);
		if (token != null)
			return token;

		// Intenta extraer la palabra reservada para desicion
		token = extraerExpresionDesicion(cod, i);
		if (token != null)
			return token;

		// Intenta extraer la palabra reservada para clase
		token = extraerExpresionClase(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el identificador del nombre de una variable
		token = extraerIdentificadorVariable(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el identificador del nombre de un metodo
		token = extraerIdentificadorMetodo(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el identificador del nombre de una clase
		token = extraerIdentificadorClase(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerIdentificador(cod, i);
		if (token != null)
			return token;

		// Extrae un token no reconocido
		token = extraerNoReconocido(cod, i);
		return token;
	}

	/**
	 * Intenta extraer la palabra reservada de los enteros que es In de la cadena
	 * cod a partir de la posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            de los enteros - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada de los enteros - 0<=i<codigo.length()
	 * @return el token de la palabra reservada de los enteros In o NULL, si el
	 *         token en la posición dada no cumple con lo esperado. El Token se
	 *         compone de el lexema, el tipo y la posición del siguiente lexema.
	 */
	public Token extraerPalabraEnteros(String cod, int i) {

		int j = i;
		String lex;

		if (esLetra(cod.charAt(j)) && cod.charAt(j) == 'I') {
			j++;
			if (j < cod.length() && esLetra(cod.charAt(j)) && cod.charAt(j) == 'n') {
				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.RESERVADA_ENTEROS, j);
				return token;
			}
		}

		return null;
	}

	/**
	 * Intenta extraer la palabra reservada de los reales que es Fl de la cadena cod
	 * a partir de la posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            de los reales - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada de los reales - 0<=i<codigo.length()
	 * @return el token de la palabra reservada de los reales Fl o NULL, si el token
	 *         en la posición dada no cumple con lo esperado. El Token se compone de
	 *         el lexema, el tipo y la posición del siguiente lexema.
	 */
	public Token extraerPalabraReales(String cod, int i) {

		int j = i;
		String lex;

		if (esLetra(cod.charAt(j)) && cod.charAt(j) == 'F') {
			j++;
			if (j < cod.length() && esLetra(cod.charAt(j)) && cod.charAt(j) == 'l') {
				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.RESERVADA_REALES, j);
				return token;
			}
		}

		return null;
	}

	/**
	 * Intenta extraer un entero de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un entero
	 *            - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posición dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posición del
	 *         siguiente lexema.
	 */

	public Token extraerEntero(String cod, int i) {

		int j = i;
		String lex;
		while (j < cod.length() && esDigito(cod.charAt(j))) {
			j++;
		}
		if (j != i) {

			if (j < cod.length()) {
				if (esLetra(cod.charAt(j)) && cod.charAt(j) == 'I') {
					j++;
					if (esLetra(cod.charAt(j)) && cod.charAt(j) == 'n') {
						j++;
						lex = cod.substring(i, j);
						Token token = new Token(lex, Token.VALORASIGNACIONENTERO, j);
						return token;
					}
				}
				return null;
			}
		}

		return null;
	}

	/**
	 * Intenta extraer un real de la cadena cod a partir de la posición i, basándose
	 * en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un real -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un real -
	 *            0<=i<codigo.length()
	 * @return el token correspondiente a un real (Ej:4.5Fl), si el token en la
	 *         posición dada no cumple con lo esperado. El Token se compone de el
	 *         lexema, el tipo y la posición del siguiente lexema.
	 */

	public Token extraerFloat(String cod, int i) {

		int j = i;
		String lex;

		while (j < cod.length() && esDigito(cod.charAt(j))) {
			j++;
		}
		if (j != i) {

			if (j < cod.length() && cod.charAt(j) == '.') {

				j++;
				if (j < cod.length()) {
					while (j < cod.length() && esDigito(cod.charAt(j))) {
						j++;
					}
				}
			}
			if (j < cod.length()) {
				if (esLetra(cod.charAt(j)) && cod.charAt(j) == 'F') {
					j++;
					if (j < cod.length() && esLetra(cod.charAt(j)) && cod.charAt(j) == 'l') {
						j++;
						lex = cod.substring(i, j);
						Token token = new Token(lex, Token.VALORASIGNACIONFLOAT, j);
						return token;
					}
				}
			}

		}

		return null;
	}

	/**
	 * Intenta extraer una cadena valida de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            de los reales - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer una cadena
	 *            valida - 0<=i<codigo.length()
	 * @return el token de una cadena valida Ej: <<Hola Mundo>> o NULL, si el token
	 *         en la posición dada no cumple con lo esperado. El Token se compone de
	 *         el lexema, el tipo y la posición del siguiente lexema.
	 */
	public Token extraerCadena(String cod, int i) {

		int j = i;
		String lex;

		if (cod.charAt(j) == '<') {
			j++;
			if (j < cod.length() && cod.charAt(j) == '<') {
				j++;
				while (j < cod.length()) {

					if (cod.charAt(j) == '.') {
						j++;
						if (j < cod.length() && cod.charAt(j) == '.') {
							j++;
							if ((cod.charAt(j) == 'n' || cod.charAt(j) == 't' || cod.charAt(j) == 'b'
									|| cod.charAt(j) == '<')) {
								j++;
							}
						}
					}
					if (j < cod.length() && cod.charAt(j) == '>') {
						j++;
						if (j < cod.length() && cod.charAt(j) == '>') {
							j++;
							lex = cod.substring(i, j);
							Token token = new Token(lex, Token.VALORASIGNACIONCADENA, j);
							return token;
						} else {
							return null;
						}
					} else {
						int ascii = (int) cod.charAt(j);

						if (ascii == 60) {

							return null;

						} else {
							j++;
						}

					}

				}
			} else {
				return null;
			}

		}
		return null;
	}

	/**
	 * Intenta extraer un caracter valido de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un caracter -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un
	 *            caracter - 0<=i<codigo.length()
	 * @return el token de un caracter Ej: <H> o NULL, si el token en la posición
	 *         dada no cumple con lo esperado. El Token se compone de el lexema, el
	 *         tipo y la posición del siguiente lexema.
	 */
	public Token extraerCaracter(String cod, int i) {
		int j = i;
		String lex;

		if (cod.charAt(j) == '<') {
			j++;
			if (j < cod.length()) {
				if (cod.charAt(j) == '.') {
					j++;
					if (j < cod.length() && cod.charAt(j) == '.') {
						j++;
						if ((cod.charAt(j) == 'n' || cod.charAt(j) == 't' || cod.charAt(j) == 'b'
								|| cod.charAt(j) == '<')) {
							j++;
						}
					}
				} else {
					int ascii = (int) cod.charAt(j);
					if (ascii != 60 && ascii != 62) {
						j++;
					}
				}
				if (j < cod.length() && cod.charAt(j) == '>') {
					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.VALORASIGNACIONCARACTER, j);
					return token;
				} else {
					return null;
				}

			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Intenta extraer un operador aritmético de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador aritmético -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador aritmético - 0<=i<codigo.length()
	 * @return el token operador aritmético o NULL, si el token en la posición dada no
	 *         es un operador aritmético.El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */
	public Token extraerOperadorAritmetico(String cod, int i) {

		int j;
		String lex;

		if (cod.charAt(i) == '$') {

			j = i + 1;

			if (j < cod.length() && cod.charAt(j) == '+' || cod.charAt(j) == '-' || cod.charAt(j) == '*'
					|| cod.charAt(j) == '/' || cod.charAt(j) == '%') {

				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.OPERADORARITMETICO, j);
				return token;
			}

		}

		return null;
	}

	/**
	 * Intenta extraer un operador relacional de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador
	 *            relacional - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador relacional - 0<=i<codigo.length()
	 * @return el token operador relacional o NULL, si el token en la posición dada
	 *         no es un operador relacional. El Token se compone de el lexema, el
	 *         tipo y la posición del siguiente lexema.
	 */
	public Token extraerOperadorRelacional(String cod, int i) {

		int j;
		String lex;

		if (cod.charAt(i) == '→' || cod.charAt(i) == '←') {

			j = i + 1;

			if (j < cod.length() && cod.charAt(j) == ':') {

				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.OPERADORELACIONAL, j);
				return token;

			} else {

				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.OPERADORELACIONAL, j);
				return token;
			}

		} else {

			if (cod.charAt(i) == ':') {

				j = i + 1;

				if (j < cod.length() && cod.charAt(j) == ':') {

					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.OPERADORELACIONAL, j);
					return token;

				} else {

					if (j < cod.length() && cod.charAt(j) == '←') {

						j++;

						if (j < cod.length() && cod.charAt(j) == '→') {

							j++;
							lex = cod.substring(i, j);
							Token token = new Token(lex, Token.OPERADORELACIONAL, j);
							return token;

						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Intenta extraer un operador lógico de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador lógico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador lógico - 0<=i<codigo.length()
	 * @return el token operador lógico o NULL, si el token en la posición dada no
	 *         es un operador lógico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */
	public Token extraerOperadorLogico(String cod, int i) {

		int j;
		String lex;

		if (cod.charAt(i) == '@') {

			j = i + 1;

			if (j < cod.length() && cod.charAt(j) == 'y' || cod.charAt(j) == 'o' || cod.charAt(j) == '!') {

				j++;

				if (j < cod.length() && cod.charAt(j) == '@') {

					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.OPERADORLOGICO, j);
					return token;
				}
			}

		}

		return null;
	}

	/**
	 * Intenta extraer un operador de asignación de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador de
	 *            asignación - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador de asignación - 0<=i<codigo.length()
	 * @return el token operador asignación o NULL, si el token en la posición dada
	 *         no es un operador de asignación. El Token se compone de el lexema, el
	 *         tipo y la posición del siguiente lexema.
	 */
	public Token extraerOperadorAsignacion(String cod, int i) {

		if (cod.charAt(i) == ':') {

			String lex;
			int j = i + 1;
			lex = cod.substring(i, j);
			Token token = new Token(lex, Token.OPERADORASIGNACION, j);
			return token;
		}

		return null;
	}

	/**
	 * Intenta extraer el símbolo de abrir de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el símbolo de abrir -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el símbolo
	 *            de abrir - 0<=i<codigo.length()
	 * @return el token del símbolo de abrir o NULL, si el token en la posición dada
	 *         no es el símbolo de abrir. El Token se compone de el lexema, el tipo
	 *         y la posición del siguiente lexema.
	 */
	public Token extraerSimboloAbrir(String cod, int i) {

		int j;
		String lex;

		if (cod.charAt(i) == '◄') {

			j = i + 1;

			if (j < cod.length() && cod.charAt(j) == '[' || cod.charAt(j) == '{' || cod.charAt(j) == '(') {

				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.SIMBOLOABRIR, j);
				return token;

			}
		}

		return null;
	}

	/**
	 * Intenta extraer el símbolo de cerrar de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el símbolo de cerrar
	 *            - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el símbolo
	 *            de cerrar - 0<=i<codigo.length()
	 * @return el token del símbolo de cerrar o NULL, si el token en la posición
	 *         dada no es el símbolo de cerrar. El Token se compone de el lexema, el
	 *         tipo y la posición del siguiente lexema.
	 */
	public Token extraerSimboloCerrar(String cod, int i) {

		int j;
		String lex;

		if (cod.charAt(i) == ']' || cod.charAt(i) == '}' || cod.charAt(i) == ')') {

			j = i + 1;

			if (j < cod.length() && cod.charAt(j) == '►') {

				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.SIMBOLOCERRAR, j);
				return token;

			}
		}

		return null;
	}

	/**
	 * Intenta extraer un terminal de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un identficador -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token terminal o NULL, si el token en la posición dada no es un
	 *         identificador. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */

	public Token extraerTerminal(String cod, int i) {

		if (cod.charAt(i) == '.') {

			int j = i + 1;
			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.TERMINAL, j);
			return token;

		}
		return null;
	}

	/**
	 * Intenta extraer un separador de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un identficador -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un
	 *            separadpr - 0<=indice<codigo.length()
	 * @return el token separador o NULL, si el token en la posición dada no es un
	 *         identificador. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */

	public Token extraerSeparador(String cod, int i) {
		if (cod.charAt(i) == '~') {

			int j = i + 1;
			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.SEPARADOR, j);
			return token;

		}
		return null;
	}

	/**
	 * Intenta extraer palabra reservada para caracteres de la cadena cod a partir
	 * de la posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            para caracteres - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada para caracteres - 0<=i<codigo.length()
	 * @return el token palabra reservada para cadena de caracteres o NULL, si el
	 *         token en la posición dada no es parte de la palabra reservada para
	 *         caracteres. El Token se compone de el lexema, el tipo y la posición
	 *         del siguiente lexema.
	 */
	public Token extraerReservadaCaracter(String cod, int i) {
		if (cod.charAt(i) == 'c' && esLetra(cod.charAt(i))) {
			int j = i + 1;
			if (j < cod.length() && cod.charAt(j) == 'h') {
				j++;

				String lex = cod.substring(i, j);
				Token token = new Token(lex, Token.RESERVADA_CARACTER, j);
				return token;
			}

		}
		return null;
	}

	/**
	 * Intenta extraer palabra reservada para cadena de caracteres de la cadena cod
	 * a partir de la posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            para cadena de caracteres - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada para cadena de caracteres - 0<=i<codigo.length()
	 * @return el token palabra reservada para cadena de caracteres o NULL, si el
	 *         token en la posición dada no es parte de la palabra reservada para
	 *         cadena de caracteres. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */
	public Token extraerReservadaCadena(String cod, int i) {
		if (cod.charAt(i) == 'w' && esLetra(cod.charAt(i))) {
			int j = i + 1;
			if (j < cod.length() && cod.charAt(j) == 'r') {
				j++;

				if (j < cod.length() && cod.charAt(j) == 'd') {
					j++;
					String lex = cod.substring(i, j);
					Token token = new Token(lex, Token.RESERVADA_CADENA, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer palabra reservada para hilos de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            para hilos - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada para hilos - 0<=i<codigo.length()
	 * @return el token palabra reservada para cadena de caracteres o NULL, si el
	 *         token en la posición dada no es parte de la palabra reservada para
	 *         hilos. El Token se compone de el lexema, el tipo y la posición del
	 *         siguiente lexema.
	 */
	public Token extraerReservadaHilo(String cod, int i) {
		if (cod.charAt(i) == 'f' && esLetra(cod.charAt(i))) {
			int j = i + 1;
			if (j < cod.length() && cod.charAt(j) == 'i') {
				j++;

				if (j < cod.length() && cod.charAt(j) == 'l') {
					j++;
					String lex = cod.substring(i, j);
					Token token = new Token(lex, Token.RESERVADA_HILO, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer palabra reservada para break cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            para break - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada para break - 0<=i<codigo.length()
	 * @return el token palabra reservada para cadena de caracteres o NULL, si el
	 *         token en la posición dada no es parte de la palabra reservada para
	 *         break El Token se compone de el lexema, el tipo y la posición del
	 *         siguiente lexema.
	 */
	public Token extraerReservadaBreak(String cod, int i) {
		if (cod.charAt(i) == 'j' && esLetra(cod.charAt(i))) {
			int j = i + 1;
			if (j < cod.length() && cod.charAt(j) == 'u') {
				j++;

				if (j < cod.length() && cod.charAt(j) == 'm') {
					j++;

					if (j < cod.length() && cod.charAt(j) == 'p') {
						j++;
						String lex = cod.substring(i, j);
						Token token = new Token(lex, Token.RESERVADA_BREAK, j);
						return token;
					}
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer un identificador de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un identficador -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posición dada no es
	 *         un identificador. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */

	/**
	 * Intenta extraer la palabra reservada de bucle de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada .
	 * @return el token palabra reservada bucle o NULL, si el token en la posición
	 *         dada no es la palabra reservada. El Token se compone de el lexema, el
	 *         tipo y la posición del siguiente lexema.
	 */
	public Token extraerExpresionBucle(String cod, int i) {
		int j = i + 1;
		if (cod.charAt(i) == 'd') {
			if (cod.charAt(j) == 'u') {
				j++;
				if (cod.charAt(j) == 'r') {
					j++;
					if (cod.charAt(j) == 'i') {
						j++;
						if (cod.charAt(j) == 'n') {
							j++;
							if (cod.charAt(j) == 'g') {
								j++;
								String lexema = cod.substring(i, j);
								Token token = new Token(lexema, Token.PALABRARESERVADA_CICLO, j);
								return token;
							}
						}
					}
				}
			}

		}

		return null;
	}

	/**
	 * Intenta extraer la palabra reservada de desicion de la cadena cod a partir de
	 * la posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada .
	 * @return el token palabra reservada de desicion o NULL, si el token en la
	 *         posición dada no es la palabra reservada. El Token se compone de el
	 *         lexema, el tipo y la posición del siguiente lexema.
	 */
	public Token extraerExpresionDesicion(String cod, int i) {
		int j = i + 1;
		if (cod.charAt(i) == 'w') {
			if (cod.charAt(j) == 'h') {
				j++;
				if (cod.charAt(j) == 'e') {
					j++;
					if (cod.charAt(j) == 'n') {
						j++;
						String lexema = cod.substring(i, j);
						Token token = new Token(lexema, Token.PALABRARESERVADA_DESICION, j);
						return token;
					}
				}
			}

		}

		return null;
	}

	/**
	 * Intenta extraer la palabra reservada de clase de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer la palabra reservada
	 *            - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer la palabra
	 *            reservada .
	 * @return el token palabra reservada de clase o NULL, si el token en la
	 *         posición dada no es la palabra reservada. El Token se compone de el
	 *         lexema, el tipo y la posición del siguiente lexema.
	 */
	public Token extraerExpresionClase(String cod, int i) {
		int j = i + 1;

		if (cod.charAt(i) == 'c') {
			if (cod.charAt(j) == 'l') {
				j++;
				if (cod.charAt(j) == 's') {
					j++;
					String lexema = cod.substring(i, j);
					Token token = new Token(lexema, Token.PALABRARESERVADA_CLASE, j);
					return token;
				}
			}

		}

		return null;
	}

	/**
	 * Intenta extraer identificador de variable de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el identificador -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            identificador.
	 * @return el token identificador o NULL, si el token en la posición dada no es
	 *         el identificador. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */
	public Token extraerIdentificadorVariable(String cod, int i) {
		boolean centinela = false;
		if (cod.charAt(i) == '#') {
			int j = i + 1;
			if (j < cod.length() && esMinuscula(cod.charAt(j))) {
				j++;

				while ((j < cod.length() && esMinuscula(cod.charAt(j))) && centinela == false) {
					j++;

					while ((j < cod.length() && esDigito(cod.charAt(j)))) {
						j++;
						centinela = true;
					}
				}

				String lex = cod.substring(i, j);
				if (esPalabraReservada(cod.substring(i + 1, j))) {
					return null;
				}
				Token token = new Token(lex, Token.IDENTIFICADORVARIABLE, j);
				return token;
			}
		}

		return null;
	}

	public boolean esPalabraReservada(String palabra) {
		if (palabra.equals("during") || palabra.equals("cls") || palabra.equals("when")) {
			return true;
		}
		return false;
	}

	/**
	 * Intenta extraer el identificador de nombre de un metodo de la cadena cod a
	 * partir de la posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el identificador del
	 *            nombre del metodo - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            identificador del nombre del metodo.
	 * @return el token identificador o NULL, si el token no es el identificador del
	 *         nombre del metodo. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */
	public Token extraerIdentificadorMetodo(String cod, int i) {
		boolean centinela = false;
		if (esMayuscula(cod.charAt(i))) {
			int j = i + 1;
			if (j < cod.length() && esMinuscula(cod.charAt(j))) {
				j++;

				while ((j < cod.length() && esMinuscula(cod.charAt(j))) && centinela == false) {
					j++;

					while ((j < cod.length() && esDigito(cod.charAt(j)))) {
						j++;
						centinela = true;
					}
				}

				String lex = cod.substring(i, j);
				Token token = new Token(lex, Token.IDENTIFICADORMETODO, j);
				return token;
			}
		}

		return null;
	}

	/**
	 * Intenta extraer el identificador de nombre de una clase de la cadena cod a
	 * partir de la posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el identificador del
	 *            nombre de una clase - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            identificador del nombre del metodo.
	 * @return el token identificador o NULL, si el token no es el identificador del
	 *         nombre de una clase. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
	 */
	public Token extraerIdentificadorClase(String cod, int i) {
		boolean centinela = false;

		int j = i;
		if (j < cod.length() && esMinuscula(cod.charAt(j))) {
			j++;

			while ((j < cod.length() && esMinuscula(cod.charAt(j))) && centinela == false) {
				j++;

				while ((j < cod.length() && esDigito(cod.charAt(j)))) {
					j++;
					centinela = true;
				}
			}

			String lex = cod.substring(i, j);
			if (esPalabraReservada(lex)) {
				return null;
			}
			Token token = new Token(lex, Token.IDENTIFICADORCLASE, j);
			return token;
		}

		return null;
	}

	public boolean esMayuscula(char caracter) {
		if (esLetra(caracter)) {
			if (Character.isUpperCase(caracter)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Determina si una letra es minuscula
	 * 
	 * @param caracter - Carácter que se va a analizar - caracter!=null,
	 * @return true o false según el carácter sea minuscula o no
	 */
	public boolean esMinuscula(char caracter) {
		if (esLetra(caracter)) {
			if (Character.isLowerCase(caracter)) {
				return true;
			}
		}

		return false;
	}

	public Token extraerIdentificador(String cod, int i) {
		if (cod.charAt(i) == '_') {
			int j = i + 1;
			while (j < cod.length() && esLetra(cod.charAt(j)))
				j++;
			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.IDENTIFICADOR, j);
			return token;
		}
		return null;
	}

	/**
	 * Extraer un lexema no reconocido de la cadena cod a partir de la posición i.
	 * Antes de utilizar este método, debe haberse intentado todos los otros métodos
	 * para los otros tipos de token
	 * 
	 * @param cod - código al cual se le va a extraer el token no reconocido -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a extraer el token no
	 *            reconocido - 0<=indice<codigo.length()
	 * @return el token no reconocido. El Token se compone de lexema, el tipo y la
	 *         posición del siguiente lexema.
	 * 
	 */
	public Token extraerNoReconocido(String cod, int i) {
		String lexema = cod.substring(i, i + 1);
		int j = i + 1;
		Token token = new Token(lexema, Token.NORECONOCIDO, j);
		return token;
	}

	/**
	 * Determina si un carácter es un dígito
	 * 
	 * @param caracter - Carácter que se va a analizar - caracter!=null,
	 * @return true o false según el carácter sea un dígito o no
	 */
	public boolean esDigito(char caracter) {
		return caracter >= '0' && caracter <= '9';
	}

	/**
	 * Determina si un carácter es una letra
	 * 
	 * @param caracter - Carácter que se va a analizar - caracter!=null,
	 * @return true o false según el carácter sea una letra o no
	 */
	public boolean esLetra(char caracter) {
		return (caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z');
	}

}
