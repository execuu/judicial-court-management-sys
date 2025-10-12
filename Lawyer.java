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
    
    public void assignToCase(Case c) { 
        c.assignLawyer(this); 
    }
}
