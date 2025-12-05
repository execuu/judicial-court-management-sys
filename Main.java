import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Case[] cases = new Case[50];
        int caseCount = 0;

        Judge[] judges = new Judge[20];
        int judgeCount = 0;

        Lawyer[] lawyers = new Lawyer[40];
        int lawyerCount = 0;

        while (true) {
            try {
                int menuChoice = showVerticalMenu();
                if (menuChoice == -1 || menuChoice == 26) break;

                Case selectedCase = null;
                switch (menuChoice) {
                    // Register Case
                    case 0:
                        if (caseCount >= cases.length) {
                            JOptionPane.showMessageDialog(null, "Case list is full!", "Warning", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        String numStr = JOptionPane.showInputDialog(null, "Enter case number:", "Register Case", JOptionPane.QUESTION_MESSAGE);
                        if (numStr == null) break;
                        try {
                            int num = Integer.parseInt(numStr);
                            String title = JOptionPane.showInputDialog(null, "Enter case title:", "Case Title", JOptionPane.QUESTION_MESSAGE);
                            if (title == null) break;
                            selectedCase = new Case(num, title);
                            selectedCase.registerCase();
                            cases[caseCount++] = selectedCase;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid case number. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    // add judge
                    case 1:
                        if (judgeCount >= judges.length) {
                            JOptionPane.showMessageDialog(null, "Judge list is full!", "Warning", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        String judgeIdStr = JOptionPane.showInputDialog(null, "Enter judge ID: ", "Add Judge", JOptionPane.QUESTION_MESSAGE);
                        if (judgeIdStr == null) break;
                        try {
                            int judgeId = Integer.parseInt(judgeIdStr);
                            String judgeName = JOptionPane.showInputDialog(null, "Enter judge name: ", "Add Judge", JOptionPane.QUESTION_MESSAGE);
                            if (judgeName == null) break;
                            judges[judgeCount++] = new Judge(judgeId, judgeName);
                            JOptionPane.showMessageDialog(null, "Judge added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    // add lawyer
                    case 2:
                        if (lawyerCount >= lawyers.length) {
                            JOptionPane.showMessageDialog(null, "Lawyer list is full!", "Warning", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        String lawyerIdStr = JOptionPane.showInputDialog(null, "Enter lawyer ID:", "Add Lawyer", JOptionPane.QUESTION_MESSAGE);
                        if (lawyerIdStr == null) break;
                        try {
                            int lawyerId = Integer.parseInt(lawyerIdStr);
                            String lawyerName = JOptionPane.showInputDialog(null, "Enter lawyer name:", "Add Lawyer", JOptionPane.QUESTION_MESSAGE);
                            if (lawyerName == null) break;

                            String[] roleOptions = {"Defense", "Prosecution"};
                            int roleChoice = JOptionPane.showOptionDialog(
                                null,
                                "Select lawyer role:",
                                "Lawyer Role",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                roleOptions,
                                roleOptions[0]
                            );
                            if (roleChoice == -1) break;

                            String lawyerRole = roleOptions[roleChoice];
                            lawyers[lawyerCount++] = new Lawyer(lawyerId, lawyerName, lawyerRole);
                            JOptionPane.showMessageDialog(null, "Lawyer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid lawyer ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    // assigns judge 
                    case 3:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase == null) break;
                        Judge selectedJudge = pickJudge(judges, judgeCount);
                        if (selectedJudge != null) selectedJudge.assignToCase(selectedCase);
                        break;

                    // assigns lawyer
                    case 4:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase == null) break;
                        Lawyer selectedLawyer = pickLawyer(lawyers, lawyerCount);
                        if (selectedLawyer != null) selectedLawyer.assignToCase(selectedCase); // association here
                        break;

                    // adds an evidence
                    case 5:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase == null) break;
                        String evidenceIdStr = JOptionPane.showInputDialog(null, "Enter evidence ID:", "Add Evidence", JOptionPane.QUESTION_MESSAGE);
                        if (evidenceIdStr == null) break;
                        try {
                            int evidenceId = Integer.parseInt(evidenceIdStr);
                            String desc = JOptionPane.showInputDialog(null, "Enter evidence description:", "Add Evidence", JOptionPane.QUESTION_MESSAGE);
                            if (desc == null) break;
                            String type = JOptionPane.showInputDialog(null, "Enter type:", "Add Evidence", JOptionPane.QUESTION_MESSAGE);
                            if (type == null) break;
                            selectedCase.addEvidence(evidenceId, desc, type);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid evidence ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    // schedule the hearing
                    case 6:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase == null) break;
                        String hearingIdStr = JOptionPane.showInputDialog(null, "Enter hearing ID:", "Schedule Hearing", JOptionPane.QUESTION_MESSAGE);
                        if (hearingIdStr == null) break;
                        try {
                            int hearingId = Integer.parseInt(hearingIdStr);
                            Date date = null;
                            while (date == null) {
                                String dateStr = JOptionPane.showInputDialog(null, "Enter date (YYYY-MM-DD format):", "Schedule Hearing", JOptionPane.QUESTION_MESSAGE);
                                if (dateStr == null) break;
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    sdf.setLenient(false);
                                    date = sdf.parse(dateStr);
                                } catch (ParseException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            if (date != null) {
                                Hearing hearing = new Hearing(hearingId, date);
                                selectedCase.scheduleHearing(hearing);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid hearing ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    // record the hearing result
                    case 7:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase == null) break;
                        if (selectedCase.getHearingCount() == 0) {
                            JOptionPane.showMessageDialog(null, "No hearings scheduled.", "Warning", JOptionPane.WARNING_MESSAGE);
                            break;
                        }

                        String[] hearingOptions = new String[selectedCase.getHearingCount()];
                        for (int i = 0; i < selectedCase.getHearingCount(); i++) {
                            Hearing h = selectedCase.getHearings()[i];
                            hearingOptions[i] = "Hearing " + h.getHearingId() + " - " + h.getDate();
                        }

                        int hearingChoice = JOptionPane.showOptionDialog(
                            null,
                            "Select hearing to record result:",
                            "Record Hearing Result",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            hearingOptions,
                            hearingOptions[0]
                        );

                        if (hearingChoice != -1) {
                            String res = JOptionPane.showInputDialog(null, "Enter hearing result:", "Record Hearing Result", JOptionPane.QUESTION_MESSAGE);
                            if (res != null) {
                                selectedCase.getHearings()[hearingChoice].recordResult(res);
                            }
                        }
                        break;

                    // generate the case Reports (composition demonstration)
                    case 8:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null) {
                            // composition: Case creates Report objects 
                            // reports is tied on tthe owning Case
                            selectedCase.generateReport();
                        }
                        break;

                    // closes case
                    case 9:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null) selectedCase.closeCase();
                        break;

                    // show case details
                    case 10:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null) selectedCase.printCaseDetails();
                        break;

                    case 11:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null) selectedCase.showParticipantRoles();
                        break;

                    case 12:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null) {
                            String newTitle = JOptionPane.showInputDialog(null, "Enter new case title:", "Update Case Title", JOptionPane.QUESTION_MESSAGE);
                            if (newTitle != null) selectedCase.updateTitle(newTitle);
                        }
                        break;

                    case 13:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null) {
                            String[] statusOptions = {"Open", "In Progress", "Closed", "Pending"};
                            int statusChoice = JOptionPane.showOptionDialog(null, "Select new status:", "Update Case Status",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, statusOptions, statusOptions[0]);
                            if (statusChoice != -1) {
                                selectedCase.updateStatus(statusOptions[statusChoice]);
                            }
                        }
                        break;

                    case 14:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null && selectedCase.getEvidenceCount() > 0) {
                            Evidence[] evidences = selectedCase.getEvidences();
                            String[] evidenceOptions = new String[evidences.length];
                            for (int i = 0; i < evidences.length; i++) {
                                evidenceOptions[i] = "ID: " + evidences[i].getEvidenceId() + " - " + evidences[i].getDescription();
                            }
                            int evidenceChoice = JOptionPane.showOptionDialog(null, "Select evidence to update:", "Update Evidence",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, evidenceOptions, evidenceOptions[0]);
                            if (evidenceChoice != -1) {
                                Evidence selectedEvidence = evidences[evidenceChoice];
                                String newDesc = JOptionPane.showInputDialog(null, "Enter new description:", "Update Evidence", JOptionPane.QUESTION_MESSAGE);
                                if (newDesc != null) {
                                    String newType = JOptionPane.showInputDialog(null, "Enter new type:", "Update Evidence", JOptionPane.QUESTION_MESSAGE);
                                    selectedCase.updateEvidence(selectedEvidence.getEvidenceId(), newDesc, newType);
                                }
                            }
                        } else if (selectedCase != null) {
                            JOptionPane.showMessageDialog(null, "No evidence to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;

                    case 15:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null && selectedCase.getEvidenceCount() > 0) {
                            Evidence[] evidences = selectedCase.getEvidences();
                            String[] evidenceOptions = new String[evidences.length];
                            for (int i = 0; i < evidences.length; i++) {
                                evidenceOptions[i] = "ID: " + evidences[i].getEvidenceId() + " - " + evidences[i].getDescription();
                            }
                            int evidenceChoice = JOptionPane.showOptionDialog(null, "Select evidence to delete:", "Delete Evidence",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, evidenceOptions, evidenceOptions[0]);
                            if (evidenceChoice != -1) {
                                selectedCase.deleteEvidence(evidences[evidenceChoice].getEvidenceId());
                            }
                        } else if (selectedCase != null) {
                            JOptionPane.showMessageDialog(null, "No evidence to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;

                    case 16:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null && selectedCase.getHearingCount() > 0) {
                            Hearing[] hearingsToUpdate = selectedCase.getHearings();
                            String[] hearingUpdateOptions = new String[hearingsToUpdate.length];
                            for (int i = 0; i < hearingsToUpdate.length; i++) {
                                hearingUpdateOptions[i] = "Hearing " + hearingsToUpdate[i].getHearingId() + " - " + hearingsToUpdate[i].getDate();
                            }
                            int hearingUpdateChoice = JOptionPane.showOptionDialog(null, "Select hearing to update:", "Update Hearing",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, hearingUpdateOptions, hearingUpdateOptions[0]);
                            if (hearingUpdateChoice != -1) {
                                Date newDate = null;
                                while (newDate == null) {
                                    String dateStr = JOptionPane.showInputDialog(null, "Enter new date (YYYY-MM-DD format):", "Update Hearing", JOptionPane.QUESTION_MESSAGE);
                                    if (dateStr == null) break;
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        sdf.setLenient(false);
                                        newDate = sdf.parse(dateStr);
                                    } catch (ParseException ex) {
                                        JOptionPane.showMessageDialog(null, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                if (newDate != null) {
                                    selectedCase.updateHearing(hearingsToUpdate[hearingUpdateChoice].getHearingId(), newDate);
                                }
                            }
                        } else if (selectedCase != null) {
                            JOptionPane.showMessageDialog(null, "No hearings to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;

                    case 17:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null && selectedCase.getHearingCount() > 0) {
                            Hearing[] hearingsToDelete = selectedCase.getHearings();
                            String[] hearingDeleteOptions = new String[hearingsToDelete.length];
                            for (int i = 0; i < hearingsToDelete.length; i++) {
                                hearingDeleteOptions[i] = "Hearing " + hearingsToDelete[i].getHearingId() + " - " + hearingsToDelete[i].getDate();
                            }
                            int hearingDeleteChoice = JOptionPane.showOptionDialog(null, "Select hearing to delete:", "Delete Hearing",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, hearingDeleteOptions, hearingDeleteOptions[0]);
                            if (hearingDeleteChoice != -1) {
                                selectedCase.deleteHearing(hearingsToDelete[hearingDeleteChoice].getHearingId());
                            }
                        } else if (selectedCase != null) {
                            JOptionPane.showMessageDialog(null, "No hearings to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;

                    case 18:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null) {
                            selectedCase.removeJudge();
                        }
                        break;

                    case 19:
                        selectedCase = pickCase(cases, caseCount);
                        if (selectedCase != null && selectedCase.getLawyerCount() > 0) {
                            Lawyer[] caseLawyers = selectedCase.getLawyers();
                            String[] lawyerRemoveOptions = new String[caseLawyers.length];
                            for (int i = 0; i < caseLawyers.length; i++) {
                                lawyerRemoveOptions[i] = caseLawyers[i].getName() + " [" + caseLawyers[i].getRole() + "]";
                            }
                            int lawyerRemoveChoice = JOptionPane.showOptionDialog(null, "Select lawyer to remove:", "Remove Lawyer",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, lawyerRemoveOptions, lawyerRemoveOptions[0]);
                            if (lawyerRemoveChoice != -1) {
                                selectedCase.removeLawyer(caseLawyers[lawyerRemoveChoice]);
                            }
                        } else if (selectedCase != null) {
                            JOptionPane.showMessageDialog(null, "No lawyers to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;

                    case 20:
                        if (judgeCount == 0) {
                            JOptionPane.showMessageDialog(null, "No judges available.", "Warning", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        StringBuilder judgeList = new StringBuilder("=== Judges List ===\n\n");
                        for (int i = 0; i < judgeCount; i++) {
                            judgeList.append((i + 1)).append(". ").append(judges[i].getName())
                                    .append(" (ID: ").append(judges[i].getId()).append(")\n");
                        }
                        JOptionPane.showMessageDialog(null, judgeList.toString(), "Judges", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case 21:
                        if (lawyerCount == 0) {
                            JOptionPane.showMessageDialog(null, "No lawyers available.", "Warning", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        StringBuilder lawyerList = new StringBuilder("=== Lawyers List ===\n\n");
                        for (int i = 0; i < lawyerCount; i++) {
                            lawyerList.append((i + 1)).append(". ").append(lawyers[i].getName())
                                    .append(" [").append(lawyers[i].getRole()).append("]")
                                    .append(" (ID: ").append(lawyers[i].getId()).append(")\n");
                        }
                        JOptionPane.showMessageDialog(null, lawyerList.toString(), "Lawyers", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case 22:
                        Judge judgeToUpdate = pickJudge(judges, judgeCount);
                        if (judgeToUpdate != null) {
                            String newName = JOptionPane.showInputDialog(null, "Enter new name:", "Update Judge", JOptionPane.QUESTION_MESSAGE);
                            if (newName != null && !newName.trim().isEmpty()) {
                                judgeToUpdate.setName(newName.trim());
                                JOptionPane.showMessageDialog(null, "Judge updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;

                    case 23:
                        Judge judgeToDelete = pickJudge(judges, judgeCount);
                        if (judgeToDelete != null) {
                            int confirm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete " + judgeToDelete.getName() + "?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                int index = -1;
                                for (int i = 0; i < judgeCount; i++) {
                                    if (judges[i] == judgeToDelete || judges[i].getId() == judgeToDelete.getId()) {
                                        index = i;
                                        break;
                                    }
                                }
                                if (index != -1) {
                                    String judgeName = judges[index].getName();
                                    for (int i = index; i < judgeCount - 1; i++) {
                                        judges[i] = judges[i + 1];
                                    }
                                    judges[--judgeCount] = null;
                                    JOptionPane.showMessageDialog(null, "Judge " + judgeName + " deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                        break;

                    case 24:
                        Lawyer lawyerToUpdate = pickLawyer(lawyers, lawyerCount);
                        if (lawyerToUpdate != null) {
                            String newName = JOptionPane.showInputDialog(null, "Enter new name:", "Update Lawyer", JOptionPane.QUESTION_MESSAGE);
                            if (newName != null && !newName.trim().isEmpty()) {
                                lawyerToUpdate.setName(newName.trim());
                                String[] roleOptions = {"Defense", "Prosecution"};
                                int roleChoice = JOptionPane.showOptionDialog(null, "Select new role:", "Update Lawyer Role",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, roleOptions, roleOptions[0]);
                                if (roleChoice != -1) {
                                    lawyerToUpdate.setRole(roleOptions[roleChoice]);
                                    JOptionPane.showMessageDialog(null, "Lawyer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                        break;

                    case 25:
                        Lawyer lawyerToDelete = pickLawyer(lawyers, lawyerCount);
                        if (lawyerToDelete != null) {
                            int confirm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete " + lawyerToDelete.getName() + "?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                int index = -1;
                                for (int i = 0; i < lawyerCount; i++) {
                                    if (lawyers[i] == lawyerToDelete || lawyers[i].getId() == lawyerToDelete.getId()) {
                                        index = i;
                                        break;
                                    }
                                }
                                if (index != -1) {
                                    String lawyerName = lawyers[index].getName();
                                    for (int i = index; i < lawyerCount - 1; i++) {
                                        lawyers[i] = lawyers[i + 1];
                                    }
                                    lawyers[--lawyerCount] = null;
                                    JOptionPane.showMessageDialog(null, "Lawyer " + lawyerName + " deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Unexpected Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        JOptionPane.showMessageDialog(null, "Thank you for using Judicial Court Management System!", "Exiting System", JOptionPane.INFORMATION_MESSAGE);
    }

    // helpers

    private static int showVerticalMenu() {
        final int[] choice = {-1};
        JDialog dialog = new JDialog((Frame) null, "Judicial Court Management System", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("=== Judicial Court Management System ===", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 25, 15, 25));
        dialog.add(titleLabel, BorderLayout.NORTH);

        JLabel instructionLabel = new JLabel("Click on any option below:", JLabel.CENTER);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        String[] options = {
            "1. Register Case", "2. Add Judge", "3. Add Lawyer", "4. Assign Judge",
            "5. Assign Lawyer", "6. Add Evidence", "7. Schedule Hearing",
            "8. Record Hearing Result", "9. Generate Case Report",
            "10. Close Case", "11. Show Case Details", "12. Participant Roles",
            "13. Update Case Title", "14. Update Case Status", "15. Update Evidence",
            "16. Delete Evidence", "17. Update Hearing", "18. Delete Hearing",
            "19. Remove Judge", "20. Remove Lawyer", "21. View Judges", "22. View Lawyers",
            "23. Update Judge", "24. Delete Judge", "25. Update Lawyer", "26. Delete Lawyer",
            "27. Exit"
        };

        for (int i = 0; i < options.length; i++) {
            final int index = i;
            JButton button = new JButton(options[i]);
            button.setMaximumSize(new Dimension(250, 60));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(new Font("Arial", Font.PLAIN, 14));

            button.addActionListener(e -> {
                choice[0] = index;
                dialog.dispose();
            });
            buttonPanel.add(button);
            if (i < options.length - 1)
                buttonPanel.add(Box.createVerticalStrut(5));
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(instructionLabel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return choice[0];
    }

    private static Case pickCase(Case[] cases, int count) {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No cases available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String[] options = new String[count];
        for (int i = 0; i < count; i++) {
            if (cases[i] != null)
                options[i] = "Case " + cases[i].getCaseNumber() + " - " + cases[i].getTitle();
        }

        int choice = JOptionPane.showOptionDialog(null, "Select a case:", "Choose Case",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);

        if (choice == -1) return null;
        return cases[choice];
    }

    private static Judge pickJudge(Judge[] judges, int count) {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No judges available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String[] options = new String[count];
        for (int i = 0; i < count; i++) {
            if (judges[i] != null)
                options[i] = judges[i].getName();
        }

        int choice = JOptionPane.showOptionDialog(null, "Select a judge:", "Choose Judge",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);

        if (choice == -1) return null;
        return judges[choice];
    }

    private static Lawyer pickLawyer(Lawyer[] lawyers, int count) {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No lawyers available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String[] options = new String[count];
        for (int i = 0; i < count; i++) {
            if (lawyers[i] != null)
                options[i] = lawyers[i].getName() + " [" + lawyers[i].getRole() + "]";
        }

        int choice = JOptionPane.showOptionDialog(null, "Select a lawyer:", "Choose Lawyer",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);

        if (choice == -1) return null;
        return lawyers[choice];
    }
}
