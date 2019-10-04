package com.github.mylanguageserver;

import java.util.Arrays;
import java.util.List;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import com.github.mylanguageserver.websocket.WebSocketRunner;


public class Runner {
	/**
	 * For test only
	 */
	static MyLanguageServer server;
	
	private static final String WEBSOCKET_PARAMETER = "--websocket";
	private static final String PORT_PARAMETER = "--port=";
	private static final String HOSTNAME_PARAMETER = "--hostname=";
	private static final String CONTEXTPATH_PARAMETER = "--contextPath=";

	public static void main(String[] args) {
		List<String> arguments = Arrays.asList(args);
		if (arguments.contains(WEBSOCKET_PARAMETER)) {
			int port = extractPort(arguments);
			String hostname = extractHostname(arguments);
			String contextPath = extractContextPath(arguments);
			new WebSocketRunner().runWebSocketServer(hostname, port, contextPath);
		} else {
			server = new MyLanguageServer();
			Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
			server.connect(launcher.getRemoteProxy());
			launcher.startListening();
		}
	}

	private static String extractContextPath(List<String> arguments) {
		return extractParameterValue(arguments, CONTEXTPATH_PARAMETER);
	}

	private static String extractHostname(List<String> arguments) {
		return extractParameterValue(arguments, HOSTNAME_PARAMETER);
	}

	private static String extractParameterValue(List<String> arguments, String parameterToExtract) {
		for (String argument : arguments) {
			if (argument.startsWith(parameterToExtract)) {
				return argument.substring(parameterToExtract.length());
			}
		}
		return null;
	}

	private static int extractPort(List<String> arguments) {
		for (String argument : arguments) {
			if (argument.startsWith(PORT_PARAMETER)) {
				String providedPort = argument.substring(PORT_PARAMETER.length());
				try {
					return Integer.parseInt(providedPort);
				} catch (NumberFormatException nfe) {
					throw new IllegalArgumentException("The provided port is invalid.", nfe);
				}
			}
		}
		return -1;
	}
}
