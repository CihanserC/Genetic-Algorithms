import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BrownYellowPurple extends Genome<BrownYellowPurple> {
    private final List<Character> letters;
    private final Random random;

    public BrownYellowPurple(List<Character> letters) {
        this.letters = letters;
        random = new Random();
    }

    public static BrownYellowPurple randomInstance()
    {
        List<Character> letters = new ArrayList<>( List.of('B', 'R', 'O', 'W', 'N', 'Y', 'E', 'L', 'P', 'U'));
        Collections.shuffle(letters);
        // Initial Letters
        return checkAndFixConstraints(letters);
//        return new SendMoreMoney2(letters);
    }

    public static BrownYellowPurple checkAndFixConstraints(List<Character> currentletters){
        // First letter (leftmost char) of the word can not be zero.
        char initialLetter1  = 'B';
        char initialLetter2  = 'Y';
        char initialLetter3  = 'P';
        while(currentletters.indexOf(initialLetter1) == 0 || currentletters.indexOf(initialLetter2) == 0 || currentletters.indexOf(initialLetter3) == 0 )
        {
            Collections.shuffle(currentletters);
        }
        return new BrownYellowPurple(currentletters);
    }


    @Override
    public double fitness() {
        // 'B', 'R', 'O', 'W', 'N', 'Y', 'E', 'L', 'P', 'U'
        int b = letters.indexOf('B');
        int r = letters.indexOf('R');
        int o = letters.indexOf('O');
        int w = letters.indexOf('W');
        int n = letters.indexOf('N');
        int y = letters.indexOf('Y');
        int e = letters.indexOf('E');
        int l = letters.indexOf('L');
        int p = letters.indexOf('P');
        int u = letters.indexOf('U');

        int brown = b * 10000 + r * 1000 + o * 100 + w * 10 + n;
        int yellow = y * 100000 + e * 10000 + l * 1000 + l * 100 + o *10 + w;
        int purple = p * 100000 + u * 10000 + r * 1000 + p * 100 + l * 10 + e ;
        int difference = Math.abs(purple - (brown + yellow));

        return 1.0 / (difference + 1.0);
    }

    @Override
    public List<BrownYellowPurple> crossover(BrownYellowPurple other) {
        BrownYellowPurple child1 = new BrownYellowPurple(new ArrayList<>(letters));
        BrownYellowPurple child2 = new BrownYellowPurple(new ArrayList<>(other.letters));
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
        // If Initial letter values are zero, then change 0. index again!!
        // Repair the genomes!
        if(letters.indexOf('B') == 0 || letters.indexOf('Y') == 0  || letters.indexOf('P') == 0 ){
            int index3 = random.nextInt(letters.size());
            Collections.swap(letters, 0, index3);
        }
    }

    @Override
    public BrownYellowPurple copy() {
        return new BrownYellowPurple(new ArrayList<>(letters));
    }

    @Override
    public String toString() {

        int b = letters.indexOf('B');
        int r = letters.indexOf('R');
        int o = letters.indexOf('O');
        int w = letters.indexOf('W');
        int n = letters.indexOf('N');
        int y = letters.indexOf('Y');
        int e = letters.indexOf('E');
        int l = letters.indexOf('L');
        int p = letters.indexOf('P');
        int u = letters.indexOf('U');

        int brown = b * 10000 + r * 1000 + o * 100 + w * 10 + n;
        int yellow = y * 100000 + e * 10000 + l * 1000 + l * 100 + o *10 + w;
        int purple = p * 100000 + u * 10000 + r * 1000 + p * 100 + l * 10 + e ;
        int difference = Math.abs(purple - (brown + yellow));

        return (brown + " + " + yellow + " = " + purple + " Difference: " + difference);
    }

    public static void main(String[] args) {
        ArrayList<BrownYellowPurple> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 1000;
        final int GENERATIONS = 1000;
        final double THRESHOLD = 1.0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            initialPopulation.add(BrownYellowPurple.randomInstance());
        }
        GeneticAlgorithm<BrownYellowPurple> geneticAlgorithm = new GeneticAlgorithm<>(
                initialPopulation,
                0.2,
                0.7,
                GeneticAlgorithm.SelectionType.ROULETTE
        );
        BrownYellowPurple result = geneticAlgorithm.run(GENERATIONS, THRESHOLD);
        System.out.println(result);
    }
}