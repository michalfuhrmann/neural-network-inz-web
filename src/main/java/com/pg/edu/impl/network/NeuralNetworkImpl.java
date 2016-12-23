package com.pg.edu.impl.network;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.pg.edu.api.LearningAlgorithm;
import com.pg.edu.api.NeuralNetwork;
import com.pg.edu.api.data.ErrorData;
import com.pg.edu.api.data.LearningData;
import com.pg.edu.api.data.ResultData;
import com.pg.edu.api.dataset.ErrorDataSet;
import com.pg.edu.api.dataset.LearningDataSet;
import com.pg.edu.api.layer.InputLayer;
import com.pg.edu.api.layer.NetworkLayer;
import com.pg.edu.api.layer.OutputLayer;
import com.pg.edu.api.node.Node;
import com.pg.edu.impl.data.ErrorDataImpl;
import com.pg.edu.impl.layer.HiddenLayer;
import com.pg.edu.impl.layer.InputLayerImpl;
import com.pg.edu.impl.layer.OutputLayerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeuralNetworkImpl implements NeuralNetwork {

    private static Logger LOG = LoggerFactory.getLogger(NeuralNetworkImpl.class);

    protected final InputLayer inputLayer;
    protected final List<NetworkLayer> hiddenLayers;
    protected final OutputLayer outputLayer;

    //TODO make it configurable
    public static final Double LEARNING_RATE = 1.0;

    private final LearningAlgorithm learningAlgorithm;

    private NeuralNetworkImpl(LearningAlgorithm learningAlgorithm, int inputSize, int outputSize, int hiddenLayerSize, int hiddenLayers) {

        LOG.debug("Created neural network ");
        this.learningAlgorithm = learningAlgorithm;

        this.inputLayer = new InputLayerImpl(inputSize);
        this.hiddenLayers = new ArrayList<>();
        this.outputLayer = new OutputLayerImpl(outputSize);
        IntStream.range(0, hiddenLayers).mapToObj(value -> new HiddenLayer(hiddenLayerSize)).forEach(this.hiddenLayers::add);

        //TODO fix warnings
        //TODO clear this mess :)
        connectNodes(inputLayer.getNodes(), Iterables.getFirst(this.hiddenLayers, null).getNodes());

        if (this.hiddenLayers.size() > 1) {
            for (int i = 1; i < this.hiddenLayers.size(); i++) {
                connectNodes(this.hiddenLayers.get(i - 1).getNodes(), this.hiddenLayers.get(i).getNodes());
            }
        }
        connectNodes(Iterables.getLast(this.hiddenLayers).getNodes(), outputLayer.getNodes());
    }


    @Override
    public void train(LearningDataSet dataSet) {

        dataSet.getData().forEach(this::trainSingleData);
    }


    @Override
    public ErrorDataSet validate(LearningDataSet dataSet) {

        List<ErrorData> errorDatas = new ArrayList<>();
        for (LearningData learningData : dataSet.getData()) {

            feedForward(learningData);

            ImmutableList.Builder<Double> builder = ImmutableList.<Double>builder();
            for (int i = 0; i < learningData.getOutputs().size(); i++) {
                builder.add(learningData.getOutputs().get(i) - outputLayer.getNodes().get(i).getValue());
            }

            errorDatas.add(new ErrorDataImpl(builder.build()));

        }
        //TODO temp solution  ;)
        return () -> errorDatas;

    }

    @Override
    public ResultData compute(LearningData learningData) {
        return feedForward(learningData);
    }

    private void connectNodes(List<Node> firstLayer, List<Node> secondLayer) {
        firstLayer.forEach(firstLayerNode -> secondLayer.forEach(firstLayerNode::connect));
    }

    //TODO delegate these methods to algorithm (use template pattern)
    private void trainSingleData(LearningData learningData) {

        //TODO split learningData arguments into input/otuput and pass it
        feedForward(learningData);
        calculateError(learningData);
        updateWeights();
    }

    //TODO delegate these methods to algorithm ( use template pattern)
    private ResultData feedForward(LearningData learningData) {

        inputLayer.setInputs(learningData);
        return outputLayer.feedForward();
    }

    private ErrorData calculateError(LearningData learningData) {

        outputLayer.setExpectedData(learningData);
        return inputLayer.calculateError();
    }

    private void updateWeights() {
        hiddenLayers.forEach(NetworkLayer::updateWeights);
        outputLayer.updateWeights();
    }

    public static NeuralNetworkBuilder builder() {
        return new NeuralNetworkBuilder();
    }

    public static class NeuralNetworkBuilder {
        private LearningAlgorithm learningAlgorithm;
        private int inputSize;
        private int outputSize;
        private int hiddenLayerSize;
        private int hiddenLayers;


        public LearningAlgorithm getLearningAlgorithm() {
            return learningAlgorithm;
        }

        public NeuralNetworkBuilder setHiddenLayers(int hiddenLayers) {
            this.hiddenLayers = hiddenLayers;
            return this;
        }

        public NeuralNetworkBuilder setLearningAlgorithm(LearningAlgorithm learningAlgorithm) {
            this.learningAlgorithm = learningAlgorithm;
            return this;
        }

        public NeuralNetworkBuilder setInputSize(int inputSize) {
            this.inputSize = inputSize;
            return this;
        }

        public NeuralNetworkBuilder setOutputSize(int outputSize) {
            this.outputSize = outputSize;
            return this;
        }

        public NeuralNetworkBuilder setHiddenLayerSize(int hiddenLayerSize) {
            this.hiddenLayerSize = hiddenLayerSize;
            return this;
        }


        public NeuralNetworkImpl build() {
            return new NeuralNetworkImpl(learningAlgorithm, inputSize, outputSize, hiddenLayerSize, hiddenLayers);
        }

    }
}
