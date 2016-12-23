package com.pg.edu.impl.nodeconnector;

import java.util.Random;

import com.pg.edu.api.node.Node;
import com.pg.edu.api.nodeconnector.NodeConnector;
import com.pg.edu.impl.network.NeuralNetworkImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeConnectorImpl implements NodeConnector {

    private static Logger LOG = LoggerFactory.getLogger(NodeConnectorImpl.class);

    private final Node parentNode;
    private final Node childrenNode;
    private Double weight;

    //TODO extract
    private final Random random = new Random();


    public NodeConnectorImpl(Node parentNode, Node childrenNode) {
        this.parentNode = parentNode;
        this.childrenNode = childrenNode;
        weight = random.nextDouble();

        LOG.debug("Initialized nodeConnector with weight: {}", weight);

    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public Node getParentNode() {
        return parentNode;
    }

    @Override
    public Node getChildrenNode() {
        return childrenNode;
    }

    @Override
    public Double getWeight() {
        return weight;
    }

    /**
     * a_k ^(l-1) * delta_j
     */
    @Override
    public void updateWeight() {
//        System.out.println( this.hashCode() + " updating weight " + weight);
        weight = weight - (NeuralNetworkImpl.LEARNING_RATE * parentNode.getValue() * childrenNode.getError());
//        System.out.println(this.hashCode() +" after update " + weight);
    }
}
