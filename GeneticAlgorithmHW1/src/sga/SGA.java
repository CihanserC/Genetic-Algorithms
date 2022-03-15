package sga;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
/**
 *
 * @author Cihanser Çalışkan - 20500002010
 */
public class SGA {

    private Genome[] population;
    private double pc, pm;
    private int length;
    private int generations;
    Random rnd;

    public SGA( int length, int popSize, int generations, double pc ) {
        population = new Genome[popSize];
        this.pc = pc;
        this.pm = 1 / (length * length ) ;
        this.length = length;
        this.generations = generations;
        rnd = new Random();
    }

    public void run() throws IOException {
        long startTime = System.nanoTime();
        Genome[] matingPool = new Genome[population.length];
        Genome[] children = new Genome[population.length];
        String dataPath = "TournamentFinal.txt";
        FileWriter fileWriter = new FileWriter(dataPath,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for ( int i = 0; i < population.length; i++ ) {
            population[ i ] = new Genome( length );
            population[ i ].randomize();
        }

        for ( int i = 0; i < generations; i++ ) {
            //parent selection
            for ( int j = 0; j < population.length; j++ ) {
                matingPool[ j ] = parentSelectionByTournament();
            }
            // SUS parent selection
            //matingPool = parentSelectionBySUS();

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
                    for ( int p = 0; p < length; p++ ){
                        if ( rnd.nextDouble()<pm) {
                            children[j].setBit(k,p, !children[j].getBit (k,p) );
                        }
                    }

                }
            }
            //survivor selection
            population=children;

            int best = 0;
            int worst = length * length;
            int total = 0;
            for ( int j = 0; j < population.length; j++ ) {
                if ( population[j].fitness() > best ){
                    best = population[j].fitness();
                }
                if ( population[j].fitness() < worst ){
                    worst = population[j].fitness();
                }
                total+=population[j].fitness();
            }
            System.out.println( "Best: " + best + " Average: " + (double)total / population.length + " Worst: " + worst );

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
            duration = duration / 1000000;

            String resultString= "";
            resultString += (i+1) + "-" + best + "-" + worst + "-" + ((double)total / population.length + "-" + duration);
            bufferedWriter.write(resultString);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();


    }

    private Genome parentSelectionByTournament() {
        int p1 = rnd.nextInt( population.length );
        int p2 = rnd.nextInt( population.length );
        if ( population[ p1 ].fitness() > population[ p2 ].fitness() ) {
            return population[ p1 ];
        }
        else {
            return population[ p2 ];
        }
    }

    private Genome parentSelectionByRouletteWheel() {
        double totalFitness = 0;
        for (Genome genome : population) {
            totalFitness += genome.fitness();
        }

        double[] prob = new double[population.length];
        prob[0] = population[0].fitness()/totalFitness;
        for (int j = 1; j < population.length; j++) {
            prob[j] = population[j].fitness()/totalFitness + prob[j-1];
        }

        double randomNumber = rnd.nextDouble();
        for (int a = 0; a < population.length; a++) {
            if(randomNumber<prob[a]){
                return population[a];
            }
        }
        return population[0];
    }
    private Genome[] parentSelectionBySUS() {

        double totalFitness = 0;
        for (Genome genome : population) {
            totalFitness += genome.fitness();
        }

        double[] probability = new double[population.length];
        probability[0] = population[0].fitness()/ totalFitness;
        for (int i = 1; i < population.length; i++) {
            probability[i] = population[i].fitness() / totalFitness + probability[i-1];
        }

        int current_member = 0;
        int innerIndex = 0;
        double populationSize  = population.length;
        double max = 1/ populationSize;
        double randomNumber = rnd.nextDouble() * max ;
        Genome[] newPopulation = new Genome[population.length];

        while (current_member < populationSize){
            while (randomNumber < probability[innerIndex]){
                newPopulation[current_member] = population[innerIndex];
                randomNumber += max;
                current_member++;
            }
            innerIndex++;
        }
        return newPopulation;
    }

    private Genome[] crossOver( Genome parent1, Genome parent2 ) {
        Genome[] children = new Genome[2];
        children[ 0 ] = ( Genome ) parent1.clone();
        children[ 1 ] = ( Genome ) parent2.clone();

        int point = rnd.nextInt( length );
        for ( int i = point; i < length; i++ ) {
            for ( int j = point; j < length; j++ ) {
                children[ 0 ].setBit( i,j, parent2.getBit( i,j ) );
                children[ 1 ].setBit( i,j, parent1.getBit( i,j ) );
            }

        }
        return children;
    }
}
