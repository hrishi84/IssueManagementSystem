package model.issues;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import model.enums.IssueStatus;
import model.enums.IssueType;
import model.issues.Issue;

import java.util.ArrayList;
import java.util.UUID;


@Getter
@Setter
public class CustomerIssue extends Issue {
    private String transactionId;

    public void assignIssue(final String assignedAgentId) {
        super.setAssignedAgentId(assignedAgentId);
    }

    public CustomerIssue(@NonNull final IssueType issueType,
                         @NonNull final String subject,
                         @NonNull final String description,
                         @NonNull final String transactionId,
                         @NonNull final String userEmailId) {
        super.setIssueId(UUID.randomUUID().toString());
        super.setIssueType(issueType);
        super.setSubject(subject);
        super.setDescription(description);
        super.setCreatedByUserId(userEmailId);
        this.transactionId = transactionId;
        super.setIssueUpdateHistory(new ArrayList<>());
        super.updateIssueStatus(IssueStatus.CREATED, IssueStatus.CREATED.toString());
    }
}
