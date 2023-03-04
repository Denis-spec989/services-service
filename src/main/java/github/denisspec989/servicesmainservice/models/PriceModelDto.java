package github.denisspec989.servicesmainservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceModelDto {
    private Double fuelPrice;
    private String fuelName;
}
