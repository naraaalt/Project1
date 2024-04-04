package javaapplication2;
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
    private static String username; // store username??

    @Override
     void performAction(String[] apartments, double[] prices, int[] availableRooms, int[] rentedRooms, String[] rentalDates) {
        JPanel panel = new JPanel();
        JTextField usernameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JComboBox<String> apartmentComboBox = new JComboBox<>(apartments);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new JLabel("Select an apartment:"));
        panel.add(apartmentComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Rent Apartment",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            username = usernameField.getText();
                if (username == null || username.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter your name!", "Alert", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            String email = emailField.getText();
                if (!isValidEmail(email)){
                    JOptionPane.showMessageDialog(null, "Please enter a valid email!");
                    return;
                }
            int apartmentIndex = apartmentComboBox.getSelectedIndex();
 
                
            if (apartmentIndex >= 0 && apartmentIndex < apartments.length) {
                if (availableRooms[apartmentIndex] > 0) {
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date currentDate = new Date();
                    rentalDates[apartmentIndex] = dateFormat.format(currentDate);
                    JOptionPane.showMessageDialog(null, "Hello " + username + "! You've rented " + apartments[apartmentIndex] + " for Rp." + prices[apartmentIndex] + ". Your rent starts from: " + rentalDates[apartmentIndex]);
                    availableRooms[apartmentIndex]--;
                    rentedRooms[apartmentIndex]++;
                } else {
                    JOptionPane.showMessageDialog(null, "Sorry, no rooms available in this apartment.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid apartment selection.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Renting Cancelled.", "Alert", JOptionPane.INFORMATION_MESSAGE);
        }
     
     }
        
        private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
        }
        static String getUsername(){
            return username;
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
                + "Name: " + RentApartment.getUsername()+ "\n";

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
