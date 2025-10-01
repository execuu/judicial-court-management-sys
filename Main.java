import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Case[] cases = new Case[50];
        int caseCount = 0;

        Judge[] judges = new Judge[20];
        int judgeCount = 0;

        Lawyer[] lawyers = new Lawyer[40];
        int lawyerCount = 0;

        while (true) {
            int menuChoice = showVerticalMenu();

            if (menuChoice == -1 || menuChoice == 11) break;
                
            Case selectedCase = null;
            switch (menuChoice) {
                // register case
                case 0:
                    String numStr = JOptionPane.showInputDialog(null, "Enter case number:", "Register Case", JOptionPane.QUESTION_MESSAGE);
                    if (numStr == null) break;
                    try {
                        int num = Integer.parseInt(numStr);
                        String title = JOptionPane.showInputDialog(null, "Enter case title:", "Case Title", JOptionPane.QUESTION_MESSAGE);
                        if (title == null) break;
                        selectedCase = new Case(num, title);
                        selectedCase.registerCase();
                        cases[caseCount++] = selectedCase;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid case number. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                // add judge
                case 1:
                    if (judgeCount < judges.length) {
                        String judgeIdStr = JOptionPane.showInputDialog(null, "Enter judge ID: ", "Add Judge", JOptionPane.QUESTION_MESSAGE);
                        if (judgeIdStr == null) break;
                        try {
                            int judgeId = Integer.parseInt(judgeIdStr);
                            String judgeName = JOptionPane.showInputDialog(null, "Enter judge name: ", "Add Judge", JOptionPane.QUESTION_MESSAGE);

                            if (judgeName == null) break;
                            judges [judgeCount++] = new Judge(judgeId, judgeName);
                            JOptionPane.showMessageDialog(null, "Judge added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);   
                        } 
                    } else {
                            JOptionPane.showMessageDialog(null, "Judge list is full!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                // add lawyer
                case 2:
                    if (lawyerCount < lawyers.length) {
                        String lawyerIdStr = JOptionPane.showInputDialog(null, "Enter lawyer ID:", "Add Lawyer", JOptionPane.QUESTION_MESSAGE);
                        if (lawyerIdStr == null) break; 
                        try {
                            int lawyerId = Integer.parseInt(lawyerIdStr);
                            String lawyerName = JOptionPane.showInputDialog(null, "Enter lawyer name:", "Add Lawyer", JOptionPane.QUESTION_MESSAGE);
                            if (lawyerName == null) break; 
                            
                            String[] roleOptions = {"Defense", "Prosecution"};
                            int roleChoice = JOptionPane.showOptionDialog(
                                null, 
                                "Select lawyer role:", 
                                "Lawyer role",
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.QUESTION_MESSAGE,
                                null, 
                                roleOptions, 
                                roleOptions[0]
                            );
                            if (roleChoice == -1) break; 
                            String lawyerRole = roleOptions[roleChoice];

                            lawyers[lawyerCount++] = new Lawyer(lawyerId, lawyerName, lawyerRole);
                            JOptionPane.showMessageDialog(null, "Lawyer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid lawyer ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "Lawyer list is full!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                // assign judge
                case 3:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase == null) break;
                    Judge selectedJudge = pickJudge(judges, judgeCount);
                    if (selectedJudge != null) selectedJudge.assignCase(selectedCase);
                    break;
                // assign lawyer
                case 4:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase == null) break;
                    Lawyer selectedLawyer = pickLawyer(lawyers, lawyerCount);
                    if (selectedLawyer != null) selectedLawyer.assignToCase(selectedCase);
                    break;
                // add evidence 
                case 5:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase == null) break;
                    String evidenceIdStr = JOptionPane.showInputDialog(null, "Enter evidence ID:", "Add Evidence", JOptionPane.QUESTION_MESSAGE);
                    if (evidenceIdStr == null) break;
                    try {
                        int evidenceId = Integer.parseInt(evidenceIdStr);
                        String desc = JOptionPane.showInputDialog(null, "Enter evidence description:", "Add Evidence", JOptionPane.QUESTION_MESSAGE);
                        if (desc == null) break;
                        String type = JOptionPane.showInputDialog(null, "Enter type:", "Add Evidence", JOptionPane.QUESTION_MESSAGE);
                        if (type == null) break;
                        Evidence evidence = new Evidence(evidenceId, desc, type);
                        selectedCase.addEvidence(evidence);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid evidence ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                // schedule hearing 
                case 6:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase == null) break;
                    String hearingIdStr = JOptionPane.showInputDialog(null, "Enter hearing ID:", "Schedule Hearing", JOptionPane.QUESTION_MESSAGE);
                    if (hearingIdStr == null) break;
                    try {
                        int hearingId = Integer.parseInt(hearingIdStr);
                        Date date = null;
                        while (date == null) {
                            String dateStr = JOptionPane.showInputDialog(null, "Enter date (YYYY-MM-DD format):", "Schedule Hearing", JOptionPane.QUESTION_MESSAGE);
                            if (dateStr == null) break;
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                sdf.setLenient(false);
                                date = sdf.parse(dateStr);
                            } catch (ParseException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid date format. Please use YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (date != null) {
                            Hearing hearing = new Hearing(hearingId, date);
                            selectedCase.scheduleHearing(hearing);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid hearing ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                // record hearing results 
                case 7:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase == null) break;
                    if (selectedCase.getHearingCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No hearings scheduled.", "Warning", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    
                    String[] hearingOptions = new String[selectedCase.getHearingCount()];
                    for (int i = 0; i < selectedCase.getHearingCount(); i++) {
                        hearingOptions[i] = "Hearing " + selectedCase.getHearings()[i].getHearingId() + " - " + selectedCase.getHearings()[i].getDate();
                    }
                    
                    int hearingChoice = JOptionPane.showOptionDialog(
                        null, 
                        "Select hearing to record result:", 
                        "Record Hearing Result",
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE,
                        null, 
                        hearingOptions, 
                        hearingOptions[0]
                    );
                    
                    if (hearingChoice != -1) {
                        String res = JOptionPane.showInputDialog(null, "Enter hearing result:", "Record Hearing Result", JOptionPane.QUESTION_MESSAGE);
                        if (res != null) {
                            selectedCase.getHearings()[hearingChoice].recordResult(res);
                        }
                    }
                    break;
                // generate case reports 
                case 8:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase != null) selectedCase.generateReport();
                    break;
                // close case 
                case 9:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase != null) selectedCase.closeCase();
                    break;
                // show case details
                case 10:
                    selectedCase = pickCase(cases, caseCount);
                    if (selectedCase != null) selectedCase.printCaseDetails();
                    break;
            }
        }
        JOptionPane.showMessageDialog(null, "Thank you for using Judicial Court Management System!", "Exiting System", JOptionPane.INFORMATION_MESSAGE);
    }

    private static int showVerticalMenu() {
        final int[] choice = {-1}; 
        
        JDialog dialog = new JDialog((Frame) null, "Judicial Court Management System", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("=== Judicial Court Management System ===", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 25, 15, 25));
        dialog.add(titleLabel, BorderLayout.NORTH);
        
        JLabel instructionLabel = new JLabel("Click on any option below:", JLabel.CENTER);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        
        String[] options = {
            "1. Register Case", 
            "2. Add Judge", 
            "3. Add Lawyer", 
            "4. Assign Judge", 
            "5. Assign Lawyer",
            "6. Add Evidence", 
            "7. Schedule Hearing", 
            "8. Record Hearing Result", 
            "9. Generate Case Report",
            "10. Close Case", 
            "11. Show Case Details", 
            "12. Exit"
        };
        
        for (int i = 0; i < options.length; i++) {
            final int index = i; 
            JButton button = new JButton(options[i]);
            button.setMaximumSize(new Dimension(250, 60));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            
            button.addActionListener(e -> {
                choice[0] = index;
                dialog.dispose();
            });
            // button spacing
            buttonPanel.add(button);
            if (i < options.length - 1) {
                buttonPanel.add(Box.createVerticalStrut(5)); 
            }
        }
        
        // instruction and buttons to center
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(instructionLabel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        dialog.add(centerPanel, BorderLayout.CENTER);
        // center on screen
        dialog.pack();
        dialog.setLocationRelativeTo(null); 
        dialog.setVisible(true);
        
        return choice[0];
    }

    private static Case pickCase(Case[] cases, int count) {
        if (count == 0) { 
            JOptionPane.showMessageDialog(null, "No cases available.", "Warning", JOptionPane.WARNING_MESSAGE); 
            return null; 
        }
        
        String[] caseOptions = new String[count];
        for (int i = 0; i < count; i++) {
            caseOptions[i] = "Case " + cases[i].getCaseNumber() + " - " + cases[i].getTitle();
        }
        
        int choice = JOptionPane.showOptionDialog(
            null,
            "Select a case:",
            "Choose Case",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            caseOptions,
            caseOptions[0]
        );
        
        if (choice == -1) return null; 
        return cases[choice];
    }

    private static Judge pickJudge(Judge[] judges, int judgeCount) {
        if (judgeCount == 0) {
            JOptionPane.showMessageDialog(null, "No judges available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        String[] judgeOptions = new String[judgeCount];
        for(int i = 0; i < judgeCount; i++) {
            judgeOptions[i] = judges[i].getName();
        }
        
        int choice = JOptionPane.showOptionDialog(
            null,
            "Select a judge:",
            "Choose Judge",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            judgeOptions,
            judgeOptions[0]
        );
        
        if (choice == -1) return null;
        return judges[choice];
    }

    private static Lawyer pickLawyer(Lawyer[] lawyers, int lawyerCount) {
        if (lawyerCount == 0) {
            JOptionPane.showMessageDialog(null, "No lawyers available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        String[] lawyerOptions = new String[lawyerCount];
        for(int i = 0; i < lawyerCount; i++) {
            lawyerOptions[i] = lawyers[i].getName() + " [" + lawyers[i].getRole() + "]";
        }
        
        int choice = JOptionPane.showOptionDialog(
            null,
            "Select a lawyer:",
            "Choose Lawyer",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            lawyerOptions,
            lawyerOptions[0]
        );
        
        if (choice == -1) return null;
        return lawyers[choice];
    }
}


class Case {
    private int caseNumber;
    private String title;
    private String status;
    private Date filedDate;
    private Judge judge;
    private Lawyer[] lawyers = new Lawyer[10];
    private int lawyerCount = 0;
    private Evidence[] evidences = new Evidence[20];
    private int evidenceCount = 0;
    private Hearing[] hearings = new Hearing[10];
    private int hearingCount = 0;
    private Report[] reports = new Report[10];
    private int reportCount = 0;

    public Case(int caseNumber, String title) {
        this.caseNumber = caseNumber;
        this.title = title;
    }

    public void registerCase() {
        this.status = "Open";
        this.filedDate = new Date();
        JOptionPane.showMessageDialog(null, "Case " + caseNumber + " registered.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void assignJudge(Judge judge) {
        this.judge = judge;
        JOptionPane.showMessageDialog(null, judge.getName() + " assigned to case " + caseNumber, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void assignLawyer(Lawyer lawyer) {
        if (lawyerCount < lawyers.length) {
            lawyers[lawyerCount++] = lawyer;
            JOptionPane.showMessageDialog(null, "Lawyer " + lawyer.getName() + " assigned to case " + caseNumber, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void addEvidence(Evidence e) {
        if (evidenceCount < evidences.length) {
            evidences[evidenceCount++] = e;
            JOptionPane.showMessageDialog(null, "Evidence added: " + e.getDescription(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void scheduleHearing(Hearing h) {
        for (int i = 0; i < hearingCount; i++) {
            if (hearings[i].getDate().equals(h.getDate())) {
                JOptionPane.showMessageDialog(null, "Scheduling conflict! A hearing already exists on that date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        hearings[hearingCount++] = h;
        h.schedule();
    }

    public void generateReport() {
        if (reportCount < reports.length) {
            Report r = new Report(reportCount+1, "Report for case " + caseNumber);
            reports[reportCount++] = r;
            r.export();
        }
    }

    public void closeCase() {
        this.status = "Closed";
        JOptionPane.showMessageDialog(null, "Case " + caseNumber + " closed.", "Case Closed", JOptionPane.INFORMATION_MESSAGE);
    }

    public void printCaseDetails() {
        StringBuilder details = new StringBuilder();
        details.append("===== Case Details =====\n\n");
        details.append("Case ").append(caseNumber).append(": ").append(title).append("\n");
        details.append("Status: ").append(status).append("\n");
        details.append("Filed: ").append(filedDate).append("\n");
        details.append("Judge: ").append(judge != null ? judge.getName() : "Not assigned").append("\n\n");
        
        details.append("Lawyers:\n");
        if (lawyerCount == 0) {
            details.append("  - No lawyers assigned\n");
        } else {
            for (int i = 0; i < lawyerCount; i++) {
                details.append("  - ").append(lawyers[i].getName()).append(" [").append(lawyers[i].getRole()).append("]\n");
            }
        }
        
        details.append("\nEvidence:\n");
        if (evidenceCount == 0) {
            details.append("  - No evidence added\n");
        } else {
            for (int i = 0; i < evidenceCount; i++) {
                details.append("  - ").append(evidences[i].getDescription()).append("\n");
            }
        }
        
        details.append("\nHearings:\n");
        if (hearingCount == 0) {
            details.append("  - No hearings scheduled\n");
        } else {
            for (int i = 0; i < hearingCount; i++) {
                details.append("  - Hearing ").append(hearings[i].getHearingId())
                       .append(" on ").append(hearings[i].getDate())
                       .append(" | Result: ").append(hearings[i].getResult()).append("\n");
            }
        }
        
        JOptionPane.showMessageDialog(null, details.toString(), "Case Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public int getCaseNumber() { return caseNumber; }
    public String getTitle() { return title; }
    public int getHearingCount() { return hearingCount; }
    public Hearing[] getHearings() { return hearings; }
}

class Judge {
    private int judgeId;
    private String name;

    public Judge(int id, String name) {
        this.judgeId = id;
        this.name = name;
    }

    public String getName() { return name; }
    public void assignCase(Case c) { c.assignJudge(this); }
}

class Lawyer {
    private int lawyerId;
    private String name;
    private String role;

    public Lawyer(int id, String name, String role) {
        this.lawyerId = id;
        this.name = name;
        this.role = role;
    }

    public String getName() { return name; }
    public String getRole() { return role; }
    public void assignToCase(Case c) { c.assignLawyer(this); }
}

class Evidence {
    private int id;
    private String description;
    private String type;

    public Evidence(int id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    public String getDescription() { return description; }
    public String getType() { return type; }
    public void upload() { JOptionPane.showMessageDialog(null, "Uploaded evidence: " + description, "Evidence", JOptionPane.INFORMATION_MESSAGE); }
}

class Hearing {
    private int hearingId;
    private Date date;
    private String result = "Pending";

    public Hearing(int id, Date date) {
        this.hearingId = id;
        this.date = date;
    }

    public void schedule() {
        JOptionPane.showMessageDialog(null, "Hearing " + hearingId + " scheduled for " + date, "Hearing Scheduled", JOptionPane.INFORMATION_MESSAGE);
    }

    public void recordResult(String res) {
        this.result = res;
        JOptionPane.showMessageDialog(null, "Result recorded for hearing " + hearingId + ": " + res, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public int getHearingId() { return hearingId; }
    public Date getDate() { return date; }
    public String getResult() { return result; }
}

class Report {
    private int reportId;
    private Date generatedDate;
    private String content;

    public Report(int id, String content) {
        this.reportId = id;
        this.generatedDate = new Date();
        this.content = content;
    }

    public void export() {
        JOptionPane.showMessageDialog(null, "Exporting Report " + reportId + ": " + content, "Export Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
