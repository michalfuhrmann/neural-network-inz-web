package com.pg.edu.api.layer;

import com.pg.edu.api.data.ErrorData;
import com.pg.edu.api.data.LearningData;

public interface InputLayer extends NetworkLayer {

    void setInputs(LearningData learningData);

    ErrorData calculateError();

}
