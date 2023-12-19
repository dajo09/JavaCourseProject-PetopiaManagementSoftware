
package com.petopia;

import com.petopia.api.AppointmentQuery;
import com.petopia.api.PetQuery;
import com.petopiables.Functions;
import com.petopiables.PetopiaHeader;
import java.util.Scanner;

public class AppointmentInfo extends PetopiaHeader{
      Scanner sc = new Scanner(System.in);
      PetQuery petQuery = new PetQuery();
      ManageServices manageServices = new ManageServices();
      Functions fx = new Functions();
         
      public void appointmentInfo(){
        //instantiations
        AppointmentQuery apptQuery = new AppointmentQuery();
       
        petopiaHeader();
        
        System.out.format("|            PET INFORMATION            |%n");
        System.out.format("+---------------------------------------+%n");
        System.out.format("|                                       |%n");  
        System.out.format("|                                       |%n");  
        System.out.format("|      (\u001B[36m1\u001B[0m) CREATE APPOINTMENT           |%n");       
        System.out.format("|      (\u001B[36m2\u001B[0m) SHOW ALL APPOINTMENT         |%n");
        System.out.format("|      (\u001B[36m3\u001B[0m) UPDATE APPOINTMENT COST      |%n");
        System.out.format("|      (\u001B[36m4\u001B[0m) CANCEL AN APPOINTMENT        |%n");  
        System.out.format("|      (\u001B[36m5\u001B[0m) CANCEL/RETURN                |%n");
        padThreeBars();
        System.out.format("+---------------------------------------+%n");
        
    try {
        System.out.print("Enter a service: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                // INSERT INTO PET VALUES....
                // PETID WILL BE AUTOGENERATED
                petQuery.displayOwnerName();

                //validate and make use the user input is an int 
                int custId = 0;
                boolean validCustomerInputId = false;

                do {
                    System.out.print("Choose the customer's ID : ");
                    try {
                        custId = Integer.parseInt(sc.nextLine());
                        validCustomerInputId = true;

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number for the customer ID.");

                    }
                } while (!validCustomerInputId);


                System.out.print("Enter appointment date and time (YYYY-MM-DD): ");
                String appointmentDate = sc.nextLine();


                double totalCost = 0;     
                int status = 0;

                apptQuery.addNewAppointment(custId, appointmentDate, totalCost, status);

                //redirect to CRUD menu
                this.appointmentInfo();
                break;

            //DIPLAY APPOINTMENTS   
            case 2:
                apptQuery.displayAppointmentsUsingInnerJoin();

                //redirect to CRUD menu
                fx.xToCancel(this::appointmentInfo);// daff: changed code to this
                break;

            //CALCULATE THE TOTAL COST OF APPOINTMENT SERVICE AND  UPDATE ITS TOTAL COST FIELD
            case 3:
                apptQuery.displayAppointmentsUsingInnerJoin();
                System.out.println("Enter appointment id: ");
                int appTId = sc.nextInt();
                sc.nextLine();

                boolean addMoreServices = true;
                double total = 0.0;

                //loop thru so the user has an option to add more service
                do {
                    apptQuery.displayServiceDescription();
                    System.out.print("CHOOSE SERVICE (ID) NO: ");
                    int serviceId = sc.nextInt();
                    sc.nextLine();
                    double servicePrice = apptQuery.getServicePrice(serviceId);

                    // Add the service price to the total cost
                    total += servicePrice;

                    System.out.println("Service added successfully.");

                    // Prompt user to add another service
                    System.out.print("Do you want to add another service? (y/n): ");
                    String addMore = sc.nextLine();

                    if (addMore.equalsIgnoreCase("n")) {
                        addMoreServices = false;
                    }

                } while (addMoreServices);

                // Store the total cost in the other table
                apptQuery.updateTotalAmouintService(total, appTId); 

                System.out.println("");  

                //redirect to CRUD menu
                this.appointmentInfo();
                break;

            //CANCEL APPOINTMENT 
            case 4:
                apptQuery.displayAppointmentsUsingInnerJoin();
                System.out.print("Enter appointment you want to delete: ");
                int apptIdToArchived = sc.nextInt();
                sc.nextLine();
                apptQuery.cancelAppointment(apptIdToArchived);

                //redirect to CRUD menu
                this.appointmentInfo();
                break;

            default:
              System.out.println("Invalid Input! Enter choice enclosed in ().");

                //redirect to CRUD menu
                manageServices.manageServices();
            }
        } catch (Exception e) {
            System.out.println(e);
              
        } //end of try catch   
   }        
}
