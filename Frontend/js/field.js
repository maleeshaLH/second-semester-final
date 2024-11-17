$(document).ready(function() {
    loadFields();

    $('#fieldForm').on('submit', function(e) {
        e.preventDefault();

        let formData = new FormData();
        formData.append('fieldCode', $('#fieldCode').val());
        formData.append('fieldName', $('#fieldName').val());
        formData.append('fieldLocation', $('#fieldLocation').val());
        formData.append('extentSize', $('#extentSize').val());
        formData.append('fieldImage1', $('#fieldImage1')[0].files[0]);
        formData.append('fieldImage2', $('#fieldImage2')[0].files[0]);

        let url = formData.get('fieldCode') ? `api/v1/field/${formData.get('fieldCode')}` : 'api/v1/field';
        let method = formData.get('fieldCode') ? 'PATCH' : 'POST';

        $.ajax({
            url: "http://localhost:5050/controller/api/v1/field",
            method: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            success: function(response) {
                $('#fieldModal').modal('hide');
                loadFields();
            },
            error: function(error) {
                console.log(error);
            }
        });
    });

    function loadFields() {
        $.ajax({
            url: 'http://localhost:5050/controller/api/v1/field',
            method: 'GET',
            success: function(response) {
                $('#fieldTableBody').empty();
                response.forEach(function(field) {
                    $('#fieldTableBody').append(`
                        <tr>
                            <td>${field.fieldCode}</td>
                            <td>${field.fieldName}</td>
                            <td>${field.fieldLocation}</td>
                            <td>${field.extentSize}</td>
                            <td><img src="data:image/png;base64,${field.fieldImage1}" alt="Field Image 1" width="50"></td>
                            <td><img src="data:image/png;base64,${field.fieldImage2}" alt="Field Image 2" width="50"></td>
                            <td>
                                <button class="btn btn-info btn-sm" onclick="editField('${field.fieldCode}')">Edit</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteField('${field.fieldCode}')">Delete</button>
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

    window.editField = function(fieldCode) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/field/${fieldCode}`,
            method: 'PATCH',
            success: function(response) {
                $('#fieldCode').val(response.fieldCode);
                $('#fieldName').val(response.fieldName);
                $('#fieldLocation').val(response.fieldLocation);
                $('#extentSize').val(response.extentSize);
                $('#fieldModal').modal('show');
            },
            error: function(error) {
                console.log(error);
            }
        });
    };

    window.deleteField = function(fieldCode) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/field/${fieldCode}`,
            method: 'DELETE',
            success: function(response) {
                loadFields();
            },
            error: function(error) {
                console.log(error);
            }
        });
    };
});
