package lk.ijse.backend.dto.impl;

import lk.ijse.backend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDto implements SuperDto {
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private String gender;
    private Date  joinedDate;
    private Date  dob;
    private String role;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;
    private String vehicleCode;
}
