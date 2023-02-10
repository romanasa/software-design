package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.product.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.dao.Utils.getConnection;

public class ProductDao {

    private ResultSet getQuery(String sqlQuery) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            return stmt.executeQuery(sqlQuery);
        }
    }

    public void insert(Product product) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES " +
                    "(\"" + product.getName() + "\"," + product.getPrice() + ")";
            stmt.executeUpdate(sql);
        }
    }

    public List<Product> getProducts() throws SQLException {
        return parseProducts(getQuery("SELECT * FROM PRODUCT"));
    }

    public Optional<Product> findMaxPriceProduct() throws SQLException {
        return parseProducts(getQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1")).stream().findFirst();
    }

    public Optional<Product> findMinPriceProduct() throws SQLException {
        return parseProducts(getQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1")).stream().findFirst();
    }

    public long getPricesSum() throws SQLException {
        return getQuery("SELECT SUM(price) as sum FROM PRODUCT").getLong("sum");
    }

    public int getProductsCount() throws SQLException {
        return getQuery("SELECT COUNT(*) as cnt FROM PRODUCT").getInt("cnt");
    }

    private List<Product> parseProducts(ResultSet rs) throws SQLException {
        List<Product> result = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            result.add(new Product(name, price));
        }
        return result;
    }

}
