package api.dadata.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private String query;
    private Integer count;
    private String language;

    public AddressRequest() {
    }

    public AddressRequest(String query, Integer count, String language) {
        this.query = query;
        this.count = count;
        this.language = language;
    }

    public AddressRequest(String query) {
        this.query = query;
    }

    public AddressRequest(String query, Integer count) {
        this.query = query;
        this.count = count;
    }

    public AddressRequest(String query, String language) {
        this.query = query;
        this.language = language;
    }
}
