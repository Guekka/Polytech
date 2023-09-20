package interfaces;

import java.io.Serializable;

public class Resultat implements Serializable {
    private String infoCB;

    public Resultat(String infoCB) {
        this.infoCB = infoCB;

        System.out.println("Resultat created!");
    }

    public String getInfoCB() {
        return infoCB;
    }

    public void setInfoCB(String infoCB) {
        this.infoCB = infoCB;
    }
    
    @Override
    public String toString() {
        return "Resultat{" +
                "infoCB='" + infoCB + '\'' +
                '}';
    }
}
