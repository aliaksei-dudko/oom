import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Context context = Context.newBuilder("js").build();
		Thread.sleep(10000);
		try  {
			new Processor().process(context);
		} finally {
			context.close(true);
		}
		Thread.sleep(100000);
	}


}