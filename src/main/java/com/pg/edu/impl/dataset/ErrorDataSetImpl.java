package com.pg.edu.impl.dataset;

import java.util.List;

import com.pg.edu.api.data.ErrorData;
import com.pg.edu.api.dataset.ErrorDataSet;

public class ErrorDataSetImpl implements ErrorDataSet {

    private final List<ErrorData> data;

    public ErrorDataSetImpl(List<ErrorData> data) {
        this.data = data;
    }


    @Override
    public List<ErrorData> getData() {
        return null;
    }
}
