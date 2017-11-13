package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import neuralnet.Encog;
import neuralnet.ml.data.MLData;
import neuralnet.ml.data.MLDataPair;
import neuralnet.ml.data.MLDataSet;
import neuralnet.neural.networks.BasicNetwork;
import play.libs.Akka;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by GeorgeMario on 02/06/2016.
 */
public class SplitedData {

    public MLDataSet dataSet;

    public MLDataSet trainingSet;

    public MLDataSet validationSet;

    public MLDataSet testingSet;

    public SplitedData(MLDataSet dataSet, MLDataSet trainingSet, MLDataSet validationSet, MLDataSet testingSet){
        this.dataSet = dataSet;
        this.trainingSet = trainingSet;
        this.validationSet = validationSet;
        this.testingSet = testingSet;
    }

    public SplitedData(){}






}
