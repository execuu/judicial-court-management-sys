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
    
    public void assignCase(Case c) { 
        c.assignJudge(this); 
    }
}
