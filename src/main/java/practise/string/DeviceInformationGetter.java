package practise.string;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DeviceInformationGetter {
	private static final Logger logger = LoggerFactory.getLogger(DeviceInformationGetter.class);
	private static DeviceInformationGetter instance = null;
	private static final String PATTERN = "(\"name\"):(\"\\w+-[\\d\\-]*\").*?(\"encrypted_password\"):(\"(.*?)\").*?(\"publicKey\"):(\"(.*?)\")";
	private static final String DEVICEDATAFILE = "properties/DeviceData.properties";

	private static final Map<String, String> deviceData = new HashMap<>();

	private static Properties properties = null;
	private static HttpClient authClient = null;

	private DeviceInformationGetter() {
		readDevicesFromMdr();
	}
	final static class AcessTokenManager {
		public static String getAccessToken() {
			return "";
		}
	}
	public static DeviceInformationGetter getInstance(Properties properties, HttpClient authClient) {
		if (instance == null) {
			synchronized (DeviceInformationGetter.class) {
				if (instance == null) {
					DeviceInformationGetter.properties = properties;
					DeviceInformationGetter.authClient = authClient;
					instance = new DeviceInformationGetter();
				}
			}
		}
		return instance;
	}

	public static DeviceInformationGetter getInstance() {
		if (deviceData.isEmpty()) {
			synchronized (DeviceInformationGetter.class) {
				if (deviceData.isEmpty()) {
					readFromFile();
				}
			}
		}
		if (instance != null && !deviceData.isEmpty()) {
			return instance;
		}
		throw new IllegalStateException("Please create an instance with properties and authClient first...");
	}

	private static void readDevicesFromMdr() {
		String response = getDeviceResponse();
		System.out.println("running program");
		response = "[{\"name\":\"client-192-0-19-244\",\"id\":\"00007b6e-c462-424c-9aa3-af88cb45cf35\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"BM5RE4ObAiXCeKfDpoyOdMckHye+XZVS6Hy0Q/N/HyBa59U8Faz3wzjyJxFUsSPduUatW8N0RMNysKMa2Gs/fo4yW1rJyfFnbhRxSh8QUtIeyBpDHKe4ZNcyYwwSEl/vKwVYkf/ohhxYdaG5H3O2kHpQ5+gvROsC6FThedF/+DAt51bEbyc+2hy/ugOeKVoicx91JbStpCOud+rsxPNXvbDnhV/g+Uyi9iePoWfyMDbfH+4Nt+U8umzN1PzhuQEBokUsMkDJdeb8TK2f/dUDjAAz0u06SEyNnq3r+Gjyt4P6BUOwzmYzxVZqVUdwuPrCvRyNtGdUR2q6YfK5JSeedw==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:03:02.743Z\",\"modified\":\"2017-03-31T07:03:21.243Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00007b6e-c462-424c-9aa3-af88cb45cf35\"},{\"name\":\"client-192-0-36-136\",\"id\":\"00044bf8-4c1f-479a-ac5b-b418eb3a170b\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"QCZ6W0IqCwOsCX1UAQaQSGib1VDIA6NK8FlCWjPnZ9rWXSgdbltksSWGL9Dy0GYeNjD3JDEHg92eWS9tfdR06sQCSRraeu21j+Ct0pWvpuitq/Fua6eRwqVjJSD+o4inGQQN70E8kZ8NU8PJM7uG1pJyI/0vZfLzjlJnicsC3bTIKCf7ZHHQeRC+KbO7kjLk45R10TFR6GtX5CWHUIiHkUlG3xbF5sUKQV83j3JPPc1pLNgjGWcyDt9qmb3Fa1YCTwu0ee+WLeXFHmyrkKi/Mua4PrkNZ71KJ5lfPDdjkNQbMMknyCV1g8V9oPJv7mwJgzXGL+6Hp4Fp6nKgvX1ThA==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:07:56.309Z\",\"modified\":\"2017-03-31T07:08:15.672Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00044bf8-4c1f-479a-ac5b-b418eb3a170b\"},{\"name\":\"client-192-0-8-110\",\"id\":\"00083c19-bd7d-4df0-884e-875a4e49d63d\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"AY5wAqvZ1VrqY9uU/v7kGlZBNneoTEU7GjquXl/5iv/ZRH7KO1gfUW0OJydu0UUxiMojHK20styGk3gnS8R7nDdLSdSdCkhxMPw0/SOLNnsQUuZOaYaXgVhgO+N24V6G8FbvaVzhn3Vldf3SOdMQpnQwFHPhQPTrH7C4AvnOjxsqfGajNwv0P79yQ7RZ+36w+qwE4onmQLYHkw4qYmXMoRb3R/8xggO+vewFHxvLb/rpsR3HKeViintFnWdgp8pNB44yc4ZAk6LCWK+NCGHHi1b+16hM+Sv7getYTp2ZayEhaCoKkKTzdrsq+Skfq37yksa+VPTe2g8vB53EhnSymA==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:10:00.981Z\",\"modified\":\"2017-03-31T07:10:20.488Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00083c19-bd7d-4df0-884e-875a4e49d63d\"},{\"name\":\"client-192-0-15-178\",\"id\":\"0006e9ba-4d3f-47a3-8272-d37d3ed3534f\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"IS0DOuKhQBbNc6skjv5Lu1TGREmviPpCRWbLnj/Yht8w+CdfFCr0ykY/05TUKh+LI7jzObAKSvxaJzVbWDnkYR7C9+XWfhbqjF46Izq5Y2BxJZxlwNozzsaaCADgTHxDEWr6NPmVzCv4+BGGkSqes+crqlwsZall0oxEjeDxQ/jAngUU6zMfiCtaKYHc5ZRWPhP2lpCrxbYDwflz0dG5ELmSIzWZavgsGIj+YvCrvU5JZ3xMSgOUcJsiO6mS353jcEjdagbQ3zQqKd9UbetNyiV/tMAeC0MqyWapBm3ckgFY5LOdkfiEbc3EY4w6Y5QQfE8txIIKFvmh4xSbtpxPmQ==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:02:04.507Z\",\"modified\":\"2017-03-31T07:02:23.034Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/0006e9ba-4d3f-47a3-8272-d37d3ed3534f\"},{\"name\":\"client-192-0-129-166\",\"id\":\"00059812-804a-4dbf-9e44-382d58b0c850\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"RR7R0Gm46mFXhmHMQnzLC8TKOym6gvi2vZN4Q9PRFL3KvPU5IcqhcSg/fdaTmHqI0nSPuBkI03aR24cK2NVObZvLpmOeCshqvPOeojjkSpdzPaoZ2iscYxsKhgNmNYvtsntwHGyt1I8QXVg2eKbnGFyO10QhMQBt9yPfHxXgrLYeAwOuqmgyf9ls6LFv+oZi3krIhM02J2i5EHhqm9k1IFvh1AjYULWKwxS9HkyTcJCt0tWvKtRokyKSlH4j8H4ZUbB58v8ZJO3eUqdt+SdSEar0o6Qnb/l29B/nd03ELgM3v6umV7LlZR7XjeTFwuJud0P4DwxRZYs5R5IBhT69Cw==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T06:04:47.121Z\",\"modified\":\"2017-03-31T06:05:05.048Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00059812-804a-4dbf-9e44-382d58b0c850\"}";
		String parsedResponse = parseResponse(response);
		appendToFile(parsedResponse);
		System.out.println("string builder " + parsedResponse);
	}

	private static String getDeviceResponse() {
		String hosname = properties.getProperty("ATP_HOST");
		HttpGet getDevicesRequest = new HttpGet("https://" + hosname + "/r3_epmp_i/v1/mdr/devices?limit=10");
		getDevicesRequest.addHeader(new BasicHeader("x-epmp-customer-id", "symantecinfra1"));
		getDevicesRequest.addHeader(new BasicHeader("x-epmp-domain-id", "symantec-atp"));
		getDevicesRequest.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
		getDevicesRequest.addHeader(new BasicHeader("Authorization", "Bearer" + AcessTokenManager.getAccessToken()));
		String responseBody = "";
		try {
			HttpResponse getDevicesResponse = authClient.execute(getDevicesRequest);
			System.out.println("getDevicesResponse " + getDevicesResponse.getStatusLine());
			logger.info("getDevicesResponse " + getDevicesResponse.getStatusLine());
			responseBody = EntityUtils.toString(getDevicesResponse.getEntity(), "UTF-8");
			System.out.println("responseBody " + responseBody);
			logger.info("responseBody " + responseBody);
		} catch (Exception ex) {
			logger.error("", ex);
		}
		return responseBody;
	}

	private static String parseResponse(String str) {
		Pattern mixPattern = Pattern.compile(PATTERN);
		Matcher m = mixPattern.matcher(str);
		StringBuilder strBuilder = new StringBuilder();
		while (m.find()) {
			m.start();
			strBuilder.append(m.group(2) + ":" + m.group(4) + "\n");
			System.out.println("m2.group(1) " + m.group(1));
			System.out.println("m2.group(2) " + m.group(2));
			logger.info("m2.group(1) " + m.group(1));
			logger.info("m2.group(2) " + m.group(2));
			logger.info("m2.group(3) " + m.group(3));
			logger.info("m2.group(4) " + m.group(4));
			// // System.out.println("m2.group(5) " + m.group(5));
			logger.info("m2.group(6) " + m.group(6));
			logger.info("m2.group(7) " + m.group(7));
			logger.info("m2.group(8) " + m.group(8));
			m.end();
		}
		return strBuilder.toString();
	}

	private static void appendToFile(String str) {
		String fileName = "properties/DeviceData.properties";
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
			out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// readFromFile();
	}

	private static void readFromFile() {
		logger.info("Reading from file ");
		Properties properties = new Properties();
		try (FileInputStream in = new FileInputStream(DEVICEDATAFILE)) {
			properties.load(in);
		} catch (IOException ioe) {
			logger.error("",ioe);
		}
		Set<Object> keys = properties.keySet();
		for (Object key : keys) {
			deviceData.put((String) key, (String) properties.getProperty((String) key));
			System.out.println((String) key + ":" + (String) properties.getProperty((String) key));
		}
	}

	private CloseableHttpClient getHttpClient() throws Exception {
		String getSupportedProtocols = System.getProperty("https.protocols");
		String[] supportedProtocols = TextUtils.isBlank(getSupportedProtocols) ? null
				: getSupportedProtocols.split(" *, *");
		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial((TrustStrategy) new TrustSelfSignedStrategy()).build();

		SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslcontext, supportedProtocols, null,
				new NoopHostnameVerifier());

		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", ssf).build();

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		// cm = new PoolingNHttpClientConnectionManager(ioReactor, registry);
		// cm.setDefaultMaxPerRoute(300);
		// cm.setMaxTotal(600);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(0).setConnectionRequestTimeout(0)
				.setSocketTimeout(0).build();
		CloseableHttpClient authClient = HttpClients.custom().setDefaultRequestConfig(config).setConnectionManager(cm)
				.build();

		return authClient;
	}

	public String getDevicePassword(String deviceId) {
		return deviceData.get(deviceId);
	}
	public static void main(String[] args) throws Exception {

		// generateAccessToken();
		Properties p = new Properties();
		// String token =
		// "eyJraWQiOiJiRFEzUGYwR1RPU1RXSE1WbExqV3d3IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ7XCJkb21haW5faWRcIjpcInN5bWFudGVjLWF0cFwiLFwicHJpdnNcIjpcIm1hbmFnZV9vcmdfdW5pdHMgbWFuYWdlX2dyb3VwcyBtYW5hZ2Vfc2VydmljZXMgY3JlYXRlX2RldmljZXMgdmlld19zZXJ2aWNlcyBjcmVhdGVfY3VzdG9tZXJfdG9rZW4gdmlld19vcmdfdW5pdHMgbWFuYWdlX2RvbWFpbiB2aWV3X2dyb3VwcyB2aWV3X3Byb2R1Y3RzIG1hbmFnZV9wcm9kdWN0cyB2aWV3X2V2ZW50c1wiLFwiY3VzdG9tZXJfaWRcIjpcInN5bWFudGVjaW5mcmExXCIsXCJ1cmlcIjpcIlwvdjFcL21kclwvdXNlcnNcL1BmTm9hUU9vUnpxd0JpckRVU1NCVHdcIn0iLCJ2ZXIiOjEsImlzcyI6ImlkX2VwbXBfaV9hbGRvbWFpbiIsImV4cCI6MTQ5MTI4MjkxMiwiaWF0IjoxNDkxMTk2NTEyLCJqdGkiOiJIZ2VMNEtteFRSbUtibzZPYkR6WGt3In0.UAXs3sxoARrpY6H7xHvVfTIbQmUOb604rXcIJDt7IYtW4s4pWFYap1SVCOxUm9SyK9n0Ie_3xRaOPC4_UkvHBDKtr3hEOcheTdR2LhP1ODWoKuw5Vr-XQOMKqD_R3oN2TjugF-FvwTj9lJQqTDlFMJb-mh0aFpKn9qBNipPqp-5wZ9Yf6-pTaCCFesXQ2BLLoYh-KtZxYSW2G6jUAPcARYIgufD1xlGUIXMfL6YFZ7rE5BW4bAAhglujnnZGv3NI4HeDCGKCZubSTEqaVy7wCMCZL3qHQ1ZHB8wikv5e-D7ug-Dk7ZWu49RVHwIBpKSNxZE9fA81bpF-_XvIs9wVjg";
		// AcessTokenManager.setAccessToken(token);
		p.put("ATP_HOST", "192.0.0.2");
		DeviceInformationGetter deviceInformationGetter = new DeviceInformationGetter();
		// HttpClient authClient = getHttpClient();
		// deviceInformationGetter.readDevicesFromMdr(p, authClient);

		readFromFile();
//		logger.info("deviceWithPassword " + deviceWithPassword.size());
		// Map<Integer, DeviceDetails> deviceDetails = new HashMap<>();
		// deviceDetails.put(1, new DeviceDetails("192.0.0.10",
		// "client-192-0-129-166", 1));
		// deviceDetails.put(2, new DeviceDetails("192.0.0.11",
		// "client-192-0-19-244", 2));
		// deviceDetails.put(3, new DeviceDetails("192.0.0.12",
		// "client-192-0-121-95", 3));
		// deviceDetails.put(4, new DeviceDetails("192.0.0.14",
		// "client-192-0-129-120", 4));
		// deviceDetails.put(5, new DeviceDetails("192.0.0.15",
		// "client-192-0-8-110", 5));
		//
		// for (DeviceDetails details : deviceDetails.values()) {
		// logger.info("details " + details);
		// String devicename = details.getDeviceId();
		// logger.info("devicename " + devicename);
		// String password = deviceWithPassword.get(details.getDeviceId());
		// System.out.println("name " + devicename + " password " + password);
		//
		// }
	}

	public void generateAccessToken() throws Exception {
		HttpPost authTokenRequest = new HttpPost("https://192.0.0.2/r3_epmp_i/v1/authentication?response_type=token");
		authTokenRequest.addHeader(new BasicHeader("x-epmp-customer-id", "symantecinfra1"));
		authTokenRequest.addHeader(new BasicHeader("x-epmp-domain-id", "symantec-atp"));
		authTokenRequest.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
		authTokenRequest.setEntity(new StringEntity(
				"{\"email\":\"" + "edr@example.com" + "\"," + "\"password\":\"" + "PM7-kgKuMyOXJcTH" + "\"}."));

		CloseableHttpClient authClient = getHttpClient();
		try {
			// logger.info("Tryting to connect: " + authenticationUri);
			HttpResponse authResponse = authClient.execute(authTokenRequest);
			String responseBody = EntityUtils.toString(authResponse.getEntity(), "UTF-8");
			logger.info("response status : " + authResponse.getStatusLine());
			if (authResponse.getStatusLine().getStatusCode() != 200) {
				logger.info("Authentication response is not 200 OK, Exiting the test");
				System.exit(0);
			}
			if (null == responseBody || responseBody.isEmpty()) {
				logger.info("Authentication token not fetched, exiting the test.");
				System.exit(0);
			}
			JSONObject jsonObject = new JSONObject(responseBody);
			String token = jsonObject.getString("access_token");
			logger.info("token: " + token);
			// AcessTokenManager.setAccessToken(token);
			long tokenExpiryPeriod = jsonObject.getLong("expires_in");
			logger.info("expire time : " + tokenExpiryPeriod);

		} catch (Exception e) {

		}

	}
}
