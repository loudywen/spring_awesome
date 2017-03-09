package com.devon.demo.metrics;

import com.codahale.metrics.*;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <h1>MetricsReporterConfig</h1>
 * 
 * <p>
 * This class is used to report the metric configuration
 * </p>
 * 
 * @author GBOS Integration Technologies
 * @version 0.1
 * @since 20 June,2016
 */
@EnableAutoConfiguration
@ComponentScan
@Service
public class MetricsReporterConfig extends MetricsConfigurerAdapter {
	// Default reporter
	private static Reporter	      reporter	                = Reporter.CONSOLE;
	public static final String	  ip	                    = "armory-metrics.cloud.wal-mart.com";
//	final Graphite	              graphite	                = new Graphite(new InetSocketAddress(ip, 2003));
	private static MetricRegistry	metricRegistry1	        = new MetricRegistry();
	public static final int	      CONFIGURATION_REPORT_TIME	= 30;
	
	/**
	 * This method is used to configure the reporters
	 * 
	 * @param metricRegistry
	 *            - MetricRegistry which holds the metricRegistry
	 * 
	 **/
	@Override
	public void configureReporters(MetricRegistry metricRegistry) {
		metricRegistry1 = metricRegistry;
		registerJVMMetrics(metricRegistry1);
		
		switch (reporter)
		{
			case CSV:
				File currentDirectory = new File(new File(".").getAbsolutePath());
				String path = null;
				File file = null;
				try {
					path = currentDirectory.getCanonicalPath() + "/metrics";
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				file = new File(path);
				if (!file.exists()) {
					file.mkdir();
				}
				else {
					FileSystemUtils.deleteRecursively(file);
					file.mkdir();
				}
				registerReporter(
				        CsvReporter.forRegistry(metricRegistry1).formatFor(Locale.US).convertRatesTo(TimeUnit.SECONDS)
				                .convertDurationsTo(TimeUnit.MILLISECONDS).build(file)).start(CONFIGURATION_REPORT_TIME,
				        TimeUnit.SECONDS);
				break;
			/*case GRAPHITE:
				registerReporter(
				        GraphiteReporter.forRegistry(metricRegistry1).prefixedWith(MetricsReporterConfig.getHostName() + ".te.tp.armory")
				                .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL)
				                .build(graphite)).start(CONFIGURATION_REPORT_TIME,
				        TimeUnit.SECONDS);
				break;*/
			case CONSOLE:
				registerReporter(
				        ConsoleReporter.forRegistry(metricRegistry1).convertRatesTo(TimeUnit.SECONDS)
				                .convertDurationsTo(TimeUnit.MILLISECONDS).build()).start(CONFIGURATION_REPORT_TIME, TimeUnit.SECONDS);
				break;
			case NULL:
				break;
		}
		
	}
	
	/**
	 * This method is used to register the metrics for JVM
	 * 
	 * @param metricRegistry
	 *            - MetricRegistry which holds the metricRegistry
	 * 
	 **/
	private void registerJVMMetrics(MetricRegistry metricRegistry) {
		MetricSet jvmMetrics = new MetricSet() {
			
			@Override
			public Map<String, Metric> getMetrics() {
				

				Map<String, com.codahale.metrics.Metric> metrics = new HashMap<String, Metric>();
				metrics.put("jvm.gc", new GarbageCollectorMetricSet());
				metrics.put("jvm.file-descriptors", new FileDescriptorRatioGauge());
				metrics.put("jvm.memory-usage", new MemoryUsageGaugeSet());
				metrics.put("jvm.threads", new ThreadStatesGaugeSet());
				return metrics;
			}
		};
		metricRegistry.registerAll(jvmMetrics);

	}
	
	private static final String	HOSTNAME_DEFAULT	= "unknown-host";
	
	/**
	 * This method is used to obtain host name
	 * 
	 * @return hostName
	 * 
	 **/
	public static String getHostName() {
		String hostname = HOSTNAME_DEFAULT;
		
		if (System.getProperty("os.name").startsWith("Windows")) {
			// Windows will always set the 'COMPUTERNAME' variable
			hostname = System.getenv("COMPUTERNAME");
		}
		else {
			hostname = getHostnameOfNonWindowsSystem();
		}
		
		return getHostnameWithoutDomainName(hostname);
	}
	
	/**
	 * This method is used to obtain host name for windows system
	 * 
	 * @return hostName
	 * 
	 **/
	private static String getHostnameOfNonWindowsSystem() {
		
		String hostname = System.getenv("HOSTNAME");
		if (hostname != null) {
			return hostname;
		}
		else {
			return getLocalHostname();
		}
	}
	
	/**
	 * This method is used to return the host name
	 * 
	 * @return hostName
	 * 
	 **/
	private static String getLocalHostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		}
		catch (Exception exception) {
			//			ArmoryLogger.getConsoleLogger().info(CommonConstants.DEFAULT_HOSTNAME + exception);
			return HOSTNAME_DEFAULT;
		}
	}
	
	/**
	 * This method is used to obtain host name without domain name
	 * 
	 * @return hostName
	 * 
	 **/
	private static String getHostnameWithoutDomainName(String hostname) {
		return hostname.replaceAll("[.].*", "");
	}
	
	/**
	 * @return the graphite
	 *//*
	public Graphite getGraphite() {
		return graphite;
	}
	*/
	/**
	 * @return the reporter
	 */
	public static Reporter getReporter() {
		return reporter;
	}
	
	/**
	 * @param reporter
	 *            the reporter to set
	 */
	public static void setReporter(Reporter reporter) {
		MetricsReporterConfig.reporter = reporter;
	}
	
	
	public static MetricRegistry metricRegistry() {
		return metricRegistry1;
	}
}
