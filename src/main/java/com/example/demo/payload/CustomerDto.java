package com.example.demo.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotNull(message = "Ism - sharif bo'sh bo'lishi mumkin emas !")
    private String fullName;

    @NotNull(message = "telefon - raqam bo'sh bo'lishi mumkin emas !")
    private String phoneNumber;

    @NotNull(message = "Address bo'sh bo'lishi mumkin emas !")
    private String address;
}
