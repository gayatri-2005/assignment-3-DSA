import java.util.*;

class Node {
    int key, height, size;
    Node left, right;

    Node(int k) {
        key = k;
        height = 1;
        size = 1;
        left = null;
        right = null;
    }
}

class AVLTree {
    private Node root;

    private int height(Node node) {
        return node != null ? node.height : 0;
    }

    private int size(Node node) {
        return node != null ? node.size : 0;
    }

    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        y.size = size(y.left) + size(y.right) + 1;
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        x.size = size(x.left) + size(x.right) + 1;
        y.size = size(y.left) + size(y.right) + 1;

        return y;
    }

    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node.size = size(node.left) + size(node.right) + 1;

        int balance = balanceFactor(node);

        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node minNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    private Node remove(Node node, int key) {
        if (node == null) return node;

        if (key < node.key)
            node.left = remove(node.left, key);
        else if (key > node.key)
            node.right = remove(node.right, key);
        else {
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            Node temp = minNode(node.right);
            node.key = temp.key;
            node.right = remove(node.right, temp.key);
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node.size = size(node.left) + size(node.right) + 1;

        int balance = balanceFactor(node);

        if (balance > 1 && balanceFactor(node.left) >= 0)
            return rightRotate(node);

        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
     
