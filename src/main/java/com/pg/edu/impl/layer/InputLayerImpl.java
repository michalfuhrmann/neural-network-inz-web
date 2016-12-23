package com.pg.edu.impl.layer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import com.pg.edu.api.data.ErrorData;
import com.pg.edu.api.data.LearningData;
import com.pg.edu.api.layer.InputLayer;
import com.pg.edu.api.node.Node;
import com.pg.edu.impl.data.ErrorDataImpl;
import com.pg.edu.impl.node.NodeImpl;

import static java.util.stream.Collectors.toList;

public class InputLayerImpl implements InputLayer {

    private final int size;
    private List<Node> nodes;

    public InputLayerImpl(int size) {
        this.size = size;
        this.nodes = new LinkedList<>();
        initNodes();

    }

    private void initNodes() {
        IntStream.range(0, size).mapToObj(value -> new NodeImpl()).forEach(node -> nodes.add(node));
    }


    @Override
    public void setInputs(LearningData learningData) {
        IntStream.range(0, size).forEach(index -> nodes.get(index).setValue(learningData.getInputs().get(index)));
    }

    // not thread safe REFActor ?
    @Override
    public ErrorData calculateError() {

        return new ErrorDataImpl(nodes.stream().map(Node::calculateError).collect(toList()));


    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public void updateWeights() {
        // no weights to be updated
    }


}
