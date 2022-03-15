
public class BinaryGenome implements Comparable<BinaryGenome> {

    private boolean genes[];
    private boolean changed;
    private int fitness;
    private GAProblem problem;
    private final int penaltyCoefficient = 1;

    public BinaryGenome( GAProblem problem ) {
        this.problem = problem;
        genes = new boolean[problem.getNumberOfItems()];

        for ( int i = 0; i < genes.length; i++ ) {
            if ( Math.random() > 0.5 ) {
                genes[ i ] = true;
            }
        }
        changed = true;
    }

    public int fitness() {      // Add penalty in the Fitness methods
        if (changed) {

            fitness = 0;
            int totalWeight = 0;


            for (int i = 0; i < problem.getNumberOfItems(); i++) {

                if (getGene(i) == true) {
                    fitness += problem.getItemProfit(i);
                    totalWeight += problem.getItemWeight(i); // total weight obtained from the solution
                }
            }

            for (int j = 0; j < problem.getNumberOfItems(); j++) {

                if (totalWeight > problem.getKnapsackCapacity()) {
                    this.fitness -= (totalWeight - problem.getKnapsackCapacity()) * penaltyCoefficient;
                }

            }

        }
    return fitness;
    }

    int size() {
        return genes.length;
    }

    public void setGene( int position, boolean value ) {
        genes[ position ] = value;
        changed = true;
    }

    public boolean getGene( int position ) {
        return genes[ position ];
    }

    void flipGene( int position ) {
        genes[ position ] = !genes[ position ];
        changed = true;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for ( boolean gene : genes ) {
            if ( gene ) {
                r.append( "1" );
            }
            else {
                r.append( "0" );
            }
        }
        return "fitness=" + fitness() + " genes=" + r.toString();
    }

    @Override
    public int compareTo( BinaryGenome o ) {
        return o.fitness - this.fitness;
    }

}
