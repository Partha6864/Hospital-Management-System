import java.util.Scanner;

public class User 
{
    private Scanner scanner;

    public User(Scanner scanner) 
    {
        this.scanner = scanner;
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
            System.out.println("5.Exit");

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
                    return;
                default:
                    System.out.println("Invalid option");
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
            System.out.println("4.View Appointment Status");
            System.out.println("5.Exit");

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
                    appointment.viewAppointmentStatus();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\nInvalid Entry.\nTry Again.");
            }
        }
    }
}