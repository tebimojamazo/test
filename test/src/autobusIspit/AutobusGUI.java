package autobusIspit;

import java.awt.EventQueue;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutobusGUI {

    private JFrame frame;
    private ArrayList<Putnik> listaPutnika = new ArrayList<>();
    private ArrayList<Putnik> redovniPutnici = new ArrayList<>();
    private ArrayList<Putnik> iznajmljeniPutnici = new ArrayList<>();

    private DefaultListModel<String> modelLista = new DefaultListModel<>();
    private JTextArea textArea;
    private JTextField txtUser, txtPass;
    private JPanel panelLogin, panelGlavni;
    private JList<String> list = new JList<>();
    private JTextField txtSedista;
    private Redovni redovni;
    private Iznajmljeni iznajmljeni;

    private final String FAJL_PUTNICI = "putnici.txt";
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AutobusGUI window = new AutobusGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AutobusGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 550, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0, 0));

        panelLogin = new JPanel();
        panelGlavni = new JPanel();

        frame.getContentPane().add(panelLogin, "login");
        frame.getContentPane().add(panelGlavni, "main");

        panelLogin.setLayout(null);
        panelGlavni.setLayout(null);

        // ---------------- LOGIN PANEL ----------------
        JLabel lblUser = new JLabel("Korisničko ime:");
        lblUser.setBounds(60, 60, 120, 20);
        panelLogin.add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(180, 60, 120, 20);
        panelLogin.add(txtUser);

        JLabel lblPass = new JLabel("Lozinka:");
        lblPass.setBounds(60, 100, 120, 20);
        panelLogin.add(lblPass);

        txtPass = new JTextField();
        txtPass.setBounds(180, 100, 120, 20);
        panelLogin.add(txtPass);

        JButton btnLogin = new JButton("Uloguj se");
        btnLogin.setBounds(180, 140, 120, 25);
        panelLogin.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = txtPass.getText().trim();
            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                JOptionPane.showMessageDialog(null, "Uspešno logovanje!");
                panelLogin.setVisible(false);
                panelGlavni.setVisible(true);
                ucitajPutnikeIzFajla();
            } else {
                JOptionPane.showMessageDialog(null, "Pogrešno korisničko ime ili lozinka!");
            }
        });

        // ---------------- MAIN PANEL ----------------
        JLabel lblSedista = new JLabel("Broj sedišta:");
        lblSedista.setBounds(20, 10, 100, 20);
        panelGlavni.add(lblSedista);

        txtSedista = new JTextField();
        txtSedista.setBounds(100, 10, 60, 20);
        panelGlavni.add(txtSedista);

        JButton btnKreirajAutobuse = new JButton("Kreiraj autobuse");
        btnKreirajAutobuse.setBounds(180, 10, 140, 25);
        panelGlavni.add(btnKreirajAutobuse);

        btnKreirajAutobuse.addActionListener(e -> {
            try {
                int sedista = Integer.parseInt(txtSedista.getText());
                redovni = new Redovni(sedista, 500);
                iznajmljeni = new Iznajmljeni(sedista, 200, 100);
                JOptionPane.showMessageDialog(null, "Autobusi kreirani!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unesite validan broj sedišta!");
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 160, 250);
        panelGlavni.add(scrollPane);

        list.setModel(modelLista);
        scrollPane.setViewportView(list);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(280, 50, 230, 180);
        panelGlavni.add(scrollPane2);

        textArea = new JTextArea();
        scrollPane2.setViewportView(textArea);

        JButton btnDodajUFajl = new JButton("Dodaj putnika");
        btnDodajUFajl.setBounds(20, 310, 140, 25);
        panelGlavni.add(btnDodajUFajl);

        JButton btnUcitaj = new JButton("Učitaj iz fajla");
        btnUcitaj.setBounds(180, 310, 140, 25);
        panelGlavni.add(btnUcitaj);

        JButton btnDodajR = new JButton("Dodaj u Redovni");
        btnDodajR.setBounds(200, 80, 150, 25);
        panelGlavni.add(btnDodajR);

        JButton btnDodajI = new JButton("Dodaj u Iznajmljeni");
        btnDodajI.setBounds(200, 115, 150, 25);
        panelGlavni.add(btnDodajI);

        JButton btnZaradaR = new JButton("Zarada Redovni");
        btnZaradaR.setBounds(200, 150, 150, 25);
        panelGlavni.add(btnZaradaR);

        JButton btnZaradaI = new JButton("Zarada Iznajmljeni");
        btnZaradaI.setBounds(200, 185, 150, 25);
        panelGlavni.add(btnZaradaI);

        JButton btnNazad = new JButton("Odjava");
        btnNazad.setBounds(400, 310, 110, 25);
        panelGlavni.add(btnNazad);

        JLabel lblPonuda = new JLabel("Putnici");
        lblPonuda.setBounds(70, 35, 60, 15);
        panelGlavni.add(lblPonuda);

        JLabel lblInfo = new JLabel("Informacije");
        lblInfo.setBounds(350, 35, 100, 15);
        panelGlavni.add(lblInfo);

        // ---------------- LISTENERS ----------------

        btnDodajUFajl.addActionListener(e -> {
            String ime = JOptionPane.showInputDialog("Unesi ime putnika:");
            String prezime = JOptionPane.showInputDialog("Unesi prezime:");
            String godStr = JOptionPane.showInputDialog("Unesi godište:");

            try {
                int godiste = Integer.parseInt(godStr);
                int id = listaPutnika.size() + 1;
                Putnik p = new Putnik(id, ime, prezime, godiste);
                listaPutnika.add(p);
                sacuvajPutnika(p);
                modelLista.addElement(p.toString());
                JOptionPane.showMessageDialog(null, "Putnik dodat!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Greška pri unosu!");
            }
        });

        btnUcitaj.addActionListener(e -> ucitajPutnikeIzFajla());

        btnDodajR.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1 && redovni != null) {
                Putnik p = listaPutnika.get(index);
                redovni.dodajPutnika(p);
                textArea.append("Dodat u redovni: " + p + "\n");
            }
        });

        btnDodajI.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1 && iznajmljeni != null) {
                Putnik p = listaPutnika.get(index);
                iznajmljeni.dodajPutnika(p);
                textArea.append("Dodat u iznajmljeni: " + p + "\n");
            }
        });

        btnZaradaR.addActionListener(e -> {
            if (redovni != null)
                JOptionPane.showMessageDialog(null, "Zarada redovnog: " + redovni.zarada() + " din");
        });

        btnZaradaI.addActionListener(e -> {
            if (iznajmljeni != null)
                JOptionPane.showMessageDialog(null, "Zarada iznajmljenog: " + iznajmljeni.zarada() + " din");
        });

        btnNazad.addActionListener(e -> {
            panelGlavni.setVisible(false);
            panelLogin.setVisible(true);
        });
    }

    // -------------------- FAJL METODE --------------------

    private void ucitajPutnikeIzFajla() {
        listaPutnika.clear();
        modelLista.clear();
        File fajl = new File(FAJL_PUTNICI);
        try {
            if (!fajl.exists()) fajl.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(fajl));
            String linija;
            while ((linija = br.readLine()) != null) {
                String[] delovi = linija.split(",");
                if (delovi.length == 4) {
                    int id = Integer.parseInt(delovi[0]);
                    String ime = delovi[1];
                    String prezime = delovi[2];
                    int god = Integer.parseInt(delovi[3]);
                    Putnik p = new Putnik(id, ime, prezime, god);
                    listaPutnika.add(p);
                    modelLista.addElement(p.toString());
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sacuvajPutnika(Putnik p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FAJL_PUTNICI, true))) {
            bw.write(p.getId() + "," + p.getIme() + "," + p.getPrezime() + "," + p.getGodiste());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
