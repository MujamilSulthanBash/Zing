package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequestDto {

    @NotBlank
    @Size(min = 6, max = 10, message = "location should contain only 6 to 10 letters.")
    @Pattern(regexp = "^(GUINDY|VELACHERRY)$",message = "location should contain either PAID or UNPAID.")
    String location;
}
