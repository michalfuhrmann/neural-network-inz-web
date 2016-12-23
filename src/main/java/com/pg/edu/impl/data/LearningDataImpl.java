package com.pg.edu.impl.data;

import java.util.List;

import com.pg.edu.api.data.LearningData;

public class LearningDataImpl implements LearningData {

    private final List<Double> inputs;
    private final List<Double> outputs;

    public LearningDataImpl(List<Double> inputs, List<Double> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public List<Double> getInputs() {
        return inputs;
    }

    @Override
    public List<Double> getOutputs() {
        return outputs;
    }
}
