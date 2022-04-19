/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Teoría de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Autor del código inicial: Leonardo A. Hernández R. - Abril 2014, Agosto 2008, septiembre 2013
 * Autor:
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package interfaz;
/**
 * Panel para ingresar texto al analizador léxico
 */


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PanelEntradaCodigo extends JPanel implements ActionListener
{

    // -----------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------

    private static final String VERTOKENS = "VER TOKENS";

    // -----------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------

    /**
     * Ventana principal
     */
    private InterfazAnalizadorLexico ventanaPrincipal;

    /**
     * Etiqueta código
     */
    private JLabel etiquetaCodigo;

     /**
     * Campo donde se ingresa el código fuente
     */
    private JTextField campoCodigo;

     /**
     * Botón ver tokens
     */
    private JButton botonVerTokens;

    // -----------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------

    /**
     * Constructor del panel.
     * @param principal Ventana principal. principal != null.
     */
    public PanelEntradaCodigo( InterfazAnalizadorLexico principal )
    {
        ventanaPrincipal = principal;
        setLayout( new GridLayout( 1, 3 ) );

        etiquetaCodigo = new JLabel( "Código fuente que se va a analizar: " );
        campoCodigo = new JTextField( 20);
        botonVerTokens = new JButton( "Ver tokens" );
        botonVerTokens.addActionListener( this );
        botonVerTokens.setActionCommand( VERTOKENS );

        add( etiquetaCodigo );
        add( campoCodigo );
        add( botonVerTokens );
    }

    // -----------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------

     /**
     * Responde ante las acciones en el panel.
     * @param e Evento que generó la acción.
     */
    public void actionPerformed( ActionEvent e )
    {
//        if( e.getActionCommand( ) == VERTOKENS )
      if( e.getActionCommand( ).equals(VERTOKENS)  )
        {
        	ventanaPrincipal.verTokens(campoCodigo.getText());
        }

    }
}
