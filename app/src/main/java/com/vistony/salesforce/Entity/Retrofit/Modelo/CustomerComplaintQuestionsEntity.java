package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerComplaintQuestionsEntity {

    public String question_id;
    @SerializedName("Pregunta")
    public String question;
    @SerializedName("Respuesta")
    public List<CustomerComplaintResponseEntity> listCustomerComplaintResponse;
    public String ButtonStatus;
    public String ButtonIcon;
    @SerializedName("Tipo")
    public String type;
    @SerializedName("TipoRespuesta")
    public String QuestionsEdit;
    public String QuestionAnswered;
    public String QuestionNumber;

    public String getQuestionNumber() {
        return QuestionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        QuestionNumber = questionNumber;
    }

    public String getQuestionAnswered() {
        return QuestionAnswered;
    }

    public void setQuestionAnswered(String questionAnswered) {
        QuestionAnswered = questionAnswered;
    }

    public String getQuestionsEdit() {
        return QuestionsEdit;
    }

    public void setQuestionsEdit(String questionsEdit) {
        QuestionsEdit = questionsEdit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<CustomerComplaintResponseEntity> getListCustomerComplaintResponse() {
        return listCustomerComplaintResponse;
    }

    public void setListCustomerComplaintResponse(List<CustomerComplaintResponseEntity> listCustomerComplaintResponse) {
        this.listCustomerComplaintResponse = listCustomerComplaintResponse;
    }

    public String getButtonStatus() {
        return ButtonStatus;
    }

    public void setButtonStatus(String buttonStatus) {
        ButtonStatus = buttonStatus;
    }

    public String getButtonIcon() {
        return ButtonIcon;
    }

    public void setButtonIcon(String buttonIcon) {
        ButtonIcon = buttonIcon;
    }
}
