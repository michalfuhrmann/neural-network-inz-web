package com.pg.edu.api.nodeconnector;

import com.pg.edu.api.node.Node;

public interface NodeConnector {

    Node getParentNode();

    Node getChildrenNode();

    Double getWeight();

    void updateWeight();


}
