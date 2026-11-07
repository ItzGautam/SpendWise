package com.spendwise.Service;

import com.spendwise.DTO.ExpenseRequestDto;
import com.spendwise.DTO.ExpenseResponseDto;
import com.spendwise.Exception.ExpenseNotFoundException;
import com.spendwise.Repository.ExpenseRepository;
import com.spendwise.entity.Expense;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Convert Entity to Response DTO
    private ExpenseResponseDto convertToResponse(Expense expense) {

        ExpenseResponseDto response = new ExpenseResponseDto();

        response.setId(expense.getId());
        response.setTitle(expense.getTitle());
        response.setAmount(expense.getAmount());
        response.setCategory(expense.getCategory());
        response.setDescription(expense.getDescription());
        response.setCreatedAt(expense.getCreatedAt());
        response.setUpdatedAt(expense.getUpdatedAt());

        return response;
    }

    // Add Expense
    public ExpenseResponseDto addExpense(ExpenseRequestDto request) {

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());

        LocalDateTime now = LocalDateTime.now();

        expense.setCreatedAt(now);
        expense.setUpdatedAt(now);

        Expense savedExpense = expenseRepository.save(expense);

        return convertToResponse(savedExpense);
    }

    // Get All Expenses
    public List<ExpenseResponseDto> getAllExpenses() {

        List<Expense> expenses = expenseRepository.findAll();

        return expenses.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get Expense By ID
    public ExpenseResponseDto getExpenseById(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with ID: " + id
                        ));

        return convertToResponse(expense);
    }

    // Update Expense
    public ExpenseResponseDto updateExpense(
            Long id,
            ExpenseRequestDto request) {

        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with ID: " + id
                        ));

        existingExpense.setTitle(request.getTitle());
        existingExpense.setAmount(request.getAmount());
        existingExpense.setCategory(request.getCategory());
        existingExpense.setDescription(request.getDescription());

        existingExpense.setUpdatedAt(LocalDateTime.now());

        Expense updatedExpense = expenseRepository.save(existingExpense);

        return convertToResponse(updatedExpense);
    }

    // Delete Expense
    public void deleteExpense(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with ID: " + id
                        ));

        expenseRepository.delete(expense);
    }
}