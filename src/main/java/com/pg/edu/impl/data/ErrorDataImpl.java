package com.pg.edu.impl.data;

import java.util.List;

import com.pg.edu.api.data.ErrorData;

public class ErrorDataImpl implements ErrorData{

    private List<Double> values;

    public ErrorDataImpl(List<Double> values){
        this.values = values;
    }

    @Override
    public List<Double> getErrorValues() {
        return values;
    }

    @Override
    public int getNumberOfCorrectAnwsers() {
        return 0;
    }
}
