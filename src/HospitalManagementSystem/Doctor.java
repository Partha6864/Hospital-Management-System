import java.sql.*;
import java.util.Scanner;

public class Doctor 
{
    private Connection connection;
    private Scanner scanner;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    public Doctor(Connection connection, Scanner scanner) 
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addDoctor() 
    {
        try 
        {
            String id = generateDoctorId();
            System.out.print("\nEnter Doctor Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Doctor Specialization: ");
            String specialization = scanner.nextLine();

            String sql = "INSERT INTO doctor (id, name, specialization) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, specialization);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) 
            {
                System.out.println("\nDoctor Added Successfully With ID: " + id);
            }
            else
            {
                System.out.println("\nDoctor Not Added.");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            closeResources();
        }
    }

    public void viewDoctor()
    {
        try 
        {
            System.out.print("Enter Doctor ID: ");
            String id = scanner.nextLine();

            String sql = "SELECT * FROM doctor WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                System.out.println("\nDoctor:");
                System.out.println("Id: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Specialization: " + rs.getString("specialization"));
            }
            else
            {
                System.out.println("\nDoctor Not Found With ID: " + id);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            closeResources();
        }
    }

    public void viewAllDoctors() 
    {
        try 
        {
            String sql = "SELECT * FROM doctor";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
    
            boolean found = false;
            System.out.println("\nDoctors:");
            while (rs.next()) 
            {
                found = true;
                System.out.println("Id: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Specialization: " + rs.getString("specialization"));
                System.out.println("-----------------------------");
            }
            if (!found) 
            {
                System.out.println("\nNo Doctors Found!");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            closeResources();
        }
    }

    public void updateDoctor() 
    {
        try 
        {
            System.out.print("Enter Doctor ID: ");
            String id = scanner.nextLine();
    
            String sql1 = "SELECT * FROM doctor WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();
    
            if (rs.next()) 
            {
                System.out.print("Enter Doctor Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Doctor Specialization: ");
                String specialization = scanner.nextLine();
    
                String sql2 = "UPDATE doctor SET name = ?, specialization = ? WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, name);
                statement.setString(2, specialization);
                statement.setString(3, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) 
                {
                    System.out.println("\nDoctor Updated Successfully With ID: " + id);
                } 
                else 
                {
                    System.out.println("\nDoctor Not Updated With ID: " + id);
                }
            } 
            else 
            {
                System.out.println("\nDoctor Not Found With ID: " + id);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            closeResources();
        }
    }    

    public void deleteDoctor() 
    {
        try
        {
            System.out.print("Enter Doctor ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM doctor WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                String sql2 = "DELETE FROM doctor WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, id);
                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) 
                {
                    System.out.println("\nDoctor Deleted Successfully With ID: " + id);
                }
                else
                {
                    System.out.println("\nDoctor Not Deleted With ID: " + id);
                }
            }
            else
            {
                System.out.println("\nDoctor Not Found With ID: " + id);
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            closeResources();
        }
    }

    private String generateDoctorId() 
    {
        String newId = "D001";
        try
        {
            String sql = "SELECT id FROM doctor ORDER BY id DESC LIMIT 1";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                String id = rs.getString("id");
                int num = Integer.parseInt(id.substring(1)) + 1;
                newId = String.format("D%03d", num);
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            closeResources();
        }
        return newId;
    }

    private void closeResources() 
    {
        if (rs != null) 
        {
            try 
            {
                rs.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        if (statement != null) 
        {
            try 
            {
                statement.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    }
}