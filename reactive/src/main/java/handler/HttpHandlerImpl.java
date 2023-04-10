package handler;

import dao.CatalogDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Product;
import model.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class HttpHandlerImpl implements HttpHandler {
    private final CatalogDao catalogDao;

    public HttpHandlerImpl(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    @Override
    public Observable<String> makeRequest(HttpServerRequest<?> request) {
        Observable<?> res;
        Map<String, List<String>> params = request.getQueryParameters();
        switch (request.getDecodedPath()) {
            case "/user/add":
                res = catalogDao.addUser(new User(
                        get(params, "id"),
                        get(params, "name"),
                        get(params, "login"),
                        get(params, "currency")
                ));
                break;
            case "/user/get":
                res = catalogDao.getUserById(get(params, "id"));
                break;
            case "/product/add":
                res = catalogDao.addProduct(new Product(
                        get(params, "id"),
                        get(params, "title"),
                        get(params, "price"),
                        get(params, "currency")
                ));
                break;
            case "/product/get":
                res = catalogDao.getProductById(
                        get(params, "user-id"),
                        get(params, "id")
                );
                break;
            case "/products":
                res = catalogDao.productsGet(get(params, "user-id"),
                        Integer.parseInt(get(params, "limit")),
                        Integer.parseInt(get(params, "offset")));
                break;
            default:
                res = Observable.just("Invalid request: " + request.getDecodedPath());
        }
        return res.map(Object::toString);
    }

    String get(Map<String, List<String>> params, String key) {
        return params.get(key).get(0);
    }
}
