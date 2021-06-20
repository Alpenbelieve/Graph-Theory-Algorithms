package DataStructure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdjMatrix {

    private int egdeNum;
    private int nodeNum;
    private boolean isDigraph;
    private int[][] adjacentMatrix;

    public AdjMatrix() {
    }

    //生成图的第一种方式
    public AdjMatrix(int nodeNum, boolean isDigraph) {
        this.isDigraph = isDigraph;
        this.nodeNum = nodeNum;
        this.adjacentMatrix = new int[nodeNum][nodeNum];
    }

    //生成图的第二种方式
    //文件格式：第一行是节点数目、边数目和是否为有向图，从第二行开始是具体的边
    public static AdjMatrix generateGraphFromFile(String filePath) {
        AdjMatrix graph = new AdjMatrix();
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(filePath));
//            String [] firstLine = in.readLine().split(" ");
//            graph = new Graph01(Integer.parseInt(firstLine[0]), firstLine[2].equals("true"));
//            for (int i = 0; i < Integer.parseInt(firstLine[1]); i++) {
//                String[] line = in.readLine().split(" ");
//                graph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
//            }
        //另一种写法
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
            graph = new AdjMatrix(nodeNum, isDigraph.equals("true"));
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
        builder.append("\\  ");
        for (int i = 0; i < nodeNum - 1; i++) {
            builder.append(i).append(" ");
        }
        builder.append(nodeNum - 1).append("\n \\ ");
        for (int i = 0; i < nodeNum; i++) {
            builder.append("__");
        }
        builder.append("\n");
        for (int i = 0; i < adjacentMatrix.length; i++) {
            builder.append(i).append(" |");
            int[] matrix = adjacentMatrix[i];
            for (int j = 0; j < adjacentMatrix[0].length - 1; j++) {
                builder.append(matrix[j]).append(" ");
            }
            builder.append(matrix[adjacentMatrix[0].length - 1]).append("\n");
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
        if (adjacentMatrix[fromNode][toNode] == 0) {
            egdeNum++;
            adjacentMatrix[fromNode][toNode] = 1;
            if (!isDigraph)
                adjacentMatrix[toNode][fromNode] = 1;
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
        if (adjacentMatrix[fromNode][toNode] != 0) {
            egdeNum--;
            adjacentMatrix[fromNode][toNode] = 0;
            if (!isDigraph)
                adjacentMatrix[toNode][fromNode] = 0;
            return true;
        } else
            return false;
    }

    public boolean isEdgeExist(int fromNode, int toNode) {
        validateEdge(fromNode, toNode);
        return adjacentMatrix[fromNode][toNode] == 1;
    }

    public ArrayList<Integer> adjacentNodes(int node) {
        validateNode(node);
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < nodeNum; i++) {
            if (adjacentMatrix[node][i] == 1) {
                result.add(i);
            }
        }
        return result;
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
            if (adjacentMatrix[i][node] == 1) {
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
