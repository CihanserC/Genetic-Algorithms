/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author HP
 */
public class Genome {
    private int[] genes;
    private GAProblem problem;
    private final int penaltyCoefficient = 10;

    @Override
    protected Object clone() {        
        return new Genome(this);
    }
    
    public Genome(GAProblem problem) {
        this.problem = problem;
        genes = new int[problem.getTaskCount()];
    }

    public Genome(Genome g) {
        genes = Arrays.copyOf( g.genes, g.getLength() );
        this.problem = g.problem;
    }
    
    public int getLength() {
        return genes.length;
    }
    
    public void setBit(int task, int processor){
        genes[task]=processor;
    }
    public int getBit(int task) {
        return genes[task];
    }
    
    public void initialize() {
        Random rng = new Random();
        for ( int i = 0; i < genes.length; i++ ) {
                genes[i]=rng.nextInt(problem.getProcessorCount());
        }
    }
    
    @Override
    public String toString() {
        String rep="";
        for ( int i = 0; i < genes.length; i++ ) {
            rep += genes[i] + " | ";
        }
        return rep;
    }

    public int fitness() {
        int totalFitness = 0;
        int capacityOverflow = 0;
        int remainingFitness[] = new int[problem.getProcessorCount()];
        for (int p = 0; p < problem.getProcessorCount(); p++) {
            remainingFitness[p] = problem.getCapacity(p);
        }

        for (int task = 0; task < problem.getTaskCount(); task++) {
            int processor = genes[task];
            totalFitness += problem.getCost(processor,task);
            remainingFitness[processor] -= problem.getResource(processor,task);
        }

        for (int i = 0; i < problem.getProcessorCount(); i++) {
            if(remainingFitness[i]<0){
                capacityOverflow = capacityOverflow + (-1*remainingFitness[i]);
            }
        }
        totalFitness = totalFitness - (capacityOverflow*penaltyCoefficient);
        return totalFitness;
    }
}
