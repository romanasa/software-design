package manager.http;

import com.github.vanbv.num.AbstractHttpMappingHandler;
import com.github.vanbv.num.annotation.Get;
import com.github.vanbv.num.annotation.PathParam;
import com.github.vanbv.num.annotation.Post;
import com.github.vanbv.num.annotation.RequestBody;
import com.github.vanbv.num.json.JsonParser;
import common.command.CommandProcessor;
import common.db.ConnectionProvider;
import common.query.QueryProcessor;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import manager.command.CommandDaoImpl;
import manager.query.QueryDaoImpl;
import manager.command.commands.AddNewUserCommand;
import manager.command.commands.ExtendMembershipCommand;
import manager.query.queries.GetMembershipInfoQuery;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

@ChannelHandler.Sharable
public class HttpMappingHandler extends AbstractHttpMappingHandler {
    private final CommandProcessor commandProcessor;
    private final QueryProcessor queryProcessor;

    public HttpMappingHandler(JsonParser parser) throws Exception {
        super(parser);
        this.commandProcessor = new CommandProcessor(new CommandDaoImpl(ConnectionProvider.connect()));
        this.queryProcessor = new QueryProcessor(new QueryDaoImpl(ConnectionProvider.connect()));
    }

    DefaultFullHttpResponse wrapResult(String result) {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(result, StandardCharsets.UTF_8));
    }


    @Post("/user/new")
    public DefaultFullHttpResponse userNew() {
        return wrapResult(commandProcessor.process(new AddNewUserCommand()));
    }

    public static class ExtendBody {
        private Long userId;
        private String expiryDate;

        public Long getUserId() {
            return userId;
        }

        public String getExpiryDate() {
            return expiryDate;
        }
    }

    @Post("/membership/extend")
    public DefaultFullHttpResponse membershipExtend(@RequestBody ExtendBody extendBody) {
        return wrapResult(commandProcessor.process(new ExtendMembershipCommand(extendBody.getUserId(),
                Date.valueOf(extendBody.getExpiryDate()))));
    }

    @Get("/user/{id}")
    public DefaultFullHttpResponse userGet(@PathParam(value = "id") Long userId) {
        return wrapResult(queryProcessor.process(new GetMembershipInfoQuery(userId)));
    }
}
