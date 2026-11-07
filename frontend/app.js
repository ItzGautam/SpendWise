
const API_URL = "http://localhost:8080/api/expenses";


// Get HTML elements

const expenseForm = document.getElementById("expenseForm");

const title = document.getElementById("title");
const amount = document.getElementById("amount");
const category = document.getElementById("category");
const description = document.getElementById("description");

const expenseId = document.getElementById("expenseId");

const totalAmount = document.getElementById("totalAmount");
const expenseList = document.getElementById("expenseList");

const message = document.getElementById("message");

const formHeading = document.getElementById("formHeading");
const saveButton = document.getElementById("saveButton");
const cancelButton = document.getElementById("cancelButton");
const refreshButton = document.getElementById("refreshButton");


// 1. Show message

function showMessage(text,type) {

    message.textContent = text;
    message.className = type;
    
}


// 2. Reset form

function resetForm() {

    expenseForm.reset();

    expenseId.value = "";

    formHeading.textContent = "Add an Expense";

    saveButton.textContent = "Add Expense";

    cancelButton.classList.add("hidden");

}

// 3. Get all expenses from backend

async function getExpenses() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to get expenses");
        }

        const expenses = await response.json();

        displayExpenses(expenses);

    }

    catch (error) {

        expenseList.textContent = "Unable to load expenses.";

        showMessage(error.message, "error");

        console.log(error);

    }

}

// 4. Display expenses on webpage

function displayExpenses(expenses) {

    expenseList.innerHTML = "";

    let total = 0;

    if (expenses.length === 0) {

        expenseList.textContent = "No expenses added yet.";

        totalAmount.textContent = "₹0.00";

        return;

    }

    for (let i = 0; i < expenses.length; i++) {

        let expense = expenses[i];

        total = total + Number(expense.amount);


        // Create a div

        let div = document.createElement("div");

        div.className = "expense-row";


        // Expense title

        let heading = document.createElement("h3");

        heading.textContent = expense.title;


        // Expense amount

        let amountText = document.createElement("p");

        amountText.textContent = "Amount: ₹" + expense.amount;


        // Expense category

        let categoryText = document.createElement("p");

        categoryText.textContent = "Category: " + expense.category;


        // Expense description

        let descriptionText = document.createElement("p");

        descriptionText.textContent =
            "Description: " + (expense.description || "No description");


        // Edit button

        let editButton = document.createElement("button");

        editButton.textContent = "Edit";

        editButton.onclick = function() {

            editExpense(expense);

        };


        // Delete button

        let deleteButton = document.createElement("button");

        deleteButton.textContent = "Delete";

        deleteButton.onclick = function() {

            deleteExpense(expense.id);

        };


        // Add elements to div

        div.appendChild(heading);

        div.appendChild(amountText);

        div.appendChild(categoryText);

        div.appendChild(descriptionText);

        div.appendChild(editButton);

        div.appendChild(deleteButton);


        // Add div to webpage

        expenseList.appendChild(div);

    }


    // Calculate total expenses

    totalAmount.textContent = "₹" + total.toFixed(2);

}

// 5. Add or update expense

expenseForm.addEventListener("submit", async function(event) {

    event.preventDefault();


    // Get values from form

    let expense = {

        title: title.value.trim(),

        amount: Number(amount.value),

        category: category.value.trim(),

        description: description.value.trim()

    };


    // Check form values

    if (
        expense.title === "" ||
        expense.category === "" ||
        !Number.isFinite(expense.amount) ||
        expense.amount <= 0
    ) {

        showMessage("Please enter valid details.", "error");

        return;

    }


    // Check whether adding or updating

    let id = expenseId.value;

    let url = API_URL;

    let method = "POST";


    if (id !== "") {

        url = API_URL + "/" + id;

        method = "PUT";

    }


    try {

        const response = await fetch(url, {

            method: method,

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(expense)

        });


        if (!response.ok) {

            throw new Error("Unable to save expense");

        }


        if (id === "") {

            showMessage("Expense added successfully!", "success");

        }

        else {

            showMessage("Expense updated successfully!", "success");

        }


        resetForm();

        await getExpenses();

    }

    catch (error) {

        showMessage(error.message, "error");

        console.log(error);

    }

});


// 6. Edit expense

function editExpense(expense) {

    expenseId.value = expense.id;

    title.value = expense.title;

    amount.value = expense.amount;

    category.value = expense.category;

    description.value = expense.description;


    formHeading.textContent = "Edit Expense";

    saveButton.textContent = "Update Expense";

    cancelButton.classList.remove("hidden");

}


// 7. Delete expense

async function deleteExpense(id) {

    let choice = confirm("Are you sure you want to delete this expense?");

    if (choice === false) {

        return;

    }


    try {

        const response = await fetch(API_URL + "/" + id, {

            method: "DELETE"

        });


        if (!response.ok) {

            throw new Error("Unable to delete expense");

        }


        showMessage("Expense deleted successfully!", "success");

        if (expenseId.value === String(id)) {

            resetForm();

        }

        await getExpenses();

    }

    catch (error) {

        showMessage(error.message, "error");

        console.log(error);

    }

}


// 8. Cancel editing

cancelButton.addEventListener("click", function() {

    resetForm();

    showMessage("Editing cancelled.", "success");

});

refreshButton.addEventListener("click", function() {

    getExpenses();

});


// 10. Load expenses when page opens

getExpenses();
