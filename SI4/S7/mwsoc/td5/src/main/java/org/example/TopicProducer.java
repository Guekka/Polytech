package org.example;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class TopicProducer {
    private final String topicName = "SAMPLE_TOPIC";
    private final Session session;
    private final MessageProducer producer;

    public TopicProducer(Session session) throws JMSException {
        this.session = session;
        this.producer = session.createProducer(session.createTopic(topicName));
    }

    public void send(String s) throws JMSException {
        producer.send(session.createTextMessage(s));
    }
}
