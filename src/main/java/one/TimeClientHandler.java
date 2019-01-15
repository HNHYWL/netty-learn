package one;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private final ByteBuf firstMessage;

    public TimeClientHandler() {
        byte[] req = ("QUERY TIME ORDER"+ System.getProperty("line.separator")).getBytes();
        firstMessage =  Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 100; i++) {
            byte[] req = ("QUERY TIME ORDER"+ System.getProperty("line.separator")).getBytes();
             ByteBuf firstMessage =  Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
            ctx.writeAndFlush(firstMessage);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //String body = getString((ByteBuf) msg);
        System.out.println("Now is : " + msg);
        //ctx.close();
    }

    private String getString(ByteBuf msg) throws UnsupportedEncodingException {
        ByteBuf buf = msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        return new String(req, "utf-8");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("exception!!!!!===");
        ctx.close();
    }
}
