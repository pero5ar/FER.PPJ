package lab2.models;

/**
 * Created by CHOPPER on 11/19/2016.
 */
public class AnalizerInput {

    private String klasa;
    private int redak;
    private String identifikator;

    public AnalizerInput(String klasa, int redak, String identifikator) {
        this.klasa = klasa;
        this.redak = redak;
        this.identifikator = identifikator;
    }

    public String getKlasa() {
        return klasa;
    }

    public void setKlasa(String klasa) {
        this.klasa = klasa;
    }

    public int getRedak() {
        return redak;
    }

    public void setRedak(int redak) {
        this.redak = redak;
    }

    public String getIdentifikator() {
        return identifikator;
    }

    public void setIdentifikator(String identifikator) {
        this.identifikator = identifikator;
    }
}
