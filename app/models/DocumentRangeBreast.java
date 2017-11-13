package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Samuel on 6/28/2016.
 */
@Entity
public class DocumentRangeBreast extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;


    public double maxMeanRadius;
    public double maxMeanArea;
    public double maxMeanPerimeter;
    public double maxMeanTexture;
    public double maxMeanSmoothness;
    public double maxMeanCompactness;
    public double maxMeanConcavity;
    public double maxMeanConcavePoints;
    public double maxMeanSymmetry;
    public double maxMeanFractalDimension;

    public double minMeanRadius;
    public double minMeanArea;
    public double minMeanPerimeter;
    public double minMeanTexture;
    public double minMeanSmoothness;
    public double minMeanCompactness;
    public double minMeanConcavity;
    public double minMeanConcavePoints;
    public double minMeanSymmetry;
    public double minMeanFractalDimension;


    public double maxSeRadius;
    public double maxSeArea;
    public double maxSePerimeter;
    public double maxSeTexture;
    public double maxSeSmoothness;
    public double maxSeCompactness;
    public double maxSeConcavity;
    public double maxSeConcavePoints;
    public double maxSeSymmetry;
    public double maxSeFractalDimension;

    public double minSeRadius;
    public double minSeArea;
    public double minSePerimeter;
    public double minSeTexture;
    public double minSeSmoothness;
    public double minSeCompactness;
    public double minSeConcavity;
    public double minSeConcavePoints;
    public double minSeSymmetry;
    public double minSeFractalDimension;


    public double maxWorstRadius;
    public double maxWorstArea;
    public double maxWorstPerimeter;
    public double maxWorstTexture;
    public double maxWorstSmoothness;
    public double maxWorstCompactness;
    public double maxWorstConcavity;
    public double maxWorstConcavePoints;
    public double maxWorstSymmetry;
    public double maxWorstFractalDimension;

    public double minWorstRadius;
    public double minWorstArea;
    public double minWorstPerimeter;
    public double minWorstTexture;
    public double minWorstSmoothness;
    public double minWorstCompactness;
    public double minWorstConcavity;
    public double minWorstConcavePoints;
    public double minWorstSymmetry;
    public double minWorstFractalDimension;

    @OneToOne
    public Document document;


    public void rangeData(long id){

        this.maxMeanRadius = Patient.find
                .select("meanRadius")
                .where()
                .eq("document_id", id)
                .orderBy("meanRadius desc")
                .setMaxRows(1)
                .findList().get(0).meanRadius;
        this.minMeanRadius = Patient.find
                .select("meanRadius")
                .where()
                .eq("document_id", id)
                .orderBy("meanRadius asc")
                .setMaxRows(1)
                .findList().get(0).meanRadius;

        this.maxMeanArea = Patient.find
                .select("meanArea")
                .where()
                .eq("document_id", id)
                .orderBy("meanArea desc")
                .setMaxRows(1)
                .findList().get(0).meanArea;
        this.minMeanArea = Patient.find
                .select("meanArea")
                .where()
                .eq("document_id", id)
                .orderBy("meanArea asc")
                .setMaxRows(1)
                .findList().get(0).meanArea;


        this.maxMeanPerimeter = Patient.find
                .select("meanPerimeter")
                .where()
                .eq("document_id", id)
                .orderBy("meanPerimeter desc")
                .setMaxRows(1)
                .findList().get(0).meanPerimeter;
        this.minMeanPerimeter = Patient.find
                .select("meanPerimeter")
                .where()
                .eq("document_id", id)
                .orderBy("meanPerimeter asc")
                .setMaxRows(1)
                .findList().get(0).meanPerimeter;


        this.maxMeanTexture = Patient.find
                .select("meanTexture")
                .where()
                .eq("document_id", id)
                .orderBy("meanTexture desc")
                .setMaxRows(1)
                .findList().get(0).meanTexture;
        this.minMeanTexture = Patient.find
                .select("meanTexture")
                .where()
                .eq("document_id", id)
                .orderBy("meanTexture asc")
                .setMaxRows(1)
                .findList().get(0).meanTexture;

        this.maxMeanSmoothness = Patient.find
                .select("meanSmoothness")
                .where()
                .eq("document_id", id)
                .orderBy("meanSmoothness desc")
                .setMaxRows(1)
                .findList().get(0).meanSmoothness;
        this.minMeanSmoothness = Patient.find
                .select("meanSmoothness")
                .where()
                .eq("document_id", id)
                .orderBy("meanSmoothness asc")
                .setMaxRows(1)
                .findList().get(0).meanSmoothness;


        this.maxMeanCompactness = Patient.find
                .select("meanCompactness")
                .where()
                .eq("document_id", id)
                .orderBy("meanCompactness desc")
                .setMaxRows(1)
                .findList().get(0).meanCompactness;
        this.minMeanCompactness = Patient.find
                .select("meanCompactness")
                .where()
                .eq("document_id", id)
                .orderBy("meanCompactness asc")
                .setMaxRows(1)
                .findList().get(0).meanCompactness;


        this.maxMeanConcavity = Patient.find
                .select("meanConcavity")
                .where()
                .eq("document_id", id)
                .orderBy("meanConcavity desc")
                .setMaxRows(1)
                .findList().get(0).meanConcavity;
        this.minMeanConcavity = Patient.find
                .select("meanConcavity")
                .where()
                .eq("document_id", id)
                .orderBy("meanConcavity asc")
                .setMaxRows(1)
                .findList().get(0).meanConcavity;

        this.maxMeanConcavePoints = Patient.find
                .select("meanConcavePoints")
                .where()
                .eq("document_id", id)
                .orderBy("meanConcavePoints desc")
                .setMaxRows(1)
                .findList().get(0).meanConcavePoints;
        this.minMeanConcavePoints = Patient.find
                .select("meanConcavePoints")
                .where()
                .eq("document_id", id)
                .orderBy("meanConcavePoints asc")
                .setMaxRows(1)
                .findList().get(0).meanConcavePoints;

        this.maxMeanSymmetry = Patient.find
                .select("meanSymmetry")
                .where()
                .eq("document_id", id)
                .orderBy("meanSymmetry desc")
                .setMaxRows(1)
                .findList().get(0).meanSymmetry;
        this.minMeanSymmetry = Patient.find
                .select("meanSymmetry")
                .where()
                .eq("document_id", id)
                .orderBy("meanSymmetry asc")
                .setMaxRows(1)
                .findList().get(0).meanSymmetry;

        this.maxMeanFractalDimension = Patient.find
                .select("meanFractalDimension")
                .where()
                .eq("document_id", id)
                .orderBy("meanFractalDimension desc")
                .setMaxRows(1)
                .findList().get(0).meanFractalDimension;
        this.minMeanFractalDimension = Patient.find
                .select("meanFractalDimension")
                .where()
                .eq("document_id", id)
                .orderBy("meanFractalDimension asc")
                .setMaxRows(1)
                .findList().get(0).meanFractalDimension;

        this.maxSeRadius = Patient.find
                .select("seRadius")
                .where()
                .eq("document_id", id)
                .orderBy("seRadius desc")
                .setMaxRows(1)
                .findList().get(0).seRadius;
        this.minSeRadius = Patient.find
                .select("seRadius")
                .where()
                .eq("document_id", id)
                .orderBy("seRadius asc")
                .setMaxRows(1)
                .findList().get(0).seRadius;

        this.maxSeArea = Patient.find
                .select("seArea")
                .where()
                .eq("document_id", id)
                .orderBy("seArea desc")
                .setMaxRows(1)
                .findList().get(0).seArea;
        this.minSeArea = Patient.find
                .select("seArea")
                .where()
                .eq("document_id", id)
                .orderBy("seArea asc")
                .setMaxRows(1)
                .findList().get(0).seArea;

        this.maxSePerimeter = Patient.find
                .select("sePerimeter")
                .where()
                .eq("document_id", id)
                .orderBy("sePerimeter desc")
                .setMaxRows(1)
                .findList().get(0).sePerimeter;
        this.minSePerimeter = Patient.find
                .select("sePerimeter")
                .where()
                .eq("document_id", id)
                .orderBy("sePerimeter asc")
                .setMaxRows(1)
                .findList().get(0).sePerimeter;

        this.maxSeTexture = Patient.find
                .select("seTexture")
                .where()
                .eq("document_id", id)
                .orderBy("seTexture desc")
                .setMaxRows(1)
                .findList().get(0).seTexture;
        this.minSeTexture = Patient.find
                .select("seTexture")
                .where()
                .eq("document_id", id)
                .orderBy("seTexture asc")
                .setMaxRows(1)
                .findList().get(0).seTexture;

        this.maxSeSmoothness = Patient.find
                .select("seSmoothness")
                .where()
                .eq("document_id", id)
                .orderBy("seSmoothness desc")
                .setMaxRows(1)
                .findList().get(0).seSmoothness;
        this.minSeSmoothness = Patient.find
                .select("seSmoothness")
                .where()
                .eq("document_id", id)
                .orderBy("seSmoothness asc")
                .setMaxRows(1)
                .findList().get(0).seSmoothness;

        this.maxSeCompactness = Patient.find
                .select("seCompactness")
                .where()
                .eq("document_id", id)
                .orderBy("seCompactness desc")
                .setMaxRows(1)
                .findList().get(0).seCompactness;
        this.minSeCompactness = Patient.find
                .select("seCompactness")
                .where()
                .eq("document_id", id)
                .orderBy("seCompactness asc")
                .setMaxRows(1)
                .findList().get(0).seCompactness;

        this.maxSeConcavity = Patient.find
                .select("seConcavity")
                .where()
                .eq("document_id", id)
                .orderBy("seConcavity desc")
                .setMaxRows(1)
                .findList().get(0).seConcavity;
        this.minSeConcavity = Patient.find
                .select("seConcavity")
                .where()
                .eq("document_id", id)
                .orderBy("seConcavity asc")
                .setMaxRows(1)
                .findList().get(0).seConcavity;

        this.maxSeConcavePoints = Patient.find
                .select("seConcavePoints")
                .where()
                .eq("document_id", id)
                .orderBy("seConcavePoints desc")
                .setMaxRows(1)
                .findList().get(0).seConcavePoints;
        this.minSeConcavePoints = Patient.find
                .select("seConcavePoints")
                .where()
                .eq("document_id", id)
                .orderBy("seConcavePoints asc")
                .setMaxRows(1)
                .findList().get(0).seConcavePoints;

        this.maxSeSymmetry = Patient.find
                .select("seSymmetry")
                .where()
                .eq("document_id", id)
                .orderBy("seSymmetry desc")
                .setMaxRows(1)
                .findList().get(0).seSymmetry;
        this.minSeSymmetry = Patient.find
                .select("seSymmetry")
                .where()
                .eq("document_id", id)
                .orderBy("seSymmetry asc")
                .setMaxRows(1)
                .findList().get(0).seSymmetry;

        this.maxSeFractalDimension = Patient.find
                .select("seFractalDimension")
                .where()
                .eq("document_id", id)
                .orderBy("seFractalDimension desc")
                .setMaxRows(1)
                .findList().get(0).seFractalDimension;
        this.minSeFractalDimension = Patient.find
                .select("seFractalDimension")
                .where()
                .eq("document_id", id)
                .orderBy("seFractalDimension asc")
                .setMaxRows(1)
                .findList().get(0).seFractalDimension;

        this.maxWorstRadius = Patient.find
                .select("worstRadius")
                .where()
                .eq("document_id", id)
                .orderBy("worstRadius desc")
                .setMaxRows(1)
                .findList().get(0).worstRadius;
        this.minWorstRadius = Patient.find
                .select("worstRadius")
                .where()
                .eq("document_id", id)
                .orderBy("worstRadius asc")
                .setMaxRows(1)
                .findList().get(0).worstRadius;

        this.maxWorstArea = Patient.find
                .select("worstArea")
                .where()
                .eq("document_id", id)
                .orderBy("worstArea desc")
                .setMaxRows(1)
                .findList().get(0).worstArea;
        this.minWorstArea = Patient.find
                .select("worstArea")
                .where()
                .eq("document_id", id)
                .orderBy("worstArea asc")
                .setMaxRows(1)
                .findList().get(0).worstArea;

        this.maxWorstPerimeter = Patient.find
                .select("worstPerimeter")
                .where()
                .eq("document_id", id)
                .orderBy("worstPerimeter desc")
                .setMaxRows(1)
                .findList().get(0).worstPerimeter;
        this.minWorstPerimeter = Patient.find
                .select("worstPerimeter")
                .where()
                .eq("document_id", id)
                .orderBy("worstPerimeter asc")
                .setMaxRows(1)
                .findList().get(0).worstPerimeter;

        this.maxWorstTexture = Patient.find
                .select("worstTexture")
                .where()
                .eq("document_id", id)
                .orderBy("worstTexture desc")
                .setMaxRows(1)
                .findList().get(0).worstTexture;
        this.minWorstTexture = Patient.find
                .select("worstTexture")
                .where()
                .eq("document_id", id)
                .orderBy("worstTexture asc")
                .setMaxRows(1)
                .findList().get(0).worstTexture;

        this.maxWorstSmoothness = Patient.find
                .select("worstSmoothness")
                .where()
                .eq("document_id", id)
                .orderBy("worstSmoothness desc")
                .setMaxRows(1)
                .findList().get(0).worstSmoothness;
        this.minWorstSmoothness = Patient.find
                .select("worstSmoothness")
                .where()
                .eq("document_id", id)
                .orderBy("worstSmoothness asc")
                .setMaxRows(1)
                .findList().get(0).worstSmoothness;

        this.maxWorstCompactness = Patient.find
                .select("worstCompactness")
                .where()
                .eq("document_id", id)
                .orderBy("worstCompactness desc")
                .setMaxRows(1)
                .findList().get(0).worstCompactness;
        this.minWorstCompactness = Patient.find
                .select("worstCompactness")
                .where()
                .eq("document_id", id)
                .orderBy("worstCompactness asc")
                .setMaxRows(1)
                .findList().get(0).worstCompactness;

        this.maxWorstConcavity = Patient.find
                .select("worstConcavity")
                .where()
                .eq("document_id", id)
                .orderBy("worstConcavity desc")
                .setMaxRows(1)
                .findList().get(0).worstConcavity;
        this.minWorstConcavity = Patient.find
                .select("worstConcavity")
                .where()
                .eq("document_id", id)
                .orderBy("worstConcavity asc")
                .setMaxRows(1)
                .findList().get(0).worstConcavity;

        this.maxWorstConcavePoints = Patient.find
                .select("worstConcavePoints")
                .where()
                .eq("document_id", id)
                .orderBy("worstConcavePoints desc")
                .setMaxRows(1)
                .findList().get(0).worstConcavePoints;
        this.minWorstConcavePoints = Patient.find
                .select("worstConcavePoints")
                .where()
                .eq("document_id", id)
                .orderBy("worstConcavePoints asc")
                .setMaxRows(1)
                .findList().get(0).worstConcavePoints;

        this.maxWorstSymmetry = Patient.find
                .select("worstSymmetry")
                .where()
                .eq("document_id", id)
                .orderBy("worstSymmetry desc")
                .setMaxRows(1)
                .findList().get(0).worstSymmetry;
        this.minWorstSymmetry = Patient.find
                .select("worstSymmetry")
                .where()
                .eq("document_id", id)
                .orderBy("worstSymmetry asc")
                .setMaxRows(1)
                .findList().get(0).worstSymmetry;

        this.maxWorstFractalDimension = Patient.find
                .select("worstFractalDimension")
                .where()
                .eq("document_id", id)
                .orderBy("worstFractalDimension desc")
                .setMaxRows(1)
                .findList().get(0).worstFractalDimension;
        this.minWorstFractalDimension = Patient.find
                .select("worstFractalDimension")
                .where()
                .eq("document_id", id)
                .orderBy("worstFractalDimension asc")
                .setMaxRows(1)
                .findList().get(0).worstFractalDimension;




        this.save();
        return;
    }


    private static double getLoanType(String selectedLoanType) {

        double loanType = 0;

        switch (selectedLoanType.toLowerCase()) {
            case "personal": loanType = 1;
                break;
            case "agric": loanType = 2;
                break;
            case "business": loanType = 3;
                break;
            default: loanType = 0;
        }

        return loanType;
    }




    public static double getCategory(String selectedCategory) {

        double category = 0;

        switch (selectedCategory.trim()) {
            case "B": category = 0;
                break;
            case "M": category = 1;
                break;
            default: category = 0;
        }

        return category;
    }








}
