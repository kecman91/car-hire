package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleFilterDto {
    private String[] manufacturers;
    private String[] categories;
    private double priceGT;
    private double priceLT;
}
