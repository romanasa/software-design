package dao;

import com.mongodb.rx.client.MongoDatabase;
import helpers.MongoDbHelper;
import model.Product;
import model.User;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.util.concurrent.Executors;

public class CatalogDao {
    private final Scheduler scheduler;
    private final MongoDbHelper helper;

    public CatalogDao(MongoDatabase database) {
        helper = new MongoDbHelper(database);
        scheduler = Schedulers.from(Executors.newFixedThreadPool(6));
    }

    public Observable<Boolean> addUser(User user) {
        return getUserById(user.getId())
                .flatMap(x -> {
                    if (x != null) {
                        return Observable.just(false);
                    }
                    return helper.insertOneTo("user", user.toDocument());
                })
                .subscribeOn(scheduler);
    }

    public Observable<User> getUserById(String userId) {
        return helper.findById("user", userId)
                .map(User::new)
                .singleOrDefault(null)
                .subscribeOn(scheduler);
    }


    public Observable<Boolean> addProduct(Product product) {
        return helper.insertOneTo("product", product.toDocument());
    }


    public Observable<Product> getProductById(String userId, String productId) {
        return getUserById(userId)
                .flatMap(user -> helper
                        .findById("product", productId)
                        .singleOrDefault(null)
                        .map(Product::new)
                        .map(product -> new Product(
                                product.getId(),
                                product.getTitle(),
                                Double.toString(
                                        convertCurrency(
                                                product.getCurrency(),
                                                user.getCurrency(),
                                                Double.parseDouble(product.getPrice())
                                        )

                                ),
                                user.getCurrency()))
                        .subscribeOn(scheduler)
                );
    }

    public Observable<Product> productsGet(String userId, int limit, int offset) {
        return getUserById(userId)
                .flatMap(user -> helper
                        .getAll("product")
                        .map(Product::new)
                        .skip(offset)
                        .take(limit)
                        .map(product -> new Product(
                                product.getId(),
                                product.getTitle(),
                                Double.toString(
                                        convertCurrency(
                                                product.getCurrency(),
                                                user.getCurrency(),
                                                Double.parseDouble(product.getPrice())
                                        )
                                ),
                                user.getCurrency()))
                        .subscribeOn(scheduler));
    }


    private double convertCurrency(String from, String to, double val) {
        MonetaryAmount amount = Monetary
                .getDefaultAmountFactory()
                .setCurrency(from)
                .setNumber(val)
                .create();
        CurrencyConversion conversion = MonetaryConversions.getConversion(to);
        MonetaryAmount converted = amount.with(conversion);
        return converted.getNumber().doubleValueExact();
    }

}
