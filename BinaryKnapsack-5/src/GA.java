import java.util.Random;

public class GA {

    private BinaryGenome[] population;
    private Random randGenerator;
    private int generations;

    BinaryGenome run( int populationSize, int generationCount, double crossOverRate, double mutationRate, int targetFitness, boolean elitism, GAProblem problem ) {
        randGenerator = new Random();
        //Generate initial population
        population = new BinaryGenome[populationSize];
        for ( int i = 0; i < population.length; i++ ) {
            population[ i ] = new BinaryGenome( problem );
        }

        BinaryGenome bestGenome = findBest( population );

        //Generational loop
        for ( generations = 0; generations < generationCount; generations++ ) {

            if ( bestGenome.fitness() == targetFitness ) {
                break;
            }

            BinaryGenome[] matingPool = parentSelection();
            crossOver( matingPool, crossOverRate, problem );
            mutation( matingPool, mutationRate );
            BinaryGenome bestChild = findBest( matingPool );

            //copy the best so far to the new population if necessary - elitism
            if ( bestGenome.fitness() > bestChild.fitness() ) {
                if ( elitism ) { // replace a random child
                    matingPool[ randGenerator.nextInt( matingPool.length ) ] = bestGenome;
                }
            }
            else {
                bestGenome = bestChild;
            }

            population = matingPool;
        }

        return bestGenome;
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

    //Parent selection using binary tournament selection
    private BinaryGenome[] parentSelection() {
        Random rnd = new Random();
        BinaryGenome[] matingPool = new BinaryGenome[population.length];
        for ( int i = 0; i < matingPool.length; i++ ) {
            BinaryGenome first = population[ rnd.nextInt( population.length ) ];
            BinaryGenome second = population[ rnd.nextInt( population.length ) ];
            if ( first.fitness() > second.fitness() ) {
                matingPool[ i ] = first;
            }
            else {
                matingPool[ i ] = second;
            }
        }
        return matingPool;
    }

    private void crossOver( BinaryGenome[] matingPool, double crossoverRate, GAProblem problem ) {
        for ( int i = 0; i < matingPool.length; i += 2 ) {
            BinaryGenome firstParent = matingPool[ i ];
            BinaryGenome secondParent = matingPool[ i + 1 ];

            if ( Math.random() < crossoverRate ) {
                BinaryGenome[] children = onePointCrossOver( firstParent, secondParent, problem );
                matingPool[ i ] = children[ 0 ];
                matingPool[ i + 1 ] = children[ 1 ];
            }
        }
    }

    private BinaryGenome[] onePointCrossOver( BinaryGenome firstParent, BinaryGenome secondParent, GAProblem problem ) {
        Random rnd = new Random();
        int position = rnd.nextInt( firstParent.size() );
        BinaryGenome[] children = new BinaryGenome[2];
        children[ 0 ] = new BinaryGenome( problem );
        children[ 1 ] = new BinaryGenome( problem );
        for ( int i = 0; i < position; i++ ) {
            children[ 0 ].setGene( i, firstParent.getGene( i ) );
            children[ 1 ].setGene( i, secondParent.getGene( i ) );
        }
        for ( int i = position; i < firstParent.size(); i++ ) {
            children[ 0 ].setGene( i, secondParent.getGene( i ) );
            children[ 1 ].setGene( i, firstParent.getGene( i ) );
        }
        return children;
    }

    private void mutation( BinaryGenome[] matingPool, double mutationRate ) {
        for ( BinaryGenome genome : matingPool ) {
            for ( int j = 0; j < genome.size(); j++ ) {
                if ( Math.random() < mutationRate ) {
                    genome.flipGene( j );
                }
            }
        }
    }

    public int getGenerations() {
        return generations;
    }

}
