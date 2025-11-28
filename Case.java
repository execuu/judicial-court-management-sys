import java.util.Arrays;
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

    // example of association case is still keeeping a reference to Judge.
    // Judge and Case can exist independently and only when assigned to judge they are linked
    public void assignJudge(Judge judge) {
        this.judge = judge;
        String message = "Association: " + judge.getName() + " assigned to Case #" + caseNumber;
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[Demo - Association] " + message);
    }

    public void assignLawyer(Lawyer lawyer) {
        if (lawyerCount >= lawyers.length) {
            JOptionPane.showMessageDialog(null, "Cannot assign more lawyers to this case.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lawyers[lawyerCount++] = lawyer;
        String message = "Association: Lawyer " + lawyer.getName() + " [" + lawyer.getRole() + "] assigned to Case #" + caseNumber;
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[Demo - Association] " + message);
    }

    
    public void addEvidence(Evidence e) {
        if (evidenceCount >= evidences.length) {
            JOptionPane.showMessageDialog(null, "Cannot add more evidence.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        evidences[evidenceCount++] = e;
        String message = "Aggregation: Evidence (id=" + e.getEvidenceId() + ", desc=\"" + e.getDescription()
                + "\") added to Case #" + caseNumber;
        JOptionPane.showMessageDialog(null, "Evidence added: " + e.getDescription(), "Success", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[Demo - Aggregation] " + message);
    }

    public void addEvidence(int evidenceId, String description, String type) {
        Evidence evidence = new Evidence(evidenceId, description, type);
        addEvidence(evidence);
    }

    public void scheduleHearing(Hearing h) {
        if (hearingCount >= hearings.length) {
            JOptionPane.showMessageDialog(null, "Cannot schedule more hearings.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
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
        if (reportCount >= reports.length) {
            JOptionPane.showMessageDialog(null, "Cannot generate more reports.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Report r = new Report(reportCount + 1, "Report for case " + caseNumber, caseNumber);
        reports[reportCount++] = r;
        String message = "Composition: Report #" + r.getReportId() + " created as part of Case #" + caseNumber;
        System.out.println("[Demo - Composition] " + message);
        JOptionPane.showMessageDialog(null, message, "Report Generated", JOptionPane.INFORMATION_MESSAGE);
        r.report();
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
        details.append("Judge: ").append(judge != null ? judge.describeRole() : "Not assigned").append("\n\n");

        details.append("Lawyers:\n");
        if (lawyerCount == 0) {
            details.append("  - No lawyers assigned\n");
        } else {
            for (int i = 0; i < lawyerCount; i++) {
                details.append("  - ").append(lawyers[i].describeRole()).append("\n");
            }
        }

        details.append("\nEvidence:\n");
        if (evidenceCount == 0) {
            details.append("  - No evidence added\n");
        } else {
            for (int i = 0; i < evidenceCount; i++) {
                details.append("  - ").append(evidences[i].getDescription()).append(" (id=").append(evidences[i].getEvidenceId()).append(")\n");
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

        details.append("\nReports:\n");
        if (reportCount == 0) {
            details.append("  - No reports generated\n");
        } else {
            for (int i = 0; i < reportCount; i++) {
                details.append("  - Report #").append(reports[i].getReportId())
                       .append(" (for Case ").append(reports[i].getParentCaseNumber()).append(") generated on ").append(reports[i].getGeneratedDate())
                       .append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, details.toString(), "Case Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public int getCaseNumber() { return caseNumber; }
    public String getTitle() { return title; }
    public int getHearingCount() { return hearingCount; }
    public Hearing[] getHearings() { return Arrays.copyOf(hearings, hearingCount); }

    public void showParticipantRoles() {
        Person[] participants = collectParticipants();
        if (participants.length == 0) {
            JOptionPane.showMessageDialog(null, "No participants assigned yet.", "Participants", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder builder = new StringBuilder("=== Participant Roles ===\n");
        for (Person person : participants) {
            builder.append("- ").append(person.describeRole()).append("\n");
        }
        JOptionPane.showMessageDialog(null, builder.toString(), "Participants", JOptionPane.INFORMATION_MESSAGE);
    }

    private Person[] collectParticipants() {
        int total = (judge != null ? 1 : 0) + lawyerCount;
        Person[] people = new Person[total];
        int index = 0;
        if (judge != null) {
            people[index++] = judge;
        }
        for (int i = 0; i < lawyerCount; i++) {
            people[index++] = lawyers[i];
        }
        return people;
    }
}
