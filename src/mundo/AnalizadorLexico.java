/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Teoría de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Diseño original por: Leonardo A. Hernández R. - Agosto 2008 - Marzo 2009
 * Modificado y usado por: Claudia E. Quiceno R- Julio 2021
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
     * @param cod - código al cual se le van a extraer los tokens - !=null
     * @return vector con los tokens
     */
    public ArrayList extraerTokens( String cod )
    {
    	Token token;
    	ArrayList vectorTokens = new ArrayList();

	    // El primer token se extrae a partir de posición cero
    	int i = 0;

    	// Ciclo para extraer todos los tokens
    	while( i < cod.length() )
		{
	        // Extrae el token de la posición i
			token = extraerSiguienteToken( cod, i);
	        vectorTokens.add( token );
	        i = token.darIndiceSiguiente();
    	}
		return vectorTokens;
    }

    /**
     * Extrae el token de la cadena cod a partir de la posición i, basándose en el Autómata
     * @param cod - código al cual se le va a extraer un token - codigo!=null
     * @param i - posición a partir de la cual se va a extraer el token  - i>=0
     * @return token que se extrajo de la cadena
     */
    public Token extraerSiguienteToken( String cod, int i )
    {
    	Token token;

		// Intenta extraer un entero
		token = extraerEntero(cod, i);
		if ( token != null )
			return token;
    	
    	// Intenta extraer un operador aditivo
		token = extraerOperadorAditivo(cod, i);
		if ( token != null )
			return token;
		
    	// Intenta extraer un operador relacional
		token = extraerOperadorRelacional(cod, i);
		if ( token != null )
			return token;

		// Intenta extraer un operador logico
		token = extraerOperadorLogico(cod, i);
		if ( token != null )
			return token;

		// Intenta extraer un operador de asignación
		token = extraerOperadorAsignacion(cod, i);
		if ( token != null )
			return token;

		// Intenta extraer el simbolo de abrir
		token = extraerSimboloAbrir(cod, i);
		if ( token != null )
			return token;
		
		// Intenta extraer el simbolo de cerrar
		token = extraerSimboloCerrar(cod, i);
		if ( token != null )
			return token;
		
		// Intenta extraer un separador
		token = extraerSeparador( cod, i);
		if (token != null)
			return token;
					
		// Intenta extraer palabra reservada para caracteres
		token = extraerReservadaCaracter( cod, i);
		if (token != null)
			return token;
							
		// Intenta extraer palabra reservada para cadenas
		token = extraerReservadaCadena(cod, i);
		if (token != null)
			return token;
		
		// Intenta extraer palabra reservada para hilos
		token = extraerReservadaHilo(cod, i);
		if (token != null)
		return token;
			
		// Intenta extraer palabra reservada para break
		token = extraerReservadaBreak(cod, i);
	    if (token != null)
	    	return token;

		// Intenta extraer un terminal
		token = extraerTerminal( cod, i);
	    if (token != null)
	    	return token;
						
		
		
		// Intenta extraer un identificador
		token = extraerIdentificador(cod, i);
		if ( token != null )
			return token;
			
		// Extrae un token no reconocido
		token = extraerNoReconocido(cod, i);
		return token;
    }

    /**
     * Intenta extraer un entero de la cadena cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer un entero - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer un entero  - 0<=indice<codigo.length()
     * @return el token entero o NULL, si el token en la posición dada no es un entero. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
	
    // Este método usa el método substring(), que se explica a continuación:
    // x.substring( i, j ) retorna una nueva cadena que es una subcadena de la cadena x.
    // La subcadena comienza en la posición i y
    // se extiende hasta el carácter en la posición j-1.
    // Ejemplo: "universidad".substring(3,6) retorna "ver",
	
	
	public Token extraerEntero ( String cod, int i)
	{
		
		int j;
		String lex;
		if( cod.charAt(i)=='#' ){
			j=i+1;
			if( j<cod.length() && esDigito(cod.charAt(j)) ){		
			    do
			    	j++;
			    while (  j<cod.length( ) && esDigito(cod.charAt(j)) );
		        lex =  cod.substring( i, j);			    
				Token token = new Token( lex, Token.ENTERO, j );
				return token;			
			}
		}
		
		return null;
	}

    /**
     * Intenta extraer un operador aditivo de la cadena cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer el operador aditivo  - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer el operador aditivo  - 0<=i<codigo.length()
     * @return el token operador aditivo o NULL, si el token en la posición dada no es un operador aditivo.El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
	public Token extraerOperadorAditivo ( String cod, int i )
	{
		
		int j;
		String lex;
		
		if(cod.charAt(i)=='$') {
			
			j=i+1;
			
			if( j<cod.length() && cod.charAt(j) == '+' || cod.charAt(j) == '-' || cod.charAt(j) == '*' || cod.charAt(j) == '/' || cod.charAt(j) == '%'){
			    
				j++;
		        lex =  cod.substring(i, j);			    
				Token token = new Token( lex, Token.OPERADORADITIVO, j);
				return token;			
			}
			
		}
		
		return null;
	}
	
	public Token extraerOperadorRelacional(String cod, int i) {
		
		int j;
		String lex;
		
		if(cod.charAt(i)=='→' || cod.charAt(i)=='←') {
			
			j=i+1;
			
			if(j<cod.length() && cod.charAt(j)==':') {
				
				j++;
		        lex =  cod.substring(i, j);			    
				Token token = new Token( lex, Token.OPERADORELACIONAL, j);
				return token;
				
			}else {
				
				lex =  cod.substring(i, j);			    
				Token token = new Token( lex, Token.OPERADORELACIONAL, j);
				return token;
			}
			
		}else {
			
			if(cod.charAt(i)==':') {
				
				j=i+1;
				
				if(j<cod.length() && cod.charAt(j)==':') {
					
					j++;
			        lex =  cod.substring(i, j);			    
					Token token = new Token( lex, Token.OPERADORELACIONAL, j);
					return token;
					
				}else {
					
					if(j<cod.length() && cod.charAt(j)=='←') {
						
						j++;
						
						if(cod.charAt(j)=='→') {
							
							j++;
					        lex =  cod.substring(i, j);			    
							Token token = new Token( lex, Token.OPERADORELACIONAL, j);
							return token;
							
						}
					}
				}
			}
		}
		
		
		return null;
	}
	
	public Token extraerOperadorLogico(String cod, int i) {
		
		int j;
		String lex;
		
		if(cod.charAt(i)=='@') {
			
			j=i+1;
			
			if(cod.charAt(j)=='y' || cod.charAt(j)=='o' || cod.charAt(j)=='!') {
				
				j++;
				
				if(cod.charAt(j)=='@') {
					
					j++;
			        lex =  cod.substring(i, j);			    
					Token token = new Token( lex, Token.OPERADORLOGICO, j);
					return token;
				}
			}
			
		}
		
		return null;
	}
	

    /**
     * Intenta extraer un operador de asignación de la cadena cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer el operador de asignación  - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer el operador de asingación  - 0<=i<codigo.length()
     * @return el token operador asignación o NULL, si el token en la posición dada no es un operador de asignación. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
	public Token extraerOperadorAsignacion ( String cod, int i )
	{
		
		if(cod.charAt(i)==':') {
			
			String lex;
			int j=i+1;
	        lex =  cod.substring(i, j);			    
			Token token = new Token( lex, Token.OPERADORASIGNACION, j);
			return token;
		}
		
		
		return null;
	}
	
	public Token extraerSimboloAbrir(String cod, int i) {
		
		int j;
		String lex;
		
		if(cod.charAt(i)=='◄') {
			
			j=i+1;
			
			if(j<cod.length() && cod.charAt(j)=='[' || cod.charAt(j)=='{' || cod.charAt(j)=='(') {
				
				j++;
		        lex =  cod.substring(i, j);			    
				Token token = new Token( lex, Token.SIMBOLOABRIR, j);
				return token;
				
			}
		}
		
		return null;
	}
	
	public Token extraerSimboloCerrar(String cod, int i) {
		
		int j;
		String lex;
		
		if(cod.charAt(i)==']' || cod.charAt(i)=='}' || cod.charAt(i)==')') {
			
			j=i+1;
			
			if(j<cod.length() && cod.charAt(j)=='►') {
				
				j++;
		        lex =  cod.substring(i, j);			    
				Token token = new Token( lex, Token.SIMBOLOCERRAR, j);
				return token;
				
			}
		}
		
		return null;
	}
	
	  /**
     * Intenta extraer un terminal de la cadena cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer un identficador - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer un identificador  - 0<=indice<codigo.length()
     * @return el token terminal o NULL, si el token en la posición dada no es un identificador. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
		
	public Token extraerTerminal ( String cod, int i)
	{
	
		
		if( cod.charAt(i)=='.' ){
			
			int j=i+1;
	        String lex =  cod.substring( i, j);			    
			Token token = new Token( lex, Token.TERMINAL, j );
			return token;
		
	
	}	
	return null;
	}
	
	  /**
     * Intenta extraer un separador de la cadena cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer un identficador - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer un separadpr  - 0<=indice<codigo.length()
     * @return el token separador o NULL, si el token en la posición dada no es un identificador. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
		
	public Token extraerSeparador ( String cod, int i)
	{
		if( cod.charAt(i)=='~' ){
			
				int j=i+1;
		        String lex =  cod.substring( i, j);			    
				Token token = new Token( lex, Token.SEPARADOR, j );
				return token;
			
		
		}	
		return null;
	}
	
	
	 /**
     * Intenta extraer palabra reservada para  caracteres de la cadena  cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer la palabra reservada para caracteres - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer la palabra reservada para  caracteres  - 0<=i<codigo.length()
     * @return el token palabra reservada para cadena de caracteres o NULL, si el token en la posición dada no es parte de la palabra reservada para caracteres. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
	public Token extraerReservadaCaracter ( String cod, int i )
	{
		if( cod.charAt(i) =='c' && esLetra(cod.charAt(i))){
			int j=i+1;
			if( j<cod.length() && cod.charAt(j) =='h'){		
				j++;
				
			        String lex =  cod.substring( i, j);			    
					Token token = new Token( lex, Token.RESERVADA_CARACTER, j );
					return token;
				}
			
		}
		return null;
	}
	
	 /**
     * Intenta extraer palabra reservada para cadena de caracteres de la cadena  cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer la palabra reservada para cadena de caracteres - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer la palabra reservada para cadena de caracteres  - 0<=i<codigo.length()
     * @return el token palabra reservada para cadena de caracteres o NULL, si el token en la posición dada no es parte de la palabra reservada para cadena de caracteres. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
	public Token extraerReservadaCadena ( String cod, int i )
	{
		if( cod.charAt(i) =='w' && esLetra(cod.charAt(i))){
			int j=i+1;
			if( j<cod.length() && cod.charAt(j) =='r'){		
				j++;
				
				if( j<cod.length() && cod.charAt(j) =='d'){		
					j++;
			        String lex =  cod.substring( i, j);			    
					Token token = new Token( lex, Token.RESERVADA_CADENA, j );
					return token;
				}
			}
			
		}
		return null;
	}
	
	 /**
     * Intenta extraer palabra reservada para hilos de la cadena  cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer la palabra reservada para hilos - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer la palabra reservada para  hilos - 0<=i<codigo.length()
     * @return el token palabra reservada para cadena de caracteres o NULL, si el token en la posición dada no es parte de la palabra reservada para hilos. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
	public Token extraerReservadaHilo( String cod, int i )
	{
		if( cod.charAt(i) =='f' && esLetra(cod.charAt(i))){
			int j=i+1;
			if( j<cod.length() && cod.charAt(j) =='i'){		
				j++;
				
				if( j<cod.length() && cod.charAt(j) =='l'){		
					j++;
			        String lex =  cod.substring( i, j);			    
					Token token = new Token( lex, Token.RESERVADA_HILO, j );
					return token;
				}
			}
			
		}
		return null;
	}
	
	 /**
     * Intenta extraer palabra reservada para break	  cod a partir de la posición i,
     * basándose en el Autómata
     * @param cod - código al cual se le va a intentar extraer la palabra reservada para break - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer la palabra reservada para  break - 0<=i<codigo.length()
     * @return el token palabra reservada para cadena de caracteres o NULL, si el token en la posición dada no es parte de la palabra reservada para break El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
	public Token extraerReservadaBreak( String cod, int i )
	{
		if( cod.charAt(i) =='j' && esLetra(cod.charAt(i))){
			int j=i+1;
			if( j<cod.length() && cod.charAt(j) =='u'){		
				j++;
				
				if( j<cod.length() && cod.charAt(j) =='m'){		
					j++;
					
					if( j<cod.length() && cod.charAt(j) =='p'){		
					j++;
			        String lex =  cod.substring( i, j);			    
					Token token = new Token( lex, Token.RESERVADA_BREAK, j );
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
     * @param cod - código al cual se le va a intentar extraer un identficador - codigo!=null
     * @param i - posición a partir de la cual se va a intentar extraer un identificador  - 0<=indice<codigo.length()
     * @return el token identificaror o NULL, si el token en la posición dada no es un identificador. El Token se compone de 
     * el lexema, el tipo y la posición del siguiente lexema.
     */
		
	public Token extraerIdentificador ( String cod, int i)
	{
		if( cod.charAt(i)=='_' ){
			int j=i+1;
			while( j<cod.length() && esLetra(cod.charAt(j)) )		
			    	j++;
		    String lex =  cod.substring( i, j);			    
		    Token token = new Token( lex, Token.IDENTIFICADOR, j );
			return token;			
		}	
		return null;
	}

    /**
     * Extraer un lexema no reconocido de la cadena cod a partir de la posición i.
     * Antes de utilizar este método, debe haberse intentado todos los otros métodos para los otros tipos de token
     * @param cod - código al cual se le va a extraer el token no reconocido - codigo!=null
     * @param i - posición a partir de la cual se va a extraer el token no reconocido  - 0<=indice<codigo.length()
     * @return el token no reconocido. El Token se compone de lexema, el tipo y la posición del siguiente lexema.

     */
	public Token extraerNoReconocido ( String cod, int i)
	{
		String lexema =  cod.substring( i, i + 1);
		int j=i+1;
		Token token = new Token( lexema, Token.NORECONOCIDO, j );
		return token;
	}
	
	/**
     * Determina si un carácter es un dígito
     * @param caracter - Carácter que se va a analizar - caracter!=null,
     * @return true o false según el carácter sea un dígito o no
     */
	public boolean esDigito (char caracter )
	{
		return  caracter >= '0' && caracter <= '9';
	}

	/**
     * Determina si un carácter es una letra
     * @param caracter - Carácter que se va a analizar - caracter!=null,
     * @return true o false según el carácter sea una letra o no
     */
	public boolean esLetra (char caracter )
	{
		return  (caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z');
	}

}
