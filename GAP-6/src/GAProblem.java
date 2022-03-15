public class GAProblem {
    private int processorTaskCost[][]; // processor,task
    private int processorTaskResource[][]; // processor,task
    private int processorCapacity[]; // processor

    public GAProblem(int[][] processorTaskCost, int[][] processorTaskResource, int[] processorCapacity) {
        this.processorTaskCost = processorTaskCost;
        this.processorTaskResource = processorTaskResource;
        this.processorCapacity = processorCapacity;
    }

    public int getCost(int processor, int task){
        return processorTaskCost[processor][task];
    }

    public int getResource(int processor, int task){
        return processorTaskResource[processor][task];
    }

    public int getCapacity(int processor){
        return processorCapacity[processor];
    }

    public int getProcessorCount(){
        return processorTaskCost.length;
    }

    public int getTaskCount(){
        return processorTaskCost[0].length;
    }
}
