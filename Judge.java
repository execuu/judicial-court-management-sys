import javax.swing.JOptionPane;

public class Judge extends Person implements Assignable {
    public Judge(int id, String name) {
        super(id, name);
    }

    @Override
    public void assignToCase(Case c) {
        c.assignJudge(this);
        String msg = "[Demo - Association] Judge " + getName() + " associated with Case #" + c.getCaseNumber();
        System.out.println(msg);
        JOptionPane.showMessageDialog(null, msg, "Judge Assigned", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String describeRole() {
        return "Judge " + getName();
    }
}
