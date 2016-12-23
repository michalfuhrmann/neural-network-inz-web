package com.pg.edu.api.data;

import java.util.List;

public interface LearningData extends Data {

    List<Double> getInputs();

    List<Double> getOutputs();
}
