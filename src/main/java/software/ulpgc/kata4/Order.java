package software.ulpgc.kata4;

public record Order(
        int id,
        String date,
        String costumer,
        String employee,
        String shipper,
        int quantity,
        String product,
        int price,
        String supplierName,
        String supplierContact
) {
}
