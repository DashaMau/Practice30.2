class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class Tree {
    Node root;
    int count;

    Tree() {
        this.root = null;
        this.count = 0;
    }


    Node createNode(int item) {
        return new Node(item);
    }

    boolean binSearch(int item) {
        return binSearchHelper(root, item);
    }

    private boolean binSearchHelper(Node node, int item) {
        if (node == null) return false;
        if (item == node.data) return true;
        return item > node.data ? binSearchHelper(node.right, item) : binSearchHelper(node.left, item);
    }


    int insert(int item) {
        if (insertHelper(root, item)) {
            count++;
            return 1;
        }
        return 2;
    }

    private boolean insertHelper(Node node, int item) {
        if (node == null) {
            root = createNode(item);
            return true;
        }

        if (item == node.data) return false;

        if (item > node.data) {
            if (node.right == null) {
                node.right = createNode(item);
                return true;
            } else {
                return insertHelper(node.right, item);
            }
        } else {
            if (node.left == null) {
                node.left = createNode(item);
                return true;
            } else {
                return insertHelper(node.left, item);
            }
        }
    }


    boolean delete(int item) {
        boolean[] result = new boolean[1];
        root = deleteHelper(root, item, result);
        if (result[0]) {
            count--;
            return true;
        }
        return false;
    }

    private Node deleteHelper(Node node, int item, boolean[] result) {
        if (node == null) return null;

        if (item < node.data) {
            node.left = deleteHelper(node.left, item, result);
        } else if (item > node.data) {
            node.right = deleteHelper(node.right, item, result);
        } else {
            result[0] = true;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node minNode = findMin(node.right);
            node.data = minNode.data;
            node.right = deleteHelper(node.right, minNode.data, result);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    void walk() {
        walkHelper(root);
    }

    private void walkHelper(Node node) {
        if (node != null) {
            walkHelper(node.left);
            System.out.print(node.data + " ");
            walkHelper(node.right);
        }
    }

    void destroy() {
        root = destroyHelper(root);
        count = 0;
    }

    private Node destroyHelper(Node node) {
        if (node == null) return null;
        node.left = destroyHelper(node.left);
        node.right = destroyHelper(node.right);
        return null; // Удаляем узел
    }

    boolean isBST() {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTUtil(Node node, int min, int max) {
        if (node == null) return true;
        if (node.data < min || node.data > max) return false;
        return isBSTUtil(node.left, min, node.data) && isBSTUtil(node.right, node.data + 1, max);
    }

    int getCount() {
        return count;
    }

    Node treeMinimum() {
        return findMin(root);
    }


    Node treeMaximum() {
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
}

public class Main {
    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);

        System.out.println("Дерево в порядке возрастания:");
        tree.walk();
        System.out.println();

        System.out.println("Поиск 4: " + tree.binSearch(4));
        System.out.println("Поиск 10: " + tree.binSearch(10));

        System.out.println("Удаление 3: " + tree.delete(3)); 
        System.out.println("Дерево после удаления 3:");
        tree.walk();
        System.out.println();

        System.out.println("Количество узлов: " + tree.getCount());
        System.out.println("Минимальный элемент: " + (tree.treeMinimum() != null ? tree.treeMinimum().data : "null"));
        System.out.println("Максимальный элемент: " + (tree.treeMaximum() != null ? tree.treeMaximum().data : "null"));

        System.out.println("Дерево является бинарным деревом поиска: " + tree.isBST());

        tree.destroy(); // Уничтожение дерева
        System.out.println("Дерево уничтожено. Количество узлов: " + tree.getCount());
    }
}

