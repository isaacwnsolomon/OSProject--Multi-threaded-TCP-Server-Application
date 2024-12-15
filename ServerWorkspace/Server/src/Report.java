import java.time.LocalDate;

public class Report {
    private int reportID;
    private LocalDate date;
    private String employeeID;
    private String reportType;
    private String reportStatus;
    private int assignedID;

    // Constructor
    public Report(int rid, LocalDate d, String eid, String rt, String rs, int aid) {
        this.reportID = rid;
        this.date = d;
        this.employeeID = eid;
        this.reportType = rt;
        this.reportStatus = rs;
        this.assignedID = aid;
    }

    // Override toString to format account details
    @Override
    public String toString() {
        return "Report ID: " + reportID + ", Date: " + date +
               ", Created By: " + employeeID + ", Type: " + reportType +
               ", Status: " + reportStatus + ", Assigned To: " + assignedID;
    }
}