package org.example;

public class TopicExample {
    public static void main(String[] args) {
        System.out.println("Starting activemq topic example");


        var producer = new TopicProducer();

        producer.send("Hello World!");

        var consumer = new TopicConsumer();


    }
}
