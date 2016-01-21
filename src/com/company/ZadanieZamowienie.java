package com.company;

import java.io.*;
import java.util.Scanner;

public class ZadanieZamowienie {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Pozycja p1 = new Pozycja("Chleb", 1, 3.5);
        System.out.println(p1);
        Pozycja p2 = new Pozycja("Cukier", 21, 4);
        System.out.println(p2);

        Zamowienie z = new Zamowienie(20);
        z.dodajPozycje(p1);
        z.dodajPozycje(p2);
        System.out.println(z);

        Zamowienie.zapiszZamowienie(z, "output");
        Zamowienie wczytaneZamowienie = Zamowienie.wczytajZamowienie("output");
        System.out.println("\n----------------------------------");
        System.out.println(wczytaneZamowienie);
    }

    private static class Zamowienie implements Serializable {
        private Pozycja[] pozycje;
        private int ileDodanych;
        private int maksRozmiar;

        public Zamowienie() {
            ileDodanych = 0;
            maksRozmiar = 10;
            pozycje = new Pozycja[maksRozmiar];
        }

        public Zamowienie(int maksRozmiar) {
            ileDodanych = 0;
            this.maksRozmiar = maksRozmiar;
            pozycje = new Pozycja[maksRozmiar];
        }

        public double obliczWartosc() {
            double suma = 0;
            for (int i = 0; i < ileDodanych; i++)
                suma += pozycje[i].obliczWartoscZRabatem();
            return suma;
        }

        public void dodajPozycje(Pozycja p) {
            boolean check = false;
            String name = p.getNazwaTowaru().toLowerCase().trim();
            for (int i = 0; i < ileDodanych; i++) {
                if (name.equals(pozycje[i].getNazwaTowaru().toLowerCase().trim())) {
                    pozycje[i].setIleSztuk(pozycje[i].getIleSztuk() + 1);
                    check = true;
                }
            }
            if (!check) {
                pozycje[ileDodanych] = p;
                ileDodanych++;
            }
        }

        public void edytujPozycje(int indeks) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Podaj proszę nową nazwe towaru: ");
            String nazwa = sc.nextLine();
            System.out.print("Podaj proszę nową cenę towaru:");
            double cena = sc.nextDouble();
            sc.nextLine();
            System.out.print("Podaj proszę nową liczbę sztuk:");
            int liczbaSztuk = sc.nextInt();

            pozycje[indeks].setAll(nazwa, liczbaSztuk, cena);
        }

        public void usunPozycje(int indeks) {
            pozycje[indeks] = null;
        }

        @Override
        public String toString() {
            String s = "\nZamówienie:\n";
            for (int i = 0; i < ileDodanych; i++) {
                int ileSztuk = pozycje[i].getIleSztuk();
                int rabat;
                if ((ileSztuk >= 5) && (ileSztuk <= 10)) {
                    rabat = 5;
                } else if ((ileSztuk > 10) && (ileSztuk <= 20)) {
                    rabat = 10;
                } else if (ileSztuk > 20) {
                    rabat = 15;
                } else {
                    rabat = 0;
                }
                s += String.format("%-20s %-10.2f %-4dszt.\t%-10.2f\tRabat: %-3d%c\n", pozycje[i].getNazwaTowaru(), pozycje[i].getCena(), pozycje[i].getIleSztuk(),
                        pozycje[i].obliczWartoscZRabatem(), rabat, '%');
            }
            s += String.format("\nRazem:  %-8.2f", obliczWartosc());
            return s;
        }

        public static void zapiszZamowienie(Zamowienie z, String nazwaPliku) throws IOException {
            ObjectOutputStream outputStream = null;
            try {
                outputStream = new ObjectOutputStream(new FileOutputStream(nazwaPliku + ".obj"));
                outputStream.writeObject(z);
            } finally {
                if (outputStream != null)
                    outputStream.close();
            }
        }

        public static Zamowienie wczytajZamowienie(String nazwaPliku) throws IOException, ClassNotFoundException {
            ObjectInputStream inputStream = null;
            Zamowienie zamowienie = null;
            try {
                inputStream = new ObjectInputStream(new FileInputStream(nazwaPliku + ".obj"));
                zamowienie = (Zamowienie) inputStream.readObject();
            } finally {
                if (inputStream != null)
                    inputStream.close();
            }
            return zamowienie;
        }
    }

    private static class Pozycja implements Serializable {
        private String nazwaTowaru;
        private int ileSztuk;
        private double cena;

        public Pozycja(String nazwaTowaru, int ileSztuk, double cena) {
            this.nazwaTowaru = nazwaTowaru;
            this.ileSztuk = ileSztuk;
            this.cena = cena;
        }

        public double obliczWartosc() {
            return cena * ileSztuk;
        }

        @Override
        public String toString() {
            return String.format("%-20s %-10.2f %-4dszt.\t%-10.2f", nazwaTowaru, cena, ileSztuk, obliczWartosc());
        }

        public String getNazwaTowaru() {
            return nazwaTowaru;
        }

        public int getIleSztuk() {
            return ileSztuk;
        }

        public double getCena() {
            return cena;
        }

        public Pozycja setIleSztuk(int ileSztuk) {
            this.ileSztuk = ileSztuk;
            return this;
        }

        public Pozycja setCena(double cena) {
            this.cena = cena;
            return this;
        }

        public Pozycja setNazwaTowaru(String nazwaTowaru) {
            this.nazwaTowaru = nazwaTowaru;
            return this;
        }

        public void setAll(String nazwaTowaru, int ileSztuk, double cena) {
            this.nazwaTowaru = nazwaTowaru;
            this.ileSztuk = ileSztuk;
            this.cena = cena;
        }

        public double obliczWartoscZRabatem() {
            double nowaWartosc;
            if ((ileSztuk >= 5) && (ileSztuk <= 10)) {
                nowaWartosc = obliczWartosc() * 0.95;
            } else if ((ileSztuk > 10) && (ileSztuk <= 20)) {
                nowaWartosc = obliczWartosc() * 0.90;
            } else if (ileSztuk > 20) {
                nowaWartosc = obliczWartosc() * 0.85;
            } else {
                nowaWartosc = obliczWartosc();
            }
            return nowaWartosc;
        }
    }
}
