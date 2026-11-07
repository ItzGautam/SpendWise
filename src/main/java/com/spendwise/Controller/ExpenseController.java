package com.spendwise.Controller;

import com.spendwise.Service.ExpenseService;
import com.spendwise.DTO.ExpenseRequestDto;
import com.spendwise.DTO.ExpenseResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Add Expense
    @PostMapping
    public ResponseEntity<ExpenseResponseDto> addExpense(
            @Valid @RequestBody ExpenseRequestDto request) {

        ExpenseResponseDto response = expenseService.addExpense(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get All Expenses
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses() {

        List<ExpenseResponseDto> expenses = expenseService.getAllExpenses();

        return ResponseEntity.ok(expenses);
    }

    // Get Expense By ID
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable Long id) {

        ExpenseResponseDto expense = expenseService.getExpenseById(id);

        return ResponseEntity.ok(expense);
    }

    // Update Expense
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDto request) {

        ExpenseResponseDto updatedExpense = expenseService.updateExpense(id, request);

        return ResponseEntity.ok(updatedExpense);
    }

    // Delete Expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }
}
