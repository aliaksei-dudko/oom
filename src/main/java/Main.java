import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.graalvm.polyglot.Context;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(10000);
		startInJava();
		Thread.sleep(300000);
	}

	public static void startInJsContext() throws InterruptedException {
		Context context = Context.newBuilder("js").build();
		try {
			new Processor().process(context);
		} finally {
			context.close(true);
		}
	}

	public static void startInJava() throws InterruptedException {
		new Processor().process();
	}
}