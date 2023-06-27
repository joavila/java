<h1>
<p>Tests <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/net/properties.html#nct">networkaddress.cache.ttl</a></p>
</h1>
<ul>
<li>
<p>Downloaded JDK from</p>

```bash
wget https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u292-b10/OpenJDK8U-jdk_x64_linux_hotspot_8u292b10.tar.gz
```

</li>
<li>
<p>Modify <i>/etc/resolv.conf</i> to resolve <i>System.getenv("FQDN")</i> to a valid IP<p>
</li>
<li>
<p>After running the application remove the entry from previous step in order to verify the code still will be able to resolve the name until the ttl has expired</p>
</li>
<li>
<table>
<tr>
<th>
Environemental variable
</th>
<th>
Usage
</th>
</tr>
<tr>
<td>
DESTINATION_FQDN
</td>
<td>Defines destination FQDN. <i>localhost</i> otherwise.
</tr>
<tr>
<td>
DESTINATION_PORT
</td>
<td>Defines destination PORT. <i>8888</i> otherwise.
</tr>
<tr>
<td>
TTL
</td>
<td>Defines <i>networkaddress.cache.ttl</i>. <i>120</i> otherwise.
</tr>
<tr>
<td>
NEGATIVE_TTL
</td>
<td>Defines <i>networkaddress.cache.negative.ttl</i>. <i>0</i> otherwise.
</tr>
<tr>
<td>
INITIAL_DELAY_SECONDS
</td>
<td>Defines <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html#scheduleAtFixedRate-java.lang.Runnable-long-long-java.util.concurrent.TimeUnit-">initialDelay</a>. <i>7</i> otherwise.
</tr>
<tr>
<td>
PERIOD_SECONDS
</td>
<td>Defines <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html#scheduleAtFixedRate-java.lang.Runnable-long-long-java.util.concurrent.TimeUnit-">period</a>. <i>3</i> otherwise.
</tr>
</table>
</ul>
