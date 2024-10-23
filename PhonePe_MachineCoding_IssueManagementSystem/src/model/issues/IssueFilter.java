package model.issues;

import lombok.Getter;
import lombok.Setter;
import model.enums.IssueFilterParameters;

@Getter
@Setter
public class IssueFilter {
    private IssueFilterParameters parameter;
    private String value;
}
