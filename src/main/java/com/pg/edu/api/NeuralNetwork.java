package com.pg.edu.api;


import com.pg.edu.api.data.LearningData;
import com.pg.edu.api.data.ResultData;
import com.pg.edu.api.dataset.ErrorDataSet;
import com.pg.edu.api.dataset.LearningDataSet;

public interface NeuralNetwork {


    //TODO put results on Queue and monitor it ?
    void train(LearningDataSet dataSet);

    ErrorDataSet validate(LearningDataSet dataSet);

    //TODO should be inputs only
    ResultData compute(LearningData dataSet);

}
