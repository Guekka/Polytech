package org.example;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

public class TopicConsumer {
    private final Session session;

    public TopicConsumer(Session session) {
        this.session = session;
    }

    public void addListener() {
        session.createConsumer(session.createTopic("SAMPLE_TOPIC")).setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("Received message: " + message);
            }
        }
    }
}
