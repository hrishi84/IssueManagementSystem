package service;

import datastore.AgentStore;
import datastore.CustomerStore;
import datastore.IssueStore;
import model.AgentWorkHistory;
import model.enums.IssueFilterParameters;
import model.enums.IssueStatus;
import model.enums.IssueType;
import model.issues.CustomerIssue;
import model.users.Agent;
import model.users.Customer;

import java.util.*;
import java.util.stream.Collectors;

public class PhonePeCustomerIssueManager implements IssueManager {

    private AgentStore agentStore;
    private CustomerStore customerStore;
    private IssueStore issueStore;

    public PhonePeCustomerIssueManager(final AgentStore agentStore, final CustomerStore customerStore, final IssueStore issueStore) {
        this.agentStore = agentStore;
        this.customerStore = customerStore;
        this.issueStore = issueStore;
    }
    @Override
    public String createIssue(final String transactionId, final IssueType issueType, final String subject, final String description, final String userEmailId) {
        final CustomerIssue customerIssue = new CustomerIssue(issueType, subject, description, transactionId, userEmailId);
        issueStore.addIssue(customerIssue);
        customerStore.addCustomer(new Customer("dummyName", userEmailId));
        System.out.printf("Issue Created with issueId %s\n", customerIssue.getIssueId());
        return customerIssue.getIssueId();
    }

    @Override
    public void addAgent(final String emailId, final String name, List<IssueType> expertiseList) {
        final Agent agent = new Agent(name, emailId, expertiseList);
        agentStore.addAgent(agent);
        System.out.printf("Agent Created %s\n", agent.getName());
    }

    @Override
    public void assignIssue(final String issueId) {
        if(validateAssignIssueRequest(issueId)) {
            final CustomerIssue issue = issueStore.getIssue(issueId).get();
            Optional<Agent> assignedAgent = getAvailableAgentWithAppropriateExpertise(issue.getIssueType());

            if(assignedAgent.isEmpty()) {
                System.out.println("No Agents available with given expertise, issue in pending queue");
                issueStore.addIssueInPendingList(issueId);
                return;
            }

            issue.setAssignedAgentId(assignedAgent.get().getName());
            issue.updateIssueStatus(IssueStatus.ASSIGNED, "Assigned to AGent " + assignedAgent.get().getName());

            issueStore.addIssue(issue);
            assignedAgent.get().addIssues(issueId);

            agentStore.updateAgentAvailabilityStatus(assignedAgent.get().getName(), Boolean.FALSE);

            System.out.printf("Assigned Issue %s to Agent %s%n", issueId, assignedAgent.get().getName());
        }
    }

    @Override
    public List<CustomerIssue> getIssue(final IssueFilterParameters parameter, final String value) {
        Set<CustomerIssue> issueList = issueStore.getIssueList();
        switch (parameter) {
            case ISSUE_TYPE:
                return issueList.stream()
                        .filter(issue -> issue.getIssueType().toString().equals(value))
                        .collect(Collectors.toList());
            case USER_EMAIL_ID:
                return issueList.stream()
                        .filter(issue -> issue.getCreatedByUserId().equals(value))
                        .collect(Collectors.toList());
            case ISSUE_RESOLUTION_STATE:
                return issueList.stream()
                        .filter(issue -> issue.getCurrentIssueStatus().toString().equals(value))
                        .collect(Collectors.toList());
            default:
                // Handle unknown parameter
                throw new IllegalArgumentException("Unsupported parameter: " + parameter);
        }
    }

    @Override
    public void updateIssue(final String issueId, final IssueStatus issueStatus, final String description) {
        CustomerIssue issue = issueStore.getIssue(issueId).get();
        issue.updateIssueStatus(issueStatus, description);
        issueStore.addIssue(issue);

        System.out.printf("Updated Issue %s%n", issueId);
    }

    @Override
    public void resolveIssue(final String issueId, final String comment) {
        CustomerIssue issue = issueStore.getIssue(issueId).get();
        updateIssue(issueId, IssueStatus.RESOLVED, comment);
        agentStore.updateAgentAvailabilityStatus(issue.getAssignedAgentId(), Boolean.TRUE);

        if(!issueStore.getPendingIssueList().isEmpty()) {
            for(String id: issueStore.getPendingIssueList()) {
                assignIssue(id);
            }
        }
    }

    @Override
    public List<AgentWorkHistory> getAgentWorkHistory() {
        List<AgentWorkHistory> agentWorkHistories = new ArrayList<>();
        Set<Agent> agentList = agentStore.getAgentList();
        Set<CustomerIssue> customerIssueList = issueStore.getIssueList();

        Map<String, List<String>> agentIssueMap = customerIssueList.stream()
                .collect(Collectors.groupingBy(CustomerIssue::getAssignedAgentId,
                        Collectors.mapping(CustomerIssue::getIssueId, Collectors.toList())));

        System.out.println(agentIssueMap);

        for(Agent agent: agentList) {
            String agentId = agent.getName();

            if(agentIssueMap.containsKey(agentId)) {
                agentWorkHistories.add(AgentWorkHistory.builder()
                                .agentId(agentId)
                                .assignedIssueList(agentIssueMap.get(agentId))
                        .build());
            }

        }


        return agentWorkHistories;
    }

    private Optional<Agent> getAvailableAgentWithAppropriateExpertise(final IssueType issueType) {

        for (Agent agentEntry : this.agentStore.getAgentList()) {
            if (agentEntry.isAvailable() && agentEntry.isExpertIn(issueType)) {
                return Optional.of(agentEntry);
            }
        }

        return Optional.empty();
    }

    private boolean validateAssignIssueRequest(final String issueId) {
        if(agentStore.getAvailableAgentList().isEmpty()) {
            System.out.println("No Agent available for the issue, issue in pending queue");
            return false;
        }
        if(issueStore.getIssue(issueId).isEmpty()) {
            System.out.println("Incorrect IssueId given Issue not found in records");
            return false;
        }
        return true;
    }


}
