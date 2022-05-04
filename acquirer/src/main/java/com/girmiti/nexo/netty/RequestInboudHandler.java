package com.girmiti.nexo.netty;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.girmiti.nexo.processor.TransactionHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Girmiti
 */
public class RequestInboudHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger.getLogger(RequestInboudHandler.class);

	private static final int LENTHBYTEINREQUEST = 4;

	private boolean firstRead = true;

	private String xmlmsg = "";

	private long posrequestlength = 0;
	
	private static final String REQUESTINBOUDHANDLER_ERROR="ERROR:: RequestInboudHandler:: processTransaction method";

	public RequestInboudHandler() {
       //Do Nothing
	}

	/**
	 * @param ctx
	 * @param msg
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String response = "";
		ByteBuf in = (ByteBuf) msg;
		if (firstRead) {
			posrequestlength = in.getUnsignedInt(0);
			firstRead = false;
		}
		xmlmsg = xmlmsg + in.toString(io.netty.util.CharsetUtil.UTF_8);
		logger.info("The Request length is : " + posrequestlength);

		try {
			if (posrequestlength < 1024 || posrequestlength == (long) (xmlmsg.length() - LENTHBYTEINREQUEST)) {
				response = processTransaction( xmlmsg);
				byte[] output = response.getBytes();
				ByteBuf firstlength = Unpooled.copyInt(output.length);
				ByteBuf firstMessage = Unpooled.copiedBuffer(response, Charset.defaultCharset());
				ctx.write(firstlength);
				ctx.writeAndFlush(firstMessage);
			} else {
				logger.error(
						"Error:: RequestInboudHandler:: received Request length is not matching with request length sent from pos");
			}
		} catch (Exception e) {
			logger.error(REQUESTINBOUDHANDLER_ERROR, e);
		}

	}

	/**
	 * @param ctx
	 * @param cause
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		logger.info("Entering:: RequestInboudHandler:: exceptionCaught method");
		logger.error(REQUESTINBOUDHANDLER_ERROR, cause);
		ctx.close();
		logger.info("Exiting:: RequestInboudHandler:: exceptionCaught method");
	}

	/**
	 * Method to process a transaction
	 * @return 
	 * 
	 * @throws IOException
	 */
	protected String processTransaction(String msg) {
		logger.info("Entering:: RequestInboudHandler:: processTransaction method");
		String responseXml = null ;
		msg = msg.substring(LENTHBYTEINREQUEST, msg.length());
		String xmlRequest = msg;
		xmlRequest = xmlRequest.replace("ns2:Document", "Document");
		xmlRequest = xmlRequest.replace("xmlns:ns2", "xmlns");
		logger.info("Entering:: RequestInboudHandler:: processTransaction method");
		logger.info("\nNexo Request :: \n" + xmlRequest);
		try {
			// here I have to call the Service layer
			byte[] result = msg.getBytes();
			responseXml = TransactionHandler.process(result);
			responseXml = responseXml.replace("ns2:Document", "Document");
			responseXml = responseXml.replace("xmlns:ns2", "xmlns");
			logger.info("\nNexo Response :: \n" + responseXml);
		} catch (Exception e) {
			logger.error(REQUESTINBOUDHANDLER_ERROR, e);
		}
		logger.info("Exiting:: RequestInboudHandler:: processTransaction method");
		return responseXml;
	}
}
