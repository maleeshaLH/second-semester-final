$(document).ready(function() {
    loadStaff();

    $('#staffForm').on('submit', function(e) {
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

        let url = staffData.id ? `api/v1/staff/${staffData.id}` : 'api/v1/staff';
        let method = staffData.id ? 'PATCH' : 'POST';

        $.ajax({
            url: "http://localhost:5050/controller/api/v1/staff",
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify(staffData),
            success: function(response) {
                $('#staffModal').modal('hide');
                loadStaff();
            },
            error: function(error) {
                console.log(error);
            }
        });
    });

    function loadStaff() {
        $.ajax({
            url: "http://localhost:5050/controller/api/v1/staff",
            method: 'GET',
            success: function(response) {
                $('#staffTableBody').empty();
                response.forEach(function(staff) {
                    $('#staffTableBody').append(`
                        <tr>
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
            error: function(error) {
                console.log(error);
            }
        });
    }

    window.editStaff = function(id) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/staff/${id}`,
            method: 'GET',
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
            },
            error: function(error) {
                console.log(error);
            }
        });
    };

    window.deleteStaff = function(id) {
        $.ajax({
            url: `api/v1/staff/${id}`,
            method: 'DELETE',
            success: function(response) {
                loadStaff();
            },
            error: function(error) {
                console.log(error);
            }
        });
    };
});
