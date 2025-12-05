import javax.swing.JOptionPane;

public class Lawyer extends Person implements Assignable {
    private String role;

    public Lawyer(int id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role != null && !role.trim().isEmpty()) {
            this.role = role.trim();
        }
    }

    @Override
    public void assignToCase(Case c) {
        c.assignLawyer(this);
        String msg = "[Demo - Association] Lawyer " + getName() + " [" + role + "] associated with Case #" + c.getCaseNumber();
        System.out.println(msg);
        JOptionPane.showMessageDialog(null, msg, "Lawyer Assigned", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String describeRole() {
        return "Lawyer " + getName() + " (" + role + ")";
    }
}
