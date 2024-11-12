package com.service.beta.data.object;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String city;
    private String pin_code;
    private String state;
    private String country;
}
