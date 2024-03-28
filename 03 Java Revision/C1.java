import java.util.*;
class Item
{
    public String name;
    public double price;
    
    public Item(String name,double price)
    {
        this.name = name;
        this.price = price;
    }
}
class Cart
{
    private ArrayList<Item> cartList = new ArrayList<>();
    
    public void addItem(Item item)
    {
        cartList.add(item);
    }
    
    public void removeItem(String name)
    {
        cartList.removeIf(item -> (item.name.equals(name)));
    }
    
    public double calculateTotalPrice()
    {
        double sum=0;
        for(int i=0;i<cartList.size();i++)
        {
            sum += cartList.get(i).price;
        }
        return sum;
    }
}
class Main
{
    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.nextLine();
        Cart cart = new Cart();
        for(int i=0;i<n;i++)
        {
            String name = scan.nextLine();
            double price = scan.nextDouble();
            scan.nextLine();
            cart.addItem(new Item(name,price));
        }
        int n1 = scan.nextInt();
        scan.nextLine();
        for(int i=0;i<n1;i++)
        {
            String name = scan.nextLine();
            cart.removeItem(name);
        }
        
        System.out.print("Total Price after removing items: $"+cart.calculateTotalPrice());
    }
}