package com.github.mylanguageserver.websocket;

import java.util.Collection;

import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.websocket.WebSocketEndpoint;

import com.github.mylanguageserver.MyLanguageServer;

public class MyLSPWebSocketEndpoint extends WebSocketEndpoint<LanguageClient> {

	@Override
	protected void configure(Builder<LanguageClient> builder) {
		builder.setLocalService(new MyLanguageServer());
		builder.setRemoteInterface(LanguageClient.class);
	}

	@Override
	protected void connect(Collection<Object> localServices, LanguageClient remoteProxy) {
		localServices.stream()
			.filter(LanguageClientAware.class::isInstance)
			.forEach(languageClientAware -> ((LanguageClientAware) languageClientAware).connect(remoteProxy));
	}

}
