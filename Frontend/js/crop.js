$(document).ready(function() {
    loadCrops();

    $('#cropForm').on('submit', function(e) {
        e.preventDefault();

        let formData = new FormData();
        formData.append('cropCode', $('#cropCode').val());
        formData.append('cropCommonName', $('#cropCommonName').val());
        formData.append('cropScientificName', $('#cropScientificName').val());
        formData.append('category', $('#category').val());
        formData.append('cropSeason', $('#cropSeason').val());
        formData.append('cropImage', $('#cropImage')[0].files[0]);
        formData.append('fieldCode', $('#fieldCode').val());

        let url = formData.get('cropCode') ? `api/v1/crop/${formData.get('cropCode')}` : 'api/v1/crop';
        let method = formData.get('cropCode') ? 'PATCH' : 'POST';

        $.ajax({
            url: "http://localhost:5050/controller/api/v1/crop",
            method: 'POST',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            success: function(response) {
                $('#cropModal').modal('hide');
                loadCrops();
            },
            error: function(error) {
                console.log(error);
            }
        });
    });

    function loadCrops() {
        $.ajax({
            url: 'http://localhost:5050/controller/api/v1/crop',
            method: 'GET',
            success: function(response) {
                $('#cropTableBody').empty();
                response.forEach(function(crop) {
                    $('#cropTableBody').append(`
                        <tr>
                            <td>${crop.cropCode}</td>
                            <td>${crop.cropCommonName}</td>
                            <td>${crop.cropScientificName}</td>
                            <td>${crop.category}</td>
                            <td>${crop.cropSeason}</td>
                            <td><img src="data:image/png;base64,${crop.cropImage}" alt="Crop Image" width="50"></td>
                            <td>${crop.fieldCode}</td>
                            <td>
                                <button class="btn btn-info btn-sm" onclick="editCrop('${crop.cropCode}')">Edit</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteCrop('${crop.cropCode}')">Delete</button>
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

    window.editCrop = function(cropCode) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/crop/${cropCode}`,
            method: 'PATCH',
            success: function(response) {
                $('#cropCode').val(response.cropCode);
                $('#cropCommonName').val(response.cropCommonName);
                $('#cropScientificName').val(response.cropScientificName);
                $('#category').val(response.category);
                $('#cropSeason').val(response.cropSeason);
                $('#fieldCode').val(response.fieldCode);
                $('#cropModal').modal('show');
            },
            error: function(error) {
                console.log(error);
            }
        });
    };

    window.deleteCrop = function(cropCode) {
        $.ajax({
            url: `http://localhost:5050/controller/api/v1/cropp/${cropCode}`,
            method: 'DELETE',
            success: function(response) {
                loadCrops();
            },
            error: function(error) {
                console.log(error);
            }
        });
    };
});
