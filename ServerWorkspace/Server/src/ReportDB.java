import java.time.LocalDate;
import java.util.LinkedList;

public class ReportDB {
	
	private LinkedList<Report> list;
	
	public ReportDB()
	{
		list = new LinkedList<Report>();
	}
	
	// Add account details to linked list
	public synchronized void addReport(int reportID, LocalDate date, String employeeID, String reportType, String reportStatus,int assignedID)
	{
		Report temp = new Report(reportID, date, employeeID, reportType, reportStatus, assignedID);
		
		list.add(temp);
		
	}
	

}