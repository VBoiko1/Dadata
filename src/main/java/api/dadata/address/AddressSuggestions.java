package api.dadata.address;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressSuggestions {

    private String value;
    private String unrestricted_value;
    private AddressData data;

}
