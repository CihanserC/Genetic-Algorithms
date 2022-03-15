
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.epochx.epox.Node;
import org.epochx.epox.Variable;
import org.epochx.epox.bool.AndFunction;
import org.epochx.epox.bool.NotFunction;
import org.epochx.epox.bool.OrFunction;
import org.epochx.epox.bool.XorFunction;
import org.epochx.gp.model.GPModel;
import org.epochx.gp.representation.GPCandidateProgram;
import org.epochx.representation.CandidateProgram;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author korhan.karabulut
 */
public class XORModel extends GPModel {
    // Variables.
    private final Variable[] variables;
    
    private final List<Boolean[]> inputs;
    private final List<Boolean> outputs;
    
    public XORModel(int nofInputBits, String inputsFile, String outputsFile) throws FileNotFoundException {
        // Define function set.
        final List<Node> syntax = new ArrayList<>();
        syntax.add(new AndFunction());
        syntax.add(new OrFunction());
        syntax.add(new NotFunction());

        // Define terminal set;
        variables = new Variable[nofInputBits ];
        for (int i = 0; i < variables.length; i++) {
            variables[i] = new Variable("d" + (i + 1), Boolean.class);
            syntax.add(variables[i]);
        }
        
        this.setSyntax(syntax);
        
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        
        Scanner in = new Scanner(new File(inputsFile));
        while ( in.hasNext()) {
            Boolean[] data = new Boolean[variables.length];
            String input = in.next();
            for (int i = 0; i < variables.length; i++) {
                if ( input.charAt(i)=='1') 
                    data[i] = Boolean.TRUE;
                else
                    data[i] = Boolean.FALSE;
            }
            inputs.add(data);
        }
        in.close();
        
        in = new Scanner(new File(outputsFile));
        while ( in.hasNext()) {
            Boolean output;
            String input = in.next();
                            if ( input.charAt(0)=='1') 
                    output = Boolean.TRUE;
                else
                    output = Boolean.FALSE;

            outputs.add(output);
        }
        in.close();
    }
    
    @Override
    public double getFitness(CandidateProgram p) {

        final GPCandidateProgram program = (GPCandidateProgram) p;
        if ( program.getReturnType() != Boolean.class )
            return Double.MAX_VALUE;
        
        double totalError = 0;

        for (int i = 0; i < inputs.size() ; i++ ) {
            Boolean[] input = inputs.get(i);
            for (int j = 0; j < input.length; j++) {
                variables[j].setValue( input[j] );                
            }
            Boolean result = (Boolean)program.evaluate();
             if ( !Objects.equals( result, outputs.get(i) ) ) totalError++;
        }
        return totalError;
    }

    @Override
    public Class<?> getReturnType() {
        return Boolean.class;
    }
}
