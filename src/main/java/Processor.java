import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

public class Processor {

	public void process(Context context) {
		try  {
			final Source source = Source.newBuilder("js", getFunction(), "function").build();
			context.eval(source);
		} catch (Throwable e) {
			System.out.println("error");
//			e.getMessage();
		}
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
				+ "        allocations.push(allocation);\n"    + "\n" + "    }\n"
				+ "}\n" + " allocToMax();";
	}
}
