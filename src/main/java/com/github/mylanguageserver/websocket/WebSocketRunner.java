package com.github.mylanguageserver.websocket;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketRunner.class);

	private static final String DEFAULT_HOSTNAME = "localhost";
	private static final int DEFAULT_PORT = 8025;
	private static final String DEFAULT_CONTEXT_PATH = "/";

	public void runWebSocketServer(String hostname, int port, String contextPath) {
		hostname = hostname != null ? hostname : DEFAULT_HOSTNAME;
		port = port != -1 ? port : DEFAULT_PORT;
		contextPath = contextPath != null ? contextPath : DEFAULT_CONTEXT_PATH;
		Server server = new Server(hostname, port, contextPath, null, MyLSPWebSocketServerConfigProvider.class);
		Runtime.getRuntime().addShutdownHook(new Thread(server::stop, "camel-lsp-websocket-server-shutdown-hook"));

		try {
			server.start();
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			LOGGER.error("Camel LSP Websocket server has been interrupted.", e);
			Thread.currentThread().interrupt();
		} catch (DeploymentException e) {
			LOGGER.error("Cannot start Camel LSP Websocket server.", e);
		} finally {
			server.stop();
		}
	}
}
