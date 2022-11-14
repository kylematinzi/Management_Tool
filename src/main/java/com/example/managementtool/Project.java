package com.example.managementtool;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

/**
 * This class creates a project object.
 */
public class Project {

    private int projectId;

    private String projectTitle;

    private LocalDate dateCreated;

    private LocalDate dateCompleted;

    private String projectDescription;


    public Project(int projectId, String projectTitle, LocalDate dateCreated, LocalDate dateCompleted, String projectDescription){
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
        this.projectDescription = projectDescription;

    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public String toString(){
        return projectDescription;
    }
}
