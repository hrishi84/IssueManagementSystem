package service;

import model.AgentWorkHistory;
import model.enums.IssueFilterParameters;
import model.enums.IssueStatus;
import model.enums.IssueType;
import model.issues.CustomerIssue;

import java.util.List;

public interface IssueManager {
    String createIssue(final String transactionId, final IssueType issueType, final String subject, final String description, final String userEmailId);
    void addAgent(final String emailId, final String name, List<IssueType> expertiseList);
    void assignIssue(final String issueId);
    void updateIssue(final String issueId, final IssueStatus issueStatus, final String description);

    public List<CustomerIssue> getIssue(final IssueFilterParameters parameter, final String value);
    void resolveIssue(final String issueId, final String comment);
    List<AgentWorkHistory> getAgentWorkHistory();
}
