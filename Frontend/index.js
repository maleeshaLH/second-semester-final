document.addEventListener("DOMContentLoaded", function() {
    const employeeCardsContainer = document.getElementById("employeeCards");
    const employeeForm = document.getElementById("employeeForm");
    const addEmployeeBtn = document.getElementById("addEmployeeBtn");
    const searchBox = document.getElementById("searchBox");

    let employees = [
        { id: 1, name: "Ankit", gender: "Male", department: "HR", city: "Hyderabad" },
        { id: 2, name: "Rahul", gender: "Male", department: "Marketing", city: "Chennai" },
        { id: 3, name: "Swati", gender: "Female", department: "Marketing", city: "New Delhi" }
    ];

    function renderCards(data) {
        employeeCardsContainer.innerHTML = "";
        data.forEach(employee => {
            const card = document.createElement("div");
            card.classList.add("employee-card");

            card.innerHTML = `
                <h3>${employee.name}</h3>
                <p><strong>Gender:</strong> ${employee.gender}</p>
                <p><strong>Department:</strong> ${employee.department}</p>
                <p><strong>City:</strong> ${employee.city}</p>
                <div>
                    <button class="action-btn edit-btn" onclick="editEmployee(${employee.id})">Edit</button>
                    <button class="action-btn delete-btn" onclick="deleteEmployee(${employee.id})">Delete</button>
                </div>
            `;

            employeeCardsContainer.appendChild(card);
        });
    }

    function addEmployee() {
        employeeForm.style.display = "block";
    }

    function saveEmployee() {
        const name = document.getElementById("employeeName").value;
        const gender = document.getElementById("employeeGender").value;
        const department = document.getElementById("employeeDepartment").value;
        const city = document.getElementById("employeeCity").value;
        const newId = employees.length + 1;

        employees.push({ id: newId, name, gender, department, city });
        renderCards(employees);
        employeeForm.style.display = "none";
    }

    function cancelForm() {
        employeeForm.style.display = "none";
    }

    function deleteEmployee(id) {
        employees = employees.filter(emp => emp.id !== id);
        renderCards(employees);
    }

    function searchEmployee() {
        const searchText = searchBox.value.toLowerCase();
        const filteredEmployees = employees.filter(emp =>
            emp.name.toLowerCase().includes(searchText) ||
            emp.department.toLowerCase().includes(searchText) ||
            emp.city.toLowerCase().includes(searchText)
        );
        renderCards(filteredEmployees);
    }

    addEmployeeBtn.addEventListener("click", addEmployee);
    searchBox.addEventListener("input", searchEmployee);

    renderCards(employees);
});
