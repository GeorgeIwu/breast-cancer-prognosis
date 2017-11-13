package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import neuralnet.bot.browse.range.DocumentRange;

import javax.persistence.*;

/**
 * Created by chigozirim on 6/10/2016.
 */

@Entity
public class Patient extends Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public double testPrediction;
    public String patientId;

    public String category;

    public double meanRadius;
    public double meanArea;
    public double meanPerimeter;
    public double meanTexture;
    public double meanSmoothness;
    public double meanCompactness;
    public double meanConcavity;
    public double meanConcavePoints;
    public double meanSymmetry;
    public double meanFractalDimension;

    public double seRadius;
    public double seArea;
    public double sePerimeter;
    public double seTexture;
    public double seSmoothness;
    public double seCompactness;
    public double seConcavity;
    public double seConcavePoints;
    public double seSymmetry;
    public double seFractalDimension;

    public double worstRadius;
    public double worstArea;
    public double worstPerimeter;
    public double worstTexture;
    public double worstSmoothness;
    public double worstCompactness;
    public double worstConcavity;
    public double worstConcavePoints;
    public double worstSymmetry;
    public double worstFractalDimension;


    @ManyToOne()
    @JsonIgnore
    public Document document;

    @OneToOne
    public NormalizedPatient normalizedPatient;

    public static Finder<Long, Patient> find = new Finder<Long, Patient>(Long.class, Patient.class);


    public NormalizedPatient NormalizeAttributes() {

        NormalizedPatient normalizedPatient = new NormalizedPatient();
        normalizedPatient.patient = this;
//        normalizedPatient.save();

        normalizedPatient.category = DocumentRangeBreast.getCategory(this.category);

        normalizedPatient.meanRadius = normalize(this.meanRadius, this.document.documentRangeBreast.maxMeanRadius,this.document.documentRangeBreast.minMeanRadius);
        normalizedPatient.meanArea = normalize(this.meanArea, this.document.documentRangeBreast.maxMeanArea,this.document.documentRangeBreast.minMeanArea);
        normalizedPatient.meanPerimeter = normalize(this.meanPerimeter, this.document.documentRangeBreast.maxMeanPerimeter,this.document.documentRangeBreast.minMeanPerimeter);
        normalizedPatient.meanTexture = normalize(this.meanTexture, this.document.documentRangeBreast.maxMeanTexture,this.document.documentRangeBreast.minMeanTexture);
        normalizedPatient.meanSmoothness = normalize(this.meanSmoothness, this.document.documentRangeBreast.maxMeanSmoothness,this.document.documentRangeBreast.minMeanSmoothness);
        normalizedPatient.meanCompactness = normalize(this.meanCompactness, this.document.documentRangeBreast.maxMeanCompactness,this.document.documentRangeBreast.minMeanCompactness);
        normalizedPatient.meanConcavity = normalize(this.meanConcavity, this.document.documentRangeBreast.maxMeanConcavity,this.document.documentRangeBreast.minMeanConcavity);
        normalizedPatient.meanConcavePoints = normalize(this.meanConcavePoints, this.document.documentRangeBreast.maxMeanConcavePoints,this.document.documentRangeBreast.minMeanConcavePoints);
        normalizedPatient.meanSymmetry = normalize(this.meanSymmetry, this.document.documentRangeBreast.maxMeanSymmetry,this.document.documentRangeBreast.minMeanSymmetry);
        normalizedPatient.meanFractalDimension = normalize(this.meanFractalDimension, this.document.documentRangeBreast.maxMeanFractalDimension,this.document.documentRangeBreast.minMeanFractalDimension);

        normalizedPatient.seRadius = normalize(this.seRadius, this.document.documentRangeBreast.maxSeRadius,this.document.documentRangeBreast.minSeRadius);
        normalizedPatient.seArea = normalize(this.seArea, this.document.documentRangeBreast.maxSeArea,this.document.documentRangeBreast.minSeArea);
        normalizedPatient.sePerimeter = normalize(this.sePerimeter, this.document.documentRangeBreast.maxSePerimeter,this.document.documentRangeBreast.minSePerimeter);
        normalizedPatient.seTexture = normalize(this.seTexture, this.document.documentRangeBreast.maxSeTexture,this.document.documentRangeBreast.minSeTexture);
        normalizedPatient.seSmoothness = normalize(this.seSmoothness, this.document.documentRangeBreast.maxSeSmoothness,this.document.documentRangeBreast.minSeSmoothness);
        normalizedPatient.seCompactness = normalize(this.seCompactness, this.document.documentRangeBreast.maxSeCompactness,this.document.documentRangeBreast.minSeCompactness);
        normalizedPatient.seConcavity = normalize(this.seConcavity, this.document.documentRangeBreast.maxSeConcavity,this.document.documentRangeBreast.minSeConcavity);
        normalizedPatient.seConcavePoints = normalize(this.seConcavePoints, this.document.documentRangeBreast.maxSeConcavePoints,this.document.documentRangeBreast.minSeConcavePoints);
        normalizedPatient.seSymmetry = normalize(this.seSymmetry, this.document.documentRangeBreast.maxSeSymmetry,this.document.documentRangeBreast.minSeSymmetry);
        normalizedPatient.seFractalDimension = normalize(this.seFractalDimension, this.document.documentRangeBreast.maxSeFractalDimension,this.document.documentRangeBreast.minSeFractalDimension);


        normalizedPatient.worstRadius = normalize(this.worstRadius, this.document.documentRangeBreast.maxWorstRadius,this.document.documentRangeBreast.minWorstRadius);
        normalizedPatient.worstArea = normalize(this.worstArea, this.document.documentRangeBreast.maxWorstArea,this.document.documentRangeBreast.minWorstArea);
        normalizedPatient.worstPerimeter = normalize(this.worstPerimeter, this.document.documentRangeBreast.maxWorstPerimeter,this.document.documentRangeBreast.minWorstPerimeter);
        normalizedPatient.worstTexture = normalize(this.worstTexture, this.document.documentRangeBreast.maxWorstTexture,this.document.documentRangeBreast.minWorstTexture);
        normalizedPatient.worstSmoothness = normalize(this.worstSmoothness, this.document.documentRangeBreast.maxWorstSmoothness,this.document.documentRangeBreast.minWorstSmoothness);
        normalizedPatient.worstCompactness = normalize(this.worstCompactness, this.document.documentRangeBreast.maxWorstCompactness,this.document.documentRangeBreast.minWorstCompactness);
        normalizedPatient.worstConcavity = normalize(this.worstConcavity, this.document.documentRangeBreast.maxWorstConcavity,this.document.documentRangeBreast.minWorstConcavity);
        normalizedPatient.worstConcavePoints = normalize(this.worstConcavePoints, this.document.documentRangeBreast.maxWorstConcavePoints,this.document.documentRangeBreast.minWorstConcavePoints);
        normalizedPatient.worstSymmetry = normalize(this.worstSymmetry, this.document.documentRangeBreast.maxWorstSymmetry,this.document.documentRangeBreast.minWorstSymmetry);
        normalizedPatient.worstFractalDimension = normalize(this.worstFractalDimension, this.document.documentRangeBreast.maxWorstFractalDimension,this.document.documentRangeBreast.minWorstFractalDimension);

        normalizedPatient.save();

        this.normalizedPatient = normalizedPatient;
        this.save();

        return this.normalizedPatient;

    }



    public NormalizedPatient UnNormalizeAttributes() {

        NormalizedPatient normalizedPatient = new NormalizedPatient();
        normalizedPatient.patient = this;
        normalizedPatient.save();

        normalizedPatient.category = DocumentRangeBreast.getCategory(this.category);

        normalizedPatient.meanRadius = this.meanRadius;
        normalizedPatient.meanArea = this.meanArea;
        normalizedPatient.meanPerimeter = this.meanPerimeter;
        normalizedPatient.meanTexture = this.meanTexture;
        normalizedPatient.meanSmoothness = this.meanSmoothness;
        normalizedPatient.meanCompactness = this.meanCompactness;
        normalizedPatient.meanConcavity = this.meanConcavity;
        normalizedPatient.meanConcavePoints = this.meanConcavePoints;
        normalizedPatient.meanSymmetry = this.meanSymmetry;
        normalizedPatient.meanFractalDimension = this.meanFractalDimension;

        normalizedPatient.seRadius = this.seRadius;
        normalizedPatient.seArea = this.seArea;
        normalizedPatient.sePerimeter = this.sePerimeter;
        normalizedPatient.seTexture = this.seTexture;
        normalizedPatient.seSmoothness = this.seSmoothness;
        normalizedPatient.seCompactness = this.seCompactness;
        normalizedPatient.seConcavity = this.seConcavity;
        normalizedPatient.seConcavePoints = this.seConcavePoints;
        normalizedPatient.seSymmetry = this.seSymmetry;
        normalizedPatient.seFractalDimension = this.seFractalDimension;

        normalizedPatient.worstRadius = this.worstRadius;
        normalizedPatient.worstArea = this.worstArea;
        normalizedPatient.worstPerimeter = this.worstPerimeter;
        normalizedPatient.worstTexture = this.worstTexture;
        normalizedPatient.worstSmoothness = this.worstSmoothness;
        normalizedPatient.worstCompactness = this.worstCompactness;
        normalizedPatient.worstConcavity = this.worstConcavity;
        normalizedPatient.worstConcavePoints = this.worstConcavePoints;
        normalizedPatient.worstSymmetry = this.worstSymmetry;
        normalizedPatient.worstFractalDimension = this.worstFractalDimension;

        normalizedPatient.save();

        this.normalizedPatient = normalizedPatient;
        this.save();

        return this.normalizedPatient;

    }


    private static double normalize(double xValue, double maxValue, double minValue) {

        double normValue = (xValue - minValue) / (maxValue - minValue);

        return  normValue;

    }

}

