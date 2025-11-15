package autobusIspit;
public class Iznajmljeni extends Autobus {
    private int kilometraza;
    private int cenaPoKilometru;

    public Iznajmljeni(int brojSedista, int kilometraza, int cenaPoKilometru) {
        super(brojSedista);
        this.kilometraza = kilometraza;
        this.cenaPoKilometru = cenaPoKilometru;
    }

    public int getKilometraza() { return kilometraza; }
    public void setKilometraza(int kilometraza) { this.kilometraza = kilometraza; }
    public int getCenaPoKilometru() { return cenaPoKilometru; }
    public void setCenaPoKilometru(int cenaPoKilometru) { this.cenaPoKilometru = cenaPoKilometru; }

    @Override
    public float zarada() {
        float zarada = kilometraza * cenaPoKilometru;
        if (getListaPutnika().size() > 30)
            zarada *= 1.2;
        return zarada;
    }
}
