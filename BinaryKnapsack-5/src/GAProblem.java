import java.util.ArrayList;

public class GAProblem
{
    private int numberOfItems;     // Number of items
    private int knapsackCapacity;  // Knapsack capacity
    private ArrayList<Integer> itemProfit;    // Profit of item
    private ArrayList<Integer> itemWeight;    // Weight of item
    private ArrayList<Integer> problemSolution; // Selection of items (It is for the last row of the text files.)

    public GAProblem(int numberOfItems, int knapsackCapacity, ArrayList<Integer> itemProfit,
                     ArrayList<Integer> itemWeight, ArrayList<Integer> problemSolution ) {

        this.numberOfItems = numberOfItems;
        this.knapsackCapacity = knapsackCapacity;
        this.itemProfit = itemProfit;
        this.itemWeight = itemWeight;
        this.problemSolution = problemSolution;
    }

    public int getItemProfit(int itemIndex ){
        return itemProfit.get(itemIndex);
    }

    public int getItemWeight(int itemIndex){
        return itemWeight.get(itemIndex);
    }

    public int getKnapsackCapacity(){
        return knapsackCapacity;
    }

    public int getNumberOfItems(){
        return numberOfItems;
    }

    public ArrayList<Integer> getProblemSolution(){
        return problemSolution;
    }
}