package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerComplaintSectionEntity {
    public String section_id;
    @SerializedName("SubTitulo")
    public String section;
    @SerializedName("Preguntas")
    public List<CustomerComplaintQuestionsEntity> listCustomercomplaintQuestions;
    @SerializedName("Repetitivo")
    public String Repetitivo;



    public String getRepetitivo() {
        return Repetitivo;
    }

    public void setRepetitivo(String repetitivo) {
        Repetitivo = repetitivo;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<CustomerComplaintQuestionsEntity> getListCustomercomplaintQuestions() {
        return listCustomercomplaintQuestions;
    }

    public void setListCustomercomplaintQuestions(List<CustomerComplaintQuestionsEntity> listCustomercomplaintQuestions) {
        this.listCustomercomplaintQuestions = listCustomercomplaintQuestions;
    }
}
