import java.util.Date;
import javax.swing.JOptionPane;

public class Report {
    private int reportId;
    private Date generatedDate;
    private String content;

    public Report(int id, String content) {
        this.reportId = id;
        this.generatedDate = new Date();
        this.content = content;
    }

    public void report() {
        JOptionPane.showMessageDialog(null, "Exporting Report " + reportId + ": " + content, "Export Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
