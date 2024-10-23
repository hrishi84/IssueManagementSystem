package model.issues;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.enums.IssueStatus;

@Getter
@Setter
@Builder
public class IssueHistory {
    private IssueStatus issueStatus;
    private String description;
}
