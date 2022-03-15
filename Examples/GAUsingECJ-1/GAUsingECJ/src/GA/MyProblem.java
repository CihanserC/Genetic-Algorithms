/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GA;

import ec.*;
import ec.simple.*;
import ec.vector.*;

/**
 *
 * @author HP
 */
public class MyProblem extends Problem implements SimpleProblemForm {

    @Override
    public void evaluate( EvolutionState state, Individual ind, int subpopulation, int thread ) {
        if ( ind.evaluated ) {
            return;
        }
        if ( !(ind instanceof IntegerVectorIndividual) ) {
            state.output.fatal( "Whoa!  It’s not an IntegerVectorIndividual!!!" );
        }
        int[] genome = (( IntegerVectorIndividual ) ind).genome;
        double product = 1.0;
        for ( int x = 0; x < genome.length; x++ ) {
            product = product * genome[ x ];
        }
        (( SimpleFitness ) ind.fitness).setFitness( state, product, product == Double.POSITIVE_INFINITY );
        ind.evaluated = true;
    }
}
