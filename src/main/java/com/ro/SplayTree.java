package com.ro;

public class SplayTree {

    private Node root;

    public void insert(Song song) {
        root = insert(root, song);
    }

    private Node insert(Node root, Song song) {
        if (root == null) return new Node(song);

        root = splay(root, song.playCount);

        if (root.song.playCount == song.playCount) return root;

        Node newNode = new Node(song);

        if (root.song.playCount > song.playCount) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        } else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }

        return newNode;

    }

    public Song findMax() {
        if (root == null) return null;

        Node node = root;
        while (node.right != null) {
            node = node.right;
        }
        root = splay(root, node.song.playCount);
        return root.song;
    }

    private Node splay(Node root, int playCount) {

        if (root == null || root.song.playCount == playCount) return root;

        if (root.song.playCount > playCount) {
            if (root.left == null) return root;

            if (root.left.song.playCount > playCount) {
                root.left.left = splay(root.left.left, playCount);
                root = rightRotation(root);
            } else if (root.left.song.playCount < playCount) {
                root.left.right = splay(root.left.right, playCount);
                if (root.left.right != null) root.left = leftRotation(root.left);
            }

            return root.left == null ? root : rightRotation(root);
        } else {
            if (root.right == null) return root;

            if (root.right.song.playCount > playCount) {
                root.right.left = splay(root.right.left, playCount);
                if (root.right.left != null) root.right = rightRotation(root.right);
            } else if (root.right.song.playCount < playCount) {
                root.right.right = splay(root.right.right, playCount);
                root = leftRotation(root);
            }

            return (root.right == null) ? root : leftRotation(root);
        }
    }

    private Node rightRotation(Node node) {
        Node root = node.left;
        node.left = root.right;
        root.right = node;
        return root;
    }

    private Node leftRotation(Node node) {
        Node root = node.right;
        node.right = root.left;
        root.left = node;
        return root;
    }

}

class Node {

    Song song;
    Node left, right;

    public Node(Song song) {
        this.song = song;
    }
}

class Song {

    String name;

    int playCount;

    public Song(String name, int playCount) {
        this.name = name;
        this.playCount = playCount;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + playCount + '}';
    }
}
