package model.issues;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import model.enums.IssueStatus;
import model.enums.IssueType;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Issue {
    @NonNull
    private String issueId;
    @NonNull
    private IssueType issueType;
    @NonNull
    private String subject;
    @NonNull
    private String description;
    @NonNull
    private String createdByUserId;
    private String assignedAgentId;
    @NonNull
    private IssueStatus currentIssueStatus;
    private List<IssueHistory> issueUpdateHistory;

    public void updateIssueStatus(@NonNull final IssueStatus issueStatus,
                                  @NonNull final String comment) {
        this.currentIssueStatus = issueStatus;
        this.issueUpdateHistory.add(IssueHistory.builder()
                        .issueStatus(this.currentIssueStatus)
                        .description(comment)
                .build());
    }
}
