
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: InterfazAnalizadorLexico.java,v 1.0 2008/08/17 10:39:08 da-romer Exp $
 * Universidad del Quindío (Bogotá - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura Teoría de Lenguajes Formales
 * Ejercicio : Analizador léxico
 * Autor inicial: Leonardo A. Hernández R. - Agosto 17 de 2008, sep 2013
 * Autor:
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package interfaz;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mundo.AnalizadorLexico;
import mundo.Token;

/**
 * Ventana principal de la aplicación
 */
public class InterfazAnalizadorLexico extends JFrame
{

    // -----------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------

    /**
     * Panel donde se muestran los resultados
     */
    private PanelEntradaCodigo  panelEntradaCodigo;

    /**
     * El diálogo usado para mostrar los tokens
     */
    private DialogoTokens dialogoTokens;

    /**
     * Analizador léxico
     */
    private AnalizadorLexico analizadorLexico;

    // -----------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------

    /**
     * Constructor de la interfaz
     */
    public InterfazAnalizadorLexico( )
    {
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setVisible( true );
        setResizable( false );
        setTitle( "Analizador Léxico" );
        setLayout( new GridLayout( 1, 1 ) );
        analizadorLexico = new AnalizadorLexico( );
        panelEntradaCodigo = new PanelEntradaCodigo( this );
        dialogoTokens = new DialogoTokens( );
        dialogoTokens.setModal( true );
        add( panelEntradaCodigo );
        pack( );
        centrarFrame( );
    }

    // -----------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------

    /**
     * Centra el frame en la pantalla según resolución
     */
    private void centrarFrame( )
    {
        Dimension screenSize = getToolkit( ).getScreenSize( );
        int screenWidth = ( int )screenSize.getWidth( );
        int screenHeight = ( int )screenSize.getHeight( );
        setLocation( ( ( screenWidth / 2 ) - ( getWidth( ) / 2 ) ),   ( ( screenHeight / 2 ) - ( getHeight( ) / 2 ) ) );
    }

    /**
     * Método usado para ver los tokens del código ingresada
     * param codigo código fuente que se va a analizar
     */
    public void verTokens( String codigo )
    {
    	if(!codigo.equals(""))
    	{
	    	ArrayList vectorTokens;
	    	vectorTokens = analizadorLexico.extraerTokens(codigo);
	    	ArrayList vectorTokensEditados = new ArrayList( );
	        Token token;

	        for( int i = 0; i < vectorTokens.size( ); i++)
	        {
		        token = (Token)vectorTokens.get(i);
		        vectorTokensEditados.add(token.darDescripcion());
	        }

	        dialogoTokens.setSize( 450, 200 );
	        dialogoTokens.cambiarListaTokens( vectorTokensEditados );
	        dialogoTokens.setLocation( calculaPosicionCentral( this, dialogoTokens ) );
	        dialogoTokens.setVisible( true );
        }
        else
        {
            JOptionPane.showMessageDialog( this, "Debe ingresar una codigo fuente primero", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Calcula el punto que indica la posición centrada del frame
     * @param componentePadre - Ventana Padre del componente - !=null
     * @param componenteHijo - Ventana Hija del componente - !=null
     * @return punto - Localizacion en coordinadas x,y del nuevo componente - !=null
     */
    private Point calculaPosicionCentral( Component componentePadre, Component componenteHijo )
    {
        Dimension tamanoPantalla, tamanoPadre, tamanoHijo;
        Point localizacionPadre;

        // Centra la ventana y verifica que no sea mayor que la resolución
        // actual
        tamanoPantalla = Toolkit.getDefaultToolkit( ).getScreenSize( );
        int max_y = tamanoPantalla.height;
        int min_y = 0;

        // Tamaño de la resolucion de la pantalla
        tamanoPadre = componentePadre.getSize( );
        localizacionPadre = componentePadre.getLocation( );
        tamanoHijo = componenteHijo.getSize( );
        int x = ( tamanoPadre.width - tamanoHijo.width ) / 2 + localizacionPadre.x;
        int y = tamanoPadre.height + localizacionPadre.y;

        // Ajuste para abajo
        if( y + tamanoHijo.height > max_y )
        {
            y = max_y - tamanoHijo.height;
        }

        // Ajuste para arriba
        if( y < min_y )
        {
            y = 0;
        }
        return new Point( x, y );
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Ejecuta la aplicación
     * @param args Son los parámetros de la línea de comandos. No se utilizan.
     */
    public static void main( String[] args )
    {
    	InterfazAnalizadorLexico interfaz = new InterfazAnalizadorLexico( );
        interfaz.setVisible( true );
    }
}
