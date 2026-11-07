
package com.spendwise.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ExpenseRequestDto {

    @NotBlank(message = "Title cannot be Empty or only spaces")
    @Size(max = 100, message = "Size should be less than 100 characters")
    private String title;

    @NotNull
    @Positive(message = "Amount cannot be Negative")
    private Double amount;

    @NotBlank
    private String category;

    @Size(max = 500, message = "Should be less than 500 words")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

