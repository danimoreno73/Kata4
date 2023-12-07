package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;

public class SqliteOrderLoader implements OrderLoader{
    private final Connection connection;


    private SqliteOrderLoader(Connection connection) {
        this.connection = connection;
    }

    public static OrderLoader with(Connection connection){return new SqliteOrderLoader(connection);}
    @Override
    public List<Order> loadAll() {
        try {
            return load(queryAll());
        }catch (SQLException e){
            return emptyList();
        }
    }

    private List<Order> load(ResultSet resultSet) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            orderList.add(customerCreator(resultSet));
        }
        return  orderList;
    }

    private Order customerCreator(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getInt("Id"),
                resultSet.getString("date"),
                resultSet.getString("Customer"),
                resultSet.getString("employee"),
                resultSet.getString("shipper"),
                resultSet.getInt("quantity"),
                resultSet.getString("product"),
                resultSet.getInt("price"),
                resultSet.getString("supplierName"),
                resultSet.getString("supplierContact")
        );
    }

    private ResultSet queryAll() throws SQLException {
        return connection.createStatement().executeQuery(QueryAll);
    }

    private final static String QueryAll = "SELECT \n" +
            "               Orders.OrderID as Id,\n" +
            "               Orders.OrderDate as Date,\n" +
            "               Customers.CustomerName as Customer,\n" +
            "               Employees.FirstName || ' ' || Employees.LastName AS Employee,\n" +
            "               Shippers.ShipperName as Shipper,\n" +
            "               OrderDetails.Quantity,\n" +
            "               Products.ProductName as Product,\n" +
            "               Products.Price,\n" +
            "               Suppliers.SupplierName,\n" +
            "               Suppliers.ContactName AS SupplierContact\n" +
            "FROM\n" +
            "                Orders, Customers, Employees, Shippers, OrderDetails, Products, Suppliers\n" +
            "WHERE\n" +
            "               Orders.CustomerID = Customers.CustomerID AND\n" +
            "               Orders.EmployeeID = Employees.EmployeeID AND\n" +
            "               Orders.ShipperID = Shippers.ShipperID AND\n" +
            "               Orders.OrderID = OrderDetails.OrderID AND\n" +
            "               OrderDetails.ProductID = Products.ProductID AND\n" +
            "               Products.SupplierID = Suppliers.SupplierID;";
}
