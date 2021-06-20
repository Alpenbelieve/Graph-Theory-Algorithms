package Algorithm;

import DataStructure.Graph;

import java.util.ArrayList;
import java.util.Arrays;

public final class CycleDetection {
    public static boolean isCycleExist(Graph graph) {
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i])
                if (_dfs_cycle_detect(graph, i, i, visited))
                    return true;
        }
        return false;
    }

    //返回图中的一个环，若有多个只返回一个，可多次调用，直到返回值为空
    public static ArrayList<Integer> cyclePathIfExist(Graph graph) {
        ArrayList<Integer> result = new ArrayList<>();
        if (!isCycleExist(graph))
            return result;

        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                if (_dfs_cycle_path(graph, i, i, visited, result)) {
                    return result;
                }
            }
        }
        return result;
    }

    private static boolean _dfs_cycle_detect(Graph graph, int node, int parent, boolean[] visited) {
        visited[node] = true;
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n]) {
                if (_dfs_cycle_detect(graph, n, node, visited))
                    return true;
            } else if (n != parent) //visited[n]==true
                return true;
        }
        return false;
    }

    private static boolean _dfs_cycle_path(Graph graph, int node, int parent, boolean[] visited, ArrayList<Integer> arrayList) {
        visited[node] = true;
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n]) {
                if (_dfs_cycle_path(graph, n, node, visited, arrayList)) {
                    arrayList.add(n);
                    return true;
                }
            } else if (n != parent) {
                arrayList.add(n);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Graph graph = Graph.generateGraphFromFile("graphinfo.txt");
        System.out.println(cyclePathIfExist(graph));
    }
}











