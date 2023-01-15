package com.example.managementtool;

import java.time.LocalDate;

/**
 * This class allows the creation of a ticket object.
 */
public class Ticket {

    private int ticketId;
    private int projectAssociation;
    private String ticketTitle;
    private String ticketPriorityLevel;
    private String ticketStatusLevel;
    private LocalDate dateCreated;
    private LocalDate dateCompleted;
    private String ticketDescription;

    /**
     * This method is the constructor for the Ticket object.
     * @param ticketId - ticket's id
     * @param projectAssociation - ticket's project identifier
     * @param ticketTitle - ticket's title
     * @param ticketPriorityLevel - ticket's priority level (low, medium, high)
     * @param ticketStatusLevel - ticket's status level (not started, in work, complete)
     * @param dateCreated - ticket's creation date
     * @param dateCompleted - ticket's completion date
     * @param ticketDescription - ticket's description
     */
    public Ticket(int ticketId, int projectAssociation, String ticketTitle, String ticketPriorityLevel, String ticketStatusLevel,
                  LocalDate dateCreated, LocalDate dateCompleted, String ticketDescription){
        this.ticketId = ticketId;
        this.projectAssociation = projectAssociation;
        this.ticketTitle = ticketTitle;
        this.ticketPriorityLevel = ticketPriorityLevel;
        this.ticketStatusLevel = ticketStatusLevel;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
        this.ticketDescription = ticketDescription;

    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getProjectAssociation() {
        return projectAssociation;
    }

    public void setProjectAssociation(int projectAssociation) {
        this.projectAssociation = projectAssociation;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketPriorityLevel() {
        return ticketPriorityLevel;
    }

    public void setTicketPriorityLevel(String ticketPriorityLevel) {
        this.ticketPriorityLevel = ticketPriorityLevel;
    }

    public String getTicketStatusLevel() {
        return ticketStatusLevel;
    }

    public void setTicketStatusLevel(String ticketStatusLevel) {
        this.ticketStatusLevel = ticketStatusLevel;
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

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }
}