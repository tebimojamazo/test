package autobusIspit;
public class Redovni extends Autobus {
    private int cenaKarte;

    public Redovni(int brojSedista, int cenaKarte) {
        super(brojSedista);
        this.cenaKarte = cenaKarte;
    }

    public int getCenaKarte() { return cenaKarte; }
    public void setCenaKarte(int cenaKarte) { this.cenaKarte = cenaKarte; }

    @Override
    public float zarada() {
        float suma = 0;
        for (Putnik p : getListaPutnika()) {
            int starost = 2025 - p.getGodiste();
            if (starost < 7 || starost > 65)
                suma += cenaKarte / 2.0;
            else
                suma += cenaKarte;
        }
        return suma;
    }
}
