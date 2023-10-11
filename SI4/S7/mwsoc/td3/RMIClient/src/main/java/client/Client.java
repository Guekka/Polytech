package client;

import interfaces.Distante;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {
    static void main1() {
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            threads.add(new Thread(Client::main2));
        }

        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    static void main2() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            var distante = (Distante) registry.lookup(Distante.SERVICE_NAME);

            var service = distante.getService();

            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " val reçue : " + service.getVal() + "du Server ");
                service.setVal(2, new ClientIdImpl(Thread.currentThread().getName()));
            }

        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        main1();
    }
}
