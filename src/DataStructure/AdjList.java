package DataStructure;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class AdjList {

    private int egdeNum;
    private int nodeNum;
    private boolean isDigraph;
    private LinkedList<Integer>[] adjacentList;

    public AdjList() {
    }

    //生成图的第一种方式
    public AdjList(int nodeNum, boolean isDigraph) {
        this.isDigraph = isDigraph;
        this.nodeNum = nodeNum;
        this.adjacentList = new LinkedList[nodeNum];
    }

    public AdjList(LinkedList[] linkedLists, boolean isDigraph) {
        this.isDigraph = isDigraph;
        this.nodeNum = linkedLists.length;
        this.adjacentList = linkedLists;
    }

    //生成图的第二种方式
    //文件格式：第一行是节点数目、边数目和是否为有向图，从第二行开始是具体的边
    public static AdjList generateGraphFromFile(String filePath) {
        AdjList graph = new AdjList();
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
            LinkedList[] adjacentMatrix = new LinkedList[nodeNum];
            for (int i = 0; i < nodeNum; i++) {
                adjacentMatrix[i] = new LinkedList();
            }
            graph = new AdjList(adjacentMatrix, isDigraph.equals("true"));
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
            if (adjacentList[i].size() == 0) {
                builder.append("\n");
                continue;
            }
            for (int n : adjacentList[i]) {
                builder.append(n).append(" -> ");
            }
            if (builder.length() > 4 && builder.substring(builder.length() - 4).equals(" -> "))
                builder.delete(builder.length() - 4, builder.length()).append("\n");
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
        if (!adjacentList[fromNode].contains(toNode)) {
            egdeNum++;
            adjacentList[fromNode].add(toNode);
            if (!isDigraph)
                adjacentList[toNode].add(fromNode);
        } else {
            throw new IllegalArgumentException(String.format("Edge %d->%d is already exist.", fromNode, toNode));
        }
    }

    //边的合法性检验
    private void validateEdge(int fromNode, int toNode) {
        if (fromNode < 0 || fromNode >= nodeNum) {
            throw new IllegalArgumentException(String.format("Index of fromNode: %d is out of range.", fromNode));
        }
        if (toNode < 0 || toNode >= nodeNum) {
            throw new IllegalArgumentException(String.format("Index of toNode: %d is out of range.", toNode));
        }
    }

    private void validateNode(int node) {
        if (node < 0 || node >= nodeNum) {
            throw new IllegalArgumentException(String.format("Index of node: %d is out of range.", node));
        }
    }

    //返回操作是否成功，若为false说明要删除的边不存在
    public boolean removeEdge(int fromNode, int toNode) {
        validateEdge(fromNode, toNode);
        if (adjacentList[fromNode].removeFirstOccurrence(toNode)) {
            egdeNum--;
            if (!isDigraph)
                adjacentList[toNode].removeFirstOccurrence(fromNode);
            return true;
        } else
            return false;
    }

    public boolean isEdgeExist(int fromNode, int toNode) {
        validateEdge(fromNode, toNode);
        return adjacentList[fromNode].contains(toNode);
    }

    public LinkedList<Integer> adjacentNodes(int node) {
        validateNode(node);
        return adjacentList[node];
    }

    public int degree(int node) {
        if (isDigraph) {
            throw new IllegalArgumentException("Digraph should use inDegree() or outDegree() method.");
        }
        return adjacentNodes(node).size();
    }

    public int inDegree(int node) {
        if (!isDigraph) {
            throw new IllegalArgumentException("Graph should use degree() method.");
        }
        validateNode(node);
        int result = 0;
        for (int i = 0; i < nodeNum; i++) {
            if (adjacentList[i].contains(node)) {
                result++;
            }
        }
        return result;
    }

    public int outDegree(int node) {
        if (!isDigraph) {
            throw new IllegalArgumentException("Graph should use degree() method.");
        }
        return adjacentNodes(node).size();
    }

    public static void main(String[] args) {
        AdjList graph = AdjList.generateGraphFromFile("graphInfo.txt");
        System.out.println(graph);
    }
}
