import org.epochx.epox.Node;
import org.epochx.tools.util.TypeUtils;

public class ConcatFunction extends org.epochx.epox.Node {

    public ConcatFunction() {
        this((Node)null, (Node)null, (Node)null, (Node)null);
    }

    public ConcatFunction(Node child1, Node child2, Node child3, Node child4) {
        super(new Node[]{child1, child2, child3, child4});
    }

    @Override
    public Object evaluate() {
        String result = "";
        for (int i = 0; i < 4; i++){
            if((Boolean)this.getChild(i).evaluate()){
                result += "1";
            }
            else{
                result += "0";
            }
        }
        return result;
    }

    public String getIdentifier() {
        return "CONCAT";
    }

    public Class<?> getReturnType(Class<?>... inputTypes) {
        return inputTypes.length == 4 && TypeUtils.allEqual(inputTypes, Boolean.class) ? String.class : null;
    }
}
