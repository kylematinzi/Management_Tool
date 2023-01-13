package com.example.managementtool;

/**
 * This class allows the creation of the employee object
 */
public class Employee {

    private int employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String userName;
    private String jobTitle;
    private String emailAddress;
    private String employeePassword;

    /**
     * This method is the constructor for the employee object.
     * @param employeeId - employee's user id
     * @param employeeFirstName - employee's first name
     * @param employeeLastName - employee's last name
     * @param userName - employee's username
     * @param jobTitle - employee's job title
     * @param emailAddress - employee's email address
     * @param employeePassword - employee's password
     */
    public Employee(int employeeId, String employeeFirstName, String employeeLastName, String userName, String jobTitle,
                    String emailAddress, String employeePassword){
        this.employeeId = employeeId;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.userName = userName;
        this.jobTitle = jobTitle;
        this.emailAddress = emailAddress;
        this.employeePassword = employeePassword;

    }

    // Below are the getters and setters for the employee class. I'm not going to comment each one as I find it pretty self-
    // evident on what each one does.
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }
}
