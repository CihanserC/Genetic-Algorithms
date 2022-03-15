import java.util.Arrays;
import java.util.Random;

public class Genome {
    public double[] genes;   // Object variable defined as double
    public double   sigma;   // Mutation step size, many sigma

    @Override
    public Genome clone() {
        return new Genome(this);
    }

    public Genome(Genome g)
    {
        genes = Arrays.copyOf(g.genes, g.genes.length);
        sigma = g.sigma;
    }

    public Genome()
    {
    }

    public void setValue(int index, double value){
        genes[index]=value;
    }
    public double getValue(int index) {
        return genes[index];
    }

    public void initialize(int length) {
        genes = new double[length];
        Random random = new Random();
        double max = 100.0;
        double min = -100.0;
        double range = max - min;
        for ( int i = 0; i < genes.length; i++ )
        {
            genes[i] = range*random.nextDouble() + min;
        }
        sigma = random.nextDouble()*(max-min) + min;
    }


    // All three functions are down below:

    // Zakharov Algorithm Fitness Calculation Method
//    public double fitness()
//    {
//        double fitnessValue = 0;
//        double value1=0;
//        double value2=0;
//        double value3=0;
//        for (int i = 1; i <= genes.length; i++)
//        {
//            value1 += Math.pow(genes[i-1],2);
//        }
//
//        for (int j = 1; j <= genes.length; j++)
//        {
//            value2 += 0.5 * j *  genes[j-1];
//        }
//        value2 = Math.pow(value2,2);
//
//        for (int k = 1; k <= genes.length; k++)
//        {
//            value3 += 0.5 * k *  genes[k-1];
//        }
//        value3 = Math.pow(value3,4);
//
//        fitnessValue = value1 + value2 + value3;
//        return fitnessValue;
//    }

    // Schaffer's F7 function Fitness Calculation Method
//    public double fitness()
//    {
//        double fitnessValue = 0;
//        double normalizer = 1.0 / (double)(genes.length-1);
//        for(int i=0; i< genes.length-1; i++)
//        {
//            double x = Math.pow(genes[i],2);
//            double y = Math.pow(genes[i+1],2);
//            double si = Math.sqrt(x+y);
//            fitnessValue += Math.pow(normalizer * Math.sqrt(si) * Math.sin(50*Math.pow(si,0.20) +1) ,2);
//        }
//        return fitnessValue;
//    }

    // Styblisnki-Tang Function Fitness Calculation Method
    public double fitness()
    {
        double fitnessValue = 0;
        for (int i = 1; i < genes.length; i++)
        {
            fitnessValue += Math.pow(genes[i], 4) - (16 * Math.pow(genes[i],2)) + (5*genes[i]);
        }
        return fitnessValue/2;
    }

}
