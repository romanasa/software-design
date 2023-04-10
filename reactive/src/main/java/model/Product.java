package model;

import org.bson.Document;

import java.util.Map;

public class Product {
    public final String id;
    public final String title;
    public final String price;
    public final String currency;

    public Product(String id, String title, String price, String currency) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.currency = currency;
    }

    public Product(Document document) {
        this(document.getString("id"),
                document.getString("title"),
                document.getString("price"),
                document.getString("currency")
        );
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Document toDocument() {
        return new Document(Map.of(
                "id", id,
                "title", title,
                "price", price,
                "currency", currency));
    }
}
