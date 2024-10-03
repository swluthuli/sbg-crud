package za.co.sbg.demo.domain.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;
    private Double price;
}
