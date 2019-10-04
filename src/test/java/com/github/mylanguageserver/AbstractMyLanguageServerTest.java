/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mylanguageserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.junit.jupiter.api.AfterEach;

public abstract class AbstractMyLanguageServerTest {

	protected static final String DUMMY_URI = "dummyUri";
	private String extensionUsed = ".demo";
	private MyLanguageServer myLanguageServer;

	public AbstractMyLanguageServerTest() {
		super();
	}
	
	@AfterEach
	public void tearDown() {
		if (myLanguageServer != null) {
			myLanguageServer.stopServer();
		}
	}
	
	final class DummyLanguageClient implements LanguageClient {

		@Override
		public void telemetryEvent(Object object) {
		}

		@Override
		public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
			return null;
		}

		@Override
		public void showMessage(MessageParams messageParams) {
		}

		@Override
		public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		}

		@Override
		public void logMessage(MessageParams message) {
		}
	}
	
	protected MyLanguageServer initializeLanguageServer(String text) throws URISyntaxException, InterruptedException, ExecutionException {
		return initializeLanguageServer(extensionUsed, createTestTextDocument(text, extensionUsed));
	}

	protected MyLanguageServer initializeLanguageServer(String suffixFileName, TextDocumentItem... documentItems) throws URISyntaxException, InterruptedException, ExecutionException {
		this.extensionUsed = suffixFileName;
		initializeLanguageServer(getInitParams());
		for (TextDocumentItem docItem : documentItems) {
			myLanguageServer.getTextDocumentService().didOpen(new DidOpenTextDocumentParams(docItem));
		}
		return myLanguageServer;
	}
	
	private void initializeLanguageServer(InitializeParams params) throws ExecutionException, InterruptedException {
		myLanguageServer = new MyLanguageServer();
		myLanguageServer.connect(new DummyLanguageClient());
		myLanguageServer.startServer();
		CompletableFuture<InitializeResult> initialize = myLanguageServer.initialize(params);

		assertThat(initialize).isCompleted();
		assertThat(initialize.get().getCapabilities().getCompletionProvider().getResolveProvider()).isTrue();
	}
	
	private InitializeParams getInitParams() throws URISyntaxException {
		InitializeParams params = new InitializeParams();
		params.setProcessId(new Random().nextInt());
		return params;
	}
	
	private TextDocumentItem createTestTextDocument(String text, String suffixFileName) {
		return createTestTextDocumentWithFilename(text, DUMMY_URI + suffixFileName);
	}
	
	private TextDocumentItem createTestTextDocumentWithFilename(String text, String fileName) {
		return new TextDocumentItem(fileName, MyLanguageServer.LANGUAGE_ID, 0, text);
	}

	protected CompletableFuture<Either<List<CompletionItem>, CompletionList>> getCompletionFor(MyLanguageServer camelLanguageServer, Position position) {
		return getCompletionFor(camelLanguageServer, position, DUMMY_URI+extensionUsed);
	}
	
	protected CompletableFuture<Either<List<CompletionItem>, CompletionList>> getCompletionFor(MyLanguageServer camelLanguageServer, Position position, String filename) {
		TextDocumentService textDocumentService = camelLanguageServer.getTextDocumentService();
		CompletionParams completionParams = new CompletionParams(new TextDocumentIdentifier(filename), position);
		return textDocumentService.completion(completionParams);
	}
	
}