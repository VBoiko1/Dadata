package api.dadata.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressIplocate {
    private String value;
    private String unrestricted_value;
    private AddressDataIplocate data;
}
