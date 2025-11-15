package autobusIspit;
import java.util.ArrayList;

public abstract class Autobus {
    private int brojSedista;
    private ArrayList<Putnik> listaPutnika;

    public Autobus(int brojSedista) {
        this.brojSedista = brojSedista;
        this.listaPutnika = new ArrayList<>();
    }

    public boolean imaSlobodnihMesta() {
        return listaPutnika.size() < brojSedista;
    }

    public void dodajPutnika(Putnik p) {
        if (imaSlobodnihMesta()) {
            listaPutnika.add(p);
        } else {
            System.out.println("Nema slobodnih mesta u autobusu!");
        }
    }

    public ArrayList<Putnik> getListaPutnika() {
        return listaPutnika;
    }

    public int getBrojSedista() {
        return brojSedista;
    }

    public abstract float zarada();
}
