package com.ro;

public class AVLTree {

    private Node root;

    public void insert(String name, int grade) {
        root = insert(root, name, grade);
    }

    public Node search(int grade) {
        return search(root, grade);
    }

    public Node searchByName(String name) {
        return searchByName(root, name);
    }

    public void delete(int grade) {
        root = deleteNode(root, grade);
    }

    public void deleteByName(String name) {
        root = deleteByName(root, name);
    }

    public void update(String name, int oldGrade, int newGrade) {
        Node node = search(oldGrade);
        if (node != null) {
            if (node.names.contains(name)) {
                node.names.remove(name);
                if (node.names.isEmpty()) {
                    delete(oldGrade);
                }
                insert(name, newGrade);
            }
        }
    }

    public Node insert(Node node, String name, int grade) {
        if (node == null) {
            return new Node(name, grade);
        }

        if (grade < node.grade) {
            node.left = insert(node.left, name, grade);
        } else if (grade > node.grade) {
            node.right = insert(node.right, name, grade);
        } else {
            node.names.add(name);
            return node;
        }

        node.height = max(height(node.left), height(node.right)) + 1;

        int balance = balanceFactor(node);

        // Left Left
        if (balance > 1 && grade < node.left.grade) {
            return rightRotate(node);
        }

        // Right Right
        if (balance < -1 && grade > node.right.grade) {
            return leftRotate(node);
        }

        // Left Right
        if (balance > 1 && grade > node.left.grade) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left
        if (balance < -1 && grade < node.right.grade) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public Node search(Node node, int grade) {
        if (node == null || node.grade == grade) {
            return node;
        }

        if (grade < node.grade) {
            return search(node.left, grade);
        } else {
            return search(node.right, grade);
        }
    }

    public Node searchByName(Node node, String name) {
        if (node == null) {
            return null;
        }

        if (node.names.contains(name)) {
            return node;
        }

        Node leftSearch = searchByName(node.left, name);
        if (leftSearch != null) {
            return leftSearch;
        }

        return searchByName(node.right, name);
    }

    public Node deleteNode(Node root, int grade) {
        if (root == null) {
            return root;
        }

        if (grade < root.grade) {
            root.left = deleteNode(root.left, grade);
        } else if (grade > root.grade) {
            root.right = deleteNode(root.right, grade);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = root.left != null ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);

                root.grade = temp.grade;
                root.names = temp.names;

                root.right = deleteNode(root.right, temp.grade);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = max(height(root.left), height(root.right)) + 1;

        int balance = balanceFactor(root);

        // Left Left
        if (balance > 1 && balanceFactor(root.left) >= 0) {
            return rightRotate(root);
        }

        // Left Right
        if (balance > 1 && balanceFactor(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right
        if (balance < -1 && balanceFactor(root.right) <= 0) {
            return leftRotate(root);
        }

        // Right Left
        if (balance < -1 && balanceFactor(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node deleteByName(Node node, String name) {
        if (node == null) {
            return null;
        }

        if (node.names.contains(name)) {
            node.names.remove(name);
            if (node.names.isEmpty()) {
                return deleteNode(node, node.grade);
            }
        }

        node.left = deleteByName(node.left, name);
        node.right = deleteByName(node.right, name);

        node.height = max(height(node.left), height(node.right)) + 1;

        int balance = balanceFactor(node);

        // Left Left
        if (balance > 1 && balanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right
        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right
        if (balance < -1 && balanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left
        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    public int max(int a, int b) {
        return (a >= b) ? a : b;
    }

    private Node rightRotate(Node node) {
        Node root = node.left;
        Node x = root.right;

        root.right = node;
        node.left = x;

        node.height = max(height(node.left), height(node.right)) + 1;
        root.height = max(height(root.left), height(root.right)) + 1;

        return root;
    }

    private Node leftRotate(Node node) {
        Node root = node.right;
        Node x = root.left;

        root.left = node;
        node.right = x;

        node.height = max(height(node.left), height(node.right)) + 1;
        root.height = max(height(root.left), height(root.right)) + 1;

        return root;
    }

    private int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    private Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    public void preOrder() {
        preOrder(root);
    }

    public void preOrder(Node node) {
        if (node != null) {
            System.out.println(node.names + " " + node.grade);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public static class Node {
        LinkedList names;
        int grade, height;
        Node left, right;

        public Node(String name, int grade) {
            this.names = new LinkedList();
            this.names.add(name);
            this.grade = grade;
            this.height = 1;
        }
    }

    public static class LinkedList {
        private ListNode head;

        public static class ListNode {
            String name;
            ListNode next;

            ListNode(String name) {
                this.name = name;
            }
        }

        public void add(String name) {
            if (head == null) {
                head = new ListNode(name);
            } else {
                ListNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = new ListNode(name);
            }
        }

        public boolean contains(String name) {
            ListNode current = head;
            while (current != null) {
                if (current.name.equals(name)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        public void remove(String name) {
            if (head == null) {
                return;
            }

            if (head.name.equals(name)) {
                head = head.next;
                return;
            }

            ListNode current = head;
            while (current.next != null && !current.next.name.equals(name)) {
                current = current.next;
            }

            if (current.next != null) {
                current.next = current.next.next;
            }
        }

        public boolean isEmpty() {
            return head == null;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            ListNode current = head;
            while (current != null) {
                result.append(current.name);
                if (current.next != null) {
                    result.append(", ");
                }
                current = current.next;
            }
            return result.toString().trim();
        }
    }

}
