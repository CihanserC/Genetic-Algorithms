import java.util.Arrays;
import java.util.Random;


public class PBIL {

    private BinaryGenome[] population;
    private Random randGenerator;
    private int generations;

    BinaryGenome run( int populationSize, int generationCount, int targetFitness, double alpha, int selectionSize, boolean useTruncationSelection, GAProblem problem ) {
        randGenerator = new Random();

        //start with uniform distributions
        double distributions[] = new double[problem.getNumberOfItems()];
        for ( int i = 0; i < distributions.length; i++ ) {
            distributions[ i ] = 0.5;
        }

        BinaryGenome bestGenome = null;

        //Generational loop
        for ( generations = 0; generations < generationCount; generations++ ) {
            //Generate new population sampling from the current n marginal distributions
            generatePopulation( populationSize, distributions, problem );

            //find best
            BinaryGenome bestChild = findBest( population );

            if ( bestGenome == null || bestChild.fitness() > bestGenome.fitness() ) {
                bestGenome = bestChild;
            }

            //exit if target fitness reached
            if ( bestGenome.fitness() == targetFitness ) {
                break;
            }

            //make selection
            if ( useTruncationSelection ) {
                population = truncationSelection( selectionSize );
            }
            else { // make a tournament selection of size selectionSize
                population = tournamentSelection( selectionSize, selectionSize );
            }

            //update distributions using the selected invididuals
            updateDistributions( distributions, alpha, problem );
        }
        return bestGenome;
    }

    private void generatePopulation( int populationSize, double[] distributions, GAProblem problem ) {
        population = new BinaryGenome[populationSize];
        for ( int i = 0; i < population.length; i++ ) {
            BinaryGenome g = new BinaryGenome( problem );
            for ( int j = 0; j < problem.getNumberOfItems(); j++ ) {
                if ( randGenerator.nextDouble() <= distributions[ j ] ) {
                    g.setGene( j, true );
                }
                else {
                    g.setGene( j, false );
                }
            }
            population[ i ] = g;
        }
    }

    //find best individual
    private BinaryGenome findBest( BinaryGenome[] population ) {
        int bestFitness = Integer.MIN_VALUE;
        BinaryGenome best = null;

        for ( BinaryGenome genome : population ) {
            if ( genome.fitness() > bestFitness ) {
                best = genome;
                bestFitness = genome.fitness();
            }
        }
        return best;
    }

    public int getGenerations() {
        return generations;
    }

    private BinaryGenome[] truncationSelection( int truncationSize ) {
        for(int i=0; i< population.length;i++){
            population[i].fitness();
        }
        Arrays.sort( population );
        BinaryGenome[] truncated = Arrays.copyOf( population, truncationSize );
        return truncated;
    }

    private BinaryGenome[] tournamentSelection( int truncationSize, int tournamentSize ) {
        BinaryGenome[] truncated = new BinaryGenome[truncationSize];
        for ( int i = 0; i < truncationSize; i++ ) {
            int index = randGenerator.nextInt( population.length );
            BinaryGenome bestCandidate = population[ index ];
            for ( int j = 0; j < tournamentSize - 1; j++ ) {
                index = randGenerator.nextInt( population.length );
                BinaryGenome curr = population[ index ];
                if ( curr.fitness() > bestCandidate.fitness() ) {
                    bestCandidate = curr;
                }
            }
            truncated[ i ] = bestCandidate;
        }
        return truncated;
    }

    private void updateDistributions( double[] distributions, double alpha, GAProblem problem) {
        for ( int i = 0; i < problem.getNumberOfItems(); i++ ) {
            int count = 0;
            for ( BinaryGenome genome : population ) {
                if ( genome.getGene( i ) ) {
                    count++; //count the number of 1s
                }
            }
            double frequency = ( double ) count / population.length; //frequency of 1s
            //update distribution according to the formula
            distributions[ i ] = (1 - alpha) * distributions[ i ] + alpha * frequency;
        }
    }
}
