package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AgentWorkHistory {
    private String agentId;
    private List<String> assignedIssueList;
}
