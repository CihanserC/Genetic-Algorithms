import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TwoTwoFour extends Genome<TwoTwoFour> {
    private final List<Character> letters;
    private final Random random;

    public TwoTwoFour(List<Character> letters) {
        this.letters = letters;
        random = new Random();
    }

    public static TwoTwoFour randomInstance()
    {
        List<Character> letters = new ArrayList<>( List.of('T', 'W', 'O', 'F', 'U', 'R', ' ', ' ', ' ', ' '));
        Collections.shuffle(letters);
        // Initial Letters
        return checkAndFixConstraints(letters);
//        return new SendMoreMoney2(letters);
    }

    public static TwoTwoFour checkAndFixConstraints(List<Character> currentletters){
        // First letter (leftmost char) of the word can not be zero.
        char initialLetter1  = 'T';
        char initialLetter2  = 'F';
        while(currentletters.indexOf(initialLetter1) == 0 || currentletters.indexOf(initialLetter2) == 0 )
        {
            Collections.shuffle(currentletters);
        }
        return new TwoTwoFour(currentletters);
    }


    @Override
    public double fitness() {
        int t = letters.indexOf('T');
        int w = letters.indexOf('W');
        int o = letters.indexOf('O');
        int f = letters.indexOf('F');
        int u = letters.indexOf('U');
        int r = letters.indexOf('R');

        int two = t * 100 + w * 10 + o;
        int four = f * 1000 + o * 100 + u * 10 + r ;
        int difference = Math.abs(four - (two + two));
        return 1.0 / (difference + 1.0);
    }

    @Override
    public List<TwoTwoFour> crossover(TwoTwoFour other) {
        TwoTwoFour child1 = new TwoTwoFour(new ArrayList<>(letters));
        TwoTwoFour child2 = new TwoTwoFour(new ArrayList<>(other.letters));
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
        if(letters.indexOf('T') == 0 || letters.indexOf('F') == 0 ){
            int index3 = random.nextInt(letters.size());
            Collections.swap(letters, 0, index3);
        }
    }

    @Override
    public TwoTwoFour copy() {
        return new TwoTwoFour(new ArrayList<>(letters));
    }

    @Override
    public String toString() {
        int t = letters.indexOf('T');
        int w = letters.indexOf('W');
        int o = letters.indexOf('O');
        int f = letters.indexOf('F');
        int u = letters.indexOf('U');
        int r = letters.indexOf('R');

        int two = t * 100 + w * 10 + o;
        int four = f * 1000 + o * 100 + u * 10 + r ;
        int difference = Math.abs(four - (two + two));

        return (two + " + " + two + " = " + four + " Difference: " + difference);
    }

    public static void main(String[] args) {
        ArrayList<TwoTwoFour> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 1000;
        final int GENERATIONS = 1000;
        final double THRESHOLD = 1.0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            initialPopulation.add(TwoTwoFour.randomInstance());
        }
        GeneticAlgorithm<TwoTwoFour> geneticAlgorithm = new GeneticAlgorithm<>(
                initialPopulation,
                0.7,
                0.7,
                GeneticAlgorithm.SelectionType.ROULETTE
        );
        TwoTwoFour result = geneticAlgorithm.run(GENERATIONS, THRESHOLD);
        System.out.println(result);
    }
}