package edu.java.clients.stackoverflow;

import edu.java.dto.stackoveflow.StackOverFlowResponse;

public interface StackOverflowClient {
    StackOverFlowResponse fetchUpdates(Long questionId);
}
