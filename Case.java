import java.util.Date;
import javax.swing.JOptionPane;

public class Case {
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
            r.report();
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
