import java.util.*;
class InsufficientBalanceException extends Exception
{
    public InsufficientBalanceException(String msg)
    {
        super(msg);
    }
}
class Main
{
    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);
        double available = scan.nextDouble();
        double withdraw = scan.nextDouble();
        try
        {
            if(withdraw<=available)
            {
                System.out.print("Transaction successful.");
            }
            else
            {
                throw new InsufficientBalanceException("Insufficient balance: Insufficient balance to perform the withdrawal.");
            }
        }
        catch(InsufficientBalanceException error)
        {
            System.out.print(error.getMessage());
        }
    }
}