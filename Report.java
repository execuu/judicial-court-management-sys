import java.util.Date;
import javax.swing.JOptionPane;

public class Report {
    private int reportId;
    private Date generatedDate;
    private String content;
    private int parentCaseNumber; 

    public Report(int id, String content, int parentCaseNumber) {
        this.reportId = id;
        this.generatedDate = new Date();
        this.content = content;
        this.parentCaseNumber = parentCaseNumber;
    }

    public void report() {
        JOptionPane.showMessageDialog(null, "Exporting Report " + reportId + ": " + content, "Export Report", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[Demo - Composition] Report #" + reportId + " exported for Case #" + parentCaseNumber);
    }

    public int getReportId() {
        return reportId;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public int getParentCaseNumber() {
        return parentCaseNumber;
    }
}
