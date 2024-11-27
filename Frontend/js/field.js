const BASE_URL = "http://localhost:5050/controller/api/v1/field";

// Save Field (POST)
function saveField(formData) {
    $.ajax({
        url: BASE_URL,
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function () {
            alert("Field added successfully!");
            $("#fieldModal").modal("hide");
            loadFields();
            resetForm();
        },
        error: function () {
            alert("Failed to save field.");
        },
    });
}

// Update Field (PATCH)
function updateField(fieldCode, formData) {
    $.ajax({
        url: `${BASE_URL}/${fieldCode}`, // PATCH endpoint
        type: "PATCH",
        data: formData,
        processData: false,
        contentType: false,
        success: function () {
            alert("Field updated successfully!");
            $("#fieldModal").modal("hide");
            loadFields();
            resetForm();
        },
        error: function () {
            alert("Failed to update field.");
        },
    });
}

// Edit Field (Populate form for PATCH)
function editField(fieldCode) {
    $.ajax({
        url: `${BASE_URL}/${fieldCode}`,
        type: "GET",
        success: function (field) {
            // Populate form fields with existing data
            $("#fieldCode").val(field.fieldCode).prop("readonly", true); // Readonly fieldCode
            $("#fieldName").val(field.fieldName);
            $("#fieldLocation").val(field.fieldLocation);
            $("#extentSize").val(field.extentSize);

            // Set the existing images for display (base64 encoding)
            if (field.fieldImage1) {
                $("#currentImage1").attr("src", `data:image/png;base64,${field.fieldImage1}`);
            }
            if (field.fieldImage2) {
                $("#currentImage2").attr("src", `data:image/png;base64,${field.fieldImage2}`);
            }

            // Switch to Update mode
            $("#saveButton").hide();
            $("#updateButton").show();

            $("#fieldModalLabel").text("Edit Field");
            $("#fieldModal").modal("show");
        },
        error: function () {
            alert("Failed to load field for editing.");
        },
    });
}

// Reset Form
function resetForm() {
    $("#fieldForm")[0].reset();
    $("#fieldCode").prop("readonly", false);
    $("#saveButton").show();
    $("#updateButton").hide();
    $("#fieldModalLabel").text("Add Field");

    // Clear image previews on reset
    $("#currentImage1").attr("src", "");
    $("#currentImage2").attr("src", "");
}

// Fetch Fields to Refresh Table
function loadFields() {
    $.ajax({
        url: BASE_URL,
        type: "GET",
        success: function (fields) {
            const tableBody = $("#fieldTableBody");
            tableBody.empty(); // Clear existing rows
            fields.forEach((field) => {
                const row = `
                    <tr>
                        <td>${field.fieldCode}</td>
                        <td>${field.fieldName}</td>
                        <td>${field.fieldLocation}</td>
                        <td>${field.extentSize}</td>
                        <td><img src="data:image/png;base64,${field.fieldImage1}" width="50"></td>
                        <td><img src="data:image/png;base64,${field.fieldImage2}" width="50"></td>
                        <td>
                            <button class="btn btn-info btn-sm" onclick="editField('${field.fieldCode}')">Edit</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteField('${field.fieldCode}')">Delete</button>
                        </td>
                    </tr>
                `;
                tableBody.append(row);
            });
        },
        error: function () {
            alert("Failed to load fields.");
        },
    });
}

// Save New Field (POST)
$("#saveButton").on("click", function (e) {
    e.preventDefault();
    let formData = new FormData();
    formData.append("fieldCode", $("#fieldCode").val());
    formData.append("fieldName", $("#fieldName").val());
    formData.append("fieldLocation", $("#fieldLocation").val());
    formData.append("extentSize", $("#extentSize").val());

    const fieldImage1 = $("#fieldImage1")[0].files[0];
    const fieldImage2 = $("#fieldImage2")[0].files[0];

    if (fieldImage1) formData.append("fieldImage1", fieldImage1);
    if (fieldImage2) formData.append("fieldImage2", fieldImage2);

    saveField(formData);
});

// Update Field (PATCH)
$("#updateButton").on("click", function (e) {
    e.preventDefault();
    let formData = new FormData();
    formData.append("fieldCode", $("#fieldCode").val());
    formData.append("fieldName", $("#fieldName").val());
    formData.append("fieldLocation", $("#fieldLocation").val());
    formData.append("extentSize", $("#extentSize").val());

    const fieldImage1 = $("#fieldImage1")[0].files[0];
    const fieldImage2 = $("#fieldImage2")[0].files[0];

    if (fieldImage1) formData.append("fieldImage1", fieldImage1);
    if (fieldImage2) formData.append("fieldImage2", fieldImage2);

    updateField($("#fieldCode").val(), formData);
});

// Delete Field
window.deleteField = function (fieldCode) {
    $.ajax({
        url: `${BASE_URL}/${fieldCode}`,
        method: "DELETE",
        success: function () {
            loadFields();
        },
        error: function (error) {
            console.log(error);
        },
    });
};

// Initialize Page with Field Data
$(document).ready(function () {
    loadFields(); // Fetch and display all fields on page load

    // Handle form submission for adding or updating a field
    $("#fieldForm").on("submit", function (e) {
        e.preventDefault();
        const formData = new FormData(this);
        const isUpdate = $("#updateButton").is(":visible");

        if (isUpdate) {
            const fieldCode = $("#fieldCode").val();
            updateField(fieldCode, formData); // PATCH for updating
        } else {
            saveField(formData); // POST for saving new data
        }
    });
});
