package Grafos;
import java.util.*;

public class DFS {
    private int V;
    private LinkedList<Integer> adj[];

    // Constructor
    DFS(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }


    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    void DFS(int v) {
        boolean visited[] = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        stack.push(v);

        while (!stack.isEmpty()) {
            v = stack.pop();

            if (!visited[v]) {
                System.out.print(v+1 + " ");
                visited[v] = true;
            }

            Iterator<Integer> it = adj[v].iterator();
            while (it.hasNext()) {
                int n = it.next();
                if (!visited[n])
                    stack.push(n);
            }
        }
    }

    public static void main(String args[]) {
        DFS g = new DFS(9);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 5);
        g.addEdge(2, 5);
        g.addEdge(2, 6);
        g.addEdge(2, 7);
        g.addEdge(3, 6);
        g.addEdge(4, 0);
        g.addEdge(5, 1);
        g.addEdge(5, 3);
        g.addEdge(6, 1);
        g.addEdge(7, 8);
        g.addEdge(8, 5);

        g.DFS(6);
    }
}
