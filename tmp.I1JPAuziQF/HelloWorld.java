import java.net.Socket;
import java.util.concurrent.*;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.util.concurrent.TimeUnit.*;

class HelloWorld extends sun.net.NetworkClient {
	private final ScheduledExecutorService scheduler =
		Executors.newScheduledThreadPool(1);

	private static final HelloWorld nc = new HelloWorld();
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	protected ScheduledFuture<?> dox() {
		final Runnable beeper = new Runnable() {
			final String FQDN = System.getenv("DESTINATION_FQDN") == null ? "localhost" : System.getenv("DESTINATION_FQDN");
			final int PORT = Integer.parseInt(System.getenv("DESTINATION_PORT") == null ? "8888" : System.getenv("DESTINATION_PORT"));
			public void run() {
				try (Socket s = nc.doConnect(FQDN, PORT)) {
					LocalDateTime localDateTime = LocalDateTime.now();
					if(s.isConnected()) {
						System.out.format("%s - Resolved %s and connected through port %d%n", localDateTime.format(FORMATTER), FQDN, PORT);
					} else {
						System.err.format("%s - Failed to connect to %s through port %d%n", localDateTime.format(FORMATTER), FQDN, PORT);
					}
				} catch (Exception ex) {
					LocalDateTime localDateTime = LocalDateTime.now();
					System.err.format("%s - Unexpected exception: %s%n", localDateTime.format(FORMATTER), ex);
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
