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
		final ScheduledFuture<?> ret =
			scheduler.scheduleAtFixedRate(beeper, 7, 3, SECONDS);
		return ret;
	}

	public static void main( String ... args) throws Exception {
		final String TTL = System.getenv("TTL") == null ? "120" : System.getenv("TTL");
		java.security.Security.setProperty("networkaddress.cache.ttl" , TTL);
		//java.security.Security.setProperty("networkaddress.cache.negative.ttl" , "0");
		nc.dox().get();
	}
}
