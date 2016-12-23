package com.pg.edu.impl.network;

import java.util.List;
import java.util.stream.IntStream;

import com.pg.edu.api.layer.NetworkLayer;
import com.pg.edu.api.node.Node;
import com.pg.edu.api.nodeconnector.NodeConnector;
import org.junit.Before;
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NeuralNetworkImplTest {

    private static final int INPUT_SIZE = 2;
    private static final int HIDDEN_LAYERS = 4;
    private static final int HIDDEN_LAYERS_SIZE = 8;
    private static final int OUTPUT_SIZE = 3;


    private NeuralNetworkImpl network;


    @Before()
    public void before() {

        network = NeuralNetworkImpl.builder()
                .setInputSize(INPUT_SIZE)
                .setHiddenLayers(HIDDEN_LAYERS)
                .setHiddenLayerSize(HIDDEN_LAYERS_SIZE)
                .setOutputSize(OUTPUT_SIZE)
                .build();
    }

    @Test
    public void testLayersHaveProperNumberOfNodesCreated() {

        assertThat(network.inputLayer.getNodes().size(), is(INPUT_SIZE));
        assertThat(network.hiddenLayers.size(), is(HIDDEN_LAYERS));
        for (NetworkLayer hiddenLayer : network.hiddenLayers) {
            assertThat(hiddenLayer.getNodes().size(), is(HIDDEN_LAYERS_SIZE));
        }
        assertThat(network.outputLayer.getNodes().size(), is(OUTPUT_SIZE));
    }

    @Test
    public void testNodeConnectionsAreInitializedProperlyBetweenInputAndFirstHiddenLayer() {

        testConnectionBetweenLayers(network.inputLayer, network.hiddenLayers.get(0));
    }

    @Test
    public void testNodeConnectionsAreInitializedProperlyBetweenHiddenLayers() {

        IntStream.range(0, network.hiddenLayers.size() - 1)
                .forEach(index -> testConnectionBetweenLayers(network.hiddenLayers.get(index), network.hiddenLayers.get(index + 1)));
    }

    @Test
    public void testNodeConnectionsAreInitializedProperlyBetweenLastHiddenAndOutputLayer() {

        testConnectionBetweenLayers(network.inputLayer, network.hiddenLayers.get(0));
    }

    private void testConnectionBetweenLayers(NetworkLayer firstLayer, NetworkLayer secondLayer) {
        List<Node> firstLayerNodes = firstLayer.getNodes();
        firstLayerNodes.forEach(node -> assertThat(node.getChildrenConnectors().size(), is(secondLayer.getNodes().size())));
        firstLayerNodes.forEach(node -> assertTrue(node.getChildrenConnectors().stream()
                .map(NodeConnector::getChildrenNode)
                .collect(toList())
                .containsAll(secondLayer.getNodes())));

        List<Node> firstLayerHiddenNodes = secondLayer.getNodes();
        firstLayerHiddenNodes.forEach(node -> assertThat(node.getParentConnectors().size(), is(firstLayer.getNodes().size())));
        firstLayerHiddenNodes.forEach(node -> assertTrue(node.getParentConnectors().stream()
                .map(NodeConnector::getParentNode)
                .collect(toList())
                .containsAll(firstLayerNodes)));
    }
}
