import datastore.AgentStore;
import datastore.CustomerStore;
import datastore.IssueStore;
import model.AgentWorkHistory;
import model.enums.IssueFilterParameters;
import model.enums.IssueStatus;
import model.enums.IssueType;
import model.issues.CustomerIssue;
import model.users.Agent;
import service.PhonePeCustomerIssueManager;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        IssueStore issueStore = new IssueStore();
        AgentStore agentStore = new AgentStore();
        CustomerStore customerStore = new CustomerStore();


        PhonePeCustomerIssueManager phonePeCustomerIssueManager = new PhonePeCustomerIssueManager(agentStore, customerStore, issueStore);

        final String issue1 = phonePeCustomerIssueManager.createIssue("TR1", IssueType.GOLD_LOAN, "some issue", "rrrr", "user1@gmail.com");
        final String issue2 = phonePeCustomerIssueManager.createIssue("TR2", IssueType.INSURANCE, "failed", "r3rere", "uer2@gmail.com");

        System.out.println("############");
        phonePeCustomerIssueManager.addAgent("agen1@g.com", "A1", Arrays.asList(IssueType.PAYMENT, IssueType.GOLD_LOAN));
        phonePeCustomerIssueManager.addAgent("agen2@g.com", "A2", Arrays.asList(IssueType.INSURANCE, IssueType.GOLD_LOAN));

        System.out.println("############");

        phonePeCustomerIssueManager.assignIssue(issue1);
        phonePeCustomerIssueManager.assignIssue(issue2);



        System.out.println("############");
        List<CustomerIssue> customerIssueList1 = phonePeCustomerIssueManager.getIssue(IssueFilterParameters.ISSUE_TYPE, IssueType.PAYMENT.toString());
        for(CustomerIssue cs: customerIssueList1) {
            System.out.println(cs.getIssueId());
        }
        System.out.println("############");
        List<CustomerIssue> customerIssueList2 = phonePeCustomerIssueManager.getIssue(IssueFilterParameters.USER_EMAIL_ID, "user1@gmail.com");
        for(CustomerIssue cs: customerIssueList2) {
            System.out.println(cs.getIssueId());
        }
        System.out.println("############");System.out.println("############");
        phonePeCustomerIssueManager.updateIssue(issue1, IssueStatus.WORK_IN_PROGRESS, "Pending Deep Dive");
        phonePeCustomerIssueManager.resolveIssue(issue1, "resolved");

        System.out.println("############");
        List<AgentWorkHistory>  history = phonePeCustomerIssueManager.getAgentWorkHistory();
        for(AgentWorkHistory agentWorkHistory: history) {
            System.out.println(agentWorkHistory.getAgentId());
            for (String issues: agentWorkHistory.getAssignedIssueList()) {
                System.out.println(issues);
            }

            System.out.println();
            System.out.println();
        }
        System.out.println("############");





    }
}