import javax.swing.JOptionPane;

public class Judge {
    private int judgeId;
    private String name;

    public Judge(int id, String name) {
        this.judgeId = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Judge assigns their self to  Case
    public void assignCase(Case c) {
        c.assignJudge(this); // Case records the association
        String msg = "[Demo - Association] Judge " + name + " associated with Case #" + c.getCaseNumber();
        System.out.println(msg);
        JOptionPane.showMessageDialog(null, msg, "Judge Assigned", JOptionPane.INFORMATION_MESSAGE);
    }
}
