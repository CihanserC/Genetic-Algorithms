import static org.epochx.stats.StatField.*;

import org.epochx.gp.model.*;
import org.epochx.gp.op.crossover.OnePointCrossover;
import org.epochx.life.*;
import org.epochx.op.selection.TournamentSelector;
import org.epochx.stats.Stats;
import org.epochx.tools.random.MersenneTwisterFast;

public class Example1 {
    public static void main(String[] args) {
        // Construct the model.
        final GPModel model = new EvenParity(4);

        // Set parameters.
        model.setPopulationSize(200);
        model.setNoGenerations(1000);
//        model.setMaxDepth(8);

        // Set operators and components.
        model.setCrossover(new OnePointCrossover(model));
        model.setProgramSelector(new TournamentSelector(model, 7));
        model.setRNG(new MersenneTwisterFast());

        // Request statistics every generation.
        Life.get().addGenerationListener(new GenerationAdapter(){
            @Override
            public void onGenerationEnd() {
            	Stats.get().print(GEN_NUMBER, GEN_FITNESS_MIN, GEN_FITTEST_PROGRAM);
            }
        });
        // Run the model.
        model.run();
    }
}