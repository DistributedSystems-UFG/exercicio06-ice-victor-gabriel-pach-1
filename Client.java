import com.zeroc.Ice.*;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {

            String serverIp = "SERVIDOR_PRIVATE_IP"; // <-- change this

            // --- Printer object (talking to the Python server from Ex05) ---
            ObjectPrx base = communicator.stringToProxy(
                "SimplePrinter:tcp -h " + serverIp + " -p 11000");
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            if (printer == null) throw new Error("Invalid proxy");

            String r1 = printer.printString("Hello from Java client!");
            System.out.println("printString response: " + r1);

            String r2 = printer.echoString("Java echo test");
            System.out.println("echoString response: " + r2);

            int n = printer.countChars("hello world");
            System.out.println("countChars: " + n);

            // --- Calculator object ---
            ObjectPrx base2 = communicator.stringToProxy(
                "SimpleCalc:tcp -h " + serverIp + " -p 11000");
            Demo.CalculatorPrx calc = Demo.CalculatorPrx.checkedCast(base2);
            if (calc == null) throw new Error("Invalid calc proxy");

            System.out.println("7 + 5 = " + calc.add(7, 5));
            System.out.println("7 x 5 = " + calc.multiply(7, 5));
            System.out.println("7 - 5 = " + calc.subtract(7, 5));

        } catch (LocalException e) {
            e.printStackTrace();
        }
    }
}