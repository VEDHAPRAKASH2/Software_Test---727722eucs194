import java.sql.*;
import java.util.*;

class Main
{
    public static void main(String args[]) throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/ri_db","test","test123");
        PreparedStatement st = con.prepareStatement("select LOWER(Department_Name) from department");
        
        ResultSet rs = st.executeQuery();
        while(rs.next())
        {
            System.out.println(rs.getString(1));
        }
    }
}