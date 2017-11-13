package neuralnet.ml.prg.extension;

import neuralnet.ml.prg.EncogProgramContext;
import neuralnet.ml.prg.ProgramNode;
import neuralnet.ml.prg.expvalue.ExpressionValue;
import neuralnet.ml.prg.expvalue.ValueType;

import java.util.List;
import java.util.Random;

/**
 * Created by jeffh on 9/13/2016.
 */
public class SpecificConstant implements ProgramExtensionTemplate {

    /**
     * The constant value.
     */
    private final ExpressionValue value;

    /**
     * The name of the constant.
     */
    private final String name;

    private final ParamTemplate returnValue;

    /**
     * Constructor.
     * @param theValue The constant value.
     */
    public SpecificConstant(double theValue) {
        this.value = new ExpressionValue(theValue);
        this.name = ""+theValue;
        this.returnValue = new ParamTemplate();
        this.returnValue.addType("f");
    }

    @Override
    public ExpressionValue evaluate(ProgramNode actual) {
        return this.value;
    }

    @Override
    public int getChildNodeCount() {
        return 0;
    }

    @Override
    public int getDataSize() {
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.Leaf;
    }

    @Override
    public List<ParamTemplate> getParams() {
        return ProgramExtensionTemplate.NO_PARAMS;
    }

    @Override
    public int getPrecedence() {
        return ProgramExtensionTemplate.NO_PREC;
    }

    @Override
    public ParamTemplate getReturnValue() {
        return this.returnValue;
    }

    @Override
    public boolean isPossibleReturnType(EncogProgramContext context, ValueType rtn) {
        return this.returnValue.getPossibleTypes().contains(rtn);
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public void randomize(Random rnd, List<ValueType> desiredType, ProgramNode actual, double minValue, double maxValue) {

    }
}
