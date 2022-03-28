package vn.edu.hcmuaf.fit.dto.address;

public class AddressCreate {
    private String number;
    private String street;
    private Long wardId;
    private Long districtId;
    private String path;

    public AddressCreate() {
    }

    public AddressCreate(String number, String street, Long wardId, Long districtId, String path) {
        this.number = number;
        this.street = street;
        this.wardId = wardId;
        this.districtId = districtId;
        this.path = path;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
