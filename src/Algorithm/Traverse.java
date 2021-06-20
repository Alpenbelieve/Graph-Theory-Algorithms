package Algorithm;

import DataStructure.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public final class Traverse {
    //返回深度优先搜索的前序遍历
    public static Iterable<Integer> dfs(Graph graph) {
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        ArrayList<Integer> pre = new ArrayList<>();
        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (!visited[i])
                _dfs_pre(i, graph, visited, pre);
        }
        return pre;
    }

    //返回深度优先搜索的前序遍历和后续遍历
    public static Iterable<Integer>[] dfs2(Graph graph) {
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        ArrayList<Integer> pre = new ArrayList<>();
        ArrayList<Integer> post = new ArrayList<>();
        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (!visited[i])
                _dfs_pre_post(i, graph, visited, pre, post);
        }
        return new Iterable[]{pre, post};
    }


    private static void _dfs_pre(int node, Graph graph, boolean[] visited, ArrayList<Integer> pre) {
        visited[node] = true;
        pre.add(node);
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n])
                _dfs_pre(n, graph, visited, pre);
        }
    }


    private static void _dfs_pre_post(int node, Graph graph, boolean[] visited, ArrayList<Integer> pre, ArrayList<Integer> post) {
        visited[node] = true;
        pre.add(node);
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n])
                _dfs_pre_post(n, graph, visited, pre, post);
        }
        post.add(node);
    }

    public static Iterable<Integer> bfs(Graph graph) {
        ArrayList<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] isVisited = new boolean[graph.getNodeNum()];

        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (!isVisited[i]) {
                _bfs(graph, isVisited, result, queue, i);
            }
        }
        return result;
    }

    private static void _bfs(Graph graph, boolean[] visited, ArrayList<Integer> arrayList, Queue<Integer> queue, int start) {
        visited[start] = true;
        queue.add(start);
        arrayList.add(start);
        while (!queue.isEmpty()) {
            for (int n : graph.adjacentNodes(queue.poll())) {
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                    arrayList.add(n);
                }
            }
        }
    }


    public static void main(String[] args) {
        Graph graph = Graph.generateGraphFromFile("graphinfo.txt");
        System.out.println(bfs(graph));
    }
}
