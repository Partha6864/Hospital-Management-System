import java.util.Scanner;

public class Admin {
    private Scanner scanner;

    public Admin(Scanner scanner) {
        this.scanner = scanner;
    }

    public void manageDoctors(Doctor doctor) {
        while (true) {
            System.out.println("\n===Doctor Page===");
            System.out.println("1.Add Doctor");
            System.out.println("2.View Doctor");
            System.out.println("3.View All Doctors");
            System.out.println("4.Update Doctor");
            System.out.println("5.Delete Doctor");
            System.out.println("6.Exit");

            System.out.println("\nChoose an option:");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    doctor.addDoctor();
                    break;
                case 2:
                    doctor.viewDoctor();
                    break;
                case 3:
                    doctor.viewAllDoctors();
                    break;
                case 4:
                    doctor.updateDoctor();
                    break;
                case 5:
                    doctor.deleteDoctor();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("\nInvalid Entry.\nTry Again.");
            }
        }
    }

    public void manageEmployees(Employee employee) {
        while (true) {
            System.out.println("\n===Employee Page===");
            System.out.println("1.Add Employee");
            System.out.println("2.View Employee");
            System.out.println("3.View All Employees");
            System.out.println("4.Update Employee");
            System.out.println("5.Delete Employee");
            System.out.println("6.Exit");

            System.out.println("\nChoose an option:");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    employee.addEmployee();
                    break;
                case 2:
                    employee.viewEmployee();
                    break;
                case 3:
                    employee.viewAllEmployees();
                    break;
                case 4:
                    employee.updateEmployee();
                    break;
                case 5:
                    employee.deleteEmployee();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("\nInvalid Entry.\nTry Again.");
            }
        }
    }
}