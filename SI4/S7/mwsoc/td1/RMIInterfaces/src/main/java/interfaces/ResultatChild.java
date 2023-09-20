package interfaces;

public class ResultatChild extends Resultat {
    private final int pin;

    public ResultatChild(String infoCB) {
        super(infoCB);
        this.pin = 1234;
    }

    @Override
    public String getInfoCB() {
        // gets the value, and transform it in lowercase letters to which it concatenates the codePIN
        return super.getInfoCB().toLowerCase() + this.pin;
    }

    @Override
    public void setInfoCB(String infoCB) {
        super.setInfoCB(infoCB.toUpperCase());
    }

    @Override
    public String toString() {
        return "ResultatChild{" +
                "infoCB='" + super.getInfoCB() + '\'' +
                '}';
    }


}
