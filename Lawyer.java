import javax.swing.JOptionPane;

public class Lawyer {
    private int lawyerId;
    private String name;
    private String role;

    public Lawyer(int id, String name, String role) {
        this.lawyerId = id;
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // assign this Lawyer to a Case
    public void assignToCase(Case c) {
        c.assignLawyer(this);
        String msg = "[Demo - Association] Lawyer " + name + " [" + role + "] associated with Case #" + c.getCaseNumber();
        System.out.println(msg);
        JOptionPane.showMessageDialog(null, msg, "Lawyer Assigned", JOptionPane.INFORMATION_MESSAGE);
    }
}
