package lk.ijse.backend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lk.ijse.backend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDto implements SuperDto {
//    private String fieldCode;
//    private String fieldName;
//    private String fieldLocation;
//    private Double extentSize;
//    private String fieldImage1;
//    private String fieldImage2;
@NotBlank(message = "Field code cannot be blank")
@Size(max = 10, message = "Field code must not exceed 10 characters")
private String fieldCode;

    @NotBlank(message = "Field name cannot be blank")
    @Size(max = 100, message = "Field name must not exceed 100 characters")
    private String fieldName;

    @NotBlank(message = "Field location cannot be blank")
    @Size(max = 200, message = "Field location must not exceed 200 characters")
    private String fieldLocation;

    @NotNull(message = "Extent size cannot be null")
    @Positive(message = "Extent size must be a positive number")
    private Double extentSize;

    @NotBlank(message = "Field image 1 cannot be blank")
    private String fieldImage1;

    @NotBlank(message = "Field image 2 cannot be blank")
    private String fieldImage2;
}
