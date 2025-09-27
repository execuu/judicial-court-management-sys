
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Case[] cases = new Case[50];
        int caseCount = 0;

        Judge[] judges = {
            new Judge(1, "Judge Paz"),
            new Judge(2, "Judge Leomir")
        };

        Lawyer[] lawyers = {
            new Lawyer(101, "John Martinez", "Defense"),
            new Lawyer(102, "Ace Evangelista", "Prosecution"),
            +int caseNumber
            +string title
            +string status
            +Date filedDate
            +registerCase()
            +closeCase()
            +generateReport()
        };

        while (true) {
            System.out.println("\n===== Judicial Court Management System =====");
            System.out.println("1. Register Case");
            System.out.println("2. Assign Judge");
            System.out.println("3. Assign Lawyer");
            System.out.println("4. Add Evidence");
            System.out.println("5. Schedule Hearing");
            System.out.println("6. Record Hearing Result");
            System.out.println("7. Generate Case Report");
            System.out.println("8. Close Case");
            System.out.println("9. Show Case Details");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) break;

            Case c = null;
            switch (choice) {
                case 1:
                    System.out.print("Enter case number: ");
                    int num = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter case title: ");
                    String title = scanner.nextLine();
                    c = new Case(num, title);
                    c.registerCase();
                    cases[caseCount++] = c;
                    break;

                case 2:
                    c = pickCase(scanner, cases, caseCount);
                    if (c == null) break;
                    Judge j = pickJudge(scanner, judges);
                    if (j != null) j.assignCase(c);
                    break;

                case 3:
                    c = pickCase(scanner, cases, caseCount);
                    if (c == null) break;
                    Lawyer l = pickLawyer(scanner, lawyers);
                    if (l != null) l.assignToCase(c);
                    break;

                case 4:
                    c = pickCase(scanner, cases, caseCount);
                    if (c == null) break;
                    System.out.print("Enter evidence id: ");
                    int eid = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter type: ");
                    String type = scanner.nextLine();
                    Evidence e = new Evidence(eid, desc, type);
                    c.addEvidence(e);
                    break;

                case 5:
                    c = pickCase(scanner, cases, caseCount);
                    if (c == null) break;
                    System.out.print("Enter hearing id: ");
                    int hid = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String dateStr = scanner.nextLine();
                    Date date = new Date(dateStr);
                    Hearing h = new Hearing(hid, date);
                    c.scheduleHearing(h);
                    break;

                case 6:
                    c = pickCase(scanner, cases, caseCount);
                    if (c == null) break;
                    if (c.getHearingCount() == 0) {
                        System.out.println("No hearings scheduled.");
                        break;
                    }
                    System.out.print("Enter hearing index (1-" + c.getHearingCount() + "): ");
                    int idx = scanner.nextInt() - 1; scanner.nextLine();
                    System.out.print("Enter result: ");
                    String res = scanner.nextLine();
                    c.getHearings()[idx].recordResult(res);
                    break;

                case 7:
                    c = pickCase(scanner, cases, caseCount);
                    if (c != null) c.generateReport();
                    break;

                case 8:
                    c = pickCase(scanner, cases, caseCount);
                    if (c != null) c.closeCase();
                    break;

                case 9:
                    c = pickCase(scanner, cases, caseCount);
                    if (c != null) c.printCaseDetails();
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
        int ch = scanner.nextInt(); scanner.nextLine();
        if (ch <= 0 || ch > count) return null;
        return cases[ch-1];
    }

    private static Judge pickJudge(Scanner scanner, Judge[] judges) {
        System.out.println("Available judges:");
        for (int i = 0; i < judges.length; i++) {
            System.out.println((i+1) + ". " + judges[i].getName());
        }
        System.out.print("Choose judge: ");
        int ch = scanner.nextInt(); scanner.nextLine();
        if (ch <= 0 || ch > judges.length) return null;
        return judges[ch-1];
    }

    private static Lawyer pickLawyer(Scanner scanner, Lawyer[] lawyers) {
        System.out.println("Available lawyers:");
        for (int i = 0; i < lawyers.length; i++) {
            System.out.println((i+1) + ". " + lawyers[i].getName() + " [" + lawyers[i].getRole() + "]");
        }
        System.out.print("Choose lawyer: ");
        int ch = scanner.nextInt(); scanner.nextLine();
        if (ch <= 0 || ch > lawyers.length) return null;
        return lawyers[ch-1];
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
        // check scheduling conflict
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

    // Getters for menu
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
