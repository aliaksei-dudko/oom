import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Context context = Context.newBuilder("js").build();
		try  {
			final Source source = Source.newBuilder("js", getFunction(), "function").cached(false).build();
			Value result = context.eval(source);
		} catch (Throwable e) {
//			e.printStackTrace();
		} finally {
			context.close(true);
		}
		Thread.sleep(100000);
	}

	private static String getFunction() {
		return "//\n" + "// Allocate a certain size to test if it can be done.\n" + "//\n" + "'use strict';\n" + "\n" + "function alloc(size) {\n"
				+ "    const numbers = size / 8;\n" + "    const arr = []\n" + "    arr.length = numbers; // Simulate allocation of 'size' bytes.\n"
				+ "    for (let i = 0; i < numbers; i++) {\n" + "        arr[i] = i;\n" + "    }\n" + "    return arr;\n" + "}\n" + "\n" + "//\n"
				+ "// Keep allocations referenced so they aren't garbage collected.\n" + "//\n" + "const allocations = [];\n" + "\n" + "//\n"
				+ "// Allocate successively larger sizes, doubling each time until we hit the limit.\n" + "//\n" + "function allocToMax() {\n" + "\n"
				+ "    console.log(\"Start\");\n" + "\n" + "    let allocationStep = 1024 * 1024 * 5;\n" + "\n" + "    while (true) {\n"
				+ "        // Allocate memory.\n" + "        const allocation = alloc(allocationStep);\n" + "\n"
				+ "        // Allocate and keep a reference so the allocated memory isn't garbage collected.\n"
				+ "        allocations.push(allocation);\n" + "        console.log(\"Size of array:\" + allocations.length);\n" + "\n" + "    }\n"
				+ "}\n" + " allocToMax();";
	}
}