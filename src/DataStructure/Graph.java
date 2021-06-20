package DataStructure;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

//aka. AdjSet
public class Graph {

    private int egdeNum;
    private int nodeNum;
    private boolean isDigraph;
    private TreeSet<Integer>[] adjacent;

    public Graph() {
    }

    //生成图的第一种方式
    public Graph(int nodeNum, boolean isDigraph) {
        this.isDigraph = isDigraph;
        this.nodeNum = nodeNum;
        this.adjacent = new TreeSet[nodeNum];
    }

    public Graph(TreeSet[] linkedLists, boolean isDigraph) {
        this.isDigraph = isDigraph;
        this.nodeNum = linkedLists.length;
        this.adjacent = linkedLists;
    }

    //生成图的第二种方式
    //文件格式：第一行是节点数目、边数目和是否为有向图，从第二行开始是具体的边
    public static Graph generateGraphFromFile(String filePath) {
        Graph graph = new Graph();
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            int nodeNum = scanner.nextInt();
            int edgeNum = scanner.nextInt();
            if (nodeNum < 0) {
                throw new IllegalArgumentException("Node number can't be less than 0.");
            }
            if (edgeNum < 0) {
                throw new IllegalArgumentException("Edge number can't be less than 0.");
            }
            String isDigraph = scanner.next();
            TreeSet[] adjacentMatrix = new TreeSet[nodeNum];
            for (int i = 0; i < nodeNum; i++) {
                adjacentMatrix[i] = new TreeSet();
            }
            graph = new Graph(adjacentMatrix, isDigraph.equals("true"));
            for (int i = 0; i < edgeNum; i++) {
                graph.addEdge(scanner.nextInt(), scanner.nextInt());
            }
        } catch (IOException e) {
            System.err.println("[ERROR] generateGraphFromFile failed!");
            e.printStackTrace();
        }
        return graph;
    }


    public int getEgdeNum() {
        return egdeNum;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("%s: %d Nodes, %d Edges\n", isDigraph ? "Digraph" : "Graph", getNodeNum(), getEgdeNum()));
        for (int i = 0; i < nodeNum; i++) {
            builder.append("[").append(i).append("]: ");
            for (int n : adjacent[i]) {
                builder.append(n).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public void addEdge(int fromNode, int toNode) {
        validateEdge(fromNode, toNode);
        //自环边检验
        if (fromNode == toNode) {
            throw new IllegalArgumentException(String.format("Self loop %d->%d shouldn't exist.", fromNode, toNode));
        }
        //平行边检验
        if (!adjacent[fromNode].contains(toNode)) {
            egdeNum++;
            adjacent[fromNode].add(toNode);
            if (!isDigraph)
                adjacent[toNode].add(fromNode);
        } else {
            throw new IllegalArgumentException(String.format("Edge %d->%d is already exist.", fromNode, toNode));
        }
    }

    //边的合法性检验
    public void validateEdge(int fromNode, int toNode) {
        if (fromNode < 0 || fromNode >= nodeNum) {
            throw new IllegalArgumentException(String.format("Index of fromNode: %d is out of range.", fromNode));
        }
        if (toNode < 0 || toNode >= nodeNum) {
            throw new IllegalArgumentException(String.format("Index of toNode: %d is out of range.", toNode));
        }
    }

    public void validateNode(int node) {
        if (node < 0 || node >= nodeNum) {
            throw new IllegalArgumentException(String.format("Index of node: %d is out of range.", node));
        }
    }

    //返回操作是否成功，若为false说明要删除的边不存在
    public boolean removeEdge(int fromNode, int toNode) {
        validateEdge(fromNode, toNode);
        if (adjacent[fromNode].remove(toNode)) {
            egdeNum--;
            if (!isDigraph)
                adjacent[toNode].remove(fromNode);
            return true;
        } else
            return false;
    }

    public boolean isEdgeExist(int fromNode, int toNode) {
        validateEdge(fromNode, toNode);
        return adjacent[fromNode].contains(toNode);
    }

    public Iterable<Integer> adjacentNodes(int node) {
        validateNode(node);
        return adjacent[node];
    }

    public int degree(int node) {
        if (isDigraph) {
            throw new IllegalArgumentException("Digraph should use inDegree() or outDegree() method.");
        }
        validateNode(node);
        return adjacent[node].size();
    }

    public int inDegree(int node) {
        if (!isDigraph) {
            throw new IllegalArgumentException("Graph should use degree() method.");
        }
        validateNode(node);
        int result = 0;
        for (int i = 0; i < nodeNum; i++) {
            if (adjacent[i].contains(node)) {
                result++;
            }
        }
        return result;
    }

    public int outDegree(int node) {
        if (!isDigraph) {
            throw new IllegalArgumentException("Graph should use degree() method.");
        }
        validateNode(node);
        return adjacent[node].size();
    }

    public static void main(String[] args) {
        Graph graph = Graph.generateGraphFromFile("graphInfo.txt");
        System.out.println(graph);
    }
}
