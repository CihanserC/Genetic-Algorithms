/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaGA;
/**
 *
 * @author HP
 */
public class metaGA {

    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        ec.Evolve.main( new String[] {"-file", "src/metaGA/meta.params"} );
    }
}