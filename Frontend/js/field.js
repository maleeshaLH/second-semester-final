// const BASE_URL = "http://localhost:5050/controller/api/v1/field";

// // Save Field (POST)
// function saveField(formData) {
//     $.ajax({
//         url: BASE_URL,
//         type: "POST",
//         data: formData,
//         headers: {
//             Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
//         },
//         processData: false,
//         contentType: false,
//         success: function () {
//             alert("Field added successfully!");
//             $("#fieldModal").modal("hide");
//             loadFields();
//             resetForm();
//         },
//         error: function (xhr) {
//             if (xhr.status === 401) {
//               // Handle session expiration
//               if (confirm("Session expired. Please log in again.")) {
//                 window.location.href = "/index.html";
//               }
//             } else if (xhr.status === 403) {
//               // Handle insufficient permissions
//               alert("You do not have permission to perform this action.");
//             } else {
//               // Handle other errors
//               alert("Error saving field: " + (xhr.responseText || "An unexpected error occurred."));
//             }
//           },
//     });
// }

// // Update Field (PATCH)
// function updateField(fieldCode, formData) {
//     $.ajax({
//         url: `${BASE_URL}/${fieldCode}`, // PATCH endpoint
//         type: "PATCH",
//         data: formData,
//         headers: {
//             Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
//         },
//         processData: false,
//         contentType: false,
//         success: function () {
//             alert("Field updated successfully!");
//             $("#fieldModal").modal("hide");
//             loadFields();
//             resetForm();
//         },
//         error: function (xhr) {
//             if (xhr.status === 401) {
//               // Handle session expiration
//               if (confirm("Session expired. Please log in again.")) {
//                 window.location.href = "/index.html";
//               }
//             } else if (xhr.status === 403) {
//               // Handle insufficient permissions
//               alert("You do not have permission to perform this action.");
//             } else {
//               // Handle other errors
//               alert("Error saving field: " + (xhr.responseText || "An unexpected error occurred."));
//             }
//           },
//     });
// }

// // Edit Field (Populate form for PATCH)
// function editField(fieldCode) {
//     $.ajax({
//         url: `${BASE_URL}/${fieldCode}`,
//         type: "GET",
//         headers: {
//             Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
//         },
//         success: function (field) {
//             // Populate form fields with existing data
//             $("#fieldCode").val(field.fieldCode).prop("readonly", true); // Readonly fieldCode
//             $("#fieldName").val(field.fieldName);
//             $("#fieldLocation").val(field.fieldLocation);
//             $("#extentSize").val(field.extentSize);

//             // Set the existing images for display (base64 encoding)
//             if (field.fieldImage1) {
//                 $("#fieldImage1").attr("src", `data:image/png;base64,${field.fieldImage1}`);
//             }
//             if (field.fieldImage2) {
//                 $("#fieldImage2").attr("src", `data:image/png;base64,${field.fieldImage2}`);
//             }
//             // Switch to Update mode
//             $("#saveButton").hide();
            
//             $("#updateButton").show();

//             $("#fieldModalLabel").text("Edit Field");
//             $("#fieldModal").modal("show");
//         },
//         error: function (xhr) {
//             if (xhr.status === 401) 
//               // Handle session expiration
//               if (confirm("Session expired. Please log in again.")) {
//                 window.location.href = "/index.html";
//               }
//             else {
//               // Handle other errors
//               alert("Error retrieving field list : " + (xhr.responseText || "An unexpected error occurred."));
//             }
//           },
//     });
// }

// // Reset Form
// function resetForm() {
//     $("#fieldForm")[0].reset();
//     $("#fieldCode").prop("readonly", false);
//     $("#saveButton").show();
//     $("#updateButton").hide();
//     $("#fieldModalLabel").text("Add Field");
// }

// // Fetch Fields to Refresh Table
// function loadFields() {
//     $.ajax({
//         url: BASE_URL,
//         type: "GET",
//         headers: {
//             Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
//         },
//         success: function (fields) {
//             const tableBody = $("#fieldTableBody");
//             tableBody.empty(); // Clear existing rows
//             fields.forEach((field) => {
//                 const row = `
//                     <tr>
//                         <td>${field.fieldCode}</td>
//                         <td>${field.fieldName}</td>
//                         <td>${field.fieldLocation}</td>
//                         <td>${field.extentSize}</td>
//                         <td><img src="data:image/png;base64,${field.fieldImage1}" width="50"></td>
//                         <td><img src="data:image/png;base64,${field.fieldImage2}" width="50"></td>
//                         <td>
//                             <button class="btn btn-info btn-sm" onclick="editField('${field.fieldCode}')"><i class="fas fa-pen"></i></button>
//                             <button class="btn btn-danger btn-sm" onclick="deleteField('${field.fieldCode}')"><i class="fas fa-pen"></i></button>
//                         </td>
//                     </tr>
//                 `;
//                 tableBody.append(row);
//             });
//         },
//         error: function (xhr) {
//             if (xhr.status === 401) 
//               // Handle session expiration
//               if (confirm("Session expired. Please log in again.")) {
//                 window.location.href = "/index.html";
//               }
//             else {
//               // Handle other errors
//               alert("Error retrieving field list : " + (xhr.responseText || "An unexpected error occurred."));
//             }
//           },
//     });
// }

// // Save New Field (POST)
// $("#saveButton").on("click", function (e) {
//     e.preventDefault();
//     let formData = new FormData();
//     formData.append("fieldCode", $("#fieldCode").val());
//     formData.append("fieldName", $("#fieldName").val());
//     formData.append("fieldLocation", $("#fieldLocation").val());
//     formData.append("extentSize", $("#extentSize").val());
//     formData.append("fieldImage1", $("#fieldImage1")[0].files[0]);
//     formData.append("fieldImage2", $("#fieldImage2")[0].files[0]);

//     saveField(formData);
// });

// // Update Field (PATCH)
// $("#updateButton").on("click", function (e) {
//     e.preventDefault();
//     let formData = new FormData();
//     formData.append("fieldCode", $("#fieldCode").val());
//     formData.append("fieldName", $("#fieldName").val());
//     formData.append("fieldLocation", $("#fieldLocation").val());
//     formData.append("extentSize", $("#extentSize").val());
//     formData.append("fieldImage1", $("#fieldImage1")[0].files[0]);
//     formData.append("fieldImage2", $("#fieldImage2")[0].files[0]);

//     updateField($("#fieldCode").val(), formData);
// });

// // Delete Field
// function deleteField (fieldCode) {
//     if (confirm("Are you sure you want to delete this field?")) {

//         $.ajax({
//             url: `${BASE_URL}/${fieldCode}`,
//             method: "DELETE",
//             headers: {
//                 Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
//             },
//             success: function () {
//                 alert("Field deleted successfully!");
//                 loadFields();
//             },
//             error: function (xhr) {
//                 if (xhr.status === 401) {
//                 // Handle session expiration
//                 if (confirm("Session expired. Please log in again.")) {
//                     window.location.href = "/index.html";
//                 }
//                 } else if (xhr.status === 403) {
//                 // Handle insufficient permissions
//                 alert("You do not have permission to perform this action.");
//                 } else {
//                 // Handle other errors
//                 alert("Error deleting field: " + (xhr.responseText || "An unexpected error occurred."));
//                 }
//             },
//         });
//     }
// };

// // Initialize Page with Field Data
// $(document).ready(function () {
//     loadFields(); // Fetch and display all fields on page load

//     // Handle form submission for adding or updating a field
//     $("#fieldForm").on("submit", function (e) {
//         e.preventDefault();
//         const formData = new FormData(this);
//         const isUpdate = $("#updateButton").is(":visible");

//         if (isUpdate) {
//             const fieldCode = $("#fieldCode").val();
//             updateField(fieldCode, formData); // PATCH for updating
//         } else {
//             saveField(formData); // POST for saving new data
//         }
//     });
// });
const BASE_URL = "http://localhost:5050/controller/api/v1/field";

// Save Field (POST)
function saveField(formData) {
    $.ajax({
        url: BASE_URL,
        type: "POST",
        data: formData,
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
        processData: false,
        contentType: false,
        success: function () {
            alert("Field added successfully!");
            $("#fieldModal").modal("hide");
            loadFields();
            resetForm();
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
                alert("Error saving field: " + (xhr.responseText || "An unexpected error occurred."));
            }
        },
    });
}

// Update Field (PATCH)
function updateField(fieldCode, formData) {
    $.ajax({
        url: `${BASE_URL}/${fieldCode}`, // PATCH endpoint
        type: "PATCH",
        data: formData,
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
        processData: false,
        contentType: false,
        success: function () {
            alert("Field updated successfully!");
            $("#fieldModal").modal("hide");
            loadFields();
            resetForm();
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
                alert("Error saving field: " + (xhr.responseText || "An unexpected error occurred."));
            }
        },
    });
}

// Edit Field (Populate form for PATCH)
function editField(fieldCode) {
    $.ajax({
        url: `${BASE_URL}/${fieldCode}`,
        type: "GET",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
        success: function (field) {
            // Populate form fields with existing data
            $("#fieldCode").val(field.fieldCode).prop("readonly", true); // Readonly fieldCode
            $("#fieldName").val(field.fieldName);
            $("#fieldLocation").val(field.fieldLocation);
            $("#extentSize").val(field.extentSize);

            // Set the existing images for display (base64 encoding)
            if (field.fieldImage1) {
                $("#fieldImage1Preview").attr("src", `data:image/png;base64,${field.fieldImage1}`);
            }
            if (field.fieldImage2) {
                $("#fieldImage2Preview").attr("src", `data:image/png;base64,${field.fieldImage2}`);
            }
            // Switch to Update mode
            $("#saveButton").hide();
            $("#updateButton").show();
            $("#fieldModalLabel").text("Edit Field");
            $("#fieldModal").modal("show");
        },
        error: function (xhr) {
            if (xhr.status === 401) {
                // Handle session expiration
                if (confirm("Session expired. Please log in again.")) {
                    window.location.href = "/index.html";
                }
            } else {
                // Handle other errors
                alert("Error retrieving field list : " + (xhr.responseText || "An unexpected error occurred."));
            }
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
    generateFieldCode()
}

// Fetch Fields to Refresh Table
function loadFields() {
    $.ajax({
        url: BASE_URL,
        type: "GET",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
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
                        <td><img src="data:image/png;base64,${field.fieldImage1}" width="50" alt="Field Image 1"></td>
                        <td><img src="data:image/png;base64,${field.fieldImage2}" width="50" alt="Field Image 2"></td>
                        <td>
                            <button class="btn btn-info btn-sm" onclick="editField('${field.fieldCode}')"><i class="fas fa-pen"></i></button>
                            <button class="btn btn-danger btn-sm" onclick="deleteField('${field.fieldCode}')"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>
                `;
                tableBody.append(row);
            });
        },
        error: function (xhr) {
            if (xhr.status === 401) {
                // Handle session expiration
                if (confirm("Session expired. Please log in again.")) {
                    window.location.href = "/index.html";
                }
            } else {
                // Handle other errors
                alert("Error retrieving field list: " + (xhr.responseText || "An unexpected error occurred."));
            }
        },
    });
}

// Save New Field (POST)
$("#saveButton").on("click", function (e) {
    e.preventDefault();
    if (validateForm()) {
        let formData = new FormData();
        formData.append("fieldCode", $("#fieldCode").val());
        formData.append("fieldName", $("#fieldName").val());
        formData.append("fieldLocation", $("#fieldLocation").val());
        formData.append("extentSize", $("#extentSize").val());
        formData.append("fieldImage1", $("#fieldImage1")[0].files[0]);
        formData.append("fieldImage2", $("#fieldImage2")[0].files[0]);

        saveField(formData);
    }
});

// Update Field (PATCH)
$("#updateButton").on("click", function (e) {
    e.preventDefault();
    if (validateForm()) {
        let formData = new FormData();
        formData.append("fieldCode", $("#fieldCode").val());
        formData.append("fieldName", $("#fieldName").val());
        formData.append("fieldLocation", $("#fieldLocation").val());
        formData.append("extentSize", $("#extentSize").val());
        formData.append("fieldImage1", $("#fieldImage1")[0].files[0]);
        formData.append("fieldImage2", $("#fieldImage2")[0].files[0]);

        updateField($("#fieldCode").val(), formData);
    }
});

// Validate Form Fields
function validateForm() {
    const fieldCode = $("#fieldCode").val();
    const fieldName = $("#fieldName").val();
    const fieldLocation = $("#fieldLocation").val();
    const extentSize = $("#extentSize").val();

    if (!fieldCode) {
        alert("Field code is required.");
        return false;
    }
    if (!fieldName) {
        alert("Field name is required.");
        return false;
    }
    if (!fieldLocation) {
        alert("Field location is required.");
        return false;
    }
    if (!extentSize || isNaN(extentSize)) {
        alert("Valid extent size is required.");
        return false;
    }

    return true;
}

// Delete Field
function deleteField(fieldCode) {
    if (confirm("Are you sure you want to delete this field?")) {
        $.ajax({
            url: `${BASE_URL}/${fieldCode}`,
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
            success: function () {
                alert("Field deleted successfully!");
                loadFields();
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
                    alert("Error deleting field: " + (xhr.responseText || "An unexpected error occurred."));
                }
            },
        });
    }
}
function generateFieldCode() {
    const code = "F-" + Math.floor(Math.random() * 100)+1;
    $("#fieldCode").val(code);
  }

// Initialize Page with Field Data
$(document).ready(function () {
    
    loadFields(); // Fetch and display all fields on page load
    generateFieldCode();
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