package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {

    public static void main(String[] args) throws JMSException {
        // create a Connection Factory
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // create a Connection
        Connection connection = factory.createConnection();

        connection.start();

        // create a Session
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        // create the destination Topic
        Destination destination = session.createQueue("SAMPLE_QUEUE");

        // create a MessageProducer for sending messages
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 5; i++) {
            sendMessage(session, producer);
        }

        session.commit();

        for (int i = 0; i < 5; i++) {
            sendMessage(session, producer);
        }

        session.rollback();

        // close the session and connection
        session.close();
        connection.close();
    }

    private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
        // create a text message
        MapMessage message = session.createMapMessage();
        message.setString("name", "John");
        message.setInt("age", 25);

        // randomly mark message as urgent
        message.setBooleanProperty("Urgent", Math.random() > 0.5);

        // send the message
        producer.send(message);

    }
}