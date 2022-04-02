package com.example.myegineerapplication.model;

import java.util.Map;

public class FitnessModel {
    private String fitnessName;
    private String fitnessLeader;
    private String fitnessDesc;
    private String fitntessDayOfTraining;
    private String fitnesshour;

    public String getFitnesshour() {
        return fitnesshour;
    }

    public void setFitnesshour(String fitnesshour) {
        this.fitnesshour = fitnesshour;
    }


    public String getFitnessName() {
        return fitnessName;
    }

    public void setFitnessName(String fitnessName) {
        this.fitnessName = fitnessName;
    }

    public String getFitnessPrice() {
        return fitnessLeader;
    }

    public void setFitnessPrice(String fitnessPrice) {
        this.fitnessLeader = fitnessPrice;
    }

    public String getFitnessDesc() {
        return fitnessDesc;
    }

    public void setFitnessDesc(String fitnessDesc) {
        this.fitnessDesc = fitnessDesc;
    }

    public String getFitntessDayOfTraining() {
        return fitntessDayOfTraining;
    }

    public void setFitntessDayOfTraining(String fitntessDayOfTraining) {
        this.fitntessDayOfTraining = fitntessDayOfTraining;
    }


    public FitnessModel() {
    }

    public FitnessModel(String fitnessName, String fitnessLeader, String fitnessDesc, String fitntessDayOfTraining, String fitnesshour) {
        this.fitnessName = fitnessName;
        this.fitnessLeader = fitnessLeader;
        this.fitnessDesc = fitnessDesc;
        this.fitntessDayOfTraining = fitntessDayOfTraining;
        this.fitnesshour = fitnesshour;
    }


}
