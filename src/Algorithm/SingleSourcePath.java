package Algorithm;

import DataStructure.Graph;

import java.util.*;

public final class SingleSourcePath {

    private SingleSourcePath() {
    }

    //使用DFS实现单源路径
    public static Iterable<Integer> pathToNode(Graph graph, int start, int destination) {
        ArrayList<Integer> result = new ArrayList<>();
        if (!isConnected(graph, destination, start))
            return result;
        //pre[i]表示i的上一个节点，此外pre还有visited数组的作用
        int[] pre = new int[graph.getNodeNum()];
        Arrays.fill(pre, -1);
        _dfs(start, start, graph, pre);

        int cur = destination;
        while (cur != start) {
            result.add(cur);
            cur = pre[cur];
        }
        result.add(start);
        Collections.reverse(result);
        return result;
    }

    //优化后的判断两点是否连接的方法，使用DFS
    public static boolean isConnected(Graph graph, int start, int destination) {
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        return _dfs(graph, start, visited, destination);
    }

    //返回从node开始能否到达destination
    private static boolean _dfs(Graph graph, int node, boolean[] visited, int destination) {
        visited[node] = true;
        for (int n : graph.adjacentNodes(node)) {
            if (n == destination)
                return true;
            if (!visited[n])
                //注意这里返回true的条件
                if (_dfs(graph, n, visited, destination))
                    return true;
        }
        return false;
    }

    private static void _dfs(int node, int parent, Graph graph, int[] pre) {
        pre[node] = parent;
        for (int n : graph.adjacentNodes(node))
            if (pre[n] == -1)
                _dfs(n, node, graph, pre);
    }

    //使用BFS实现单源路径，此时得到的路径就是单源最短路径
    public static Iterable<Integer> pathToNode2(Graph graph, int start, int destination) {
        ArrayList<Integer> result = new ArrayList<>();
        if (!isConnected2(graph, destination, start))
            return result;
        //pre[i]表示i的上一个节点，此外pre还有visited数组的作用
        int[] pre = new int[graph.getNodeNum()];
        Arrays.fill(pre, -1);
        Queue<Integer> queue = new LinkedList<>();
        _bfs(graph, pre, queue, 0);

        int cur = destination;
        while (cur != start) {
            result.add(cur);
            cur = pre[cur];
        }
        result.add(start);
        Collections.reverse(result);
        return result;
    }

    private static void _bfs(Graph graph, int[] pre, Queue<Integer> queue, int index) {
        queue.add(index);
        pre[index] = index;
        while (!queue.isEmpty()) {
            int top = queue.poll();
            for (int n : graph.adjacentNodes(top)) {
                if (pre[n] == -1) {
                    queue.add(n);
                    pre[n] = top;
                }
            }
        }
    }

    //优化后的判断两点是否连接的方法，使用DFS
    public static boolean isConnected2(Graph graph, int start, int destination) {
        int[] visited = new int[graph.getNodeNum()];
        Arrays.fill(visited, -1);
        Queue<Integer> queue = new LinkedList<>();
        _bfs(graph, visited, queue, start);
        return visited[destination] != -1;
    }

    //unweighted single source shortest path
    //无权单源最短路径
    public static Iterable<Integer> USSSPath(Graph graph, int start, int destination) {
        return pathToNode2(graph, start, destination);
    }

    //指定一个图的出发点，返回从该点出发到其他点的最短路径的距离，如果为-1表示非联通
    public static int[] USSSPathDist(Graph graph, int start) {
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(visited, false);
        int[] dist = new int[graph.getNodeNum()];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        _bfs_dist(graph, visited, dist, queue, start);

        return dist;
    }

    private static void _bfs_dist(Graph graph, boolean[] visited, int[] dist, Queue<Integer> queue, int start) {
        queue.add(start);
        dist[start] = 0;
        visited[start] = true;
        while (!queue.isEmpty()) {
            int top = queue.poll();
            for (int n : graph.adjacentNodes(top)) {
                if (!visited[n]) {
                    queue.add(n);
                    dist[n] = dist[top] + 1;
                    visited[n] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = Graph.generateGraphFromFile("graphinfo.txt");
        System.out.println(Arrays.toString(USSSPathDist(graph, 0)));
    }
}
