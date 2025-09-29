package Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    private NodoTrie raiz;

    public Trie(){
        raiz = new NodoTrie();
    }

    public void insertar(String palabra) {
        int length = palabra.length();
        HashMap<Character, NodoTrie> hijos = raiz.getHijos();
        for (int i = 0; i < length; i++) {
            char c = palabra.charAt(i);
            NodoTrie nodo;
            if (hijos.containsKey(c)) {
                nodo = hijos.get(c);
            } else {
                nodo = new NodoTrie();
                hijos.put(c, nodo);
            }
            hijos = nodo.getHijos();

            if (i == length - 1) {
                nodo.setHoja(true);
            }
        }
    }

    private NodoTrie buscarNodo(String palabra) {
        HashMap<Character, NodoTrie> hijos = raiz.getHijos();
        int longitud = palabra.length();
        boolean sigue = true;

        NodoTrie nodo = null;
        int i = 0;

        while(i < longitud && sigue) {
            char c = palabra.charAt(i);
            if (hijos.containsKey(c)) {
                nodo = hijos.get(c);
                hijos = nodo.getHijos();
                i++;
            } else {
                nodo = null;
                sigue = false;
            }
        }

        return nodo;
    }

    public boolean buscar(String palabra) {
        NodoTrie nodo = buscarNodo(palabra);
        return (nodo != null && nodo.esHoja());
    }

    public boolean anadirSinonimo(String palabra, String sinonimo) {
        NodoTrie nodo = buscarNodo(palabra);

        boolean exito = (nodo != null && nodo.esHoja());
        nodo.anadirSinonimo(sinonimo);

        return exito;
    }

    public List<String> listarSinonimos(String palabra) {
        NodoTrie nodo = buscarNodo(palabra);
        if (nodo != null) {
            return nodo.listarSinonimos();
        }
        return null;
    }

    public List<String> listarPalabras() {
        List<String> palabras = new ArrayList<>();
        listarPalabrasAux(raiz, "", palabras);
        return palabras;
    }

    private void listarPalabrasAux(NodoTrie nodo, String prefijo, List<String> palabras) {
        if (nodo.esHoja()) {
            palabras.add(prefijo);
        }
        for (char c : nodo.getHijos().keySet()) {
            listarPalabrasAux(nodo.getHijos().get(c), prefijo + c, palabras);
        }
    }
}
