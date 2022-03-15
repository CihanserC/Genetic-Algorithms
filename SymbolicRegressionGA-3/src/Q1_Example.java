import org.epochx.gp.op.crossover.KozaCrossover;
import org.epochx.gp.op.crossover.SubtreeCrossover;
import org.epochx.gp.op.mutation.PointMutation;
import org.epochx.gp.op.mutation.SubtreeMutation;
import org.epochx.life.GenerationAdapter;
import org.epochx.life.Life;
import org.epochx.op.selection.LinearRankSelector;
import org.epochx.op.selection.TournamentSelector;
import org.epochx.stats.Stats;
import org.epochx.tools.random.MersenneTwisterFast;
import java.io.FileNotFoundException;
import static org.epochx.stats.StatField.*;


public class Q1_Example {
    
    public static void main( String[] args ) {
        // Construct the model.
        final Q1_Model model;
        try {
            model = new Q1_Model( 2, "Q1_input.txt", "Q1_output.txt" );
        } catch ( FileNotFoundException ex ) {
            System.out.println( "File read error!" );
            return;
        }

        // Change parameters.
        // Param 1:
        model.setPopulationSize( 1000 );
        // Param 2:
        model.setCrossover( new SubtreeCrossover( model ) );
        // Param 3:
        model.setMutation(new SubtreeMutation(model ) );
         // Param 4:
        model.setMaxDepth(10);
        // Param 5:
        model.setProgramSelector( new TournamentSelector( model, 10 ) );
        //model.setProgramSelector(new LinearRankSelector(model));
        // Param 6:
//        model.setCrossoverProbability(0.3);
        // Param 7:
//        model.setMutationProbability(0.3);
        // Param 8:
        model.setNoGenerations( 1000 );

        model.setRNG( new MersenneTwisterFast() );
        model.setTerminationFitness( Math.pow(10, -10) );

        // Request statistics every generation.
        Life.get().addGenerationListener( new GenerationAdapter() {
            @Override
            public void onGenerationEnd() {
                Stats.get().print( GEN_NUMBER, GEN_FITNESS_MIN, GEN_FITNESS_AVE, GEN_FITNESS_MAX, GEN_FITNESS_STDEV );
            }
        } );
        // Run the model.
        model.run();
        Stats.get().print( GEN_FITTEST_PROGRAM );
    }
}