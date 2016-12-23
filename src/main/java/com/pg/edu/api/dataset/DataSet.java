package com.pg.edu.api.dataset;

import java.util.List;

import com.pg.edu.api.data.Data;

public interface DataSet<T extends Data> {

    List<T> getData();
}
