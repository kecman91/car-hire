package dto;

import lombok.Data;
import model.Gender;

@Data
public class CustomerDto {
    private String name;
    private String lastName;
    private String persNo;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String address;
}
