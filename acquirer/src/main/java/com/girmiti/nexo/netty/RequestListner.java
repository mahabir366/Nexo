package com.girmiti.nexo.netty;

import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class RequestListner {

	private static final Logger logger = Logger.getLogger(RequestListner.class);

	private boolean isSSL = false;

	/**
	 * This method is used to start the listener for the specified port
	 * 
	 * @param port
	 * @param tSysMod
	 * @param resourceManager
	 * @throws ListenerException
	 */
	public void connectPort(final Integer port, final Integer bossGroupValue, final Integer workerGroupValue,
			final Integer connectionPool) {
		logger.info("Entering:: RequestListner:: connectPort method");
		new Thread(()->{
			try {
				RequestListner.this.run(port, bossGroupValue, workerGroupValue, connectionPool);
			} catch (Exception e) {
				logger.error("ERROR:: RequestListner:: connectPort method", e);
			}
		}).start();
		logger.info("Exiting:: RequestListner:: connectPort method");
	}

	/**
	 * @param port
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void run(Integer port, Integer bossGroupValue, Integer workerGroupValue, Integer connectionPool) {
		logger.info("Entering:: RequestListner:: run method");
		final SslContext sslCtx;
		EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupValue);
		EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupValue);
		try {
			if (isSSL) {
				SelfSignedCertificate ssc = new SelfSignedCertificate();
				sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
				} else {
				sslCtx = null;
			}

			ServerBootstrap serverBootstrap = new ServerBootstrap();

			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, connectionPool).handler(new LoggingHandler(LogLevel.DEBUG))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline channelPipeline = socketChannel.pipeline();
							if (sslCtx != null) {
								channelPipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));
							}
							channelPipeline.addLast(new RequestInboudHandler());
						}
					});

			// Bind and start to accept incoming connections.
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

			// Wait until the server socket is closed.
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			logger.error("ERROR:: RequestListner:: run method", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		logger.info("Exiting:: RequestListner:: run method");
	}

}
