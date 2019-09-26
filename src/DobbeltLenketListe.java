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


    private Node<T> finnNode(int indeks){
        Node<T> current = hode;
        Node<T> funnetNode = new Node<>(null,null,null);
        int listeIndeks = 0;

        //Start fra hode, indeks er på nedre del
        if(indeks < antall / 2 && indeks >= 0){
            while(current != null){
                if(indeks == listeIndeks){
                    funnetNode = current;
                    break;
                }else{
                    listeIndeks++;
                    current = current.neste;
                }
            }
        }
        //Start fra hale
        else if(indeks > antall / 2 && indeks <= antall){
            current = hale;
            listeIndeks = antall - 1;

            while(current != null){
                if(indeks == listeIndeks){
                    funnetNode = current;
                    break;
                }else{
                    listeIndeks--;
                    current = current.forrige;
                }
            }
        }
        return funnetNode;
    }



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

    private static void fraTilKontroll(int antall, int fra, int til) {
        if(fra < 0) {
            throw new IndexOutOfBoundsException("Fra er negativ");
        } else if(fra > til) {
            throw new IndexOutOfBoundsException("Fra er større enn Til");
        } else if(til > antall) {
            throw new IndexOutOfBoundsException("Til er større enn lengden til listen");
        }
    }

    public Liste<T> subliste(int fra, int til){
        fraTilKontroll(antall,fra,til);

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();
        Node<T> current = new Node<T>(null,null,null);

        current = hode;
        int teller = 0;

        while(current != null) {

            while(teller >= fra && teller < til) {
                liste.leggInn(current.verdi);
                teller++;
            }

            current = current.neste;
            teller++;



        }


        return liste;
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
            endringer = 1;

            return true;
        }

        nyNode.forrige = hale;
        hale.neste = nyNode;
        hale = nyNode;
        antall++;
        endringer++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Node<T> nyNode = new Node<>(verdi, null, null);

        if(indeks <= antall && indeks >= 0){

        }

    }

    @Override
    public boolean inneholder(T verdi) {
        if(indeksTil(verdi) == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public void indeksKontroll(int indeks, boolean leggInn) {
        if (indeks < 0 || (leggInn ? indeks > antall() : indeks >= antall())) {
            throw new IndexOutOfBoundsException(melding(indeks));
        }
    }

    @Override
    public int indeksTil(T verdi) {
        Node<T> q = hode;
        int antall = 0;
        boolean funnet = false;

        while(q != null) {

            if(q.verdi.equals(verdi)){
                funnet = true;
                break;
            } else {
                q = q.neste;
                antall++;
            }
        }

        if(funnet == true) {
            return antall;
        } else {
            return -1;
        }



        //throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);

        indeksKontroll(indeks,false);
        Node<T> node = new Node<T>(null);
        T gammelVerdi;

        gammelVerdi = finnNode(indeks).verdi;
        finnNode(indeks).verdi = nyverdi;

        endringer++;

        return gammelVerdi;
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

        for(int i = 1; i<=3; i++){
            liste.leggInn(i);
        }

        System.out.println(liste.toString());
        System.out.println(liste.hent(2));
        System.out.println("Gammel verdi: " + liste.oppdater(2, 5));
        System.out.println("Ny verdi: " + liste.hent(2));

    }

} // class DobbeltLenketListe

