package com.pg.edu.api.node;

import java.util.List;

import com.pg.edu.api.nodeconnector.NodeConnector;

public interface Node {

    void setValue(double value);

    Double getValue();

    Double getError();

    Double calculateError();

    List<NodeConnector> getParentConnectors();

    List<NodeConnector> getChildrenConnectors();

    //TODO hide it
    void addParentConnector(NodeConnector nodeConnector);

    //TODO hide it
    void addChildrenConnector(NodeConnector nodeConnector);

    void connect(Node otherNode);

    void setExpectedOutput(Double value);


}
