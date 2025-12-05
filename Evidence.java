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

    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description.trim();
        }
    }

    public void setType(String type) {
        if (type != null && !type.trim().isEmpty()) {
            this.type = type.trim();
        }
    }

    public void upload() {
        JOptionPane.showMessageDialog(null, "Uploaded evidence: " + description, "Evidence", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[Demo - Aggregation] Evidence (id=" + evidenceId + ") uploaded independently.");
    }

    public String toString() {
        return "Evidence{id=" + evidenceId + ", desc='" + description + "', type='" + type + "'}";
    }
}
