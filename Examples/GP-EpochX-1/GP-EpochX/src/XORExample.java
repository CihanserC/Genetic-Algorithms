
import java.io.FileNotFoundException;
import org.epochx.gp.op.crossover.SubtreeCrossover;
import org.epochx.life.GenerationAdapter;
import org.epochx.life.Life;
import org.epochx.op.selection.TournamentSelector;
import static org.epochx.stats.StatField.GEN_FITNESS_MIN;
import static org.epochx.stats.StatField.GEN_FITTEST_PROGRAM;
import static org.epochx.stats.StatField.GEN_NUMBER;
import org.epochx.stats.Stats;
import org.epochx.tools.random.MersenneTwisterFast;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author korhan.karabulut
 */
public class XORExample {
    
    public static void main( String[] args ) {
        // Construct the model.
        final XORModel model;
        try {
            model = new XORModel( 4, "xor_in.txt", "xor_out.txt" );
        } catch ( FileNotFoundException ex ) {
            System.out.println( "File read error!" );
            return;
        }

        // Set parameters.
        model.setPopulationSize( 1000 );
        model.setNoGenerations( 1000 );

        // Set operators and components.
        model.setCrossover( new SubtreeCrossover( model ) );
        model.setProgramSelector( new TournamentSelector( model, 10 ) );
        model.setRNG( new MersenneTwisterFast() );
        model.setTerminationFitness( 0 );

        // Request statistics every generation.
        Life.get().addGenerationListener( new GenerationAdapter() {
            @Override
            public void onGenerationEnd() {
                Stats.get().print( GEN_NUMBER, GEN_FITNESS_MIN );
            }
        } );
        // Run the model.
        model.run();
        Stats.get().print( GEN_FITTEST_PROGRAM );
    }
}