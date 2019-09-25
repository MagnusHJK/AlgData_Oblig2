import jdk.jshell.spi.ExecutionControl.*;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        //throw new UnsupportedOperationException("Ikke kodet");
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a);
        if(a.length == 0){
            return;
        }

        int i = 0;
        for(; i < a.length && a[i] == null; i++);
        if(i < a.length) {
            Node<T> p = hode =  new Node<T>(a[i], null, null);
            antall = 1;

            for(i++; i < a.length; i++){
                if(a[i] != null){
                    Node<T> q = p.neste = new Node<T>(a[i], p, null);
                    q.forrige = p;
                    p = q;
                    antall++;
                }
            }
            hale = p;
        }
    }

    public Liste<T> subliste(int fra, int til){
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if(antall <= 0){
            return true;
        }else
            return false;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        Node<T> nyNode = new Node<T>(verdi, null, null);

        if(antall == 0){
            hode = hale = nyNode;
            antall = 1;

            return true;
        }

        nyNode.forrige = hale;
        hale.neste = nyNode;
        hale = nyNode;
        antall++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");
        if(this.antall > 0){
            Node<T> current = hode;

            string.append(current.verdi);
            current = current.neste;

            while(current != null){
                string.append(", ").append(current.verdi);
                current = current.neste;
            }
        }
        string.append("]");

        return string.toString();
    }

    public String omvendtString() {
        StringBuilder string = new StringBuilder("[");
        if(this.antall > 0){
            Node<T> current = hale;

            string.append(current.verdi);
            current = current.forrige;

            while(current != null){
                string.append(", ").append(current.verdi);
                current = current.forrige;
            }
        }
        string.append("]");

        return string.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            throw new UnsupportedOperationException();
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            throw new UnsupportedOperationException();
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args){
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();

        System.out.println(liste.toString() + " " + liste.omvendtString());

        for(int i = 1; i<=3; i++){
            liste.leggInn(i);
            System.out.println(liste.toString() + " " + liste.omvendtString());
        }
    }

} // class DobbeltLenketListe

