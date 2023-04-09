package common.query;

public interface Query {
    String process(QueryDao queryDao) throws Exception;
}
