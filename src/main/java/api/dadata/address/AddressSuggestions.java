package api.dadata.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressSuggestions {

    private String value;
    private String unrestricted_value;
    private AddressData data;


    public AddressSuggestions() {
    }

    public AddressSuggestions(String value, String unrestricted_value, AddressData data) {
        this.value = value;
        this.unrestricted_value = unrestricted_value;
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public String getUnrestricted_value() {
        return unrestricted_value;
    }

    public AddressData getData() {
        return data;
    }
}
