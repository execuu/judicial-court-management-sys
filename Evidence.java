import javax.swing.JOptionPane;

public class Evidence {
    private int evidenceId;
    private String description;
    private String type;

    public Evidence(int evidenceId, String description, String type) {
        this.evidenceId = evidenceId;
        this.description = description;
        this.type = type;
    }

    public int getEvidenceId() { 
        return evidenceId; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public String getType() { 
        return type; 
    }
    
    public void upload() { 
        JOptionPane.showMessageDialog(null, "Uploaded evidence: " + description, "Evidence", JOptionPane.INFORMATION_MESSAGE); 
    }
}
