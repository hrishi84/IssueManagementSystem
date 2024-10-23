package model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import model.enums.IssueType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Agent extends User {

    private boolean isAvailable;

    private List<IssueType> issueExpertiseList;

    private List<String> assignedIssues;

    public Agent(final String name, final String emailId, final List<IssueType> issueExpertiseList) {
        super.setEmailId(emailId);
        super.setName(name);
        this.assignedIssues = new ArrayList<>();
        this.issueExpertiseList = issueExpertiseList;
        this.isAvailable = Boolean.TRUE;
    }

    public boolean isExpertIn(@NonNull IssueType issueType) {
        return this.issueExpertiseList.contains(issueType);
    }

    public void addIssues(final String issueId) {
        this.assignedIssues.add(issueId);
    }
}
