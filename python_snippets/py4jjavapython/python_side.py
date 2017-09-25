from py4j.java_gateway import JavaGateway

gateway = JavaGateway()
stack = gateway.entry_point.getStack()
stack.push("First item")
stack.push("Second item")
print stack