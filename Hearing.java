import java.util.Date;
import javax.swing.JOptionPane;

public class Hearing {
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

    public int getHearingId() { 
        return hearingId; 
    }
    
    public Date getDate() { 
        return date; 
    }
    
    public String getResult() { 
        return result; 
    }
}
