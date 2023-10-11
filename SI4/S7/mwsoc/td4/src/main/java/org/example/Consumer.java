package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) throws JMSException {
        // create a Connection Factory
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // create a Connection
        Connection connection = factory.createConnection();

        connection.start();

        // create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create the destination Topic
        Destination destination = session.createQueue("SAMPLE_QUEUE");

        // create a MessageConsumer for receiving messages, processing only urgent ones
        MessageConsumer urgentConsumer = session.createConsumer(destination, "Urgent = TRUE");

        // create a MessageConsumer for receiving messages, processing only non-urgent ones
        MessageConsumer nonUrgentConsumer = session.createConsumer(destination, "Urgent = FALSE");

        // empty the queues and exit
        while (true) {
            if (processMessage(urgentConsumer) && processMessage(nonUrgentConsumer)) {
                break;
            }
        }

        // close the session and connection
        session.close();
        connection.close();
    }

    private static boolean processMessage(MessageConsumer consumer) throws JMSException {
        Message message = consumer.receive(1000);
        if (message == null) {
            System.out.println("No more messages in queue with selector: " + consumer.getMessageSelector());
            return true;
        }
        printMessage(message);
        return false;
    }

    private static void printMessage(Message message) throws JMSException {
        if (message instanceof MapMessage mapMessage) {
            System.out.println("Name: " + mapMessage.getString("name"));
            System.out.println("Age: " + mapMessage.getInt("age"));
        } else if (message instanceof TextMessage textMessage) {
            System.out.println("Text: " + textMessage.getText());
        } else {
            System.out.println(message);
        }
    }
}