from py4j.clientserver import ClientServer, JavaParameters, PythonParameters
from helper import Helper

class SimpleHello(object):

    def __init__(self, helper):
        self.helper = helper

    def sayHello(self, int_value=None, string_value=None):
        print(int_value, string_value)
        return "Said hello to {0} with help of helper having msg: {1}".format(string_value, self.helper.help())

    def sayHelloToPojo(self, pojo):
        return  "Said hello to {0}".format(pojo.getName())

    class Java:
        implements = ["py4j.examples.IHello"]

simple_hello = SimpleHello(Helper())

gateway = ClientServer(
    java_parameters=JavaParameters(),
    python_parameters=PythonParameters(),
    python_server_entry_point=simple_hello)