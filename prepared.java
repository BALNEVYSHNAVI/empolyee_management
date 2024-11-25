package jdbc;
import java.sql.*;
import java.util.*;

public class prepared
{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        try
        {
            String sql = "DROP TABLE IF EXISTS employee; CREATE TABLE employee (empid INT PRIMARY KEY, empname VARCHAR(50) NOT NULL, empsalary DOUBLE NOT NULL);";
            Connection con = DriverManager.getConnection("jdbc:h2:./db", "root", "password");
            PreparedStatement st = con.prepareStatement(sql);
            st.execute();

            int choice;
            do {
                System.out.println("\nEmployee Management:");
                System.out.println("1. Insert");
                System.out.println("2. Display");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice)
                {
                    case 1 -> insert(con);
                    case 2 -> display(con);
                    case 3 -> update(con);
                    case 4 -> delete(con);
                    case 5 -> System.out.println("Exited");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
            while (choice != 5);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void insert(Connection con)
    {
        System.out.print("Enter Employee ID: ");
        int empid = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Employee Name: ");
        String empname = sc.nextLine();
        System.out.print("Enter Employee Salary: ");
        double empsalary = sc.nextDouble();

        String SQL = "INSERT INTO employee (empid, empname, empsalary) VALUES (?, ?, ?)";
        try
        {
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, empid);
            ps.setString(2, empname);
            ps.setDouble(3, empsalary);
            ps.executeUpdate();
            System.out.println("inserted successfully.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void display(Connection con)
    {
        String SQL = "SELECT * FROM employee";
        try
        {
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            System.out.println("EmpID\tEmpName\t\tEmpSalary");
            while (rs.next()) {
                System.out.println(rs.getInt("empid")+"\t\t" +rs.getString("empname")+"\t\t"+rs.getDouble("empsalary"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void update(Connection con)
    {
        System.out.print("Enter Employee ID to update: ");
        int empid = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Employee Name: ");
        String empname = sc.nextLine();
        System.out.print("Enter new Employee Salary: ");
        double empsalary = sc.nextDouble();

        String SQL = "UPDATE employee SET empname = ?, empsalary = ? WHERE empid = ?";
        try
        {
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, empname);
            ps.setDouble(2, empsalary);
            ps.setInt(3, empid);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void delete(Connection con)
    {
        System.out.print("Enter Employee ID to delete: ");
        int empid = sc.nextInt();

        String SQL = "DELETE FROM employee WHERE empid = ?";
        try
        {
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, empid);
            int rows = ps.executeUpdate();
            if (rows > 0)
            {
                System.out.println("deleted successfully.");
            }
            else
            {
                System.out.println("Employee id not found.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
