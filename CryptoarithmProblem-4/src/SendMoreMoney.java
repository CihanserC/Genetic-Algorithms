import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SendMoreMoney extends Genome<SendMoreMoney> {
    private final List<Character> letters;
    private final Random random;

    public SendMoreMoney(List<Character> letters) {
        this.letters = letters;
        random = new Random();
    }

    public static SendMoreMoney randomInstance()
    {
        List<Character> letters = new ArrayList<>( List.of('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y', ' ', ' '));
        Collections.shuffle(letters);

        // Check For Initial Letters
        return checkAndFixConstraints(letters);
    }

    public static SendMoreMoney checkAndFixConstraints(List<Character> currentletters){
        // First letter (leftmost char of the word) can not be zero.
        char initialLetter1  = 'S';
        char initialLetter2  = 'M';

        while(currentletters.indexOf(initialLetter1) == 0 || currentletters.indexOf(initialLetter2) == 0)
        {
            Collections.shuffle(currentletters);
        }
        return new SendMoreMoney(currentletters);
    }


    @Override
    public double fitness() {
        int s = letters.indexOf('S');
        int e = letters.indexOf('E');
        int n = letters.indexOf('N');
        int d = letters.indexOf('D');
        int m = letters.indexOf('M');
        int o = letters.indexOf('O');
        int r = letters.indexOf('R');
        int y = letters.indexOf('Y');
        int send = s * 1000 + e * 100 + n * 10 + d;
        int more = m * 1000 + o * 100 + r * 10 + e;
        int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
        int difference = Math.abs(money - (send + more));
        return 1.0 / (difference + 1.0);
    }

    @Override
    public List<SendMoreMoney> crossover(SendMoreMoney other) {
        SendMoreMoney child1 = new SendMoreMoney(new ArrayList<>(letters));
        SendMoreMoney child2 = new SendMoreMoney(new ArrayList<>(other.letters));
        int index1 = random.nextInt(letters.size());
        int index2 = random.nextInt(other.letters.size());
        Character letter1 = letters.get(index1);
        Character letter2 = other.letters.get(index2);
        int index3 = letters.indexOf(letter2);
        int index4 = other.letters.indexOf(letter1);
        Collections.swap(child1.letters, index1, index3);
        Collections.swap(child1.letters, index2, index4);
        return List.of(child1, child2);
    }

    @Override
    public void mutate() {

        int index1 = random.nextInt(letters.size());
        int index2 = random.nextInt(letters.size());

        Collections.swap(letters, index1, index2);

        // If Initial letter values are zero, then change 0. indice again!!
        if(letters.indexOf('S') == 0 || letters.indexOf('M') == 0){
            int index3 = random.nextInt(letters.size());
            Collections.swap(letters, 0, index3);
        }
    }

    @Override
    public SendMoreMoney copy() {
        return new SendMoreMoney(new ArrayList<>(letters));
    }

    @Override
    public String toString() {
        int s = letters.indexOf('S');
        int e = letters.indexOf('E');
        int n = letters.indexOf('N');
        int d = letters.indexOf('D');
        int m = letters.indexOf('M');
        int o = letters.indexOf('O');
        int r = letters.indexOf('R');
        int y = letters.indexOf('Y');

        int send = s * 1000 + e * 100 + n * 10 + d;
        int more = m * 1000 + o * 100 + r * 10 + e;
        int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
        int difference = Math.abs(money - (send + more));
        return (send + " + " + more + " = " + money + " Difference: " + difference);
    }

    public static void main(String[] args) {
        ArrayList<SendMoreMoney> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 1000;
        final int GENERATIONS = 1000;
        final double THRESHOLD = 1.0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            initialPopulation.add(SendMoreMoney.randomInstance());
        }
        GeneticAlgorithm<SendMoreMoney> geneticAlgorithm = new GeneticAlgorithm<>(
                initialPopulation,
                0.2,
                0.7,
                GeneticAlgorithm.SelectionType.ROULETTE
        );
        SendMoreMoney result = geneticAlgorithm.run(GENERATIONS, THRESHOLD);
        System.out.println(result);
    }
}