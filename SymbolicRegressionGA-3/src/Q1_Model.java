import org.epochx.epox.Node;
import org.epochx.epox.Variable;
import org.epochx.epox.bool.AndFunction;
import org.epochx.epox.bool.NotFunction;
import org.epochx.epox.bool.OrFunction;
import org.epochx.epox.math.*;
import org.epochx.gp.model.GPModel;
import org.epochx.gp.representation.GPCandidateProgram;
import org.epochx.representation.CandidateProgram;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Q1_Model extends GPModel {
    // Variables.
    private final Variable[] variables;

    private final List<Double[]> inputs;
    private final List<Double> outputs;

    public Q1_Model(int nofInputBits, String inputsFile, String outputsFile) throws FileNotFoundException {
        // Define function set.
        final List<Node> syntax = new ArrayList<>();
        syntax.add(new AddFunction());
        syntax.add(new SubtractFunction());
        syntax.add(new MultiplyFunction());
        syntax.add(new DivisionProtectedFunction());
        syntax.add(new PowerFunction());

        // Define terminal set;
        variables = new Variable[nofInputBits + 4 ];
        for (int i = 0; i < nofInputBits; i++) {
            variables[i] = new Variable("d" + (i + 1), Double.class);
            syntax.add(variables[i]);
        }
        variables[2] = new Variable("1.0", Double.class);
        variables[3] = new Variable("2.0", Double.class);
        variables[4] = new Variable("3.0", Double.class);
        variables[5] = new Variable("4.0", Double.class);
        syntax.add(variables[2]);
        syntax.add(variables[3]);
        syntax.add(variables[4]);
        syntax.add(variables[5]);
        
        this.setSyntax(syntax);
        
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        
        Scanner in = new Scanner(new File(inputsFile));
        while ( in.hasNext()) {
            Double[] data = new Double[variables.length];
            data[0] = Double.parseDouble(in.next());
            data[1] = Double.parseDouble(in.next());
            inputs.add(data);
        }
        in.close();
        
        in = new Scanner(new File(outputsFile));
        while ( in.hasNext()) {
            Double output = Double.parseDouble(in.next());
            outputs.add(output);
        }
        in.close();
    }
    
    @Override
    public double getFitness(CandidateProgram p) {

        final GPCandidateProgram program = (GPCandidateProgram) p;
        if ( program.getReturnType() != Double.class )
            return Double.MAX_VALUE;
        
        double totalError = 0;

        for (int i = 0; i < inputs.size() ; i++ ) {
            Double[] input = inputs.get(i);
            for (int j = 0; j < input.length; j++) {
                variables[j].setValue( input[j] );                
            }
            variables[2].setValue( 1.0 );
            variables[3].setValue( 2.0 );
            variables[4].setValue( 3.0 );
            variables[5].setValue( 4.0 );
            Double result = (Double)program.evaluate();
            if(result.isNaN())
                result = 999999.0;
            totalError += Math.pow(result - outputs.get(i), 2);
        }
        totalError = Math.sqrt(totalError / inputs.size());
        return totalError;
    }

    @Override
    public Class<?> getReturnType() {
        return Double.class;
    }
}
