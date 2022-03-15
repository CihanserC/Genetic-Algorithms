import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GATest {

    private static final int populationSize = 300;
    private static final double crossOverRate = 0.7;
    private static final int generationCount = 1000;
    private static final int nBits = 100;
    private static final double mutationRate = 1.0 / populationSize;
    private static final int targetFitness = nBits;
    private static final boolean elitism = true;

    public static void main(String[] args) {
        runDataInstances();
    }

    public static void runDataInstances() {
        String[] fileNames = {
                "src/knapsack_data_instances/knapPI_1_100_1000_1",
                "src/knapsack_data_instances/knapPI_1_200_1000_1",
                "src/knapsack_data_instances/knapPI_1_500_1000_1",
                "src/knapsack_data_instances/knapPI_1_1000_1000_1",
                "src/knapsack_data_instances/knapPI_1_2000_1000_1",
                "src/knapsack_data_instances/knapPI_1_5000_1000_1",
                "src/knapsack_data_instances/knapPI_1_10000_1000_1"};

        String[] OptimumfileName= {
                "src/knapsack_optimum/knapPI_1_100_1000_1.txt",
                "src/knapsack_optimum/knapPI_1_200_1000_1.txt",
                "src/knapsack_optimum/knapPI_1_500_1000_1.txt",
                "src/knapsack_optimum/knapPI_1_1000_1000_1.txt",
                "src/knapsack_optimum/knapPI_1_2000_1000_1.txt",
                "src/knapsack_optimum/knapPI_1_5000_1000_1.txt",
                "src/knapsack_optimum/knapPI_1_10000_1000_1.txt"};

        for (int i=0;i< fileNames.length;i++) {
            System.out.println("Reading File Name:" + fileNames[i]);
            GATestRunAll(fileNames[i], OptimumfileName[i]);
        }
    }

    public static void GATestRunAll(String fileName, String OptimumfileName) {

        // -------------------- File Reading Parts

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        assert scanner != null;
        int numberOfItems = scanner.nextInt();
        int knapsackCapacity = scanner.nextInt();
        ArrayList<Integer> itemProfit = new ArrayList<>();
        ArrayList<Integer> itemWeight = new ArrayList<>();
        ArrayList<Integer> problemSolution = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++) {
            itemProfit.add(scanner.nextInt());
            itemWeight.add(scanner.nextInt());
        }

        for (int i = 0; i < numberOfItems; i++) {
            problemSolution.add(scanner.nextInt());
        }

        // Read Optimum values for Target fitness
        Scanner scannerOptimums = null;
        try {
            scannerOptimums = new Scanner(new File(OptimumfileName));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int optimumValue = scannerOptimums.nextInt();

//        --------------------------------- Initialize Genetic Algorithms
        GAProblem problem = new GAProblem(numberOfItems,knapsackCapacity,itemProfit, itemWeight, problemSolution );

        GA ga = new GA();
        BinaryGenome best = ga.run( populationSize, generationCount, crossOverRate, mutationRate, optimumValue, elitism, problem );
        System.out.println( best );
        System.out.println( "GA found in " + ga.getGenerations() + " generations." );

        PBIL pbil = new PBIL();
        best = pbil.run( populationSize, generationCount, optimumValue, 0.1, populationSize / 50, true, problem );
        System.out.println( best );
        System.out.println( "PBIL with truncation selection found in " + pbil.getGenerations() + " generations." );

        //PBIL with tournament selection can find the solution faster than regular GA most of the time
        best = pbil.run( populationSize, generationCount, optimumValue, 0.1, populationSize / 5, false, problem );
        System.out.println( best );
        System.out.println( "PBIL with tournament selection found in " + pbil.getGenerations() + " generations." );

        //UDMA; recall that UDMA is a special case of PBIL where alpha is 1
        //Generally the result is bad for this problem since it "forgets" the past
        //So you have to create many offspring, notice 10 * populationSize
        //Try with only populationSize, and see the results are very bad
        best = pbil.run( 10 * populationSize, generationCount, optimumValue, 1, populationSize / 5, true, problem );
        System.out.println( best );
        System.out.println( "UDMA with truncation selection found in " + pbil.getGenerations() + " generations." );

        best = pbil.run( 10 * populationSize, generationCount, optimumValue, 1, populationSize / 5, false, problem );
        System.out.println( best );
        System.out.println( "UDMA with tournament selection found in " + pbil.getGenerations() + " generations." );

        //compact GA
        //cGA is slower since it compares each pair of genes in every generation
        //cGA works very bad for this problem since when a distribution is set to 0 it is not possible to generate
        //a 1 for that bit anymore
        cGA cga = new cGA();
        best=cga.run( populationSize / 3, generationCount, optimumValue, 20000, problem );
        System.out.println( best );
        System.out.println( "cGA found in " + cga.getGenerations() + " generations." );

    }
}