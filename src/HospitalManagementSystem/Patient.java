import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    public Patient(Connection connection, Scanner scanner) 
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() 
    {
        try 
        {
            String id = generatePatientId();
            System.out.print("\nEnter Patient Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Gender: ");
            String gender = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phoneNo = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            String sql = "INSERT INTO patient (id, name, age, gender, phoneNo, address) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.setString(4, gender);
            statement.setString(5, phoneNo);
            statement.setString(6, address);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) 
            {
                System.out.println("\nPatient Added Successfully With ID: " + id);
            }
            else
            {
                System.out.println("\nPatient Not Added.");
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

    public void viewPatient() 
    {
        try 
        {
            System.out.print("\nEnter Patient ID: ");
            String id = scanner.nextLine();
    
            String sql1 = "SELECT * FROM patient WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                System.out.println("\nPatient:");
                System.out.println("Id: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Phone Number: " + rs.getString("phoneNo"));
                System.out.println("Address: " + rs.getString("address"));
            } 
            else 
            {
                System.out.println("\nPatient Not Found With ID: " + id);
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

    public void viewAllPatients()
    {
        try 
        {
            String sql = "SELECT * FROM patient";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            boolean found = false;
            System.out.println("\nPatients:");
            while (rs.next()) 
            {
                found = true;
                System.out.println("Id: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Phone Number: " + rs.getString("phoneNo"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("-----------------------------");
            }
            if (!found) 
            {
                System.out.println("\nNo Patients Found!");
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

    public void updatePatient() 
    {
        try 
        {
            System.out.print("\nEnter Patient ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM patient WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if(rs.next())
            {
                System.out.print("\nEnter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new age: ");
                int newAge = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new gender: ");
                String newGender = scanner.nextLine();
                System.out.print("Enter new phone number: ");
                String newPhoneNo = scanner.nextLine();
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();

                String sql2 = "UPDATE patient SET name = ?, age = ?, gender = ?, phoneNo = ?, address = ? WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, newName);
                statement.setInt(2, newAge);
                statement.setString(3, newGender);
                statement.setString(4, newPhoneNo);
                statement.setString(5, newAddress);
                statement.setString(6, id);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) 
                {
                    System.out.println("\nPatient Updated Successfully With ID: " + id);
                }
                else
                {
                    System.out.println("\nPatient Not Updated With ID: " + id);
                }
            }
            else
            {
                System.out.println("\nPatient Not Found With ID: " + id);
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

    public void deletePatient() 
    {
        try 
        {
            System.out.print("\nEnter Patient ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM patient WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                String sql2 = "DELETE FROM patient WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, id);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) 
                {
                    System.out.println("\nPatient Deleted Successfully With ID: " + id);
                }
                else
                {
                    System.out.println("\nPatient Not Deleted With ID: " + id);
                }
            } 
            else 
            {
                System.out.println("\nPatient Not Found With ID: " + id);
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

    private String generatePatientId() 
    {
        String newId = "P0001";
        try
        {
            String sql = "SELECT id FROM patient ORDER BY id DESC LIMIT 1";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                String id = rs.getString("id");
                int num = Integer.parseInt(id.substring(1)) + 1;
                newId = String.format("P%04d", num);
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