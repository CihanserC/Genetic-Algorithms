import java.io.IOException;

public class Test {

    public static void main(String[] args)
    {
//      Use D=10, 30, 50

        SetEvolutionStrategy es = new SetEvolutionStrategy(10,200);
        SetESNSigma esnSigma = new SetESNSigma(10,200);

        try
        {
            esnSigma.run();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
