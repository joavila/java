import java.net.Socket;
import java.util.concurrent.*;
import java.time.LocalTime;
import java.time.LocalDateTime;
import static java.util.concurrent.TimeUnit.*;

class HelloWorld extends sun.net.NetworkClient {
	private final ScheduledExecutorService scheduler =
		Executors.newScheduledThreadPool(1);

	private static final HelloWorld nc = new HelloWorld();

	protected ScheduledFuture<?> dox() {
		final Runnable beeper = new Runnable() {
			public void run() {
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				try (Socket s = nc.doConnect("www.nowayjose1.com", 80)) {
					if(s.isConnected()) {
						System.out.format("%s - %s%n", localTime, "OK");
					} else {
						System.err.format("%s - %s%n", localTime, "NOK");
					}
				} catch (Exception ex) {
					System.err.format("%s - %s%n", localTime, ex);
				}
			}};
		final ScheduledFuture<?> ret =
			scheduler.scheduleAtFixedRate(beeper, 7, 3, SECONDS);
		return ret;
	}

	public static void main( String ... args) throws Exception {
		//java.security.Security.setProperty("networkaddress.cache.ttl" , "120");
		//java.security.Security.setProperty("networkaddress.cache.negative.ttl" , "0");
		nc.dox().get();
	}
}
