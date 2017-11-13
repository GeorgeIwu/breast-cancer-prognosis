package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by chigozirim on 6/10/2016.
 */

@Entity
public class NormalizedPatient extends Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;


    public double category;

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


    @OneToOne
    public Patient patient;


    public double[] toDoubleArrayForInput() {
        double[] nPatientDouble = new double[30];

        nPatientDouble[0] = this.meanRadius;
        nPatientDouble[1] = this.meanArea;
        nPatientDouble[2] = this.meanPerimeter;
        nPatientDouble[3] = this.meanTexture;
        nPatientDouble[4] = this.meanSmoothness;
        nPatientDouble[5] = this.meanCompactness;
        nPatientDouble[6] = this.meanConcavity;
        nPatientDouble[7] = this.meanConcavePoints;
        nPatientDouble[8] = this.meanSymmetry;
        nPatientDouble[9] = this.meanFractalDimension;
        nPatientDouble[10] = this.seRadius;
        nPatientDouble[11] = this.seArea;
        nPatientDouble[12] = this.sePerimeter;
        nPatientDouble[13] = this.seTexture;
        nPatientDouble[14] = this.seSmoothness;
        nPatientDouble[15] = this.seCompactness;
        nPatientDouble[16] = this.seConcavity;
        nPatientDouble[17] = this.seConcavePoints;
        nPatientDouble[18] = this.seSymmetry;
        nPatientDouble[19] = this.seFractalDimension;
        nPatientDouble[20] = this.worstRadius;
        nPatientDouble[21] = this.worstArea;
        nPatientDouble[22] = this.worstPerimeter;
        nPatientDouble[23] = this.worstTexture;
        nPatientDouble[24] = this.worstSmoothness;
        nPatientDouble[25] = this.worstCompactness;
        nPatientDouble[26] = this.worstConcavity;
        nPatientDouble[27] = this.worstConcavePoints;
        nPatientDouble[28] = this.worstSymmetry;
        nPatientDouble[29] = this.worstFractalDimension;


        return nPatientDouble;
    }

    public double[] toDoubleArrayForIdeal() {
        double[] nPatientDouble = new double[1];

        nPatientDouble[0] = this.category;

        return nPatientDouble;
    }





    public static Finder<Long, NormalizedPatient> find = new Finder<Long, NormalizedPatient>(Long.class, NormalizedPatient.class);


}

