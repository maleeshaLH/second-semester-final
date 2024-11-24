$(document).ready(function() {
    loadLogs();
    loadFieldCodes();
    loadCropCodes();
    loadStaffIds();

    $('#logForm').on('submit', function(e) {
        e.preventDefault();

        let formData = new FormData();
        formData.append('logCode', $('#logCode').val());
        formData.append('logDate', $('#logDate').val());
        formData.append('observation', $('#observation').val());
        formData.append('logImage', $('#logImage')[0].files[0]);
        formData.append('fieldCode', $('#fieldCode').val());
        formData.append('cropCode', $('#cropCode').val());
        formData.append('staffId', $('#staffId').val());

        let url = formData.get('logCode') ? `http://localhost:5050/controller/api/v1/logmonitoring/${formData.get('logCode')}` : 'http://localhost:5050/controller/api/v1/logmonitoring';
        let method = formData.get('logCode') ? 'PATCH' : 'POST';

        $.ajax({
            url: "http://localhost:5050/controller/api/v1/logmonitoring",
            method: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            success: function(response) {
                $('#logModal').modal('hide');
                loadLogs();
            },
            error: function(error) {
                console.log(error);
            }
        });
    });

    function loadLogs() {
        $.ajax({
            url: 'http://localhost:5050/controller/api/v1/logmonitoring/allLogs',
            method: 'GET',
            success: function(response) {
                $('#logTableBody').empty();
                response.forEach(function(log) {
                    $('#logTableBody').append(`
                        <tr>
                            <td>${log.log_code}</td>
                            <td>${log.log_date}</td>
                            <td>${log.observation}</td>
                            <td><img src="data:image/png;base64,${log.log_image}" alt="Log Image" width="50"></td>
                            <td>${log.fieldCode}</td>
                            <td>${log.cropCode}</td>
                            <td>${log.id}</td>
                            <td>
                                <button class="btn btn-info btn-sm" onclick="editLog('${log.log_code}')">Edit</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteLog('${log.log_code}')">Delete</button>
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

    function loadFieldCodes() {
        $.ajax({
          url: 'http://localhost:5050/controller/api/v1/field',
          method: 'GET',
          success: function (response) {
            const vehicleCodeDropdown = $('#fieldCode');
            vehicleCodeDropdown.empty();
            vehicleCodeDropdown.append('<option value="" disabled selected>Select Field Code</option>');
    
            response.forEach(function (vehicle) {
              vehicleCodeDropdown.append(`<option value="${vehicle.fieldCode}">${vehicle.fieldCode}</option>`);
            });
          },
          error: function (error) {
            console.error('Error loading vehicle codes:', error);
          }
        });
    
    }

    function loadCropCodes() {
        $.ajax({
          url: 'http://localhost:5050/controller/api/v1/crop',
          method: 'GET',
          success: function (response) {
            const vehicleCodeDropdown = $('#cropCode');
            vehicleCodeDropdown.empty();
            vehicleCodeDropdown.append('<option value="" disabled selected>Select Vehicle Code</option>');
    
            response.forEach(function (vehicle) {
              vehicleCodeDropdown.append(`<option value="${vehicle.cropCode}">${vehicle.cropCode}</option>`);
            });
          },
          error: function (error) {
            console.error('Error loading vehicle codes:', error);
          }
        });
    }

    function loadStaffIds() {
        $.ajax({
          url: 'http://localhost:5050/controller/api/v1/staff',
          method: 'GET',
          success: function (response) {
            const vehicleCodeDropdown = $('#staffId');
            vehicleCodeDropdown.empty();
            vehicleCodeDropdown.append('<option value="" disabled selected>Select Vehicle Code</option>');
    
            response.forEach(function (vehicle) {
              vehicleCodeDropdown.append(`<option value="${vehicle.vehicleCode}">${vehicle.vehicleCode}</option>`);
            });
          },
          error: function (error) {
            console.error('Error loading vehicle codes:', error);
          }
        });
      }
    window.editLog = function(logCode) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/logmonitoring/06/logmonitoring/${logCode}`,
            method: 'GET',
            success: function(response) {
                $('#logCode').val(response.log_code);
                $('#logDate').val(response.log_date);
                $('#observation').val(response.observation);
                $('#fieldCode').val(response.fieldCode);
                $('#cropCode').val(response.cropCode);
                $('#staffId').val(response.id);
                $('#logModal').modal('show');
            },
            error: function(error) {
                console.log(error);
            }
        });
    };

    window.deleteLog = function(logCode) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/logmonitoring/${logCode}`,
            method: 'DELETE',
            success: function(response) {
                loadLogs();
            },
            error: function(error) {
                console.log(error);
            }
        });
    };
});
