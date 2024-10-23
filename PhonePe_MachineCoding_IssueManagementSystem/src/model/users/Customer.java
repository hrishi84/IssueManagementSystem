package model.users;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import model.users.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Customer extends User {

    private List<String> issueList;

    public Customer(@NonNull final String name, @NonNull final String emailId) {
        super.setName(name);
        this.setEmailId(emailId);
        this.issueList = new ArrayList<>();
    }

    public void addIssueToUserIssueList(final String issueId) {
        this.issueList.add(issueId);
    }

}
