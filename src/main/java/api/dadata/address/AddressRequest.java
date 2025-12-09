package api.dadata.address;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    private String query;
    private Integer count;
    private String language;


}




