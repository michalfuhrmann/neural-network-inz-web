package com.pg.edu.api.layer;


import java.util.List;

import com.pg.edu.api.node.Node;

public interface NetworkLayer {

    List<Node> getNodes();

    void updateWeights();

}
