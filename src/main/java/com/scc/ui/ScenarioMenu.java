package com.scc.ui;

import com.scc.dao.ScenarioDao;
import com.scc.model.ScenarioEntity;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ScenarioMenu {

    private static final String LINE_50 = "=".repeat(50);
    private static final String LINE_72 = "=".repeat(72);
    private static final String BORDER =
            "+----+--------------------+------------+--------------+----------------+";

    private final Scanner scanner = new Scanner(System.in);
    private final ScenarioDao dao = new ScenarioDao();

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            if (input.equals("1")) {
                addJobOrder();
            } else if (input.equals("2")) {
                viewJobOrders();
            } else if (input.equals("3")) {
                System.out.println("\nExiting system... Thank you!");
                running = false;
            } else {
                System.out.println("\nInvalid choice. Please enter 1, 2, or 3.\n");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println(LINE_50);
        System.out.println("            LAUNDRY SHOP JOB ORDER SYSTEM");
        System.out.println(LINE_50);
        System.out.println("[1] Add New Job Order");
        System.out.println("[2] View Active Job Orders");
        System.out.println("[3] Exit System");
        System.out.println(LINE_50);
    }

    private void addJobOrder() {
        System.out.println("\n--- ADD NEW JOB ORDER ---");

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("\nERROR: Customer name cannot be empty.\n");
            return;
        }

        double weight;
        double amount;
        try {
            System.out.print("Enter Weight (KG): ");
            weight = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter Total Amount: ");
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\nERROR: Weight and amount must be valid numbers.\n");
            return;
        }

        if (weight <= 0 || amount < 0) {
            System.out.println("\nERROR: Weight must be positive and amount cannot be negative.\n");
            return;
        }

        System.out.print("Enter Status (RECEIVED / WASHING / READY): ");
        String status = scanner.nextLine().trim().toUpperCase();
        if (!status.equals("RECEIVED") && !status.equals("WASHING") && !status.equals("READY")) {
            System.out.println("\nERROR: Status must be RECEIVED, WASHING, or READY.\n");
            return;
        }

        ScenarioEntity entity = new ScenarioEntity(0, name, weight, amount, status);

        if (dao.insert(entity)) {
            System.out.println("\nSUCCESS: Laundry job order created!\n");
        } else {
            System.out.println("\nERROR: Could not save the job order.\n");
        }
    }

    private void viewJobOrders() {
        List<ScenarioEntity> orders = dao.findAll();

        System.out.println();
        System.out.println(LINE_72);
        System.out.println("                           ACTIVE LAUNDRY ORDERS");
        System.out.println(LINE_72);
        System.out.println(BORDER);
        System.out.printf("| %-2s | %-18s | %-10s | %-12s | %-14s |%n",
                "ID", "CUSTOMER NAME", "WEIGHT_KG", "TOTAL AMOUNT", "STATUS");
        System.out.println(BORDER);

        for (ScenarioEntity o : orders) {
            System.out.printf(Locale.US, "| %-2d | %-18s | %-10.1f | %-12.2f | %-14s |%n",
                    o.id(), o.customerName(), o.weightKg(), o.totalAmount(), o.status());
        }

        System.out.println(BORDER);
        System.out.println("Total Records Displayed: " + orders.size());
        System.out.println();
    }
}