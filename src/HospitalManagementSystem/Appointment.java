import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointment 
{
    private Connection connection;
    private Scanner scanner;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    public Appointment(Connection connection, Scanner scanner) 
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void bookAppointment() 
    {
        try 
        {
            String id = generateAppointmentId();
            System.out.print("\nEnter Patient ID: ");
            String patientId = scanner.nextLine();
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine();
            System.out.print("Enter Appointment Date (DD-MM-YYYY): ");
            String appointmentDate = scanner.nextLine();

            String sql = "INSERT INTO appointment (id, patient_id, doctor_id, appointment_date, status) VALUES (?, ?, ?, ?, 'Booked')";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, patientId);
            statement.setString(3, doctorId);
            statement.setString(4, appointmentDate);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) 
            {
                System.out.println("\nAppointment Booked Successfully With ID: " + id);
            }
            else
            {
                System.out.println("\nAppointment Not Booked.");
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

    public void rescheduleAppointment() 
    {
        try 
        {
            System.out.print("\nEnter Appointment ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM appointment WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                System.out.print("\nEnter New Appointment Date (DD-MM-YYYY): ");
                String newDate = scanner.nextLine();

                String sql2 = "UPDATE appointment SET status = 'Rescheduled', appointment_date = ? WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, newDate);
                statement.setString(2, id);
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) 
                {
                    System.out.println("\nAppointment Rescheduled Successfully With ID: " + id);
                }
                else
                {
                    System.out.println("\nAppointment Not Rescheduled With ID: " + id);
                }
            }
            else
            {
                System.out.println("\nAppointment Not Found With ID: " + id);
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

    public void cancelAppointment() 
    {
        try 
        {
            System.out.print("\nEnter Appointment ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM appointment WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                String sql2 = "UPDATE appointment SET status = 'Canceled' WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, id);
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) 
                {
                    System.out.println("\nAppintment Canceled Successfully With ID: " + id);
                }
                else
                {
                    System.out.println("\nAppintment Not Canceled With ID: " + id);
                }
            }
            else
            {
                System.out.println("\nAppintment Not Found With ID: " + id);
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

    public void viewAppointmentStatus() 
    {
        try 
        {
            System.out.print("\nEnter Appointment ID: ");
            String id = scanner.nextLine();

            String sql = "SELECT status FROM appointment WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                System.out.println("Appointment Status: " + rs.getString("status"));
            } 
            else 
            {
                System.out.println("Appointment Not Found With ID: " + id);
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

    public void deleteAppointment()
    {
        try 
        {
            System.out.print("\nEnter Appointment ID: ");
            String id = scanner.nextLine();

            String sql1 = "SELECT * FROM appointment WHERE id = ?";
            statement = connection.prepareStatement(sql1);
            statement.setString(1, id);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                if ("Canceled".equals(rs.getString("status"))) 
                {
                    String sql2 = "DELETE FROM appointment WHERE id = ?";
                    statement = connection.prepareStatement(sql2);
                    statement.setString(1, id);
                    int rowsDeleted = statement.executeUpdate();

                    if (rowsDeleted > 0) 
                    {
                        System.out.println("\nAppointment Deleted Successfully With ID: " + id);
                    }
                    else
                    {
                        System.out.println("\nAppointment Not Deleted With ID: " + id);
                    }
                }
                else
                {
                    System.out.println(id + " Not A Canceled Appointment.");
                }
            }
            else
            {
                System.out.println("\nAppointment Not Found With ID: " + id);
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

    private String generateAppointmentId() 
    {
        String newId = "A0001";
        try
        {
            String sql = "SELECT id FROM appointment ORDER BY id DESC LIMIT 1";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
                String id = rs.getString("id");
                int num = Integer.parseInt(id.substring(1)) + 1;
                newId = String.format("A%04d", num);
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