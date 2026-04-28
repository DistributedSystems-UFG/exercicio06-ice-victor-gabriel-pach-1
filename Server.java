import com.zeroc.Ice.*;

public class Server {

    static class PrinterI implements Demo.Printer {
        @Override
        public String printString(String s, Current current) {
            System.out.println("printString: " + s);
            return "Printed: " + s;
        }

        @Override
        public String echoString(String s, Current current) {
            System.out.println("echoString: " + s);
            return s;
        }

        @Override
        public int countChars(String s, Current current) {
            return s.length();
        }
    }

    static class CalculatorI implements Demo.Calculator {
        @Override
        public int add(int a, int b, Current current) { return a + b; }

        @Override
        public int multiply(int a, int b, Current current) { return a * b; }

        @Override
        public int subtract(int a, int b, Current current) { return a - b; }
    }

    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(
                "SimpleAdapter", "default -p 11000");

            adapter.add(new PrinterI(), Util.stringToIdentity("SimplePrinter"));
            adapter.add(new CalculatorI(), Util.stringToIdentity("SimpleCalc"));

            adapter.activate();
            System.out.println("Java server running on port 11000...");
            communicator.waitForShutdown();
        }
    }
}