import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try 
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?useSSL=false", "root", "Partha@6864");

            Admin admin = new Admin(scanner);
            Employee employee = new Employee(connection, scanner);
            Patient patient = new Patient(connection, scanner);
            Appointment appointment = new Appointment(connection, scanner);
            User user = new User(scanner);
            Doctor doctor = new Doctor(connection, scanner);

            while (true) {
                System.out.println("\n===Login Page===");
                System.out.println("1.Admin");
                System.out.println("2.Employee");
                System.out.println("3.User");
                System.out.println("4.Exit");

                System.out.println("\nChoose an option:");
                int loginOption = scanner.nextInt();
                scanner.nextLine();

                switch (loginOption) {
                    case 1:
                        while (true) {
                            System.out.println("\n===Admin Page===");
                            System.out.println("1.Doctor");
                            System.out.println("2.Employee");
                            System.out.println("3.Exit");

                            System.out.println("\nChoose an option:");
                            int adminOption = scanner.nextInt();
                            scanner.nextLine();

                            switch (adminOption) {
                                case 1:
                                    admin.manageDoctors(doctor);
                                    break;
                                case 2:
                                    admin.manageEmployees(employee);
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Invalid option");
                            }
                            if (adminOption == 3) break;
                        }
                        break;
                    case 2:
                        while (true) {
                            System.out.println("\n===Employee Page===");
                            System.out.println("1.Patient");
                            System.out.println("2.Appointment");
                            System.out.println("3.Exit");

                            System.out.println("\nChoose an option:");
                            int employeeOption = scanner.nextInt();
                            scanner.nextLine();

                            switch (employeeOption) {
                                case 1:
                                    employee.managePatients(patient);
                                    break;
                                case 2:
                                    employee.manageAppointments(appointment);
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("\nInvalid Entry.\nTry Again.");
                            }
                            if (employeeOption == 3) break;
                        }
                        break;
                    case 3:
                        while (true) {
                            System.out.println("\n===User Page===");
                            System.out.println("1.Patient");
                            System.out.println("2.Appointment");
                            System.out.println("3.Exit");

                            System.out.println("\nChoose an option:");
                            int userOption = scanner.nextInt();
                            scanner.nextLine();

                            switch (userOption) {
                                case 1:
                                    user.managePatients(patient);
                                    break;
                                case 2:
                                    user.manageAppointments(appointment);
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("\nInvalid Entry.\nTry Again.");
                            }
                            if (userOption == 3) break;
                        }
                        break;
                    case 4:
                        System.out.println("Thank you for visiting...");
                        return;
                    default:
                        System.out.println("\nInvalid Entry.\nTry Again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        scanner.close();
    }
}