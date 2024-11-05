package util.clientHelper;

import java.util.Date;
import java.util.List;

public class ClientHelper {

    // 1. Validation Methods

    // Validate string length with min and max length constraints
    public static boolean validateStringLength(String input, int minLength, int maxLength) {
        if (input == null || input.length() < minLength || input.length() > maxLength) {
            System.out.println("Error: Input length must be between " + minLength + " and " + maxLength + " characters.");
            return false;
        }
        return true;
    }

    // Validate email format using regex
    public static boolean validateEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email == null || !email.matches(emailRegex)) {
            System.out.println("Error: Invalid email format.");
            return false;
        }
        return true;
    }

    // Validate if a date is after today
    public static boolean validateDateAfterToday(Date date) {
        Date today = new Date();
        if (date == null || !date.after(today)) {
            System.out.println("Error: Date must be after today.");
            return false;
        }
        return true;
    }

    // Validate if Date A is after Date B
    public static boolean validateDateAfterDateB(Date dateA, Date dateB) {
        if (dateA == null || dateB == null || !dateA.after(dateB)) {
            System.out.println("Error: Date A must be after Date B.");
            return false;
        }
        return true;
    }

    // Validate non-empty string
    public static boolean validateNotEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Error: Input cannot be empty.");
            return false;
        }
        return true;
    }

    // Validate integer is within a specified range
    public static boolean validateIntRange(int value, int min, int max) {
        if (value < min || value > max) {
            System.out.println("Error: Value must be between " + min + " and " + max + ".");
            return false;
        }
        return true;
    }

    // 2. Menu Generation Method

    // Generate a menu with a title and a list of numbered options
    public static void generateMenu(String menuTitle, List<String> options) {
        System.out.println("=== " + menuTitle + " ===");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("Select an option: ");
    }

    // 3. Display Numbered List Methods

    // Display a numbered list of objects (for selection or viewing)
    public static <T> void displayNumberedList(List<T> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).toString());
        }
    }

    // Display a numbered list specifically for selection purposes
    public static <T> void displayNumberedListForSelection(List<T> items) {
        System.out.println("Please select from the following options:");
        displayNumberedList(items);
        System.out.println("Enter your selection: ");
    }
}

