import java.util.*;
import java.sql.*;
class Main
{
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(System.in);
        
        int n = scan.nextInt();
        if(n<1 || n>3)
        {
            System.out.print("Invalid operation number. Please try again.");
        }
        else
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/ri_db","test","test123");
            PreparedStatement st = null;
            
            if(n==1)
            {
                st = con.prepareStatement("insert into customer values(?,?,?,?,?)");
                scan.nextLine();
                st.setInt(1,Integer.parseInt(scan.nextLine()));
                st.setString(2,scan.nextLine());
                st.setString(3,scan.nextLine());
                st.setString(4,scan.nextLine());
                st.setString(5,scan.nextLine());
                st.execute();
                System.out.println("Customer record inserted successfully.");
            }
            
            if(n==2)
            {
                st = con.prepareStatement("update customer set name=?,contact_information=?,subscription_plan=?,payment_history=? where customer_id=?");
                scan.nextLine();
                st.setInt(5,Integer.parseInt(scan.nextLine()));
                st.setString(1,scan.nextLine());
                st.setString(2,scan.nextLine());
                st.setString(3,scan.nextLine());
                st.setString(4,scan.nextLine());
                st.execute();
                System.out.println("Customer record updated successfully.");
            }
            
            st = con.prepareStatement("select * from customer");
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                System.out.println("ID: "+rs.getInt(1)+", Name: "+rs.getString(2)+", Contact Information: "+rs.getString(3)+", Subscription Plan: "+rs.getString(4)+", Payment History: "+rs.getString(5));
            }
        }
    }
}