import java.util.ArrayList;
import java.util.List;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

public class Processor {

	private List<Integer> bigArray = new ArrayList<>();

	public void process(Context context) {
		try  {
			final Source source = Source.newBuilder("js", getOOMFunction(), "function").build();
			context.eval(source);
		} catch (Throwable e) {
			System.out.println("error in js 1");
		}
	}

	public void process() {
		try  {
			allocToMax();
		} catch (Throwable e) {
			System.out.println("error in java 5");
		}
	}

	public void allocToMax() {

		System.out.println("Start");

		int allocationStep = 1024 * 1024 * 5;

		while (true) {
			List<Integer> allocation = alloc(allocationStep);
			bigArray.addAll(allocation);
		}
	}

	public List<Integer> alloc(int size) {
		int numbers = size / 8;
		List<Integer> arr = new ArrayList<>();
		for (int i = 0; i < numbers; i++) {
			arr.add(i);
		}
		return arr;
	}

	private static String getOOMFunction() {
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

	private static String getLogFunction() {
		return "console.log(\"Start easy function\");";
	}
}
