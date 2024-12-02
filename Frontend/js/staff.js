$(document).ready(function() {
    loadStaff();

    loadVehicleCodes();

            
    const STAFF_URL = "http://localhost:5050/controller/api/v1/staff";

    $('#saveButton').on('click', function(e) {
        e.preventDefault();

        let staffData = {
            id: $('#staffId').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            designation: $('#designation').val(),
            gender: $('#gender').val(),
            joinedDate: $('#joinedDate').val(),
            dob: $('#dob').val(),
            role: $('#role').val(),
            addressLine01: $('#addressLine01').val(),
            addressLine02: $('#addressLine02').val(),
            addressLine03: $('#addressLine03').val(),
            addressLine04: $('#addressLine04').val(),
            addressLine05: $('#addressLine05').val(),
            contactNo: $('#contactNo').val(),
            email: $('#email').val(),
            vehicleCode: $('#vehicleCode').val()
        };

        

        $.ajax({
            url: "http://localhost:5050/controller/api/v1/staff",
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
            contentType: 'application/json',
            data: JSON.stringify(staffData),
            success: function(response) {
                $('#staffModal').modal('hide');
                alert("Staff added successfully!");
                loadStaff();
                
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
                  alert("Error saving staff: " + (xhr.responseText || "An unexpected error occurred."));
                }
            },
        });
    });

    $('#updateButton').on('click', function(e) {
        e.preventDefault();
        const id = $("#staffId").val();

        let staffData = {
            id: $('#staffId').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            designation: $('#designation').val(),
            gender: $('#gender').val(),
            joinedDate: $('#joinedDate').val(),
            dob: $('#dob').val(),
            role: $('#role').val(),
            addressLine01: $('#addressLine01').val(),
            addressLine02: $('#addressLine02').val(),
            addressLine03: $('#addressLine03').val(),
            addressLine04: $('#addressLine04').val(),
            addressLine05: $('#addressLine05').val(),
            contactNo: $('#contactNo').val(),
            email: $('#email').val(),
            vehicleCode: $('#vehicleCode').val()
        };

        

        $.ajax({
            url: `${STAFF_URL}/${id}`,
            method: "PATCH",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
            contentType: 'application/json',
            data: JSON.stringify(staffData),
            success: function(response) {
                $('#staffModal').modal('hide');
                alert("Staff added successfully!");
                loadStaff();
                
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
                  alert("Error saving staff: " + (xhr.responseText || "An unexpected error occurred."));
                }
            },
        });
    });


    function loadVehicleCodes() {
        $.ajax({
          url: 'http://localhost:5050/controller/api/v1/vehicle',
          method: 'GET',
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
          success: function (response) {
            const vehicleCodeDropdown = $('#vehicleCode');
            vehicleCodeDropdown.empty();
            vehicleCodeDropdown.append('<option value="" disabled selected>Select Vehicle Code</option>');
    
            response.forEach(function (vehicle) {
              vehicleCodeDropdown.append(`<option value="${vehicle.vehicleCode}">${vehicle.vehicleCode}</option>`);
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

    function loadStaff() {
        $.ajax({
            url: "http://localhost:5050/controller/api/v1/staff",
            method: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
              },
            success: function(response) {
                $('#staffTableBody').empty();
                response.forEach(function(staff) {
                    $('#staffTableBody').append(`
                        <tr>
                            <td>${staff.id}</td>
                            <td>${staff.firstName}</td>
                            <td>${staff.lastName}</td>
                            <td>${staff.designation}</td>
                            <td>${staff.gender}</td>
                            <td>${staff.joinedDate.split('T')[0]}</td>
                            <td>${staff.dob.split('T')[0]}</td>
                            <td>${staff.role}</td>
                            <td>${staff.addressLine01}, ${staff.addressLine02}, ${staff.addressLine03}, ${staff.addressLine04}, ${staff.addressLine05}</td>
                            <td>${staff.contactNo}</td>
                            <td>${staff.email}</td>
                            <td>${staff.vehicleCode}</td>
                            <td>
                                <button class="btn btn-info btn-sm" onclick="editStaff('${staff.id}')">Edit</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteStaff('${staff.id}')">Delete</button>
                            </td>
                        </tr>
                    `);
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

    window.editStaff = function(id) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/staff/${id}`,
            method: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
            success: function(response) {
                $('#staffId').val(response.id);
                $('#firstName').val(response.firstName);
                $('#lastName').val(response.lastName);
                $('#designation').val(response.designation);
                $('#gender').val(response.gender);
                $('#joinedDate').val(response.joinedDate.split('T')[0]);
                $('#dob').val(response.dob.split('T')[0]);
                $('#role').val(response.role);
                $('#addressLine01').val(response.addressLine01);
                $('#addressLine02').val(response.addressLine02);
                $('#addressLine03').val(response.addressLine03);
                $('#addressLine04').val(response.addressLine04);
                $('#addressLine05').val(response.addressLine05);
                $('#contactNo').val(response.contactNo);
                $('#email').val(response.email);
                $('#vehicleCode').val(response.vehicleCode);
                $('#staffModal').modal('show');

                // Update modal title
            $("#saveButton").hide();
            $("#updateButton").show();
            $("#staffModalLabel").text("Edit Staff");
            $("#staffModal").modal("show");
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
    };

    // Reset Vehicle Form
function resetVehicleForm() {
    $("#staffForm")[0].reset();
    $("#staffId").prop("readonly", false);
    $("#saveButton").show();
    $("#updateButton").hide();
    $("#staffModalLabel").text("Add Vehicle");
}

    window.deleteStaff = function(id) {
        if (confirm("Are you sure you want to delete this staff ?")) {
            $.ajax({
                url: `${STAFF_URL}/${id}`,
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                },
                success: function(response) {
                    alert("Staff deleted successfully!");
                    loadStaff();
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
    };
});
