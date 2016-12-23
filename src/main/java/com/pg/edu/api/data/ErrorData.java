package com.pg.edu.api.data;

import java.util.List;

public interface ErrorData extends Data{

    List<Double> getErrorValues();

    //TODO add option to choose THRESHOLD or HIGHEST_VALUE
    int getNumberOfCorrectAnwsers();


}