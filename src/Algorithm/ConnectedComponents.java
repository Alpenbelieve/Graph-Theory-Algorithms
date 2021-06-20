package Algorithm;

import DataStructure.Graph;

import java.util.ArrayList;
import java.util.Arrays;

public final class ConnectedComponents {

    public static int connectedComponentsNum(Graph graph) {
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        ArrayList<Integer> pre = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (!visited[i]) {
                counter++;
                _dfs_pre(i, graph, visited, pre);
            }
        }
        return counter;
    }


    private static void _dfs_pre(int node, Graph graph, boolean[] visited, ArrayList<Integer> pre) {
        visited[node] = true;
        pre.add(node);
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n])
                _dfs_pre(n, graph, visited, pre);
        }
    }


    public static ArrayList<ArrayList<Integer>> connectedComponents(Graph graph) {
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> pre = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (!visited[i]) {
                result.add(new ArrayList<>());
                _dfs_components(i, graph, visited, pre, result);
            }
        }
        return result;
    }

    //返回一个visited的int非负数组，数组中蕴含着连通分量的信息
    public static int[] connectedComponentsArrayInfo(Graph graph) {
        int[] visited = new int[graph.getNodeNum()];
        Arrays.fill(visited, -1);
        int counter = 0;
        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (visited[i] == -1) {
                _dfs_components_arr(i, graph, visited, counter);
                counter++;
            }
        }
        return visited;
    }

    public static boolean isConnected(Graph graph, int nodeA, int nodeB) {
        graph.validateNode(nodeA);
        graph.validateNode(nodeB);
        int[] visited = connectedComponentsArrayInfo(graph);
        return visited[nodeA] == visited[nodeB];
    }


    private static void _dfs_components(int node, Graph graph, boolean[] visited, ArrayList<Integer> pre, ArrayList<ArrayList<Integer>> result) {
        visited[node] = true;
        result.get(result.size() - 1).add(node);
        pre.add(node);
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n])
                _dfs_components(n, graph, visited, pre, result);
        }
    }


    private static void _dfs_components_arr(int node, Graph graph, int[] visited, int counter) {
        visited[node] = counter;
        for (int n : graph.adjacentNodes(node)) {
            if (visited[n] == -1)
                _dfs_components_arr(n, graph, visited, counter);
        }
    }

    public static void main(String[] args) {
        Graph graph = Graph.generateGraphFromFile("graphInfo.txt");
        System.out.println(isConnected(graph, 0, 6));
        System.out.println(isConnected(graph, 0, 5));
    }
}
