import java.util.*;

public class BinaryTree {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Node root = null;
        boolean isNew = true, isDone = false;
        byte option = 0;

        do {
            try {
                System.out.println("(Separate with commas ex: 1,2,3,4) ");
                System.out.print("Enter series of integers: ");

                String numbers = scan.nextLine();
                String[] numarray = removeChars(numbers).split(",");

                for (String integer : numarray) {
                    root = Tree.insert(root, Integer.parseInt(integer));
                }

                do {
                    option = Common.getOption(isNew);
                    switch (option) {
                        case 1:
                            Tree.traverse(root);
                            break;
                        case 2:
                            Common.insertNumber(root);
                            break;
                        case 3:
                            Common.deleteNumber(root);
                            break;
                        case 4:
                            Common.searchNumber(root);
                            break;
                        case 0:
                            System.out.println("\n[ Goodbye! ]");
                            isDone = true;
                            break;
                        default:
                            System.out.println("\n ERROR: Option not found, try again! ");
                            break;
                    }
                } while (option != 0);
            } catch (Exception e) {
                System.out.println("\nERROR: Try again!\n");
                // scan.next();
                isDone = false;
            }
        } while (isDone == false);

        scan.close();
    }

    private static String removeChars(String string) {

        string = string.replace("+", " ");
        string = string.replace(" ", "");

        return string;
    }
}

class Common {
    private static Scanner scan = new Scanner(System.in);

    public static byte getOption(boolean isNew) {
        byte option = 0;
        scan = new Scanner(System.in);
        if (isNew == true) {
            System.out.print("\n[ 1. Traversal | 2. Insert | 3. Delete | 4. Search | 0. Exit] : ");
            option = scan.nextByte();
        } else {
            System.out.print("\n[ 1. Inorder | 2. Preorder | 3. Postorder | 0. Exit] : ");
            option = scan.nextByte();
        }

        return option;
    }

    public static Node getSuccessor(Node node) {
        if (node == null) {
            return null;
        }

        Node tempNode = node.right;

        while (tempNode.left != null) {
            tempNode = tempNode.left;
        }

        return tempNode;
    }

    public static void insertNumber(Node root) {
        System.out.print("\nEnter number to insert: ");
        int number = scan.nextInt();

        Tree.insert(root, number);

        System.out.printf("\n%d inserted to tree.\n", number);
    }

    public static void deleteNumber(Node root) {
        System.out.print("Enter number to delete: ");
        int number = scan.nextInt();

        if (Tree.search(root, number) == true) {
            Tree.delete(root, number);
            System.out.printf("\n%d deleted from tree.\n", number);
        } else {
            System.out.printf("\n%d is not found in tree. Try another number.\n", number);
        }
    }

    public static void searchNumber(Node root) {
        System.out.print("\nEnter number to search: ");
        int number = scan.nextInt();

        if (Tree.search(root, number) == true) {
            System.out.printf("\n%d is found in tree.\n", number);
        } else {
            System.out.printf("\n%d is not found in tree.\n", number);
        }
    }
}

class Node {
    int value;
    Node left;
    Node right;
}

class Tree {
    static Scanner scan = new Scanner(System.in);

    public static Node createNode(int value) {
        Node node = new Node();

        node.value = value;
        node.left = null;
        node.right = null;

        return node;
    }

    public static void traverse(Node root) {
        switch (Common.getOption(false)) {
            case 1:
                System.out.print("\nINORDER: ");
                Traversal.inorder(root);
                System.out.println();
                break;
            case 2:
                System.out.print("\nPREORDER: ");
                Traversal.preorder(root);
                System.out.println();
                break;
            case 3:
                System.out.print("\nPOSTORDER: ");
                Traversal.postorder(root);
                System.out.println();
                break;
            default:
                System.out.println("\nERROR: Try Again!");
                break;
        }
    }

    public static Node insert(Node node, int value) {
        if (node == null) {
            return createNode(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        }

        return node;
    }

    public static Node delete(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = delete(node.left, value);
        } else if (value > node.value) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                Node tempNode = null;

                tempNode = node.left == null ? node.right : node.left;

                if (tempNode == null) {
                    return null;
                } else {
                    return node;
                }
            } else {
                Node successor = Common.getSuccessor(node);
                node.value = successor.value;

                node.right = delete(node.right, successor.value);

                return node;
            }
        }

        return node;
    }

    public static boolean search(Node node, int value) {
        boolean isFound = false;

        if (node == null) {
            return false;
        }

        while (node != null) {
            if (value < node.value) {
                node = node.left;
            } else if (value > node.value) {
                node = node.right;
            } else {
                isFound = true;
                break;
            }
        }

        return isFound;
    }
}

class Traversal {
    public static void inorder(Node node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.print(node.value + ", ");
        inorder(node.right);
    }

    public static void preorder(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.value + ", ");
        preorder(node.left);
        preorder(node.right);
    }

    public static void postorder(Node node) {
        if (node == null) {
            return;
        }

        postorder(node.left);
        postorder(node.right);
        System.out.print(node.value + ", ");
    }
}
