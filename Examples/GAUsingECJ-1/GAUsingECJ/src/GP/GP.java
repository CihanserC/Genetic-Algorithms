/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GP;

/**
 *
 * @author HP
 */
public class GP {

    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        // TODO code application logic here
        ec.Evolve.main( new String[] {"-file", "src/GP/gp.params"} );
        ec.Evolve.buildOutput();
    }
}