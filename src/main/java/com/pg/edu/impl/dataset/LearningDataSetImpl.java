package com.pg.edu.impl.dataset;

import java.util.List;

import com.pg.edu.api.data.LearningData;
import com.pg.edu.api.dataset.LearningDataSet;

public class LearningDataSetImpl implements LearningDataSet {

    private final List<LearningData> learningDatas;

    public LearningDataSetImpl(List<LearningData> learningDatas) {
        this.learningDatas = learningDatas;
    }


    @Override
    public List<LearningData> getData() {
        return learningDatas;
    }
}
