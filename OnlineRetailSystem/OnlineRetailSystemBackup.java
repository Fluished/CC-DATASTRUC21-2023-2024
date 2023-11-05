//package OnlineRetailSystem;

import java.util.*;
import java.time.*;

class User {

    User(int _userId, String _username, String _email) {
        userId = _userId;
        username = _username;
        email = _email;
    }

    private int userId;
    private String username;
    private String email;

    public void login() {
        System.out.println("\n[ " + username + " Successfully logged in! ]\n");
        System.out.println("User ID: " + userId);
        System.out.println("Email: " + email);
    }

    public void logout() {
        System.out.println("\n[ " + username + " Successfully logged out! ]");
    }

}

class Customer extends User {

    Customer(int _userId, String _username, String _email, String _address) {
        super(_userId, _username, _email);
        customerId = _userId;
        address = _address;
    }

    int customerId;
    String address;
    ArrayList<Order> orderHistory = new ArrayList<Order>();

    ArrayList<Product> placeOrder(int productId, ArrayList<Product> orderedProducts,
            ArrayList<Product> productMasterList) {

        for (Product product : productMasterList) {
            if (product.productId == productId) {
                System.out.println("\n[ ProductID: " + product.productId + " has been added. ]");
                orderedProducts.add(product);
            }
        }

        return orderedProducts;
    }

    void viewOrderHistory() {
        System.out.println(
                "\n[ ======================================== ORDER HISTORY ======================================== ]\n");
        for (Order orderHist : orderHistory) {
            double totalAmount = 0;
            System.out.println(
                    "\n[ ======================================== ORDER ID " + orderHist.orderId
                            + "  ======================================== ]");
            for (Product product : orderHist.productOrders) {
                System.out.println("Order: " + product.productName + " | $" + product.productPrice);
                totalAmount += product.productPrice;
            }
            System.out.println("Total Amount: $" + totalAmount);
            System.out.println("Order Date: " + orderHist.orderDate);
            System.out.println(
                    "\n[ ================================================================================ ]");
        }
    }
}

class Admin extends User {

    Admin(int _userId, String _username, String _email, String _department) {
        super(_userId, _username, _email);
        adminId = _userId;
        department = _department;
    }

    int adminId;
    String department;

    Scanner scan = new Scanner(System.in);

    Common common = new Common();

    ArrayList<Product> addProduct(ArrayList<Product> productMasterList) {
        common.showMenu(productMasterList);

        System.out.println("[ Enter Product Name: ]");
        String productName = scan.nextLine();
        System.out.println("[ Enter Product Price: ]");
        double productPrice = scan.nextDouble();
        System.out.println("[ Enter Number of Stocks: ]");
        int productStock = scan.nextInt();

        int productId = productMasterList.size() + 1;
        Product addProduct = new Product(productId, productName, productPrice, productStock);

        productMasterList.add(addProduct);

        System.out.println("[ Product successfully added! ]");
        common.showMenu(productMasterList);
        return productMasterList;
    }

    ArrayList<Product> removeProduct(ArrayList<Product> productMasterList) {
        System.out.println("[ Enter ProductID of Product to remove: ]");
        int removeProductId = scan.nextInt();

        common.showMenu(productMasterList);
        for (Product product : productMasterList) {
            if (removeProductId == product.productId) {
                productMasterList.remove(product);
            }
        }
        System.out.println("[ Product successfully removed! ]");
        common.showMenu(productMasterList);

        return productMasterList;
    }

    ArrayList<Product> manageInventory(ArrayList<Product> productMasterList) {
        System.out.println("[ Enter ProductID to manage: ]");
        int manageProductId = scan.nextInt();

        common.showMenu(productMasterList);
        for (Product product : productMasterList) {
            if (manageProductId == product.productId) {
                System.out.println("[ Enter new Product Name: ]");
                String productName = scan.nextLine();
                product.productName = productName;
                System.out.println("[ Enter new Product Price: ]");
                double productPrice = scan.nextDouble();
                product.updatePrice(productPrice);
                System.out.println("[ Enter new Number of Stocks: ]");
                int productStock = scan.nextInt();
                product.updateStock(productStock);
            }
        }

        System.out.println("[ Product Successfully Updated! ]");
        common.showMenu(productMasterList);
        return productMasterList;
    }
}

class Product {

    public Product(int _productId, String _productName, double _productPrice, int _stockQuantity) {
        productId = _productId;
        productName = _productName;
        productPrice = _productPrice;
        stockQuantity = _stockQuantity;
    }

    int productId;
    String productName;
    double productPrice;
    int stockQuantity;

    void updatePrice(double _newPrice) {
        productPrice = _newPrice;
    }

    void updateStock(int _newQuantity) {
        stockQuantity = _newQuantity;
    }
}

class Order {

    public Order(int _orderId, int _customerId, ArrayList<Product> _productOrders, LocalDate _orderDate) {
        orderId = _orderId;
        customerId = _customerId;
        productOrders = _productOrders;
        orderDate = _orderDate;
    }

    int orderId;
    int customerId;
    double totalAmount = 0;
    ArrayList<Product> productOrders;
    LocalDate orderDate;

    double calculateTotalAmount() {
        for (Product product : productOrders) {
            totalAmount += product.productPrice;
        }

        return totalAmount;
    }

    void addProductToOrder(Product productToAdd) {
        ArrayList<Product> productList = new ArrayList<Product>();

        for (Product product : productList) {
            productList.add(product);
        }
        productList.add(productToAdd);

        productOrders = productList;
    }

    int confirmOrder(Customer customer, ArrayList<Product> productMasterList) {

        System.out.println(
                "\n[ ======================================== ORDER DETAILS ======================================== ]\n");

        if (productOrders.size() < 1) {
            System.err.println("There are no items in your cart!");
        } else {
            for (Product product : productOrders) {
                System.out.println("Order: " + product.productName + " | $" + product.productPrice);
                product.stockQuantity--;
            }

            System.out.printf("\nTotal Amount: $%.2f", calculateTotalAmount());

            System.out
                    .println(
                            "\n[ ==================== OrderID " + orderId + " has been confirmed on: " + orderDate
                                    + " ==================== }");
        }

        customer.orderHistory.add(this);

        return orderId + 1;
    }
}

public class OnlineRetailSystemBackup {
    public static void main(String[] args) {
        int userID = 0;
        String username = new String();
        String email = new String();

        String department = new String();
        String address = new String();

        byte loginOption = 0;
        byte userOption = 0;
        byte confirmOrderOption = 0;
        byte addProductOption = 1;
        byte historyOption = 0;

        int productId;
        int globalOrderId = 1;

        Scanner scan = new Scanner(System.in);

        Common common = new Common();

        // Admin/Customer Authentication
        // ====================================================================================================
        User user = new User(userID, username, email);

        // Static Product Master List
        // =====================================================================================
        Product product1 = new Product(01, "Mouse", 10.15, 20);
        Product product2 = new Product(02, "Keyboard", 20.20, 30);
        Product product3 = new Product(03, "Headphones", 5.50, 40);

        ArrayList<Product> productMasterList = new ArrayList<Product>() {
            {
                add(product1);
                add(product2);
                add(product3);
            }
        };

        // ArrayList<Product> productMasterList = new ArrayList<Product>();
        do {
            System.out.println("\n[ Admin | Customer : Login ]");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Cancel");
            System.out.print(" == ");
            loginOption = scan.nextByte();

            // User login
            // ====================================================================================================
            switch (loginOption) {
                case 1:
                    System.out.print("\nAdmin ID: ");
                    userID = scan.nextInt();
                    System.out.print("Username: ");
                    username = scan.next();
                    System.out.print("Email: ");
                    email = scan.next();
                    System.out.print("Department: ");
                    department = scan.next();

                    user = new User(userID, username, email);
                    Admin admin = new Admin(userID, username, email, department);
                    user.login();

                    do {
                        System.out.println("\n[ 1. Manage Inventory ]");
                        System.out.println("[ 2. Add Product ]");
                        System.out.println("[ 3. Remove Product ]");
                        System.out.println("[ 4. Logout]");
                        System.out.print(" == ");
                        userOption = scan.nextByte();

                        switch (userOption) {
                            case 1:
                                productMasterList = admin.manageInventory(productMasterList);
                                break;
                            case 2:
                                productMasterList = admin.addProduct(productMasterList);
                                break;
                            case 3:
                                productMasterList = admin.removeProduct(productMasterList);
                                break;
                            case 4:
                                user.logout();
                                break;
                            default:
                                System.out.println("\n!!! INVALID ADMIN OPTION !!!");
                                break;
                        }

                    } while (userOption < 1 || userOption > 4);

                    // System.out.println(" == ");
                    // userOption = scan.nextByte();
                    break;

                // If Customer
                // ==================================================================
                case 2:
                    System.out.print("\nCustomer ID: ");
                    userID = scan.nextInt();
                    System.out.print("Username: ");
                    username = scan.next();
                    System.out.print("Email: ");
                    email = scan.next();
                    System.out.print("Address: ");
                    address = scan.next();

                    user = new User(userID, username, email);
                    Customer customer = new Customer(userID, username, email, address);
                    user.login();

                    ArrayList<Product> orderedProducts = new ArrayList<Product>();
                    LocalDate orderDate = LocalDate.now();
                    Order customerOrder = new Order(globalOrderId, userID, orderedProducts, orderDate);

                    do {
                        System.out.println("\n[ 1. Place an Order ]");
                        System.out.println("[ 2. Logout]");
                        System.out.print(" == ");
                        userOption = scan.nextByte();

                        switch (userOption) {
                            case 1:
                                do {
                                    common.showMenu(productMasterList);
                                    System.out.println("\n[ Enter ProductID: ]");
                                    System.out.print(" == ");
                                    productId = scan.nextInt();

                                    orderedProducts = customer.placeOrder(productId, orderedProducts,
                                            productMasterList);

                                    System.out.println("\n[ Add another product? ]");
                                    System.out.println("[ 1. Yes ]");
                                    System.out.println("[ 2. No ]");
                                    System.out.print(" == ");
                                    addProductOption = scan.nextByte();

                                    switch (addProductOption) {
                                        case 1:
                                            common.showMenu(productMasterList);
                                            System.out.println("\n[ Enter ProductID: ]");
                                            System.out.print(" == ");
                                            productId = scan.nextInt();

                                            orderedProducts = customer.placeOrder(productId, orderedProducts,
                                                    productMasterList);

                                            System.out.println("\n[ Add another product? ]");
                                            System.out.println("[ 1. Yes ]");
                                            System.out.println("[ 2. No ]");
                                            System.out.print(" == ");
                                            addProductOption = scan.nextByte();
                                            break;
                                        case 2:
                                            do {
                                                System.out.println("\n[ Confirm order? ]");
                                                System.out.println("[ 1. Yes ]");
                                                System.out.println("[ 2. No ]");
                                                System.out.print(" == ");
                                                confirmOrderOption = scan.nextByte();

                                                switch (confirmOrderOption) {
                                                    case 1:
                                                        customerOrder = new Order(globalOrderId, userID,
                                                                orderedProducts,
                                                                LocalDate.now());
                                                        globalOrderId = customerOrder.confirmOrder(customer,
                                                                productMasterList);
                                                        orderedProducts = new ArrayList<Product>();

                                                        do {
                                                            System.out.println("\n[ View order history? ]");
                                                            System.out.println("[ 1. Yes ]");
                                                            System.out.println("[ 2. No ]");
                                                            System.out.print(" == ");
                                                            historyOption = scan.nextByte();

                                                            switch (historyOption) {
                                                                case 1:
                                                                    customer.viewOrderHistory();
                                                                    break;
                                                                case 2:
                                                                    break;
                                                                default:
                                                                    System.out.println(
                                                                            "\n!!! INVALID HISTORY OPTION !!!");
                                                                    break;
                                                            }
                                                        } while (historyOption < 1 || historyOption > 2);
                                                        addProductOption = 3;
                                                        userOption = 3;
                                                        break;
                                                    case 2:
                                                        System.out.println("\n[ Order Cancelled ]");
                                                        addProductOption = 3;
                                                        userOption = 3;
                                                        break;
                                                    default:
                                                        System.out.println("\n!!! INVALID CONFIRM OPTION !!!");
                                                        break;
                                                }
                                            } while (confirmOrderOption < 1 || confirmOrderOption > 2);
                                            break;
                                        default:
                                            System.out.println("\n!!! INVALID ADD PRODUCT OPTION !!!");
                                            break;
                                    }
                                } while (addProductOption == 1 || addProductOption == 2);
                                break;
                            case 2:
                                user.logout();
                                loginOption = 4;
                                break;
                            default:
                                System.out.println("\n!!! INVALID USER OPTION !!!");
                                break;
                        }
                    } while (userOption < 1 || userOption > 2);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("\n!!! INVALID LOGIN OPTION !!!");
                    break;
            }
        } while (loginOption < 1 || loginOption > 3);
    }
}

class Common {

    void showMenu(ArrayList<Product> productMasterList) {
        // Menu
        // ======================================================================================================
        System.out.println(
                "\n\n[ ======================================== LIST OF PRODUCTS ======================================== ]\n");

        if (productMasterList.size() < 1) {
            System.out.println("There are no products available.");
        }

        for (Product product : productMasterList) {
            String formatProductList = String.format(
                    "ProductID: %s | Product Name: %s | Price: %.2f | Stock Quantity: %d",
                    (product.productId), (product.productName), (product.productPrice), (product.stockQuantity));
            System.out.println(formatProductList);
        }

        System.out.println(
                "\n\n[ ======================================== END OF LIST ======================================== ]\n");
    }

    void showOrderedProducts(ArrayList<Product> productOrders) {
        // Ordered Products
        // =====================================================================================
        for (Product product : productOrders) {
            String formatProductList = String.format(
                    "ProductID: %s | Product Name: %s | Price: %.2f | Stock Quantity: %d",
                    (product.productId), (product.productName), (product.productPrice), (product.stockQuantity));
            System.out.println(formatProductList);
        }
    }
}
