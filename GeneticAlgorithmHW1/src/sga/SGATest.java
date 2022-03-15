package sga;

import java.io.IOException;

/**
 *
 * @author Cihanser Çalışkan - 20500002010
 */
public class SGATest {

    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) throws IOException {
        SGA sga = new SGA(25,100,1000,0.7);
        sga.run();
    }

}
