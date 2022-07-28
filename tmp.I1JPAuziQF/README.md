<h1></h1>
<p>Tests <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/net/properties.html#nct">networkaddress.cache.ttl</a></p>
<ul>
<li>
<p>Downloaded JDK from</p>
```
wget https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u292-b10/OpenJDK8U-jdk_x64_linux_hotspot_8u292b10.tar.gz
```
</li>
<li>
<p>Modify <i>/etc/resolv.conf</i> to resolve <i>www.nowayjose1.com</i> to a valid IP<p>
</li>
<li>
<p>After running the application remove the entry from previous step in order to verify the code still will be able to resolve the name until the ttl has expired</p>
</li>
</ul>
