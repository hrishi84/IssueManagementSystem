package datastore;

import lombok.Getter;
import lombok.Setter;
import model.issues.CustomerIssue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class IssueStore {
    Set<CustomerIssue> issueList;

    Set<String> pendingIssueList;

    public IssueStore() {
        this.issueList = new HashSet<>();
        this.pendingIssueList = new HashSet<>();
    }

    public void addIssue(final CustomerIssue customerIssue) {
        this.issueList.add(customerIssue);
    }


    public void addIssueInPendingList(final String customerIssue) {
        this.pendingIssueList.add(customerIssue);
    }

    public Optional<CustomerIssue> getIssue(final String customerIssue) {
        Optional<CustomerIssue> matchedIssue = issueList.stream()
                .filter(issue -> issue.getIssueId().equals(customerIssue))
                .findFirst();
        return matchedIssue;
    }
}
