# language-server-template-java

template to get started writing a Language Server in Java.

It provides opinionated way to:

- default to Standard I/O communication
- have a WebScoket connection using --websocket option
- bundle as a fat jar (using SpringBoot)
- register opened files
- use Full sync mode for easier start (impact performance on big files)

# What to modify

# How to test it

- wait that I provide examples for clients :-) or better -->
- From Eclipse Desktop IDE, for Standard I/O connection:
  - Ensure LSP4E is installed
  - create a launch configuration to launch the application
  - in Preferences -> Languages Servers, add an association for the interested filetype and the launch configuration
  - open the file