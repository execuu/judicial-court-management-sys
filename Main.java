import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Case[] cases = new Case[50];
        int caseCount = 0;

        Judge[] judges = new Judge[20];
        int judgeCount = 0;

        Lawyer[] lawyers = new Lawyer[40];
        int lawyerCount = 0;

        while (true) {
            System.out.println("\n===== Judicial Court Management System =====");
            System.out.println("1. Register Case");
            System.out.println("2. Add Judge");
            System.out.println("3. Add Lawyer");
            System.out.println("4. Assign Judge");
            System.out.println("5. Assign Lawyer");
            System.out.println("6. Add Evidence");
            System.out.println("7. Schedule Hearing");
            System.out.println("8. Record Hearing Result");
            System.out.println("9. Generate Case Report");
            System.out.println("10. Close Case");
            System.out.println("11. Show Case Details");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int menuChoice = scanner.nextInt();
            scanner.nextLine();

            if (menuChoice == 0) break;

            Case selectedCase = null;
            switch (menuChoice) {
                // register case
                case 1:
                    System.out.print("Enter case number: ");
                    int num = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter case title: ");
                    String title = scanner.nextLine();
                    selectedCase = new Case(num, title);
                    selectedCase.registerCase();
                    cases[caseCount++] = selectedCase;
                    break;

                // add judge
                case 2:
                    if (judgeCount < judges.length) {
                        System.out.print("Enter judge ID: ");
                        int judgeId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter judge name: ");
                        String judgeName = scanner.nextLine();
                        
                        judges [judgeCount++] = new Judge(judgeId, judgeName);
                        System.out.println("Judge added successfully!");
                    } else {
                        System.out.println("Judge list is full!");
                    }
                    break;
                
                // add lawyer
                case 3:
                    if (lawyerCount < lawyers.length) {
                        System.out.print("Enter lawyer ID: ");
                        int lawyerId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter lawyer name: ");
                        String lawyerName = scanner.nextLine();
                        System.out.print("Enter role (Defense/Prosecution): ");
                        String lawyerRole = scanner.nextLine();

                        lawyers[lawyerCount++] = new Lawyer(lawyerId, lawyerName, lawyerRole);
                        System.out.println("Lawyer added successfully!");
                    } 
                    else {
                        System.out.println("Lawyer list is full!");
                    }
                    break;

                // assign judge
                case 4:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase == null) break;
                    Judge selectedJudge = pickJudge(scanner, judges, judgeCount);
                    if (selectedJudge != null) selectedJudge.assignCase(selectedCase);
                    break;

                // assign lawyer
                case 5:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase == null) break;
                    Lawyer selectedLawyer = pickLawyer(scanner, lawyers, lawyerCount);
                    if (selectedLawyer != null) selectedLawyer.assignToCase(selectedCase);
                    break;
                
                // add evidence 
                case 6:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase == null) break;
                    System.out.print("Enter evidence id: ");
                    int evidenceId = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter type: ");
                    String type = scanner.nextLine();
                    Evidence evidence = new Evidence(evidenceId, desc, type);
                    selectedCase.addEvidence(evidence);
                    break;

                // schedule hearing 
                case 7:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase == null) break;
                    System.out.print("Enter hearing id: ");
                    int hearingId = scanner.nextInt(); scanner.nextLine();
                    Date date = null;
                    while (date == null) {
                        System.out.print("Enter date (USE THIS FORMAT => (YYYY-MM-DD): ");
                        String dateStr = scanner.nextLine();
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            sdf.setLenient(false);
                            date = sdf.parse(dateStr);
                        } catch (ParseException ex) {
                            System.out.println("Invalid date format. Try again.");
                        }
                    }
                    Hearing hearing = new Hearing(hearingId, date);
                    selectedCase.scheduleHearing(hearing);
                    break;

                // record hearing results 
                case 8:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase == null) break;
                    if (selectedCase.getHearingCount() == 0) {
                        System.out.println("No hearings scheduled.");
                        break;
                    }
                    System.out.print("Enter hearing index (1-" + selectedCase.getHearingCount() + "): ");
                    int idx = scanner.nextInt() - 1; scanner.nextLine();
                    System.out.print("Enter result: ");
                    String res = scanner.nextLine();
                    selectedCase.getHearings()[idx].recordResult(res);
                    break;

                // generate case reports 
                case 9:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase != null) selectedCase.generateReport();
                    break;

                // close case 
                case 10:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase != null) selectedCase.closeCase();
                    break;

                // show case details
                case 11:
                    selectedCase = pickCase(scanner, cases, caseCount);
                    if (selectedCase != null) selectedCase.printCaseDetails();
                    break;
            }
        }

        scanner.close();
        System.out.println("Exiting System...");
    }

    private static Case pickCase(Scanner scanner, Case[] cases, int count) {
        if (count == 0) { System.out.println("No cases available."); return null; }
        System.out.println("Available cases:");
        for (int i = 0; i < count; i++) {
            System.out.println((i+1) + ". Case " + cases[i].getCaseNumber() + " - " + cases[i].getTitle());
        }
        System.out.print("Choose case: ");
        int choice = scanner.nextInt(); scanner.nextLine();
        if (choice <= 0 || choice > count) return null;
        return cases[choice-1];
    }

    private static Judge pickJudge(Scanner scanner, Judge[] judges, int judgeCount) {
        if (judgeCount == 0) {
            System.out.println("No judges available.");
            return null;
        }
        System.out.println("Avaiable Judges:");
        for(int i = 0; i < judgeCount; i++) {
            System.out.println((i+1) + ". " + judges[i].getName());
        }
        System.out.print("Choose judge: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice <= 0 || choice > judgeCount) return null;
        return judges[choice-1];
    }

    private static Lawyer pickLawyer(Scanner scanner, Lawyer[] lawyers, int lawyerCount) {
        if (lawyerCount == 0) {
            System.out.println("No lawyers available.");
            return null;
        }
        System.out.println("Available Lawyers:");
        for(int i = 0; i < lawyerCount; i++) {
            System.out.println((i+1) + ". " + lawyers[i].getName() + "[" + lawyers[i].getRole() + "]");
        }
        System.out.print("Choose lawyer: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice <= 0 || choice > lawyerCount) return null;
        return lawyers[choice-1];
    }
}

class Case {
    private int caseNumber;
    private String title;
    private String status;
    private Date filedDate;
    private Judge judge;
    private Lawyer[] lawyers = new Lawyer[10];
    private int lawyerCount = 0;
    private Evidence[] evidences = new Evidence[20];
    private int evidenceCount = 0;
    private Hearing[] hearings = new Hearing[10];
    private int hearingCount = 0;
    private Report[] reports = new Report[10];
    private int reportCount = 0;

    public Case(int caseNumber, String title) {
        this.caseNumber = caseNumber;
        this.title = title;
    }

    public void registerCase() {
        this.status = "Open";
        this.filedDate = new Date();
        System.out.println("Case " + caseNumber + " registered.");
    }

    public void assignJudge(Judge judge) {
        this.judge = judge;
        System.out.println(judge.getName() + " assigned to case " + caseNumber);
    }

    public void assignLawyer(Lawyer lawyer) {
        if (lawyerCount < lawyers.length) {
            lawyers[lawyerCount++] = lawyer;
            System.out.println("Lawyer " + lawyer.getName() + " assigned to case " + caseNumber);
        }
    }

    public void addEvidence(Evidence e) {
        if (evidenceCount < evidences.length) {
            evidences[evidenceCount++] = e;
            System.out.println("Evidence added: " + e.getDescription());
        }
    }

    public void scheduleHearing(Hearing h) {
        for (int i = 0; i < hearingCount; i++) {
            if (hearings[i].getDate().equals(h.getDate())) {
                System.out.println("Scheduling conflict! A hearing already exists on that date.");
                return;
            }
        }
        hearings[hearingCount++] = h;
        h.schedule();
    }

    public void generateReport() {
        if (reportCount < reports.length) {
            Report r = new Report(reportCount+1, "Report for case " + caseNumber);
            reports[reportCount++] = r;
            r.export();
        }
    }

    public void closeCase() {
        this.status = "Closed";
        System.out.println("Case " + caseNumber + " closed.");
    }

    public void printCaseDetails() {
        System.out.println("\n===== Case Details =====");
        System.out.println("Case " + caseNumber + ": " + title);
        System.out.println("Status: " + status);
        System.out.println("Filed: " + filedDate);
        System.out.println("Judge: " + (judge!=null ? judge.getName() : "Not assigned"));
        System.out.println("Lawyers:");
        for (int i=0;i<lawyerCount;i++) System.out.println(" - " + lawyers[i].getName());
        System.out.println("Evidence:");
        for (int i=0;i<evidenceCount;i++) System.out.println(" - " + evidences[i].getDescription());
        System.out.println("Hearings:");
        for (int i=0;i<hearingCount;i++) {
            System.out.println(" - Hearing " + hearings[i].getHearingId() + " on " + hearings[i].getDate() +
                               " Result: " + hearings[i].getResult());
        }
    }

    public int getCaseNumber() { return caseNumber; }
    public String getTitle() { return title; }
    public int getHearingCount() { return hearingCount; }
    public Hearing[] getHearings() { return hearings; }
}

class Judge {
    private int judgeId;
    private String name;

    public Judge(int id, String name) {
        this.judgeId = id;
        this.name = name;
    }

    public String getName() { return name; }
    public void assignCase(Case c) { c.assignJudge(this); }
}

class Lawyer {
    private int lawyerId;
    private String name;
    private String role;

    public Lawyer(int id, String name, String role) {
        this.lawyerId = id;
        this.name = name;
        this.role = role;
    }

    public String getName() { return name; }
    public String getRole() { return role; }
    public void assignToCase(Case c) { c.assignLawyer(this); }
}

class Evidence {
    private int id;
    private String description;
    private String type;

    public Evidence(int id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    public String getDescription() { return description; }
    public String getType() { return type; }
    public void upload() { System.out.println("Uploaded evidence: " + description); }
}

class Hearing {
    private int hearingId;
    private Date date;
    private String result = "Pending";

    public Hearing(int id, Date date) {
        this.hearingId = id;
        this.date = date;
    }

    public void schedule() {
        System.out.println("Hearing " + hearingId + " scheduled for " + date);
    }

    public void recordResult(String res) {
        this.result = res;
        System.out.println("Result recorded for hearing " + hearingId + ": " + res);
    }

    public int getHearingId() { return hearingId; }
    public Date getDate() { return date; }
    public String getResult() { return result; }
}

class Report {
    private int reportId;
    private Date generatedDate;
    private String content;

    public Report(int id, String content) {
        this.reportId = id;
        this.generatedDate = new Date();
        this.content = content;
    }

    public void export() {
        System.out.println("Exporting Report " + reportId + ": " + content);
    }
}
