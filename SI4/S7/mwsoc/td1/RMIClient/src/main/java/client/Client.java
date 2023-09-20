package client;

import interfaces.Distante;
import interfaces.Resultat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            Distante distante = (Distante) registry.lookup("Distante");

            long start = System.currentTimeMillis();
            distante.echo();
            long end = System.currentTimeMillis();
            System.out.println("Time taken to call echo: " + (end - start) + "ms");

            Resultat res = distante.getRes();
            System.out.println("Resultat: " + res);
            System.out.println("Info CB: " + res.getInfoCB());

        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }
}
