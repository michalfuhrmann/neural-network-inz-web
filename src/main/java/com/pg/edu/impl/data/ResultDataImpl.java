package com.pg.edu.impl.data;

import java.util.List;

import com.pg.edu.api.data.ResultData;

public class ResultDataImpl implements ResultData {


    public final List<Double> data;


    public ResultDataImpl(List<Double> data) {
        this.data = data;
    }

    public List<Double> getResults() {
        return data;
    }
}
