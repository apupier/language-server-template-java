# language-server-template-java

template to get started writing a Language Server in Java.

It provides opinionated way to:

- default to Standard I/O communication
- have a WebScoket connection using --websocket option
- bundle as a fat jar (using SpringBoot)
- register opened files
- use Full sync mode for easier start (impact performance on big files)

# Where to start

- Check Initialization parameters com.github.mylanguageserver.MyLanguageServer.createServerCapabilities()
- Check current completion provided com.github.mylanguageserver.MyTextDocumentService.completion(CompletionParams)
- Read [LSP4J documentation](https://github.com/eclipse/lsp4j/tree/master/documentation)
- Watch recording of [Language Server for Apache Camel: Java/Eclipse plugin developer perspective | EclipseCon Europe 2018](https://www.youtube.com/watch?v=3jIgpQse-zI&list=PLU-T8l-XOWOMOVW9RrR9Vw4kc8fGzGpd1)

# How to test it

* From Eclipse Desktop IDE, for Standard I/O connection:
  * Ensure LSP4E is installed
  * Start the launch configuration "Java Template Standard IO"
  * in Preferences -> Languages Servers, add an association for the interested filetype and the launch configuration
  * open the file
* For WebSocket Connection:
  * Start the launch configuration "Java Template Websocket"
  * Follow instructions on https://github.com/apupier/language-client-template-codemirror