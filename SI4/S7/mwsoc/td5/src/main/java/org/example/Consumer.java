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
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        // create the destination Topic
        Destination destination = session.createQueue("SAMPLE_QUEUE");

        // create a MessageConsumer for receiving messages, processing only urgent ones
        MessageConsumer urgentConsumer = session.createConsumer(destination);
        urgentConsumer.setMessageListener(new FaultyListener(session));

        // wait 1 seconds for messages
        Thread.sleep(1000);

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
        private final Session session;
        private int count = 0;

        public FaultyListener(Session session) throws JMSException {
            this.session = session;
        }

        @Override
        public void onMessage(Message message) {
            try {
                if (count++ % 2 == 0)
                    session.commit();
                else
                    session.rollback();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}