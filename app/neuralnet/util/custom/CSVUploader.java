package neuralnet.util.custom;

import models.Document;
import models.DocumentRangeBreast;
import models.Patient;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by GeorgeMario on 23/10/2016.
 */
public class CSVUploader {


    public static void fetchData(String filePath) throws Exception{

//        List<String[]> lines = new ArrayList<>();
        Scanner line=new Scanner(new File(filePath));

        Document document = new Document();
        document.date = new Date();
        document.name = "TEST1";
        document.user = "ADMIN";
        document.purpose = "TRAIN";
        document.save();

        while (line.hasNext()){
            try {

                Scanner word =new Scanner(line.nextLine());
                word.useDelimiter(",");

                String[] field = new String[32];
                int index = 0;
                while(word.hasNext()){
                    field[index] = word.next();
                    index++;
                }

                if (field[0].equals("DOCUMENT_CODE")) {
                    continue;
                }

                Patient patient = new Patient();


                patient.patientId = (field[0] != null ) ? field[0].toString() :"";

                patient.category = (field[1] != null ) ? field[1].toString() :"";


                patient.meanRadius = (field[2] != null ) ? Double.parseDouble(field[2]) : 0;

                patient.meanArea = (field[3] != null ) ? Double.parseDouble(field[3]) : 0;

                patient.meanPerimeter = (field[4] != null ) ? Double.parseDouble(field[4]) : 0;

                patient.meanTexture = (field[5] != null ) ? Double.parseDouble(field[5]) : 0;

                patient.meanSmoothness = (field[6] != null ) ? Double.parseDouble(field[6]) : 0;

                patient.meanCompactness = (field[7] != null ) ? Double.parseDouble(field[7]) : 0;

                patient.meanConcavity = (field[8] != null ) ? Double.parseDouble(field[8]) : 0;

                patient.meanConcavePoints = (field[9] != null ) ? Double.parseDouble(field[9]) : 0;

                patient.meanSymmetry = (field[10] != null ) ? Double.parseDouble(field[10]) : 0;

                patient.meanFractalDimension = (field[11] != null ) ? Double.parseDouble(field[11]) : 0;



                patient.seRadius = (field[12] != null ) ? Double.parseDouble(field[12]) : 0;

                patient.seArea = (field[13] != null ) ? Double.parseDouble(field[13]) : 0;

                patient.sePerimeter = (field[14] != null ) ? Double.parseDouble(field[14]) : 0;

                patient.seTexture = (field[15] != null ) ? Double.parseDouble(field[15]) : 0;

                patient.seSmoothness = (field[16] != null ) ? Double.parseDouble(field[16]) : 0;

                patient.seCompactness = (field[17] != null ) ? Double.parseDouble(field[17]) : 0;

                patient.seConcavity = (field[18] != null ) ? Double.parseDouble(field[18]) : 0;

                patient.seConcavePoints = (field[19] != null ) ? Double.parseDouble(field[19]) : 0;

                patient.seSymmetry = (field[20] != null ) ? Double.parseDouble(field[20]) : 0;

                patient.seFractalDimension = (field[21] != null ) ? Double.parseDouble(field[21]) : 0;



                patient.worstRadius = (field[22] != null ) ? Double.parseDouble(field[22]) : 0;

                patient.worstArea = (field[23] != null ) ? Double.parseDouble(field[23]) : 0;

                patient.worstPerimeter = (field[24] != null ) ? Double.parseDouble(field[24]) : 0;

                patient.worstTexture = (field[25] != null ) ? Double.parseDouble(field[25]) : 0;

                patient.worstSmoothness = (field[26] != null ) ? Double.parseDouble(field[26]) : 0;

                patient.worstCompactness = (field[27] != null ) ? Double.parseDouble(field[27]) : 0;

                patient.worstConcavity = (field[28] != null ) ? Double.parseDouble(field[28]) : 0;

                patient.worstConcavePoints = (field[29] != null ) ? Double.parseDouble(field[29]) : 0;

                patient.worstSymmetry = (field[30] != null ) ? Double.parseDouble(field[30]) : 0;

                patient.worstFractalDimension = (field[31] != null ) ? Double.parseDouble(field[31]) : 0;

                patient.document = document;
                patient.save();

                document.patients.add(patient);
//                lines.add(field);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }


        }
        line.close();
        document.save();

        DocumentRangeBreast documentRangeBreast = new DocumentRangeBreast();
        documentRangeBreast.document = document;
        documentRangeBreast.rangeData(document.Id);
        documentRangeBreast.save();
        document.documentRangeBreast = documentRangeBreast;
        document.save();


    }




}
