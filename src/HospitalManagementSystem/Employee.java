import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Employee 
{
    private Connection connection;
    private Scanner scanner;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    public Employee(Connection connection, Scanner scanner) 
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addEmployee() 
    {
        try 
        {
            String id = generateEmployeeId();
            System.out.print("\nEnter Employee Name: ");
            String name = scanner.nextLine();

            String sql = "INSERT INTO employee (id, name) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, name);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) 
            {
                System.out.println("\nEmployee Added Successfully With ID: " + id);
            }
            else
            {
                System.out.println("\nEmployee Not Added.");
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

    public void viewEmployee() 
    {
        try 
        {
            System.out.print("\nEnter Employee ID: ");
            String id = scanner.nextLine();

            String sql = "SELECT * FROM employee WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) 
            {
                System.out.println("\nEmployee:");
                System.out.println("Id: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
            } 
            else 
            {
                System.out.println("\nEmployee Not Found With ID: " + id);
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

    public void viewAllEmployees() 
    {
        try 
        {
            String sql = "SELECT * FROM employee";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            boolean found = false;
            System.out.println("\nEmployees:");
            while (rs.next()) 
            {
                found = true;
                System.out.println("Id: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("-----------------------------");
            }
            if (!found) 
            {
                System.out.println("\nNo Employees Found!");
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

    public void updateEmployee() 
    {
        try 
        {
            System.out.print("\nEnter Employee ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM employee WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();
            if (rs.next()) 
            {
                System.out.print("\nEnter new name: ");
                String newName = scanner.nextLine();

                String sql2 = "UPDATE employee SET name = ? WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, newName);
                statement.setString(2, id);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) 
                {
                    System.out.println("\nEmployee Updated Successfully With ID: " + id);
                }
                else
                {
                    System.out.println("\nEmployee Not Updated With ID: " + id);
                }
            }
            else
            {
                System.out.println("\nEmployee Not Found With ID: " + id);
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

    public void deleteEmployee() 
    {
        try 
        {
            System.out.print("\nEnter Employee ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM employee WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();
            if (rs.next()) 
            {
                String sql2 = "DELETE FROM employee WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, id);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) 
                {
                    System.out.println("\nEmployee Deleted Successfully With ID: " + id);
                }
                else
                {
                    System.out.println("\nEmployee Not Deleted With ID: " + id);
                }
            }
            else
            {
                System.out.println("\nEmployee Not Found With ID: " + id);
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

    private String generateEmployeeId() 
    {
        String newId = "E001";
        try
        {
            String sql = "SELECT id FROM employee ORDER BY id DESC LIMIT 1";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                String id = rs.getString("id");
                int num = Integer.parseInt(id.substring(1)) + 1;
                newId = String.format("E%03d", num);
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

    public void managePatients(Patient patient) 
    {
        while (true) 
        {
            System.out.println("\n===Patient Page===");
            System.out.println("1.Add Patient");
            System.out.println("2.View Patient");
            System.out.println("3.View All Patients");
            System.out.println("4.Update Patient");
            System.out.println("5.Delete Patient");
            System.out.println("6.Exit");

            System.out.println("\nChoose an option:");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) 
            {
                case 1:
                    patient.addPatient();
                    break;
                case 2:
                    patient.viewPatient();
                    break;
                case 3:
                    patient.viewAllPatients();
                    break;
                case 4:
                    patient.updatePatient();
                    break;
                case 5:
                    patient.deletePatient();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("\nInvalid Entry.\nTry Again.");
            }
        }
    }

    public void manageAppointments(Appointment appointment) 
    {
        while (true) 
        {
            System.out.println("\n===Appointment Page===");
            System.out.println("1.Book Appointment");
            System.out.println("2.Reschedule Appointment");
            System.out.println("3.Cancel Appointment");
            System.out.println("4.Delete Appointment");
            System.out.println("5.View Appointment Status");
            System.out.println("6.Exit");

            System.out.println("\nChoose an option:");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) 
            {
                case 1:
                    appointment.bookAppointment();
                    break;
                case 2:
                    appointment.rescheduleAppointment();
                    break;
                case 3:
                    appointment.cancelAppointment();
                    break;
                case 4:
                    appointment.deleteAppointment();
                    break;
                case 5:
                    appointment.viewAppointmentStatus();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("\nInvalid Entry.\nTry Again.");
            }
        }
    }
}