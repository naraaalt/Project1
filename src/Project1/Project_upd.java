package Project1;
import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Project_upd {

    public static void main(String[] args) {
        int option;
        String[] apartments = {"Normal", "VIP", "Deluxe"};
        double[] prices = {1200000, 2000000, 2800000};
        int[] availableRooms = {40, 32, 30};
        int[] rentedRooms = {0, 0, 0};
        String[] rentalDates = {"", "", ""};
        boolean exitProgram = false;

        MenuOption[] menuOptions = {
                new RentApartment(),
                new RoomDetails(),
                new CheckAvailableRooms(),
                new DisplayPaymentSchedule()
                
        };

        while (!exitProgram) {

            String input = JOptionPane.showInputDialog(null,
                    "Welcome!\n"
                            + "Please Choose a number:\n"
                            + "1. Rent an apartment\n"
                            + "2. Room Details\n"
                            + "3. Check for available rooms\n"
                            + "4. Payment schedule\n", "APARTMENT RENTAL PROGRAM", JOptionPane.INFORMATION_MESSAGE);

            if (input == null) {
                int quit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "EXIT", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (quit == JOptionPane.YES_OPTION) {
                    exitProgram = true;
                    System.exit(0);
                }
                continue;
            }
            
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", "WARNING", JOptionPane.ERROR_MESSAGE);
                continue;
            }
                
            try {
                    option = Integer.parseInt(input);
                    
            if (option >= 1 && option <= menuOptions.length) {
                menuOptions[option - 1].performAction(apartments, prices, availableRooms, rentedRooms, rentalDates);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Option.");
                    
            } 
                }catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Option.");
            }
        }
    }
}

abstract class MenuOption {
    abstract void performAction(String[] apartments, double[] prices, int[] availableRooms, int[] rentedRooms, String[] rentalDates);
}

class RentApartment extends MenuOption {
    private static String name, rentDate; // store username??

    @Override
     void performAction(String[] apartments, double[] prices, int[] availableRooms, int[] rentedRooms, String[] rentalDates) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField nameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField dateField = new JTextField(20);
        JComboBox<String> apartComboBox =  new JComboBox<>(apartments);
        
        // Name textbox
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        panel.add(namePanel);
        
        // Email textbox
        JPanel emailPanel = new JPanel();
        emailPanel.add(new JLabel("Email:"));
        emailPanel.add(emailField);
        panel.add(emailPanel);
        
        // Date textbox
        JPanel datePanel = new JPanel();
        datePanel.add (new JLabel("Rent Date (dd-mm-yyyy: )"));
        datePanel.add (dateField);
        panel.add(datePanel);
        
        // Apartment list
        JPanel apartPanel = new JPanel();
        apartPanel.add (new JLabel("Select an Apartment:"));
        apartPanel.add(apartComboBox);
        panel.add(apartPanel);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Rent Apartment", 
                + JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION){
            name = nameField.getText();
            if (name == null || name.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter your name!", "Alert",  JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            String email = emailField.getText();
            if (!emailValid(email)){
                JOptionPane.showMessageDialog(null, "Please enter a valid Email!", "Alert", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            rentDate = dateField.getText();
            
            if (!validDate(rentDate)){
                JOptionPane.showConfirmDialog(null, "Please enter a valid date.");
                return;
            }
            
            int apartIndex = apartComboBox.getSelectedIndex();
            
            if (apartIndex >= 0 && apartIndex < apartments.length){
                if (availableRooms[apartIndex] > 0){
                    rentalDates[apartIndex] = rentDate;
                    JOptionPane.showMessageDialog(null, "Hello " + name + "! You have rented " + apartments[apartIndex] + "for Rp. " + prices[apartIndex] + ". Your rent starts from: " + rentalDates[apartIndex]);
                    availableRooms[apartIndex]--;
                    rentedRooms[apartIndex]++;
                }else {
                    JOptionPane.showMessageDialog(null, "Sorry no rooms are available currently.");
                }
            }else {
                JOptionPane.showMessageDialog(null, "Invalid apartment selection");
            }
        }else {
            JOptionPane.showMessageDialog(null, "Renting cancelled.", "Alert", JOptionPane.ERROR_MESSAGE);
        }
   }
     
     private boolean emailValid(String email){
         String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
         return email.matches(emailRegex);
     }
     
     private boolean validDate (String date){
         try{
             SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
             dateFormat.setLenient(false);
             dateFormat.parse(date);
             return true;
         }catch(ParseException e){   
             return false;
         }
        
     }
     
     static String getName(){
         return name;
     }
}

class RoomDetails extends MenuOption {

    @Override
    void performAction(String[] apartments, double[] prices, int[] availableRooms, int[] rentedRooms, String[] rentalDates) {
        String[] options = {"Normal", "VIP", "Deluxe"};
        String[] descriptions = {
                "Price: Rp. 1.200.000\n"+
                "Facilities:\n" +
                "- Normal size bed\n" +
                "- TV\n" +
                "- Fridge Freezer\n" +
                "- Hairdryer\n" +
                "- Kitchen Set\n" +
                "- Air-Conditioner\n" +
                "- Electric Hob",
                
                "Price: Rp. 2.000.000\n" +
                "Facilities:\n" +
                "- Queen size bed\n" +
                "- TV\n" +
                "- Fridge Freezer\n" +
                "- Hairdryer\n" +
                "- Kitchen Set\n" +
                "- Air-Conditioner\n" +
                "- Baby cot\n" +
                "- Electric Hob",

                "Price: Rp. 2.800.000\n" + 
                "Facilities:\n" +
                "- King size bed\n" +
                "- TV\n" +
                "- Fridge Freezer\n" +
                "- Hairdryer\n" +
                "- Kitchen Set\n" +
                "- Air-Conditioner\n" +
                "- Baby cot\n" +
                "- Dining Table with 6 Chairs\n" +
                "- Oven\n" +
                "- Electric Hob"
        };

        JComboBox<String> comboBox = new JComboBox<>(options);
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);

        comboBox.addActionListener(e -> {
            int selectedIndex = comboBox.getSelectedIndex();
            descriptionArea.setText(descriptions[selectedIndex]);
        });

        JPanel panel = new JPanel();
        panel.add(comboBox);
        panel.add(descriptionArea);
        
        panel.setPreferredSize(new java.awt.Dimension(110,200));

        JOptionPane.showMessageDialog(null, panel,"Room Facilities", JOptionPane.PLAIN_MESSAGE);
    }
}

class CheckAvailableRooms extends MenuOption {

    @Override
    void performAction(String[] apartments, double[] prices, int[] availableRooms, int[] rentedRooms, String[] rentalDates) {
        String availableRoomsInfo = "Available Rooms:\n";
        for (int i = 0; i < apartments.length; i++) {
            availableRoomsInfo += apartments[i] + ": " + availableRooms[i] + " rooms\n";
        }
        JOptionPane.showMessageDialog(null, availableRoomsInfo);
    }
}

class DisplayPaymentSchedule extends MenuOption {
    @Override
    void performAction(String[] apartments, double[] prices, int[] availableRooms, int[] rentedRooms, String[] rentalDates) {
       boolean hasRentedRoom = false;
        for (int rentedRoom : rentedRooms) {
            if (rentedRoom > 0) {
                hasRentedRoom = true;
                break;
            }
        }

        if (!hasRentedRoom) {
            JOptionPane.showMessageDialog(null, "You have not rented a room yet.");
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String paymentSchedule = "Payment Schedule for:\n"
                + "Name: " + RentApartment.getName()+ "\n";

        for (int i = 0; i < apartments.length; i++) {
            if (rentedRooms[i] > 0) {
                for (int j = 0; j < rentedRooms[i]; j++) {
                    try {
                        calendar.setTime(dateFormat.parse(rentalDates[i]));
                        calendar.add(Calendar.MONTH, j);
                        Date nextPaymentDate = calendar.getTime();
                        paymentSchedule +="Type: " + apartments[i] + "\n" + "Price: Rp. " + prices[i] + "\n" + "Due on " + dateFormat.format(nextPaymentDate) + "\n";

                        calendar.setTime(dateFormat.parse(rentalDates[i]));
                        calendar.add(Calendar.MONTH, j + 1);
                        Date nextMonthPaymentDate = calendar.getTime();
                        paymentSchedule += "Next payment due on " + dateFormat.format(nextMonthPaymentDate) + "\n";

                    } catch (ParseException e) {
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, paymentSchedule);
    }
}
