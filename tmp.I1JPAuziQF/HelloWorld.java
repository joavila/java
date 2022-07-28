import java.net.Socket;
import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.*;

class HelloWorld extends sun.net.NetworkClient {
	private final ScheduledExecutorService scheduler =
		Executors.newScheduledThreadPool(1);

	private static final HelloWorld nc = new HelloWorld();

	protected ScheduledFuture<?> dox() {
		final Runnable beeper = new Runnable() {
			public void run() {
				try (Socket s = nc.doConnect("www.nowayjose1.com", 80)) {
					if( s.isConnected()) {
						System.out.println("OK");
					} else {
						System.err.println("NOK");
					}
				} catch (Exception ex) {
					System.err.println(ex);
				}
			}};
		final ScheduledFuture<?> ret =
			scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
		return ret;
	}

	public static void main( String ... args) throws Exception {
		java.security.Security.setProperty("networkaddress.cache.ttl" , "600");
		nc.dox().get();
	}
}
