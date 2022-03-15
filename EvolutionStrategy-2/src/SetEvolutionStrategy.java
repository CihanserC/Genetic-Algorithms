//import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

// implements Comparator<Genome>
public class SetEvolutionStrategy {

    private Genome[] population;
    private int dimension;
    double tau; // Can be explained as the "learning rate"
    Random rand;
    double sigma;
    int populationSize;


    public SetEvolutionStrategy(int dimension, int populationSize) {
        this.populationSize = populationSize;
        this.dimension = dimension;
        rand = new Random();
        population = new Genome[populationSize];
        tau = 1 / Math.sqrt(dimension);
        sigma = 20*rand.nextDouble() - 10 ;
    }

    public void run() {
        Genome[] parents = new Genome[30]; // 30 is maximum parent number allowed
        ArrayList<Genome> children = new ArrayList<>();


        for ( int i = 0; i < populationSize; i++ )
        {
            population[i] = new Genome();
            population[i].initialize(dimension);
        }

        int generations = 10000*dimension/200;
        for ( int indexGeneration = 0; indexGeneration < generations; indexGeneration++ ) {
            children.clear();
            //Parent Selection Step:
            for ( int i = 0; i < 30; i++ )
            {
                parents[i] = parentSelection();
            }

            // Crossover Step:
            while(children.size()<200) // 200 is maximum children number allowed
            {
                int p1 = rand.nextInt(30);
                int p2 = rand.nextInt(30);
                Genome[] offsprings = crossOver(parents[p1],parents[p2]);
                children.add(offsprings[0]);
                children.add(offsprings[1]);
            }

            // Mutation Step:
            for (Genome child : children) {
                sigma *= Math.exp(tau * rand.nextGaussian());
                for (int j = 0; j < dimension; j++) {
                    double value = child.getValue(j) + (sigma * rand.nextGaussian());
                    child.setValue(j, value);
                }
            }

            // Selecting the Survivor Step:
            for (int j = 0; j < getPopulationSize(); j++) {
                population[j] = children.get(j);
            }

            double best    = Double.MAX_VALUE;
            double average = 0;
            double worst   = 0;
            int    total   = 0;
//            Genome bestGenome = population[0].clone();

            for ( int j = 0; j < getPopulationSize(); j++ )
            {
                double fitness = population[j].fitness();
                if(fitness < best)
                {
                    best = fitness;
//                    bestGenome = population[j].clone();
                }
                if(fitness > worst)
                {
                    worst = population[j].fitness();
                }
                total+=fitness;
            }

            if(average <= 0){
                average = (double)total/getPopulationSize();
            }
            else if(average>0){
                double updatedAvg = (double)total/getPopulationSize();
                average = (average+updatedAvg)/2;
            }

            System.out.println("Gen=" + indexGeneration + "\tBest=" + best + "\tWorst=" + worst + "\tAverage=" + average);

            if(best < Math.pow(10,-8))
            {
                break;
            }
        }
    }
    public int getPopulationSize()
    {
        return populationSize;
    }
    private Genome parentSelection() {
        int p1 = rand.nextInt( 200 );
        return population[p1];

    }

    private Genome[] crossOver(Genome parent1, Genome parent2 )
    {
        Genome child1 = parent1.clone();
        Genome child2 = parent2.clone();
        int CuttingPoint = rand.nextInt(parent1.genes.length);

        for (int i = CuttingPoint; i < parent1.genes.length; i++)
        {
            child1.setValue(i,(parent1.getValue(i) + parent2.getValue(i)) / 2);
            child2.setValue(i,(parent1.getValue(i) + parent2.getValue(i)) / 2);
        }

        Genome[] offsprings = new Genome[2];
        offsprings[0] = child1;
        offsprings[1] = child2;
        return offsprings;
    }

}
