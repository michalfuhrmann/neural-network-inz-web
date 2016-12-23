package com.pg.edu.impl.node;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.pg.edu.api.node.Node;
import com.pg.edu.api.nodeconnector.NodeConnector;
import com.pg.edu.impl.nodeconnector.NodeConnectorImpl;
import com.pg.edu.util.MathUtil;

import static com.pg.edu.util.MathUtil.sigmoidPrime;

public class NodeImpl implements Node {

    public static final double BIAS = 0.01;

    private final List<NodeConnector> parentNodeConnectors;
    private final List<NodeConnector> childrenNodeConnectors;
    private Optional<Double> value = Optional.empty();
    private Optional<Double> error = Optional.empty();
    private Optional<Double> expectedOutput = Optional.empty();

    public NodeImpl(List<NodeConnector> parentNodeConnectors, List<NodeConnector> childrenNodeConnectors) {
        this.parentNodeConnectors = parentNodeConnectors;
        this.childrenNodeConnectors = childrenNodeConnectors;
    }

    public NodeImpl() {
        this.parentNodeConnectors = new LinkedList<>();
        this.childrenNodeConnectors = new LinkedList<>();
    }

    @Override
    public void setValue(double value) {
        this.value = Optional.of(value);
    }

    @Override
    public Double getValue() {
        return MathUtil.sigmoid(getRawValue());
    }

    private Double getRawValue() {

        if (parentNodeConnectors.isEmpty()) {
            return value.orElseThrow(() -> new IllegalStateException("No value was set for the node"));
        }

        this.value = Optional.of(parentNodeConnectors.stream()
                .mapToDouble(nodeConnector -> BIAS + (nodeConnector.getWeight() * nodeConnector.getParentNode().getValue()))
                .sum());
        return this.value.get();

    }

    @Override
    public Double calculateError() {
        if (childrenNodeConnectors.isEmpty()) {
            this.error = Optional.of(calculateErrorValue());
        } else {
            this.error = Optional.of(childrenNodeConnectors.stream()
                    .mapToDouble(nodeConnector -> nodeConnector.getWeight() * nodeConnector.getChildrenNode().calculateError())
                    .sum() * sigmoidPrime(value.get()));
        }
        return error.get();
    }


    public Double getError() {
        return error.orElseThrow(() -> new IllegalStateException("Error was not calculated "));
    }

    /**
     * value = z
     * activation =a = sigmoid(z)
     * expected output =y
     * <p>
     * <p>
     * a-y * sigmoid_prime (z)
     */
    private Double calculateErrorValue() {
        Preconditions.checkState(value.isPresent(), "Value is not initialized for node ");

        return (MathUtil.sigmoid(value.get()) - expectedOutput.get()) * sigmoidPrime(value.get());
    }


    @Override
    public List<NodeConnector> getParentConnectors() {
        return parentNodeConnectors;
    }

    @Override
    public List<NodeConnector> getChildrenConnectors() {
        return childrenNodeConnectors;
    }

    @Override
    public void connect(Node otherNode) {
        NodeConnector parentToChildrenConnector = new NodeConnectorImpl(this, otherNode);
//        NodeConnector childrenToParentConnector = new NodeConnectorImpl(otherNode, this);

        otherNode.getParentConnectors().add(parentToChildrenConnector);

//        otherNode.addParentConnector(parentToChildrenConnector);
        addChildrenConnector(parentToChildrenConnector);
    }

    @Override
    public void addParentConnector(NodeConnector nodeConnector) {
        parentNodeConnectors.add(nodeConnector);
    }

    @Override
    public void addChildrenConnector(NodeConnector nodeConnector) {
        childrenNodeConnectors.add(nodeConnector);
    }


    public void setExpectedOutput(Double expectedOutput) {
        this.expectedOutput = Optional.of(expectedOutput);
    }
}
