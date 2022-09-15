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
			final String FQDN = System.getenv("DESTINATION_FQDN") == null ? "www.nowayjose1.com" : System.getenv("DESTINATION_FQDN");
			final int PORT = Integer.parseInt(System.getenv("DESTINATION_PORT") == null ? "80" : System.getenv("DESTINATION_PORT"));
			public void run() {
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				try (Socket s = nc.doConnect(FQDN, PORT)) {
					if(s.isConnected()) {
						System.out.format("%s - %s%n", localTime, "OK");
					} else {
						System.err.format("%s - %s%n", localTime, "NOK");
					}
				} catch (Exception ex) {
					System.err.format("%s - %s%n", localTime, ex);
				}
			}};
		final int INITIAL_DELAY_SECONDS = Integer.parseInt(System.getenv("INITIAL_DELAY_SECONDS") == null ? "7" : System.getenv("INITIAL_DELAY_SECONDS"));
		final int PERIOD_SECONDS = Integer.parseInt(System.getenv("PERIOD_SECONDS") == null ? "3" : System.getenv("PERIOD_SECONDS"));
		final ScheduledFuture<?> ret =
			scheduler.scheduleAtFixedRate(beeper, INITIAL_DELAY_SECONDS, PERIOD_SECONDS, SECONDS);
		return ret;
	}

	public static void main( String ... args) throws Exception {
		final String TTL = System.getenv("TTL") == null ? "120" : System.getenv("TTL");
		java.security.Security.setProperty("networkaddress.cache.ttl" , TTL);
		final String NEGATIVE_TTL = System.getenv("NEGATIVE_TTL") == null ? "0" : System.getenv("NEGATIVE_TTL");
		java.security.Security.setProperty("networkaddress.cache.negative.ttl" , NEGATIVE_TTL);
		nc.dox().get();
	}
}
