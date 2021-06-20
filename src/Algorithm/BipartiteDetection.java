package Algorithm;

import DataStructure.Graph;

import java.util.ArrayList;
import java.util.Arrays;

public final class BipartiteDetection {
    public static boolean bipartiteDetection(Graph graph) {
        //表示节点的颜色，初始值为-1，红色为0，蓝色为1
        int[] colors = new int[graph.getNodeNum()];
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(colors, -1);
        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (!visited[i]) {
                if (!_dfs_bi_detection(i, graph, visited, colors, 0))
                    return false;
            }
        }
        return true;
    }

    //返回一个长度为2的ArrayList数组，分别存放着二分图的信息
    public static int[][] getBipartiteInfo(Graph graph) {
        if (!bipartiteDetection(graph)) {
            throw new IllegalArgumentException("Input graph is not a bipartite");
        }
        int[][] result = new int[2][];
        //表示节点的颜色，初始值为-1，红色为0，蓝色为1
        int[] colors = new int[graph.getNodeNum()];
        boolean[] visited = new boolean[graph.getNodeNum()];
        Arrays.fill(colors, -1);
        for (int i = 0; i < graph.getNodeNum(); i++) {
            if (!visited[i]) {
                _dfs_bi_detection(i, graph, visited, colors, 0);
            }
        }
        ArrayList<Integer> firstPart = new ArrayList<>();
        ArrayList<Integer> secondPart = new ArrayList<>();
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == 0)
                firstPart.add(i);
            else
                secondPart.add(i);
        }
        int counter = 0;
        int[] first = new int[firstPart.size()];
        int[] second = new int[secondPart.size()];
        for (Integer i : firstPart)
            first[counter++] = i;
        counter = 0;
        for (Integer i : secondPart)
            second[counter++] = i;
        result[0] = first;
        result[1] = second;
        return result;
    }

    private static void _dfs_bi_info(int node, Graph graph, boolean[] visited, int[] colors, int color) {
        visited[node] = true;
        colors[node] = color;
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n]) {
                _dfs_bi_detection(n, graph, visited, colors, 1 - color);
            }
        }
    }

    private static boolean _dfs_bi_detection(int node, Graph graph, boolean[] visited, int[] colors, int color) {
        visited[node] = true;
        colors[node] = color;
        for (int n : graph.adjacentNodes(node)) {
            if (!visited[n])
                if (!_dfs_bi_detection(n, graph, visited, colors, 1 - color))
                    return false;
                else if (colors[n] == color)
                    return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Graph graph = Graph.generateGraphFromFile("graphinfo.txt");
        System.out.println(bipartiteDetection(graph));
        for (int[] i : getBipartiteInfo(graph))
            System.out.println(Arrays.toString(i));
    }

}
