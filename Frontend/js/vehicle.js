const VEHICLE_BASE_URL = "http://localhost:5050/controller/api/v1/vehicle";

$("#saveButton").on("click", function (e) {
    e.preventDefault();

    let formData = new FormData();
    formData.append("vehicleCode", $("#vehicleCode").val());
    formData.append("licensePlateNumber", $("#licensePlateNumber").val());
    formData.append("category", $("#vehicleCategory").val());
    formData.append("fuelType", $("#fuelType").val());
    formData.append("status", $("#status").val());
    formData.append("remarks", $("#remarks").val());

    saveVehicle(formData)
});

// Save Vehicle (POST)
function saveVehicle(formData) {
    $.ajax({
        url: VEHICLE_BASE_URL,
        type: "POST",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        data: formData,
        processData: false,
        contentType: false,
        success: function () {
            alert("Vehicle added successfully!");
            $("#vehicleModal").modal("hide");
            loadVehicles();
            resetVehicleForm();
        },
        error: function (xhr) {
            if (xhr.status === 401) {
              // Handle session expiration
              if (confirm("Session expired. Please log in again.")) {
                window.location.href = "/index.html";
              }
            } else if (xhr.status === 403) {
              // Handle insufficient permissions
              alert("You do not have permission to perform this action.");
            } else {
              // Handle other errors
              alert("Error saving vehicle: " + (xhr.responseText || "An unexpected error occurred."));
            }
          },
    });
}

$("#updateButton").on("click", function (e) {
    e.preventDefault();

    const vehicleCode = $("#vehicleCode").val();
 

    let formData = new FormData();
    formData.append("vehicleCode", $("#vehicleCode").val());
    formData.append("licensePlateNumber", $("#licensePlateNumber").val());
    formData.append("category", $("#vehicleCategory").val());
    formData.append("fuelType", $("#fuelType").val());
    formData.append("status", $("#status").val());
    formData.append("remarks", $("#remarks").val());

    updateVehicle(vehicleCode, formData);
});

// Update Vehicle (PATCH)
function updateVehicle(vehicleCode, formData) {
    $.ajax({
        url: `${VEHICLE_BASE_URL}/${vehicleCode}`,
        type: "PATCH",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
        data: formData,
        processData: false,
        contentType: false,
        success: function () {
            alert("Vehicle updated successfully!");
            $("#vehicleModal").modal("hide");
            loadVehicles();
            resetVehicleForm();
        },
        error: function (xhr) {
            if (xhr.status === 401) {
              // Handle session expiration
              if (confirm("Session expired. Please log in again.")) {
                window.location.href = "/index.html";
              }
            } else if (xhr.status === 403) {
              // Handle insufficient permissions
              alert("You do not have permission to perform this action.");
            } else {
              // Handle other errors
              alert("Error updating field: " + (xhr.responseText || "An unexpected error occurred."));
            }
          },
    });
}

// Edit Vehicle (Populate form for PATCH)
function editVehicle(vehicleCode) {
    $.ajax({
        url: `${VEHICLE_BASE_URL}/${vehicleCode}`,
        type: "GET",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
        success: function (vehicle) {
            // Populate form fields
            $("#vehicleCode").val(vehicle.vehicleCode).prop("readonly", true);
            $("#licensePlateNumber").val(vehicle.licensePlateNumber);
            $("#vehicleCategory").val(vehicle.category);
            $("#fuelType").val(vehicle.fuelType);
            $("#status").val(vehicle.status);
            $("#remarks").val(vehicle.remarks);

            // Update modal title
            $("#saveButton").hide();
            $("#updateButton").show();
            $("#vehicleModalLabel").text("Edit Vehicle");
            $("#vehicleModal").modal("show");
        },
        error: function (xhr) {
            if (xhr.status === 401) 
              // Handle session expiration
              if (confirm("Session expired. Please log in again.")) {
                window.location.href = "/index.html";
              }
            else {
              // Handle other errors
              alert("Error retrieving field list : " + (xhr.responseText || "An unexpected error occurred."));
            }
          },
    });
}

// Delete Vehicle
function deleteVehicle(vehicleCode) {
    if (confirm("Are you sure you want to delete this vehicle?")) {
        $.ajax({
            url: `${VEHICLE_BASE_URL}/${vehicleCode}`,
            type: "DELETE",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
              },
            success: function () {
                alert("Vehicle deleted successfully!");
                loadVehicles();
            },
            error: function (xhr) {
                if (xhr.status === 401) {
                  // Handle session expiration
                  if (confirm("Session expired. Please log in again.")) {
                    window.location.href = "/index.html";
                  }
                } else if (xhr.status === 403) {
                  // Handle insufficient permissions
                  alert("You do not have permission to perform this action.");
                } else {
                  // Handle other errors
                  alert("Error deleting vehicle: " + (xhr.responseText || "An unexpected error occurred."));
                }
              },
        });
    }
}

// Reset Vehicle Form
function resetVehicleForm() {
    $("#vehicleForm")[0].reset();
    $("#vehicleCode").prop("readonly", false);
    $("#saveButton").show();
    $("#updateButton").hide();
    $("#vehicleModalLabel").text("Add Vehicle");
}

// Load Vehicles and Populate Table
function loadVehicles() {
    $.ajax({
        url: VEHICLE_BASE_URL,
        type: "GET",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        success: function (vehicles) {
            const tableBody = $("#vehicleTable");
            tableBody.empty(); // Clear existing rows

            vehicles.forEach((vehicle) => {
                const row = `
                    <tr>
                        <td>${vehicle.vehicleCode}</td>
                        <td>${vehicle.licensePlateNumber}</td>
                        <td>${vehicle.category}</td>
                        <td>${vehicle.fuelType}</td>
                        <td>${vehicle.status}</td>
                        <td>${vehicle.remarks}</td>
                        <td>
                            <button class="btn btn-info btn-sm" onclick="editVehicle('${vehicle.vehicleCode}')">Edit</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteVehicle('${vehicle.vehicleCode}')">Delete</button>
                        </td>
                    </tr>
                `;
                tableBody.append(row);
            });
        },
        error: function (xhr) {
            if (xhr.status === 401) 
              // Handle session expiration
              if (confirm("Session expired. Please log in again.")) {
                window.location.href = "/index.html";
              }
            else {
              // Handle other errors
              alert("Error retrieving field list : " + (xhr.responseText || "An unexpected error occurred."));
            }
          },
    });
}

// Initialize Page with Vehicle Data
$(document).ready(function () {
    loadVehicles(); // Fetch and display all vehicles on page load

    // Handle form submission
    $("#vehicleForm").on("submit", function (e) {
        e.preventDefault();
        let formData = new FormData(this);
        const isUpdate = $("#updateButton").is(":visible");

        if (isUpdate) {
            const fieldCode = $("#fieldCode").val();
            updateVehicle(fieldCode, formData); // PATCH for updating
        } else {
            saveVehicle(formData); // POST for saving new data
        }
    });

        // Open modal for adding a new vehicle
        $("#addVehicleBtn").on("click", function () {
            resetVehicleForm();
            $("#vehicleModal").modal("show");
        });
});
