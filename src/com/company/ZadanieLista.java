package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ZadanieLista {

    public static void main(String[] args) {
        final int N = 10;
        Lista lista = new Lista(N);
        for (int i = 0; i < N / 2; ++i) {
            lista.dodajeElement((1 << i));
        }
        lista.dodajeElement(2);
        lista.dodajeElement(8);
        lista.pisz();
        lista.usunPierwszy(2);
        lista.pisz();
        for (int i = 0; i < N / 2; ++i) {
            lista.dodajeElement((1 << i));
        }
        lista.pisz();
        System.out.println("Po usunieciu powtórzeń:");
        lista.usunPowtorzenia();
        lista.pisz();
        System.out.println("Po odwróceniu:");
        lista.odwroc();
        lista.pisz();
    }

     private static class Lista {
        int[] liczby;
        int pojemnosc;
        int rozmiar;

        public Lista(int pojemnosc) {
            this.pojemnosc = pojemnosc;
            liczby = new int[pojemnosc];
            rozmiar = 0;
        }

        public void dodajeElement(int liczba) {
            if (rozmiar < pojemnosc) {
                liczby[rozmiar] = liczba;
                rozmiar++;
            } else {
                System.out.println("Nie można dodać liczby, tablica jest pełna!");
            }
        }

        public void znajdz(int szukanaLiczba) {
            boolean exist = false;
            for (int i = 0; i < liczby.length; i++) {
                if (szukanaLiczba == liczby[i]) {
                    System.out.println("Liczba: " + szukanaLiczba + " indeks: " + i);
                    exist = true;
                }
                if (!exist)
                    System.out.println("Liczba: " + szukanaLiczba + " indeks: -1");
            }
        }

        public void pisz() {
            System.out.println("Lista:");
            System.out.println("\tPojemność: " + pojemnosc);
            System.out.println("\tRozmiar: " + rozmiar);
            System.out.print("\tElementy: ");
            for (int i = 0; i < rozmiar; i++)
                System.out.print(liczby[i] + " ");
            System.out.println();
        }

        public void usunPierwszy(int liczbaDoUsuniecia) {
            boolean done = false;
            for (int i = 0; !done; i++) {
                if (liczby[i] == liczbaDoUsuniecia) {
                    int tmpIndex = i;
                    for (int j = (i + 1); j <= rozmiar; j++, tmpIndex++) {
                        liczby[tmpIndex] = liczby[j];
                    }
                    rozmiar--;
                    done = true;
                }
            }
        }

        public void usunPowtorzenia() {
            for (int i = 0; i < liczby.length; i++) {
                int tmp1 = liczby[i];
                for (int j = (i + 1); j < rozmiar; j++) {
                    if (liczby[j] == tmp1) {
                        int tmpIndex = j;
                        for (int k = (j + 1); k <= rozmiar; k++, tmpIndex++) {
                            liczby[tmpIndex] = liczby[k];
                            rozmiar--;
                        }
                    }
                }
            }
            int[] tmpArray = new int[rozmiar];
            for (int i = 0; i < rozmiar; i++)
                tmpArray[i] = liczby[i];
            liczby = tmpArray;
        }

        public void odwroc() {
            int[] tmpArray = new int[liczby.length];
            for (int i = liczby.length - 1, j = 0; i >= 0; i--, j++) {
                tmpArray[j] = liczby[i];
            }
            liczby = tmpArray;
        }

        public void zapiszDoPliku(String nazwaPliku) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nazwaPliku + ".txt"));
            for (int i = 0; i < rozmiar; i++) {
                writer.write(liczby[i] + " ");
            }
            writer.close();
        }
    }
}
