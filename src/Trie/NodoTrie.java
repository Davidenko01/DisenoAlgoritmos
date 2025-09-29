package Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NodoTrie {
    private HashMap<Character, NodoTrie> hijos = new HashMap<>();
    private boolean esHoja = false;
    private List<String> sinonimos = new ArrayList<>();

    public HashMap<Character, NodoTrie> getHijos() {
        return hijos;
    }

    public void setHijos(HashMap<Character, NodoTrie> hijos) {
        this.hijos = hijos;
    }

    public boolean esHoja() {
        return esHoja;
    }

    public void setHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    public void anadirSinonimo(String sinonimo) {
        sinonimos.add(sinonimo);
    }

    public List<String> listarSinonimos() {
        return sinonimos;
    }
}
