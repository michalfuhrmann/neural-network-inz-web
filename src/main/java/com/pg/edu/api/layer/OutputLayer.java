package com.pg.edu.api.layer;

import com.pg.edu.api.data.LearningData;
import com.pg.edu.api.data.ResultData;

public interface OutputLayer extends NetworkLayer {

    ResultData feedForward();

    void setExpectedData(LearningData learningData);
}
