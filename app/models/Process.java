package models;


import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import neuralnet.Encog;
import neuralnet.engine.network.activation.ActivationSigmoid;
import neuralnet.ml.data.MLData;
import neuralnet.ml.data.MLDataPair;
import neuralnet.ml.data.MLDataSet;
import neuralnet.ml.data.basic.BasicMLDataSet;
import neuralnet.ml.train.strategy.end.EarlyStoppingStrategy;
import neuralnet.neural.networks.BasicNetwork;
import neuralnet.neural.networks.layers.BasicLayer;
import neuralnet.neural.networks.training.propagation.resilient.ResilientPropagation;
import neuralnet.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import play.libs.Akka;

import javax.persistence.*;
import java.util.Date;

/**
 * @startuml

 * car --|> wheel

 * @enduml
 * Created by Dare on 20/05/2016.
 * <p>
 * I annotated Some of the Variables in case We want to create a table and use ebean in our database
 */
@Entity
public class Process extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    public String user;

    public static int inputLayer = 30;

    public static int outputLayer = 1;

    public static String RPROP = "RPROP";

    public static String SCG = "SCG";

    public int hiddenLayer;

    public Date scgStart;

    public Date rpropStart;

    public Date scgEnd;

    public Date rpropEnd;

    public double scgEpoch;

    public double rpropEpoch;

    public double scgMinimalError;

    public double rpropMinimalError;

    public double scgTruePositives = 0;

    public double rpropTruePositives = 0;

    public double scgTrueNegatives = 0;

    public double rpropTrueNegatives = 0;

    public double scgFalsePositives = 0;

    public double rpropFalsePositives = 0;

    public double scgFalseNegatives = 0;

    public double rpropFalseNegatives = 0;

    @ManyToOne
    @JsonIgnore
    public Document document;

    @ManyToOne
    @JsonIgnore
    public Document testDocument;

    public static Finder<Long, Process> find = new Finder<Long, Process>(Long.class, Process.class);

    private static scala.concurrent.ExecutionContext getContext(){
        return  Akka.system().dispatchers().lookup("akka.db-dispatcher");
    }


    public Process (int hiddenLayer, Document document, Document testDocument) {

        this.document = document;
        this.hiddenLayer = hiddenLayer;
        this.testDocument = testDocument;

    }


    public void run() {

        SplitedData splitedData = splitDataSet(this.document);

        BasicNetwork network = initializeNework();
        network = rpropTraining(splitedData.trainingSet, splitedData.validationSet, network);

        BasicNetwork network2 = initializeNework();
        network2 = scgTraining(splitedData.trainingSet, splitedData.validationSet, network2);

        if (testDocument == null){
            testing(splitedData.testingSet, network, RPROP);
            testing(splitedData.testingSet, network2, SCG);

        }else{
            SplitedData splitedData2 = splitDataSet(this.testDocument);
            testing(splitedData2.dataSet, network2, RPROP);
            testing(splitedData2.dataSet, network2, SCG);

        }




    }

    public BasicNetwork initializeNework() {

        // create a neural network, without using a factory
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null,true, inputLayer));
        network.addLayer(new BasicLayer(new ActivationSigmoid(),true, this.hiddenLayer));
        network.addLayer(new BasicLayer(new ActivationSigmoid(),false, outputLayer));
        network.getStructure().finalizeStructure();
        network.reset();

        return  network;

    }

    public BasicNetwork rpropTraining(MLDataSet trainingSet, MLDataSet validationSet, BasicNetwork network) {

        play.Logger.info("New process rprop");
        // train the neural network
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        //Validate from over-fitting
        EarlyStoppingStrategy earlyStop = new EarlyStoppingStrategy(validationSet);
        train.addStrategy(earlyStop);

        int epoch = 1;
        this.rpropStart = new Date();
        do {
            train.iteration();
            play.Logger.info("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while(train.getError() > 0.01);
        this.rpropEnd = new Date();
        this.rpropEpoch = epoch;
        this.rpropMinimalError = train.getError();
        train.finishTraining();

        return network;

    }


    public BasicNetwork scgTraining(MLDataSet trainingSet, MLDataSet validationSet, BasicNetwork network) {

        play.Logger.info("New process scg");
        // train the neural network
        final ScaledConjugateGradient train = new ScaledConjugateGradient(network, trainingSet);

        //Validate from over-fitting
        EarlyStoppingStrategy earlyStop = new EarlyStoppingStrategy(validationSet);
        train.addStrategy(earlyStop);

        int epoch = 1;
        this.scgStart = new Date();
        do {
            train.iteration();
            play.Logger.info("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while(train.getError() > 0.01);
        this.scgEnd = new Date();
        this.scgEpoch = epoch;
        this.scgMinimalError = train.getError();
        train.finishTraining();

        return network;

    }

    public BasicNetwork testing(MLDataSet testingSet, BasicNetwork network, String learningType) {

        // test the neural network
        play.Logger.info("Neural Network Results:");
        int i = 0;
        for(MLDataPair pair: testingSet ) {
            final MLData output = network.compute(pair.getInput());
            play.Logger.info(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
                    + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));

            i++;
            classify(output.getData(0), pair.getIdeal().getData(0), learningType);
        }

        Encog.getInstance().shutdown();

        return network;

    }


    public void classify(double actual, double ideal, String learningType) {

        double zeroTolerance = 0.001;

        // test the neural network
        if (learningType.equals(RPROP)){
            if (ideal == 1.0){
                if ((ideal - actual) < zeroTolerance){
                    this.rpropTruePositives++;
                    play.Logger.info("True positive");
                }else {
                    this.rpropFalsePositives++;
                    play.Logger.info("False positive");
                }
            }else{
                if ((ideal - actual) < zeroTolerance){
                    this.rpropTrueNegatives++;
                    play.Logger.info("True negative");
                }else {
                    this.rpropFalseNegatives++;
                    play.Logger.info("False negative");
                }
            }
        }else{
            if (ideal == 1.0){
                if ((ideal - actual) < zeroTolerance){
                    this.scgTruePositives++;
                    play.Logger.info("True positive");
                }else {
                    this.scgFalsePositives++;
                    play.Logger.info("False positive");
                }
            }else{
                if ((ideal - actual) < zeroTolerance){
                    this.scgTrueNegatives++;
                    play.Logger.info("True negative");
                }else {
                    this.scgFalseNegatives++;
                    play.Logger.info("False negative");
                }
            }
        }



        return;

    }


    public SplitedData splitDataSet(Document document) {

        double[][] DATA_INPUT = new double[document.patients.size()][30];
        double[][] DATA_IDEAL = new double[document.patients.size()][1];

        int z = 0;
        for (Patient patient : document.patients) {

            DATA_INPUT[z] = patient.normalizedPatient.toDoubleArrayForInput();
            DATA_IDEAL[z] = patient.normalizedPatient.toDoubleArrayForIdeal();

            z++;

        }

        // create training data
        MLDataSet dataSet = new BasicMLDataSet(DATA_INPUT, DATA_IDEAL);

        int set1, set2, set3;
        int sizeOfMainset = dataSet.size();
        set1 =  (int) (0.4 * sizeOfMainset);
        if(set1 % 2 ==0){
            set2 = set1 /2;
            set3 = set2;
            set1 = sizeOfMainset - set1;
        }else{
            set1++;
            set2 = set1/2;
            set3 = set2;
            set1 = sizeOfMainset - set1;
        }
        set2 += set1;
        set3 += set2;

        MLDataSet trainingSet = new BasicMLDataSet();
        MLDataSet validationSet = new BasicMLDataSet();
        MLDataSet testingSet = new BasicMLDataSet();

        int count = 0;

        for(MLDataPair dataPair: dataSet){
            if(count < set1){
                trainingSet.add(dataPair);
            }else if(count < set2){
                validationSet.add(dataPair);
            }else if(count < set3){
                testingSet.add(dataPair);
            }
            count++;
        }

        SplitedData splitedDataSet = new SplitedData(dataSet, trainingSet, validationSet, testingSet);


        return splitedDataSet;

    }







}
