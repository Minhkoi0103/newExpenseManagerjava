package com.expensemanager.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetMax {

   /* public static double getTotalDate(List<Expense> expenses, String date) {
        double total = 0;

        for (Expense expense : expenses) {
            double amount = Double.parseDouble(expense.getAmount());
            total += amount;
        }
        return total;
    }*/

    public static String getMaxCategory(List<Expense> expenses) {
        Map<String, Double> totalByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(expense -> Double.parseDouble(expense.getAmount()))
                ));

        return Collections.max(
                totalByCategory.entrySet(),
                Map.Entry.comparingByValue()
        ).getKey();
    }
}
