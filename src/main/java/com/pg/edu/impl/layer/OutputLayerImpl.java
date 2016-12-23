package com.pg.edu.impl.layer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import com.pg.edu.api.data.LearningData;
import com.pg.edu.api.data.ResultData;
import com.pg.edu.api.layer.OutputLayer;
import com.pg.edu.api.node.Node;
import com.pg.edu.api.nodeconnector.NodeConnector;
import com.pg.edu.impl.data.ResultDataImpl;
import com.pg.edu.impl.node.NodeImpl;

import static java.util.stream.Collectors.toList;

public class OutputLayerImpl implements OutputLayer {

    private final int size;
    private final List<Node> nodes;

    public OutputLayerImpl(int size) {
        this.size = size;
        nodes = new LinkedList<>();
        initNodes();
    }
    private void initNodes() {

        IntStream.range(0, size).mapToObj(value -> new NodeImpl()).forEach(nodes::add);
    }

    @Override
    public void updateWeights( ) {
        nodes.forEach(node -> node.getParentConnectors().forEach(NodeConnector::updateWeight));


    }

    @Override
    public ResultData feedForward() {

        List<Double> result = nodes.stream().map(Node::getValue).collect(toList());
        return new ResultDataImpl(result);

    }

    @Override
    public void setExpectedData(LearningData learningData) {
        Preconditions.checkState(learningData.getOutputs().size() == nodes.size());

        for(int i = 0; i< learningData.getOutputs().size(); i++) {
            nodes.get(i).setExpectedOutput(learningData.getOutputs().get(i));
        }
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }
}
