package com.ro;

import java.util.*;

public class Graph {

    private int vertices;
    private LinkedList<Node>[] adjList;
    private Map<Integer, String> indexToName;
    public Map<String, Integer> nameToIndex;

    Graph(int vertices, Map<Integer, String> indexToName) {
        this.vertices = vertices;
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
        this.indexToName = indexToName;
        this.nameToIndex = new HashMap<>();
        for (Map.Entry<Integer, String> entry : indexToName.entrySet()) {
            nameToIndex.put(entry.getValue(), entry.getKey());
        }
    }

    void addEdge(int v1, int v2, int weight) {
        adjList[v1].add(new Node(v2, weight));
        adjList[v2].add(new Node(v1, weight));
    }

    void dijkstra(String sourceName, String targetName) {
        int source = nameToIndex.get(sourceName);
        int target = nameToIndex.get(targetName);

        boolean[] visited = new boolean[vertices];
        LinkedList<Node> nodes = new LinkedList<>();

        int[] cost = new int[vertices];
        int[] predecessors = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            cost[i] = Integer.MAX_VALUE;
            nodes.add(new Node(i, cost[i]));
            predecessors[i] = -1;
        }

        cost[source] = 0;
        nodes.add(new Node(source, cost[source]));

        while (!nodes.isEmpty()) {
            Node minNode = getMinNode(nodes, cost);
            int v = minNode.vertex;
            nodes.remove(minNode);

            visited[v] = true;

            for (Node node : adjList[v]) {
                if (!visited[node.vertex] && cost[v] != Integer.MAX_VALUE && cost[v] + node.weight < cost[node.vertex]) {
                    cost[node.vertex] = cost[v] + node.weight;
                    predecessors[node.vertex] = v;
                    updateNode(nodes, node.vertex, cost[node.vertex]);
                }
            }
        }

        printShortestPath(predecessors, source, target);
        System.out.println("Total distance " + cost[target]);
    }

    void printShortestPath(int[] predecessors, int source, int target) {
        List<Integer> path = new ArrayList<>();
        for (int at = target; at != -1; at = predecessors[at]) {
            path.add(at);
        }

        if (path.get(path.size() - 1) != source) {
            System.out.println("No path found from " + indexToName.get(source) + " to " + indexToName.get(target));
        } else {
            Collections.reverse(path);
            System.out.print("Shortest path from " + indexToName.get(source) + " to " + indexToName.get(target) + " - ");

            for (int i = 0; i < path.size() - 1; i++) {
                System.out.print(indexToName.get(path.get(i)) + "->");
            }
            System.out.println(indexToName.get(path.get(path.size() - 1)));
        }
    }

    Node getMinNode(LinkedList<Node> nodes, int[] cost) {
        Node minNode = null;
        int minCost = Integer.MAX_VALUE;
        for (Node node : nodes) {
            if (cost[node.vertex] < minCost) {
                minCost = cost[node.vertex];
                minNode = node;
            }
        }
        return minNode;
    }

    void updateNode(LinkedList<Node> nodes, int vertex, int updateCost) {
        for (Node node : nodes) {
            if (node.vertex == vertex) {
                node.weight = updateCost;
                break;
            }
        }
    }

    void print() {
        for (int i = 0; i < vertices; i++) {
            System.out.print("Vertex " + i + " ");
            for (Node node : adjList[i]) {
                System.out.print("(" + node.vertex + ", " + node.weight + ") ");
            }
            System.out.println();
        }
    }

    private static class Node {
        int vertex, weight;

        Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

}
