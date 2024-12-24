import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

public class ReportDB {
	// Linked list to store reports
	private LinkedList<Report> list;
	private int lastReportID = 0;
	
	// Constructor to initialise ReportDB
	public ReportDB()
	{
		list = new LinkedList<Report>();
	}
	
	// Add account details to linked list
	public synchronized int addReport(LocalDate date, String employeeID, String reportType, String reportStatus,int assignedID) throws IOException
	{
		// create new reportID 1 above the last
		 int newReportID = ++lastReportID;
		Report temp = new Report(newReportID, date, employeeID, reportType, reportStatus, assignedID);
		//add report to list 
		list.add(temp);
		
		  // Save to file, seperated by commas and a new line for each account
        try (FileWriter writer = new FileWriter("ReportDetails.txt", true)) {
            writer.write(newReportID + "," + date + "," + employeeID + "," + reportType + "," + reportStatus + "," + assignedID + System.lineSeparator());
        }
		return newReportID;
	}
	
    // Load accounts from file into the list
    public synchronized void loadReportsFromFile() throws FileNotFoundException {
        File file = new File("ReportDetails.txt");

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("No report details file found.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split by commas but handle labeled data
                String[] parts = line.split(", ");
                if (parts.length == 6) {
                    try {
                    	// parse and validate each part of the line 
                        int reportID = Integer.parseInt(parts[0].split(": ")[1].trim());
                        LocalDate date = LocalDate.parse(parts[1].split(": ")[1].trim());
                        String employeeID = parts[2].split(": ")[1].trim();
                        String reportType = parts[3].split(": ")[1].trim();
                        String reportStatus = parts[4].split(": ")[1].trim().equals("null") ? null : parts[4].split(": ")[1].trim();
                        int assignedID = Integer.parseInt(parts[5].split(": ")[1].trim());

                        // Create report and add to list 
                        Report temp = new Report(reportID, date, employeeID, reportType, reportStatus, assignedID);
                        list.add(temp);

                        if (reportID > lastReportID) {
                            lastReportID = reportID;
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
                }
              }
            }
            
    }
    // gets all accident reports 
    public synchronized String getAccidentReports() {
        if (list.isEmpty()) {
            return "No reports available.";
        }

        StringBuilder reportDetails = new StringBuilder("All Reports:\n");
        // Loop through list, if report typw equals accident report, append toString 
        for (Report report : list) {
        	if(report.getReportType().equalsIgnoreCase("Accident Report"))
        	{
            reportDetails.append(report.toString()).append("\n----------------------------\n");
        	}
        }

        return reportDetails.toString();
    }
    // gets report by ID
    public Report getReportById(int reportID) {
        for (Report report : list) {
            if (report.getReportID() == reportID) {
                return report;
            }
        }
        return null; // Report not found
    }
    // updates report and saves the changes to file
    public void updateReport(Report updatedReport) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getReportID() == updatedReport.getReportID()) {
                list.set(i, updatedReport); // Update the report
                break;
            }
        }

        // Save changes to file
        saveReportsToFile();
    }

    // Saves all reports to file 
    private void saveReportsToFile() {
        try (FileWriter writer = new FileWriter("ReportDetails.txt")) {
            for (Report report : list) {
                writer.write(report.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //returns all reports assigned to the user logged in 
    public synchronized String getAssignedReports(int employeeID) {
        if (list.isEmpty()) {
            return "No reports available.";
        }

        String reportDetails = "Reports Assigned to You:\n";
        boolean foundReports = false;

        for (Report report : list) {
            if(report.getAssignedID() == employeeID) {
                reportDetails += report.toString() + "\n----------------------------\n";
                foundReports = true;
            }
        }

        if (!foundReports) {
            return "No reports are currently assigned to you.";
        }

        return reportDetails;
    }
   


}

        
   
	  
	

    
