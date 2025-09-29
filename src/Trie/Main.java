package Trie;

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        String[] palabras = {"alas", "alan", "alce", "al"};

        for (int i = 0; i < palabras.length; i++) {
            trie.insertar(palabras[i]);
        }

        for (int i = 0; i < palabras.length; i++) {
            System.out.println("Buscando "+palabras[i]+"... ---- RESULTADO: "+trie.buscar(palabras[i]));
        }

        System.out.println("Buscando alioli... ---- RESULTADO: "+trie.buscar("alioli"));
        System.out.println("Buscando ala... ---- RESULTADO: "+trie.buscar("ala"));

        // sinonimos

        trie.anadirSinonimo("ala", "aleta");
        trie.anadirSinonimo("ala", "helice");
        trie.anadirSinonimo("alce", "ciervo");

        System.out.println("listar sinonimos de ala: " + trie.listarSinonimos("ala").toString());

        System.out.println("listar palabras: " + trie.listarPalabras().toString());
    }
}