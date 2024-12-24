import java.time.LocalDate;

public class Report {
	// feilds to store report details
    private int reportID;
    private LocalDate date;
    private String employeeID;
    private String reportType;
    private String reportStatus;
    private int assignedID;

    // getter for report ID
    public int getReportID() {
		return reportID;
	}
    //setter for report ID 
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	// Constructor to initialise report object
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

	public String getReportType() {
		return reportType;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public int getAssignedID() {
		return assignedID;
	}

	public void setAssignedID(int assignedID) {
		this.assignedID = assignedID;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}
