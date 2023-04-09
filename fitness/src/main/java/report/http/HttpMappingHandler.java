package report.http;

import com.github.vanbv.num.AbstractHttpMappingHandler;
import com.github.vanbv.num.annotation.Get;
import com.github.vanbv.num.annotation.QueryParam;
import com.github.vanbv.num.json.JsonParser;
import common.db.ConnectionProvider;
import common.query.QueryProcessor;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import report.query.QueryDaoImpl;

import io.netty.handler.codec.http.HttpResponseStatus;
import report.query.queries.GetStatsQuery;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class HttpMappingHandler extends AbstractHttpMappingHandler {
    private final QueryProcessor queryProcessor;

    public HttpMappingHandler(JsonParser parser) throws Exception {
        super(parser);
        this.queryProcessor = new QueryProcessor(new QueryDaoImpl(ConnectionProvider.connect()));
    }

    DefaultFullHttpResponse wrapResult(String result) {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(result, StandardCharsets.UTF_8));
    }

    @Get("/stats")
    public DefaultFullHttpResponse enter(@QueryParam(value = "from") String fromDate,
                                         @QueryParam(value = "to") String toDate) {
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);
        return wrapResult(queryProcessor.process(new GetStatsQuery(from, to)));
    }
}
