package com.adithyasairam.oddi.pojos;

/**
 * Created by AdiSai on 9/23/17.
 */

public class Assignment {
    private String assignmentName;
    private String assignmentType;
    private String assignmentClass;
    private String dueDate;

    public Assignment(String assignmentName, String assignmentType, String assignmentClass, String dueDate) {
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.assignmentClass = assignmentClass;
        this.dueDate = dueDate;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public String getAssignmentClass() {
        return assignmentClass;
    }

    public void setAssignmentClass(String assignemtClass) {
        this.assignmentClass = assignemtClass;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentName='" + assignmentName + '\'' +
                ", assignmentType='" + assignmentType + '\'' +
                ", assignmentClass='" + assignmentClass + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
