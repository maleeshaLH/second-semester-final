document.addEventListener("DOMContentLoaded", function() {
    const employeeTableBody = document.getElementById("employeeTableBody");
    const searchBox = document.getElementById("searchBox");

    let employees = [
        { id: 1, name: "Ankit", gender: "Male", department: "HR", city: "Hyderabad" },
        { id: 2, name: "Rahul", gender: "Male", department: "Marketing", city: "Chennai" },
        { id: 3, name: "Swati", gender: "Female", department: "Marketing", city: "New Delhi" }
    ];

    function renderTable(data) {
        employeeTableBody.innerHTML = "";
        data.forEach(employee => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${employee.id}</td>
                <td>${employee.name}</td>
                <td>${employee.gender}</td>
                <td>${employee.department}</td>
                <td>${employee.city}</td>
                <td>
                    <button class="action-btn edit-btn" onclick="editEmployee(${employee.id})">Edit</button>
                    <button class="action-btn delete-btn" onclick="deleteEmployee(${employee.id})">Delete</button>
                </td>
            `;

            employeeTableBody.appendChild(row);
        });
    }

    function addEmployee() {
        const newId = employees.length + 1;
        const newEmployee = {
            id: newId,
            name: "New Employee",
            gender: "N/A",
            department: "N/A",
            city: "N/A"
        };
        employees.push(newEmployee);
        renderTable(employees);
    }

    function editEmployee(id) {
        alert(`Edit Employee ID: ${id}`);
        // Add code for editing employee here
    }

    function deleteEmployee(id) {
        employees = employees.filter(emp => emp.id !== id);
        renderTable(employees);
    }

    function searchEmployee() {
        const searchText = searchBox.value.toLowerCase();
        const filteredEmployees = employees.filter(emp => 
            emp.name.toLowerCase().includes(searchText) ||
            emp.department.toLowerCase().includes(searchText) ||
            emp.city.toLowerCase().includes(searchText)
        );
        renderTable(filteredEmployees);
    }

    document.getElementById("addEmployeeBtn").addEventListener("click", addEmployee);
    searchBox.addEventListener("input", searchEmployee);

    renderTable(employees);
});
