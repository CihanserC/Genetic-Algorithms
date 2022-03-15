import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ToGoOut extends Genome<ToGoOut> {
    private final List<Character> letters;
    private final Random random;

    public ToGoOut(List<Character> letters) {
        this.letters = letters;
        random = new Random();
    }

    public static ToGoOut randomInstance()
    {
        List<Character> letters = new ArrayList<>( List.of('T', 'O', 'G', 'U', ' ', ' ', ' ', ' ', ' ', ' '));
        Collections.shuffle(letters);
        // Initial Letters
        return checkAndFixConstraints(letters);
//        return new SendMoreMoney2(letters);
    }

    public static ToGoOut checkAndFixConstraints(List<Character> currentletters){
        // First letter (leftmost char) of the word can not be zero.
        char initialLetter1  = 'T';
        char initialLetter2  = 'G';
        char initialLetter3  = 'O';
        while(currentletters.indexOf(initialLetter1) == 0 || currentletters.indexOf(initialLetter2) == 0 || currentletters.indexOf(initialLetter3) == 0)
        {
            Collections.shuffle(currentletters);
        }
        return new ToGoOut(currentletters);
    }


    @Override
    public double fitness() {
        int t = letters.indexOf('T');
        int o = letters.indexOf('O');
        int g = letters.indexOf('G');
        int u = letters.indexOf('U');

        int to = t * 10 + o;
        int go = g * 10 + o;
        int out = o * 100 + u * 10 + t;
        int difference = Math.abs(out - (to + go));
        return 1.0 / (difference + 1.0);
    }

    @Override
    public List<ToGoOut> crossover(ToGoOut other) {
        ToGoOut child1 = new ToGoOut(new ArrayList<>(letters));
        ToGoOut child2 = new ToGoOut(new ArrayList<>(other.letters));
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
        // Repair the genomes!
        if(letters.indexOf('T') == 0 || letters.indexOf('G') == 0 || letters.indexOf('O') == 0){
            int index3 = random.nextInt(letters.size());
            Collections.swap(letters, 0, index3);
        }
    }

    @Override
    public ToGoOut copy() {
        return new ToGoOut(new ArrayList<>(letters));
    }

    @Override
    public String toString() {
        int t = letters.indexOf('T');
        int o = letters.indexOf('O');
        int g = letters.indexOf('G');
        int u = letters.indexOf('U');

        int to = t * 10 + o;
        int go = g * 10 + o;
        int out = o * 100 + u * 10 + t;
        int difference = Math.abs(out - (to + go));

        return (to + " + " + go + " = " + out + " Difference: " + difference);
    }

    public static void main(String[] args) {
        ArrayList<ToGoOut> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 1000;
        final int GENERATIONS = 1000;
        final double THRESHOLD = 1.0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            initialPopulation.add(ToGoOut.randomInstance());
        }
        GeneticAlgorithm<ToGoOut> geneticAlgorithm = new GeneticAlgorithm<>(
                initialPopulation,
                0.2,
                0.7,
                GeneticAlgorithm.SelectionType.ROULETTE
        );
        ToGoOut result = geneticAlgorithm.run(GENERATIONS, THRESHOLD);
        System.out.println(result);
    }
}