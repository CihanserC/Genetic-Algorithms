import java.util.Arrays;
import java.util.Random;


public class cGA {

    private BinaryGenome[] population;
    private Random randGenerator;
    private int generations;

    BinaryGenome run( int populationSize, int generationCount, int targetFitness, int discretization, GAProblem problem ) {
        randGenerator = new Random();

        //start with uniform distributions
        double distributions[] = new double[problem.getNumberOfItems()];
        for ( int i = 0; i < distributions.length; i++ ) {
            distributions[ i ] = 0.5;
        }

        BinaryGenome bestGenome = null;

        boolean gameOver = false;
        //Generational loop
        for ( generations = 0; generations < generationCount && !gameOver; generations++ ) {
            //check for "gameover"
            int count = 0;
            for ( int i = 0; i < problem.getNumberOfItems(); i++ ) {
                if ( distributions[ i ] == 1 || distributions[ i ] == 0 ) {
                    count++;
                }
            }
            if ( count == problem.getNumberOfItems() ) {
                gameOver = true; //converged
            }

            generatePopulation( populationSize, distributions, problem );

            BinaryGenome bestChild = findBest( population );

            if ( bestGenome == null || bestChild.fitness() > bestGenome.fitness() ) {
                bestGenome = bestChild;
            }

            if ( bestGenome.fitness() == targetFitness ) {
                break;
            }
            
            updateDistributions( distributions, discretization, problem );
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

    private void updateDistributions( double[] distributions, int discretization, GAProblem problem ) {
        for ( int i = 0; i < population.length - 1; i++ ) {
            for ( int k = i + 1; k < population.length; k++ ) {
                BinaryGenome u = population[ i ];
                BinaryGenome v = population[ k ];
                if ( v.fitness() > u.fitness() ) {
                    u = population[ k ];
                    v = population[ i ];
                }
                for ( int j = 0; j < problem.getNumberOfItems(); j++ ) {
                    //update distribution according to the formula
                    if ( u.getGene( j ) && !v.getGene( j ) && distributions[ j ] < 1 ) {
                        distributions[ j ] += 1.0 / discretization;
                    }
                    if ( !u.getGene( j ) && v.getGene( j ) && distributions[ j ] > 0 ) {
                        distributions[ j ] -= 1.0 / discretization;
                    }

                    //do corrections on distribution if necessary
                    if ( distributions[ j ] > 1 ) {
                        distributions[ j ] = 1;
                    }
                    if ( distributions[ j ] < 0 ) {
                        distributions[ j ] = 0;
                    }
                }
            }
        }
    }
}
