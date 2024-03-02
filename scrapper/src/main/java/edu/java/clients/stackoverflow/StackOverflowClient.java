package edu.java.clients.stackoverflow;

import edu.java.response.stackoveflow.StackOverFlowResponse;

public interface StackOverflowClient {
    StackOverFlowResponse fetchUpdates(Long questionId);
}
