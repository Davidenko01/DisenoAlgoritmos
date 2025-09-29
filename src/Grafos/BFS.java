package Grafos;
import java.util.*;
public class BFS {

        private int V;
        private LinkedList<Integer> adj[];

        BFS(int v) {
            V = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i)
                adj[i] = new LinkedList();
        }

        void addEdge(int v, int w) {
            adj[v].add(w);
        }

        void BFS(int s) {
            boolean visited[] = new boolean[V];
            LinkedList<Integer> queue = new LinkedList<>();

            visited[s] = true;
            queue.add(s);

            while (queue.size() != 0) {
                s = queue.poll();
                System.out.print((s+1)+ " ");

                Iterator<Integer> it = adj[s].listIterator();
                while (it.hasNext()) {
                    int n = it.next();
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }
        }

        public static void main(String args[]) {
            BFS g = new BFS(9);

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

            g.BFS(2);
        }
    }

