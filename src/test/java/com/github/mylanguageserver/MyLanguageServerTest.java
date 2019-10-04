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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.junit.jupiter.api.Test;


public class MyLanguageServerTest extends AbstractMyLanguageServerTest {
	
	@Test
	public void testProvideCompletionForCamelBlueprintNamespace() throws Exception {
		MyLanguageServer languageServer = initializeLanguageServer("some text");
		
		CompletableFuture<Either<List<CompletionItem>, CompletionList>> completions = getCompletionFor(languageServer, new Position(0, 0));
		
		assertThat(completions.get().getLeft()).contains(new CompletionItem("demo"));
	}

	
}

