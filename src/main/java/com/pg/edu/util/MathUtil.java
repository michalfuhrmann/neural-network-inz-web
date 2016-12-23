package com.pg.edu.util;

import static java.lang.Math.exp;

public class MathUtil {

    private MathUtil() {
    }

    public static Double sigmoid(Double value) {

        return 1.0 / (1.0 + exp(-value));
    }


    public static Double sigmoidPrime(Double value) {
// TODO use temp ? measure difference
        return sigmoid(value) * (1 - sigmoid(value));
    }


}
