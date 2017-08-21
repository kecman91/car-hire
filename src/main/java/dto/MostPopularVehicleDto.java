package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MostPopularVehicleDto implements Serializable {
    private String regNumber;
    private long count;
}
