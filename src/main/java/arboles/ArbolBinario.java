/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

import java.util.ArrayList;

/**
 *
 * @author cdavi
 */
public class ArbolBinario<T> {

    private Nodo raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    protected ArrayList<T> preOrdenNID(Nodo raiz, ArrayList a) {
        if (raiz != null) {
            a.add(raiz.getDato());
            preOrdenNID(raiz.getEnlaceIzquierdo(), a);
            preOrdenNID(raiz.getEnlaceDerecha(), a);
        }
        return a;
    }

    protected ArrayList<T> inOrdenIND(Nodo raiz, ArrayList a) {
        if (raiz != null) {
            inOrdenIND(raiz.getEnlaceIzquierdo(), a);
            a.add(raiz.getDato());
            inOrdenIND(raiz.getEnlaceDerecha(), a);
        }
        return a;
    }

    protected ArrayList<T> postOrdenIDN(Nodo raiz, ArrayList a) {
        if (raiz != null) {
            postOrdenIDN(raiz.getEnlaceIzquierdo(), a);
            postOrdenIDN(raiz.getEnlaceDerecha(), a);
            a.add(raiz.getDato());
        }
        return a;
    }

    public <T extends Comparable> Nodo buscar(T dato) {
        return buscar(dato, raiz);
    }

    private <T extends Comparable> Nodo buscar(T dato, Nodo raiz) {
        if (raiz == null) {
            return null;
        }

        if (dato.compareTo(raiz.getDato()) < 0) {
            return buscar(dato, raiz.getEnlaceIzquierdo());
        }

        if (dato.compareTo(raiz.getDato()) > 0) {
            return buscar(dato, raiz.getEnlaceDerecha());
        }

        return raiz;
    }

    protected ArrayList<T> verHojas(Nodo raiz, ArrayList dato) {
        if (raiz == null) {
            return null;
        }

        if (raiz.getEnlaceIzquierdo() == null && raiz.getEnlaceDerecha() == null) {
            dato.add(raiz.getDato());
        } else {
            verHojas(raiz.getEnlaceIzquierdo(), dato);
            verHojas(raiz.getEnlaceDerecha(), dato);
        }

        return dato;
    }

    protected ArrayList<T> padres(Nodo raiz, ArrayList dato) {

        if (raiz == null) {
            return null;
        }

        if (raiz.getEnlaceIzquierdo() != null || raiz.getEnlaceDerecha() != null) {
            dato.add(raiz.getDato());
            padres(raiz.getEnlaceIzquierdo(), dato);
            padres(raiz.getEnlaceDerecha(), dato);
        }

        return dato;
    }

    protected ArrayList<T> padreDosHijos(Nodo raiz, ArrayList dato) {
        if (raiz == null) {

            return null;
        }

        if (raiz.getEnlaceIzquierdo() != null && raiz.getEnlaceDerecha() != null) {
            dato.add(raiz.getDato());
            padreDosHijos(raiz.getEnlaceIzquierdo(), dato);
            padreDosHijos(raiz.getEnlaceDerecha(), dato);
        }

        return dato;

    }

    protected int peso(Nodo raiz, int dato) {
        int aux = dato;
        if (raiz != null) {
            aux++;
            aux += peso(raiz.getEnlaceIzquierdo(), aux);
            aux += peso(raiz.getEnlaceDerecha(), dato);
        }
        return aux;
    }

    protected int mayor(Nodo raiz) {
        if (raiz.getEnlaceDerecha() == null) {
            return Integer.parseInt(raiz.getDato().toString());
        }

        return mayor(raiz.getEnlaceDerecha());
    }

    protected int sumatoriaHojas(Nodo raiz, int dato) {
        if (raiz == null) {
            return dato;
        }

        if (raiz.getEnlaceDerecha() == null && raiz.getEnlaceIzquierdo() == null) {
            return dato + Integer.parseInt(raiz.getDato().toString());
        } else {
            int sum = dato;
            sum = sumatoriaHojas(raiz.getEnlaceIzquierdo(), sum);
            sum = sumatoriaHojas(raiz.getEnlaceDerecha(), sum);
            return sum;
        }
    }

    public int altura(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }

        if (isHoja(raiz)) {
            return 1;
        } else {
            int ra = (raiz.getEnlaceIzquierdo() == null) ? 0 : altura(raiz.getEnlaceIzquierdo());
            int rb = (raiz.getEnlaceDerecha() == null) ? 0 : altura(raiz.getEnlaceDerecha());

            return Math.max(ra, rb) + 1;
        }

    }

    private boolean isHoja(Nodo raiz) {
        return (raiz.getEnlaceIzquierdo() == null)
                && (raiz.getEnlaceDerecha() == null);
    }

}
