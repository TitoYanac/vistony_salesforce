package com.vistony.salesforce.Entity;

public class ApprovalRequest {
    private int ApprovalTemplatesID;
    private String Remarks;

    public int getApprovalTemplatesID() {
        return ApprovalTemplatesID;
    }

    public void setApprovalTemplatesID(int approvalTemplatesID) {
        ApprovalTemplatesID = approvalTemplatesID;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
