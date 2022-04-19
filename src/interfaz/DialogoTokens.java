/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Teoría de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Autor: Leonardo A. Hernández R. - Agosto 2008, sep 2013
 * Autor:
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package interfaz;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * Diálogo para mostrar los tokens
 */
public class DialogoTokens extends JDialog
{

    // -----------------------------------------------------------------
    // Atributos de Interfaz
    // -----------------------------------------------------------------

    /**
     * Es el panel usado para contener la lista
     */
    private JScrollPane scrollDesplazamiento;

    /**
     * La lista donde se muestran los tokens
     */
    private JList listaTokens;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Diálogo donde se muestran los tokens
     */
    public DialogoTokens( )
    {
        setBackground( Color.white );
        setTitle( "Tokens" );
        scrollDesplazamiento = new JScrollPane( );

        // Lista donde se almacenaran los tokens
        listaTokens = new JList( );
        listaTokens.setSelectionMode( javax.swing.ListSelectionModel.SINGLE_SELECTION );

        // Scroll que desplegará la lista de tokens
        scrollDesplazamiento.setVerticalScrollBarPolicy( javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scrollDesplazamiento.setViewportView( listaTokens );
        add( scrollDesplazamiento );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Modifica la lista de tokens mostrada
     * @param vectorTokensEditados La lista con la descripción de los tokens que
     * se van a mostrar en la lista
     */
    public void cambiarListaTokens( ArrayList vectorTokensEditados )
    {
        listaTokens.setListData( vectorTokensEditados.toArray( ) );
    }
}
