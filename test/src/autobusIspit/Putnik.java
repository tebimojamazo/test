package autobusIspit;
public class Putnik {
    private int id;
    private String ime;
    private String prezime;
    private int godiste;

    public Putnik(int id, String ime, String prezime, int godiste) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.godiste = godiste;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public int getGodiste() { return godiste; }
    public void setGodiste(int godiste) { this.godiste = godiste; }

    @Override
    public String toString() {
        return ime + " " + prezime + " (" + godiste + ")";
    }
}
