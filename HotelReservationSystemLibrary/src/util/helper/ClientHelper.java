/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package util.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;


public class ClientHelper {
    
    // Display details of a single object, neatly formatted
    public static void displayObjectDetails(Object obj) {
        if (obj == null) {
            System.out.println("No object to display.");
            return;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        System.out.println("Object Details:");
        for (Field field : fields) {
            String fieldName = field.getName();
            String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            
            try {
                Method getter = clazz.getMethod(getterName);
                Object value = getter.invoke(obj);
                System.out.printf("%-25s: %-25s%n", fieldName, value != null ? value.toString() : "null");            
            } catch (NoSuchMethodException e) {
                System.out.println("No getter found for field: " + fieldName);
            } catch (Exception e) {
                System.out.println("Error accessing field: " + fieldName);
                e.printStackTrace();
            }
        }
    }

    // Display a table of objects, with neat formatting and optional sorting
    public static <T> void displayTable(List<T> objects, String sortByField) {
        if (objects == null || objects.isEmpty()) {
            System.out.println("No data to display.");
            return;
        }

        // Get the class of the objects
        Class<?> clazz = objects.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        // Print table header (field names)
        System.out.printf("%-20s", "Index");
        for (Field field : fields) {
            System.out.printf("%-25s", field.getName());
        }
        System.out.println();

        // Print a separator line to separate the header
        System.out.printf("%-20s", "-----");
        for (Field field : fields) {
            System.out.printf("%-25s", "-----");
        }
        System.out.println();

        // Print table rows (object data)
        int index = 1;
        for (T obj : objects) {
            System.out.printf("%-20d", index++);
            for (Field field : fields) {
                String fieldName = field.getName();
                String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                try {
                    Method getter = clazz.getMethod(getterName);
                    Object value = getter.invoke(obj);
                    System.out.printf("%-25s", value != null ? value.toString() : "null");
                } catch (Exception e) {
                    System.out.printf("%-25s", "Error");
                }
            }
            System.out.println();
        }
    }

    
    
    public static void displayNumberedList(List<?> objects) {
        int index = 1;
        for (Object obj : objects) {
            System.out.println(index + ": " + obj.toString());
            index++;
        }
    }
    
    
}
