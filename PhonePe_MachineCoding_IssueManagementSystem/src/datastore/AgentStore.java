package datastore;

import lombok.Getter;
import lombok.Setter;
import model.issues.CustomerIssue;
import model.users.Agent;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class AgentStore {
    private Set<Agent> agentList;
    private Set<String> availableAgentList;

    public AgentStore() {
        this.agentList = new HashSet<>();
        this.availableAgentList = new HashSet<>();
    }

    public void addAgent(final Agent agent) {
        this.agentList.add(agent);
        this.availableAgentList.add(agent.getName());
    }

    public Agent getAgent(final String agentName) {
        Optional<Agent> matchedAgent = this.agentList.stream()
                .filter(agent -> agent.getName().equals(agentName))
                .findFirst();
        return matchedAgent.get();
    }

    public void updateAgentAvailabilityStatus(final String agentId, final Boolean availableStatus) {
        final Agent agent = getAgent(agentId);
        if(availableStatus) {
            agent.setAvailable(Boolean.TRUE);
            this.availableAgentList.add(agentId);
        } else {
            agent.setAvailable(Boolean.FALSE);
            this.availableAgentList.remove(agentId);
        }

        this.agentList.add(agent);
    }

}
