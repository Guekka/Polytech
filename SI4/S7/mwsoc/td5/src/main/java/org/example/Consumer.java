package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) throws JMSException, InterruptedException {
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
        urgentConsumer.setMessageListener(new FaultyListener());

        // wait 10 seconds for urgent messages
        Thread.sleep(10000);

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

    static class FaultyListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            System.out.println("Simulating a faulty listener");
            try {
                System.out.println("ID: " + message.getJMSMessageID());
                System.out.println("Redelivery count: " + message.getIntProperty("JMSXDeliveryCount"));
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Something went wrong");
        }
    }
}