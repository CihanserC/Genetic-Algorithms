/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class SGA {

    private Genome[] population;
    private double pc, pm;
    private int length;
    private int generations;
    private GAProblem problem;

    Random rnd;

    public SGA(int popSize, int generations, double pc, double pm, GAProblem problem) {
        population = new Genome[popSize];
        this.pc = pc;
        this.pm = pm;
        this.length = problem.getTaskCount();
        this.generations = generations;
        this.problem = problem;
        rnd = new Random();
    }



    public void run() {
        Genome[] matingPool = new Genome[population.length];
        Genome[] children = new Genome[population.length];

        for ( int i = 0; i < population.length; i++ ) {
            population[ i ] = new Genome( problem );
            population[ i ].initialize();
        }


        int best = 0;
        Genome bestGenome = population[0];
        for ( int i = 0; i < generations; i++ ) {
            //parent selection
            for ( int j = 0; j < population.length; j++ ) {
                matingPool[ j ] = parentSelection();
            }
            //crossover
            for ( int j = 0; j < population.length; j += 2 ) {
                if ( rnd.nextDouble() < pc ) {
                    Genome[] offspring = crossOver( matingPool[ j ], matingPool[ j + 1 ] );
                    children[ j ] = offspring[ 0 ];
                    children[ j + 1 ] = offspring[ 1 ];
                }
                else {
                    children[ j ] = matingPool[ j ];
                    children[ j + 1 ] = matingPool[ j + 1 ];
                }
            }
            //mutation
            for ( int j = 0; j < children.length; j++ ) {
                for ( int k = 0; k < length; k++ ) {
                    if ( rnd.nextDouble()<pm) {
                        children[j].setBit( k, rnd.nextInt(problem.getProcessorCount()));
                    }
                }
            }
            //survivor selection
            population=children;

            for ( int j = 0; j < population.length; j++ ) {
                if ( population[j].fitness() > best ) {
                    best = population[j].fitness();
                    bestGenome = population[j];
                    System.out.println("Best Fitness:" + best);
                    System.out.println("Best Solution:" + bestGenome);
                    System.out.println("Generation Count:" + i);
                }
            }
        }
    }

    private Genome parentSelection() {
        int p1 = rnd.nextInt( population.length );
        int p2 = rnd.nextInt( population.length );
        if ( population[ p1 ].fitness() > population[ p2 ].fitness() ) {
            return population[ p1 ];
        }
        else {
            return population[ p2 ];
        }
    }

    private Genome[] crossOver( Genome parent1, Genome parent2 ) {
        Genome[] children = new Genome[2];
        children[ 0 ] = ( Genome ) parent1.clone();
        children[ 1 ] = ( Genome ) parent2.clone();

        int point = rnd.nextInt( length );
        for ( int i = point; i < length; i++ ) {
            children[ 0 ].setBit( i, parent2.getBit( i ) );
            children[ 1 ].setBit( i, parent1.getBit( i ) );
        }
        return children;
    }
}
