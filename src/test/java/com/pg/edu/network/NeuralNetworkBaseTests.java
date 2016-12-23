package com.pg.edu.network;


import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Iterables;
import com.pg.edu.api.NeuralNetwork;
import com.pg.edu.api.data.LearningData;
import com.pg.edu.api.data.ResultData;
import com.pg.edu.api.dataset.ErrorDataSet;
import com.pg.edu.impl.data.LearningDataImpl;
import com.pg.edu.impl.dataset.LearningDataSetImpl;
import com.pg.edu.impl.network.NeuralNetworkImpl;
import org.junit.Assert;
import org.junit.Test;

import static com.google.common.collect.ImmutableList.of;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class NeuralNetworkBaseTests {


    private static final int RESULT_SIZE = 9;


    @Test
    public void shouldDecreaseErrorOfOneNeuronWithEachIteration() {

        NeuralNetwork neuralNetwork = NeuralNetworkImpl.builder().setInputSize(1).setHiddenLayers(1).setHiddenLayerSize(1).setOutputSize(1).build();

        List<Double> input = singletonList(0d);
        List<Double> output = singletonList(1d);

        LearningData learningData = new LearningDataImpl(input, output);
        Double error = 100000D;
        for (int i = 0; i < 100; i++) {

            LearningDataSetImpl dataSet = new LearningDataSetImpl(singletonList(learningData));
            neuralNetwork.train(dataSet);

            ErrorDataSet validate = neuralNetwork.validate(dataSet);
            Double newError = Iterables.getOnlyElement(validate.getData()).getErrorValues().get(0);

            Assert.assertTrue(newError < error);
            error = newError;
        }

    }


    @Test(timeout = 30_000)
    public void testLogicalAndLearning() {
        NeuralNetwork neuralNetwork = NeuralNetworkImpl.builder().setInputSize(2).setHiddenLayers(1).setHiddenLayerSize(10).setOutputSize(1).build();


        List<Double> input00 = of(0.0, 0.0);
        List<Double> input01 = of(0.0, 1.0);
        List<Double> input10 = of(1.0, 0.0);
        List<Double> input11 = of(1.0, 1.0);

        List<Double> output00 = of(0.0);
        List<Double> output01 = of(0.0);
        List<Double> output10 = of(0.0);
        List<Double> output11 = of(1.0);

        List<LearningData> learningDatas = of(
                new LearningDataImpl(input00, output00),
                new LearningDataImpl(input01, output01),
                new LearningDataImpl(input10, output10),
                new LearningDataImpl(input11, output11));


        int i = 0;
        LearningDataSetImpl dataSet = new LearningDataSetImpl(learningDatas);
        while (!checkIfAllResultsAreCorrect(neuralNetwork, learningDatas)) {
            neuralNetwork.train(dataSet);
            neuralNetwork.validate(dataSet);
            i++;
        }

        System.out.println(i);
    }


    private boolean checkIfAllResultsAreCorrect(NeuralNetwork network, List<LearningData> learningDatas) {
        for (LearningData learningData : learningDatas) {
            ResultData resultData = network.compute(learningData);
            List<Long> collect = resultData.getResults().stream().map(Math::round).collect(toList());
            List<Long> expected = learningData.getOutputs().stream().map(Math::round).collect(toList());

            if (!Arrays.equals(collect.toArray(), expected.toArray())) {
                return false;
            }
        }

        return true;

    }


    @Test
//    @Ignore("This should be integration tests. Since it's taking long to run disable for now")
    public void test2() {

        NeuralNetwork neuralNetwork = NeuralNetworkImpl.builder().setInputSize(9).setHiddenLayers(1).setHiddenLayerSize(20).setOutputSize(9).build();

        for (int i = 0; i < 100_0000; i++) {

            LearningData learningData = createTrainingData(i);
            LearningDataSetImpl dataSet = new LearningDataSetImpl(singletonList(learningData));
            neuralNetwork.train(dataSet);
            ErrorDataSet errorDataSet = neuralNetwork.validate(dataSet);
//         errorDataSet.getResults().forEach( errorData -> errorData.getErrorValues().forEach(System.out::println));

            int validResults = validate(neuralNetwork);
            if (validResults == RESULT_SIZE) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!finished !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("Completed in iteration " + i);
                break;
            }
            //runtime display of values
//         ResultData compute = neuralNetwork.compute(learningData);
//         System.out.println("runtime check for " +i%RESULT_SIZE + " " +Arrays.toString(compute.getResults().stream().toArray()));

        }
        //validate


        for (int i = 0; i < RESULT_SIZE; i++) {


            LearningData learningData = createTrainingData(i);

            ResultData result = neuralNetwork.compute(learningData);
            System.out.println();
            System.out.println("input for " + i % RESULT_SIZE + " " + Arrays.toString(learningData.getInputs().stream().mapToLong(Math::round).toArray()));
            System.out.println("output for " + i % RESULT_SIZE + " " + Arrays.toString(learningData.getOutputs().stream().mapToLong(Math::round).toArray()));
            System.out.println("result for " + i % RESULT_SIZE + " " + Arrays.toString(result.getResults().stream().mapToLong(Math::round).toArray()));
            System.out.println("result for " + i % RESULT_SIZE + " " + Arrays.toString(result.getResults().stream().toArray()));
            System.out.println();

        }

    }

    private int maxAnswersCorrect = 0;

    private int validate(NeuralNetwork network) {

        int correctAnswerCounter = 0;

        for (int i = 0; i < RESULT_SIZE; i++) {
            LearningData learningData = createTrainingData(i);
            ResultData compute = network.compute(learningData);
            if (checkMatches(compute.getResults(), i)) {
                correctAnswerCounter++;
            }
        }


        if (correctAnswerCounter > maxAnswersCorrect) {
            maxAnswersCorrect = Math.max(maxAnswersCorrect, correctAnswerCounter);
            System.out.println("Correct answers " + correctAnswerCounter);
        }

        return correctAnswerCounter;

    }

    private boolean checkMatches(List<Double> values, int indexToCheck) {

        int counter = 0;
        for (Double d : values) {
            if (indexToCheck == counter) {
                if (!(Math.round(d) == 1.0)) {
                    return false;
                }
            } else {
                if (!(Math.round(d) == 0.0)) {
                    return false;
                }
            }
            counter++;
        }
        return true;
    }


    private LearningData createTrainingData(int value) {

        List<Double> input = IntStream.range(0, RESULT_SIZE).mapToObj(value1 -> 0d).collect(toList());
        input.set(value % RESULT_SIZE, 1.0);
        List<Double> output = IntStream.range(0, RESULT_SIZE).mapToObj(value1 -> 0d).collect(toList());
        output.set(value % RESULT_SIZE, 1.0);

        return new LearningDataImpl(input, output);
    }

}
