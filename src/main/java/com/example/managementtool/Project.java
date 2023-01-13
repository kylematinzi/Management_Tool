package com.example.managementtool;

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

    /**
     * This method is the constructor for the Project object.
     * @param projectId - project's id
     * @param projectTitle - project's title
     * @param dateCreated - project's creation date
     * @param dateCompleted - project's completion date
     * @param projectDescription - project's description
     */
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
}
