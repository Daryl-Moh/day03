package workshop;

public class MultiUsersShoppingCart {

    public static void main(String[] args) {

        ShoppingCartDB cart = new ShoppingCartDB("cartdb"); //default folder: "db"
        cart.setUp();
        cart.startShell();

    }
    
}
