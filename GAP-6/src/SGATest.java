/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class SGATest {

    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) {

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("gap1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int problemCount = scanner.nextInt();

        for (int i = 0; i <problemCount ; i++) {
            System.out.println("---------------------- Problem " + (i+1) +" -----------------------------");
            int processorCount = scanner.nextInt();
            int taskCount = scanner.nextInt();
            int processorTaskCost[][] = new int[processorCount][taskCount];
            int processorTaskResource[][] = new int[processorCount][taskCount];
            int processorCapacity[] = new int[processorCount];

            for (int p = 0; p < processorCount; p++) {
                for (int t = 0; t < taskCount; t++) {
                    int number = scanner.nextInt();
                    processorTaskCost[p][t] = number;
                }
            }

            for (int p = 0; p < processorCount; p++) {
                for (int t = 0; t < taskCount; t++) {
                    int number = scanner.nextInt();
                    processorTaskResource[p][t] = number;
                }
            }

            for (int p = 0; p < processorCount; p++) {
                int number = scanner.nextInt();
                processorCapacity[p] = number;
            }

            GAProblem problem = new GAProblem(processorTaskCost,processorTaskResource,processorCapacity);

            SGA sga = new SGA(100, 10000, 0.7, 0.1, problem);
            sga.run();
        }
    }
}
