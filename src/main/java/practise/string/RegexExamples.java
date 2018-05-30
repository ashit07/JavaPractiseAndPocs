package practise.string;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;

public class RegexExamples {

	public static void du() {
		String str = "{//event id_event version_percentage8000_1.0.0_100,8001_1.0.0_0,8002_1.0.0_0,8003_1.0.0_0,8004_1.0.0_0,8005_1.0.0_0,8006_1.0.0_0,8007_1.0.0_0,8009_1.0.0_0}";
		Pattern mixPattern = Pattern.compile("((\\d+)_([\\d\\.]*)_(\\d+))+");
		Matcher m = mixPattern.matcher(str);
		while (m.find()) {
			m.start();
			Integer event = Integer.valueOf(m.group(2));
			// System.out.println("event m.group(2)"+ event);
			// System.out.println("m.group(3) " + m.group(3));
			// System.out.println("m.group(4) " + m.group(4));

			m.end();
		}

	}

	public static void du2() {
		String str = "{COMMON_EOCPROCESS_1.0.0_TEMPLATE-20-40,COMMON_EOCPROCESS_1.0.0_TEMPLATE-2-4,COMMON_EOCPROCESS_1.0.0_TEMPLATE-10-20,COMMON_EOCPROCESS_1.0.0_TEMPLATE-5-10}";
		// Pattern mixPattern =
		// Pattern.compile("(([\\w\\_]*)_([\\d\\.]*)_(\\w+)-(\\d+)-(\\d+))+");
		Pattern mixPattern = Pattern.compile("([\\w\\_]*_[\\d\\.]*_\\w+)-(\\d+)-(\\d+)");

		Matcher m = mixPattern.matcher(str.replaceAll("[{}]", ""));
		System.out.println("m.find: " + m.find());
		while (m.find()) {
			m.start();
			System.out.println("m2.group(1) " + m.group(1));

			System.out.println("m2.group(2) " + m.group(2));
			System.out.println("m2.group(3) " + m.group(3));
			// System.out.println("m2.group(4) " + m.group(4));
			// System.out.println("m2.group(4) " + m.group(5));
			// System.out.println("m2.group(4) " + m.group(6));
			m.end();
		}

	}

	public static void du3() {
		String str = "{COMMON_EOCPROCESS_1.0.0_TEMPLATE-1-5,COMMON_EOCPROCESS_1.0.0_TEMPLATE-2-4,COMMON_EOCPROCESS_1.0.0_TEMPLATE-10-20,COMMON_EOCPROCESS_1.0.0_TEMPLATE-5-10}";
		String str2 = str.replaceAll("[{}]", "");
		System.out.println(str2);
		String[] templates = str2.split(",");
		for (String template : templates) {
			String[] vals = template.split("-");
			System.out.println(vals[0]);
			System.out.println(vals[1]);
			System.out.println(vals[2]);

		}

	}

	public static void du4() {
		String str = "[{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785370,\"some_type\":{\"ll_timestamp\":\"2007-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":38,\"version\":1,\"userid\":\"576846228d13b7dcc9506854\",\"twitterid\":\"DMiranda\",\"stream_id\":\"stream_2\",\"followers_count\":47825,\"location\":{\"latitude\":54.189559,\"longitude\":-27.059444},\"source_id\":\"sentiment\",\"event\":\"Congratulations to Leo on his first Oscar ever!!!!\",\"screenname\":\"Deirdre_Miranda\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_2\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785372,\"some_type\":{\"ll_timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":16,\"version\":1,\"userid\":\"57695930d5abe420b318c769\",\"twitterid\":\"Calhoun\",\"stream_id\":\"stream_1\",\"followers_count\":742618,\"location\":{\"latitude\":6.849549,\"longitude\":-78.741909},\"source_id\":\"sentiment\",\"event\":\"Please: Do everything you possibly can in onelifetime\",\"screenname\":\"Maricela_Tran\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_1\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785381,\"some_type\":{\"ll_timestamp\":\"20005-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":28,\"version\":1,\"userid\":\"57684622050bdb1c30f95fb0\",\"twitterid\":\"BurtG\",\"stream_id\":\"stream_3\",\"followers_count\":15583,\"location\":{\"latitude\":36.382862,\"longitude\":72.707757},\"source_id\":\"sentiment\",\"event\":\"LookingforwardtothenewFinalFantasyXV\",\"screenname\":\"Graham_Burt\",\"timestamp\":\"2005-10-02T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_3\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248795001,\"some_type\":{\"ll_timestamp\":\"2004-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":19,\"version\":1,\"userid\":\"57684622f45acfaacae21829\",\"twitterid\":\"Roman\",\"stream_id\":\"stream_0\",\"followers_count\":35581,\"location\":{\"latitude\":51.336686,\"longitude\":106.491121},\"source_id\":\"sentiment\",\"event\":\"I think comic book bad guys have the right idea, aimingtheirweaponsdirectlyatCaptainAmerica'sshield.That'sprobablyhisweakpoint.\",\"screenname\":\"Bridges_Larsen\",\"timestamp\":\"2004-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_0\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248795000,\"some_type\":{\"ll_timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":16,\"version\":1,\"userid\":\"57695930d5abe420b318c769\",\"twitterid\":\"Calhoun\",\"stream_id\":\"stream_4\",\"followers_count\":742618,\"location\":{\"latitude\":6.849549,\"longitude\":-78.741909},\"source_id\":\"sentiment\",\"event\":\"Please: Do everything you possibly can in onelifetime\",\"screenname\":\"Maricela_Tran\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_4\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"}]";
		String pattern = "(\"@timestamp\"):(\\d+)";
		// String str2 = str.replaceAll("(@timestamp):(\\d+)", "");
		Pattern mixPattern = Pattern.compile(pattern);

		Matcher m = mixPattern.matcher(str);
		System.out.println("m.find: " + m.find());
		while (m.find()) {
			m.start();
			System.out.println("m2.group(1) " + m.group(1));
			System.out.println("m2.group(2) " + m.group(2));
			// System.out.println("m2.group(4) " + m.group(4));
			// System.out.println("m2.group(4) " + m.group(5));
			// System.out.println("m2.group(4) " + m.group(6));
			m.end();
		}

		// System.out.println(str2);
	}

	public static void du6() {
		String str = "[{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785370,\"some_type\":{\"ll_timestamp\":\"2007-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":38,\"version\":1,\"userid\":\"576846228d13b7dcc9506854\",\"twitterid\":\"DMiranda\",\"stream_id\":\"stream_2\",\"followers_count\":47825,\"location\":{\"latitude\":54.189559,\"longitude\":-27.059444},\"source_id\":\"sentiment\",\"event\":\"Congratulations to Leo on his first Oscar ever!!!!\",\"screenname\":\"Deirdre_Miranda\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_2\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785372,\"some_type\":{\"ll_timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":16,\"version\":1,\"userid\":\"57695930d5abe420b318c769\",\"twitterid\":\"Calhoun\",\"stream_id\":\"stream_1\",\"followers_count\":742618,\"location\":{\"latitude\":6.849549,\"longitude\":-78.741909},\"source_id\":\"sentiment\",\"event\":\"Please: Do everything you possibly can in onelifetime\",\"screenname\":\"Maricela_Tran\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_1\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785381,\"some_type\":{\"ll_timestamp\":\"20005-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":28,\"version\":1,\"userid\":\"57684622050bdb1c30f95fb0\",\"twitterid\":\"BurtG\",\"stream_id\":\"stream_3\",\"followers_count\":15583,\"location\":{\"latitude\":36.382862,\"longitude\":72.707757},\"source_id\":\"sentiment\",\"event\":\"LookingforwardtothenewFinalFantasyXV\",\"screenname\":\"Graham_Burt\",\"timestamp\":\"2005-10-02T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_3\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248795001,\"some_type\":{\"ll_timestamp\":\"2004-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":19,\"version\":1,\"userid\":\"57684622f45acfaacae21829\",\"twitterid\":\"Roman\",\"stream_id\":\"stream_0\",\"followers_count\":35581,\"location\":{\"latitude\":51.336686,\"longitude\":106.491121},\"source_id\":\"sentiment\",\"event\":\"I think comic book bad guys have the right idea, aimingtheirweaponsdirectlyatCaptainAmerica'sshield.That'sprobablyhisweakpoint.\",\"screenname\":\"Bridges_Larsen\",\"timestamp\":\"2004-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_0\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248795000,\"some_type\":{\"ll_timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":16,\"version\":1,\"userid\":\"57695930d5abe420b318c769\",\"twitterid\":\"Calhoun\",\"stream_id\":\"stream_4\",\"followers_count\":742618,\"location\":{\"latitude\":6.849549,\"longitude\":-78.741909},\"source_id\":\"sentiment\",\"event\":\"Please: Do everything you possibly can in onelifetime\",\"screenname\":\"Maricela_Tran\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_4\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"}]";
		String pattern = ",(\"@timestamp\"):(\\d+)";
		String str2 = str.replaceAll(pattern, "");
		System.out.println(str2);
	}

	public static void du5() {
		String str = "[{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785370,\"some_type\":{\"ll_timestamp\":\"2007-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":38,\"version\":1,\"userid\":\"576846228d13b7dcc9506854\",\"twitterid\":\"DMiranda\",\"stream_id\":\"stream_2\",\"followers_count\":47825,\"location\":{\"latitude\":54.189559,\"longitude\":-27.059444},\"source_id\":\"sentiment\",\"event\":\"Congratulations to Leo on his first Oscar ever!!!!\",\"screenname\":\"Deirdre_Miranda\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_2\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785372,\"some_type\":{\"ll_timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":16,\"version\":1,\"userid\":\"57695930d5abe420b318c769\",\"twitterid\":\"Calhoun\",\"stream_id\":\"stream_1\",\"followers_count\":742618,\"location\":{\"latitude\":6.849549,\"longitude\":-78.741909},\"source_id\":\"sentiment\",\"event\":\"Please: Do everything you possibly can in onelifetime\",\"screenname\":\"Maricela_Tran\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_1\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248785381,\"some_type\":{\"ll_timestamp\":\"20005-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":28,\"version\":1,\"userid\":\"57684622050bdb1c30f95fb0\",\"twitterid\":\"BurtG\",\"stream_id\":\"stream_3\",\"followers_count\":15583,\"location\":{\"latitude\":36.382862,\"longitude\":72.707757},\"source_id\":\"sentiment\",\"event\":\"LookingforwardtothenewFinalFantasyXV\",\"screenname\":\"Graham_Burt\",\"timestamp\":\"2005-10-02T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_3\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248795001,\"some_type\":{\"ll_timestamp\":\"2004-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":19,\"version\":1,\"userid\":\"57684622f45acfaacae21829\",\"twitterid\":\"Roman\",\"stream_id\":\"stream_0\",\"followers_count\":35581,\"location\":{\"latitude\":51.336686,\"longitude\":106.491121},\"source_id\":\"sentiment\",\"event\":\"I think comic book bad guys have the right idea, aimingtheirweaponsdirectlyatCaptainAmerica'sshield.That'sprobablyhisweakpoint.\",\"screenname\":\"Bridges_Larsen\",\"timestamp\":\"2004-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_0\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"},{\"@product_id\":\"prodect3ecee_TC15829\",\"@timestamp\":1490248795000,\"some_type\":{\"ll_timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\",\"sentiment\":\"\",\"friends_count\":16,\"version\":1,\"userid\":\"57695930d5abe420b318c769\",\"twitterid\":\"Calhoun\",\"stream_id\":\"stream_4\",\"followers_count\":742618,\"location\":{\"latitude\":6.849549,\"longitude\":-78.741909},\"source_id\":\"sentiment\",\"event\":\"Please: Do everything you possibly can in onelifetime\",\"screenname\":\"Maricela_Tran\",\"timestamp\":\"2006-10-01T09:45:00.000UTC+00:00\"},\"@id\":\"tenant1ecee_tc15829stream_4\",\"@doc_type_id\":\"MaP3_5_c3dees_390___AXA____\",\"@doc_type_version\":\"1.1\",\"@tenant_id\":\"tenant1ecee_TC15829\"}]";

		JSONArray jsonArr = new JSONArray(str);
		JSONObject jsonObj = jsonArr.getJSONObject(0);
		jsonObj.remove("@timestamp");
		System.out.println(jsonArr.toString());

	}

	public static String du7() {
		String str = "[{\"name\":\"client-192-0-19-244\",\"id\":\"00007b6e-c462-424c-9aa3-af88cb45cf35\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"BM5RE4ObAiXCeKfDpoyOdMckHye+XZVS6Hy0Q/N/HyBa59U8Faz3wzjyJxFUsSPduUatW8N0RMNysKMa2Gs/fo4yW1rJyfFnbhRxSh8QUtIeyBpDHKe4ZNcyYwwSEl/vKwVYkf/ohhxYdaG5H3O2kHpQ5+gvROsC6FThedF/+DAt51bEbyc+2hy/ugOeKVoicx91JbStpCOud+rsxPNXvbDnhV/g+Uyi9iePoWfyMDbfH+4Nt+U8umzN1PzhuQEBokUsMkDJdeb8TK2f/dUDjAAz0u06SEyNnq3r+Gjyt4P6BUOwzmYzxVZqVUdwuPrCvRyNtGdUR2q6YfK5JSeedw==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:03:02.743Z\",\"modified\":\"2017-03-31T07:03:21.243Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00007b6e-c462-424c-9aa3-af88cb45cf35\"},{\"name\":\"client-192-0-36-136\",\"id\":\"00044bf8-4c1f-479a-ac5b-b418eb3a170b\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"QCZ6W0IqCwOsCX1UAQaQSGib1VDIA6NK8FlCWjPnZ9rWXSgdbltksSWGL9Dy0GYeNjD3JDEHg92eWS9tfdR06sQCSRraeu21j+Ct0pWvpuitq/Fua6eRwqVjJSD+o4inGQQN70E8kZ8NU8PJM7uG1pJyI/0vZfLzjlJnicsC3bTIKCf7ZHHQeRC+KbO7kjLk45R10TFR6GtX5CWHUIiHkUlG3xbF5sUKQV83j3JPPc1pLNgjGWcyDt9qmb3Fa1YCTwu0ee+WLeXFHmyrkKi/Mua4PrkNZ71KJ5lfPDdjkNQbMMknyCV1g8V9oPJv7mwJgzXGL+6Hp4Fp6nKgvX1ThA==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:07:56.309Z\",\"modified\":\"2017-03-31T07:08:15.672Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00044bf8-4c1f-479a-ac5b-b418eb3a170b\"},{\"name\":\"client-192-0-8-110\",\"id\":\"00083c19-bd7d-4df0-884e-875a4e49d63d\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"AY5wAqvZ1VrqY9uU/v7kGlZBNneoTEU7GjquXl/5iv/ZRH7KO1gfUW0OJydu0UUxiMojHK20styGk3gnS8R7nDdLSdSdCkhxMPw0/SOLNnsQUuZOaYaXgVhgO+N24V6G8FbvaVzhn3Vldf3SOdMQpnQwFHPhQPTrH7C4AvnOjxsqfGajNwv0P79yQ7RZ+36w+qwE4onmQLYHkw4qYmXMoRb3R/8xggO+vewFHxvLb/rpsR3HKeViintFnWdgp8pNB44yc4ZAk6LCWK+NCGHHi1b+16hM+Sv7getYTp2ZayEhaCoKkKTzdrsq+Skfq37yksa+VPTe2g8vB53EhnSymA==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:10:00.981Z\",\"modified\":\"2017-03-31T07:10:20.488Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00083c19-bd7d-4df0-884e-875a4e49d63d\"},{\"name\":\"client-192-0-15-178\",\"id\":\"0006e9ba-4d3f-47a3-8272-d37d3ed3534f\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"IS0DOuKhQBbNc6skjv5Lu1TGREmviPpCRWbLnj/Yht8w+CdfFCr0ykY/05TUKh+LI7jzObAKSvxaJzVbWDnkYR7C9+XWfhbqjF46Izq5Y2BxJZxlwNozzsaaCADgTHxDEWr6NPmVzCv4+BGGkSqes+crqlwsZall0oxEjeDxQ/jAngUU6zMfiCtaKYHc5ZRWPhP2lpCrxbYDwflz0dG5ELmSIzWZavgsGIj+YvCrvU5JZ3xMSgOUcJsiO6mS353jcEjdagbQ3zQqKd9UbetNyiV/tMAeC0MqyWapBm3ckgFY5LOdkfiEbc3EY4w6Y5QQfE8txIIKFvmh4xSbtpxPmQ==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:02:04.507Z\",\"modified\":\"2017-03-31T07:02:23.034Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/0006e9ba-4d3f-47a3-8272-d37d3ed3534f\"},{\"name\":\"client-192-0-129-166\",\"id\":\"00059812-804a-4dbf-9e44-382d58b0c850\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"RR7R0Gm46mFXhmHMQnzLC8TKOym6gvi2vZN4Q9PRFL3KvPU5IcqhcSg/fdaTmHqI0nSPuBkI03aR24cK2NVObZvLpmOeCshqvPOeojjkSpdzPaoZ2iscYxsKhgNmNYvtsntwHGyt1I8QXVg2eKbnGFyO10QhMQBt9yPfHxXgrLYeAwOuqmgyf9ls6LFv+oZi3krIhM02J2i5EHhqm9k1IFvh1AjYULWKwxS9HkyTcJCt0tWvKtRokyKSlH4j8H4ZUbB58v8ZJO3eUqdt+SdSEar0o6Qnb/l29B/nd03ELgM3v6umV7LlZR7XjeTFwuJud0P4DwxRZYs5R5IBhT69Cw==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T06:04:47.121Z\",\"modified\":\"2017-03-31T06:05:05.048Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00059812-804a-4dbf-9e44-382d58b0c850\"},{\"name\":\"client-192-0-27-238\",\"id\":\"00062e30-feee-4ab5-9c1e-673b966dc24c\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"IFNN05MNv5wX2sziooUTMW8Q1nzrBHxnrYQxOQ5h3tvDGyFxbMhNNOruBS94NZ5IlXhUKNEoXvGKubx2iO3AhrD9FK5lIi5WFsxgBj3dm+TaGZggtJVIn5via5CH9+pR9P24/zX67x1yh6RZLN2M44whjjtWBWSpnsHA7K+jeOr1BUzavHk+a00ZEHOA9yBA6ue4nqsTgO2q2FHA3qG9741SXEvXbYapoIcnynrHn1JpDG5m1Okz3nW+zOBro7lR5yv3FGiI+U1cl7b6zX7nW7nCxj7+1tlKPHn1wtbXcwy8z708MK70OrTEWpjHEXVuN7470BOUAJl4fAtWE7mQmQ==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:05:25.068Z\",\"modified\":\"2017-03-31T07:05:43.789Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00062e30-feee-4ab5-9c1e-673b966dc24c\"},{\"name\":\"client-192-0-121-95\",\"id\":\"00148a09-74bd-4116-9dee-b03260ba511a\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"OzXe3fjcDRWhGbQNmEdc8rOT3XTwA7Fi5Ge0g943F9tjop6QPLrUKIYBvrLR3uo8UpwS4bf8unSQyAWvmwiC/i2MUeEQ9xZAp3Na0bZ7s/24qzRqjVHw46v3pw9aT09aV+qobLX0UiXGF4F9DftqkyQUrZJY93wVdK0Eet01D7TK94jg578RBfH6Ux5byqJbTXbboinJD7kUXexvXkKwIr+chM71UxkMMqeN2g0zeQ41UDuTfdSfxIKw2HLg1ixB6Q/PN9v9PkV089xkBhfjSWMthCjA3WDHbklpBj2tZZtfMTMdS/6zZED3Qndx4UPYz+pO4DpaOcConVCNhVIFng==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T06:02:51.309Z\",\"modified\":\"2017-03-31T06:03:12.398Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00148a09-74bd-4116-9dee-b03260ba511a\"},{\"name\":\"client-192-0-125-141\",\"id\":\"001fb26f-a5cd-47f1-bc7d-315a9eb5f3cb\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"lPmRwQHu/n9+xmHenhp7Vk2LP2XozVJqXQefKOlJa0unLcNqS7QJobmU/gCxqxsPBhrJvRunjN/33lmpZxuwN/tytF5lP9xtiCcGMNwTh55U4eYrAFUYZmEeOjUwXKsr12WV8Upmboq6KH4p/9GQREWhHVbnH/4Q8oTBrY2Ejo1PRAkiNFuwF+SJbo1OPiKuZxqMkNeDMlXepqCtxiSxuXi9sm9wfd5YxY64cKde4mEiOiLZxK6cc9sPcelrey9alhTi6UyZxCilfJU2HWMbySVzYOtqGOYen1Cs17k2i3UD7tBU+cRVHHYtm0QV3TDXjLsR0GTi03sD6cZaYs5jdg==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T06:03:47.268Z\",\"modified\":\"2017-03-31T06:04:05.645Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/001fb26f-a5cd-47f1-bc7d-315a9eb5f3cb\"},{\"name\":\"client-192-0-8-33\",\"id\":\"000f5627-a97b-49f9-b5cc-66b6acacff2b\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"Fk9w6zGZgbkOc9evH0Gv5C4nmbUdk+JbxjTlOV9AYcIJLxD3OieK/Ip//joi0wWC5xefgDlgX9CqRaBozVK1xMfF4PEwuehjxVqRcEvrzxqnj9ZpBWBXVcMlio5BBT+bhrxXRFezhxetuaySnNTm8noSr/cf785RoPe1P93tSziVxqeoG4FSr7FenA2cC35TVJv0bBtqkQMBB07YbY6azr4TYygxyzsG8l7ErW+HX3yPSUc++j067xmDCAA10OGSUVEj5ZpzF6fr0XSMB4YD3K5LOumVRMl4qg/MZ1vzyJohjxPFavBEPokRx6dlstQphd0UauJgGAxQwWpiGVSFIg==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:09:59.204Z\",\"modified\":\"2017-03-31T07:10:18.634Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/000f5627-a97b-49f9-b5cc-66b6acacff2b\"},{\"name\":\"client-192-0-119-158\",\"id\":\"000a878d-9135-4f84-80b1-349ab169ce3d\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"hT2mANHjYW+V0yLr1XcKfuqZVIBKGrKXwkGWJPJhNTw/XvAtyZCP7fhGTIdcYr3dW1I0//8dgWtu7WdAHVLC0VutghYLrSaUi5MwmUUrL3MtZvYjvJ132PLe8N7w4VBqZlh/eeH4/WOQL676GP7Tfm2/Z1hNyX58MOmj9dsl8o2Cc5HnMtC25SCuYsRZQ09+18EvsrL3xPXr/bfQUczzHVzo5VxQlI+hvwOlhf2lfTuMJN5ie/QRvTUqbRWvsj0ws6stXEzQbuh+lpA5OtZwFODNmt6Ff6sjDyl5Y8SXzDKvSCZX4L6n8JF+rRSPg80g8kRv75MEuEupQmCeGiNw5Q==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrDeviceCreated\"}},\"created\":\"2017-03-31T05:52:02.374Z\",\"modified\":\"2017-03-31T05:52:02.374Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/000a878d-9135-4f84-80b1-349ab169ce3d\"},{\"name\":\"client-192-0-0-75\",\"id\":\"0018f708-abab-4ce7-b64e-2b68e922968f\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"uM/JGbMOK6ihC2ekqgpZDfOpRVHEvhHHkfFXVMa8nZNnMRhUufQJzrC1TQvVilknvO1zNgYocPP0GliwyRCZeGe3xtX/d8CBCNsXfhNGg+PdK6hnUjC+sR2QOX6rHZGmriSaPLvvOO0f+T4HiT02SGHfXhF2fBWiFKjxcACPzWcAMLxTxEMd8eEbj83qKVbFV+uuo95knZ/i0QHrc4dylKfsH49UmlxzbDSB1uS9fzu6/UzVRyV3LemHQDR5pxc+NDHy1NDsKlfp3EeHOZEx5/9dg6pW3wl/Fi8ZxBaAKlB7NTRCe2bRcf/DKKnroVFdrt3ejetHcWg2GWvCy2izJA==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T05:46:59.740Z\",\"modified\":\"2017-03-31T05:47:19.750Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/0018f708-abab-4ce7-b64e-2b68e922968f\"},{\"name\":\"client-192-0-10-68\",\"id\":\"001c4d0f-cbcf-4420-b563-1ed69165c058\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"P4LSE+6BrlZqiay2HZbODeNUlaf2N0xu2vTd6+g8KwV/nmL0FmKCmUe5XqWvFAehbGvI/zfS51aynYM5uAld6HZMXmR9nXUiBbQMSUqCtOuSgnI03qglMGcw7obzpDk5Nd0eCsSPKfYd2czQBiNLCA4l8vElYi1ZP28Qwgd5cVjUe2Eqr8YQ6IizzqjMnqC14OAyHXYvj3prbAtdATpbtzNs6WZknN+nN/nCIZggklegYLeC2DeFn6kSsE+IIQbOjBx0JnNmS/l+hJPIRE51clDgriMjVON8Wf7mwlHg38Uv4k77N4i1hluDc8IzkcothMTE5g8u+AxnBhgaBA79cA==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T05:48:23.773Z\",\"modified\":\"2017-03-31T05:48:41.699Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/001c4d0f-cbcf-4420-b563-1ed69165c058\"},{\"name\":\"client-192-0-108-155\",\"id\":\"00245f1d-f398-4bd3-be11-e992b55a70fc\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"MG9/ZKdmh1AA12ScFxy7sOrQKcyZ3nLV1UPKSzmKEkQmd4hlNWjvA52bVAYB0QTJjnvDYu6ZvH2QL2bbE7M5pgodIU73+WePVkvPCUHPo0P7/HoQy3Abmg9InBNUuKwOE7XjjLVAp1Ooc9MQUtuyIcjhYPWSdxnk8mq+2EG3DU8MwAzbp9Z2xxhHddgNN7ngyUDkz7esr1DR4RgdXGOrDO8ZyW48PbjDSZE9FZ7bO/AYQd6tUKTAFDzEkhfBk7Vn0rEqY4ZyjCxUJUbk9VHIJ6bfT8qeMIOhEZ59MuT92t/S6CcjN5hS9qkYkyHhe2dmCE0Cct5ZUXlTBRwVI9nANw==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T05:49:21.202Z\",\"modified\":\"2017-03-31T05:49:39.954Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00245f1d-f398-4bd3-be11-e992b55a70fc\"},{\"name\":\"client-192-0-119-30\",\"id\":\"0027f37b-9bce-4908-8175-6986273e9896\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"LN2m60iYI+WPUOXwTeCMjkpgtAnemx/1aDGpc2ncIJK7jQVcTk53pigpXjkolvz5H86yit26FIN5oFd2y200P0l97Au6wDIvksS5AbBRONEb6u6Sfb1VzZQ8Skth31KNtYMIHx37bgQTu92yQoJSS4TWhkH6I7tamDfeGEwIQPk3enmmOxcUsknhxgwKmI9WjDAvSqjWoPaGZDkqkgch2w+sJVNM5kc/3Kh5yiiToEJt20CiTAl0lNs2Ui9Xn7A8N1Wk/dz4JRKw6VuuGI/pghMNZS9HAziMX140JN3SZsetdNffvDVD0tssA9t+gkyoJ8r7GwvBT13LyzBJbvbq1g==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrDeviceCreated\"}},\"created\":\"2017-03-31T05:52:01.622Z\",\"modified\":\"2017-03-31T05:52:01.622Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/0027f37b-9bce-4908-8175-6986273e9896\"},{\"name\":\"client-192-0-127-94\",\"id\":\"0027d706-73a2-4ccc-917f-90e21cf1816b\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"oVKaJzjVmdyFO+V5ymXqMTSDAR0kRAopYuegZUfFGKkB2XuTuNxsl0fbw2zhHWThHuY0PrKQFNNvFgvsB40jZu2un6v6VEkEjNPwGU1RMq3A6vvFBTAF+Fn4cwojFrqCOf7HShSfmmRLqblQchHX7vU1+qOYDwFKZ60er7NxXIAwmh8/6FCoXpkh9I2l66qxKzw6bK91gVdAy7ELRSt+DROZqJKHfeCyWhncHNYI1z/pRVS4WkUzidkxQ5HwUay8J7KcMW1YugDyrBpLCyz8q2JFyPfrf4ENqiq0MwiiuBuLMRzjDzspAtahUnFILgt3px4rZ5KoIfpnLutj3jOUug==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T06:04:17.356Z\",\"modified\":\"2017-03-31T06:04:36.414Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/0027d706-73a2-4ccc-917f-90e21cf1816b\"},{\"name\":\"client-192-0-129-120\",\"id\":\"00186e98-6bc3-4358-badc-81a3cfeae9e7\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"S1dLuEOtcr9IHqdVzdupazzcVvS7izuYTsWHjr6qfZl/zFUSgwq7xtw6MANrl18qylQbkWfFG3Lpz/r752wl5eKpD8FbaFy0REg1QjhkJZhmjPGo2oSPJzOW4F8d4zR/tsHKJNy7ugaJqvWXo6JjQ5oMHmWJ4PdPUPt9iiXSCGPXgYcJWYxRJ2SOz4bCNXvlui8gTbofw51p/7RVT546ZU83DlmjrxLMw9tzeTy8N2GgutOlsbIU5MDaMRiMyn2/0SQZdgCuPOO0QmXADYOcymAdij4EtlAQE8JURz2KjvMd2FRQBJwRYILwISlD2SbrpU1TZSMPi8Y8nzcNbyLZZA==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T06:04:48.473Z\",\"modified\":\"2017-03-31T06:05:06.742Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/00186e98-6bc3-4358-badc-81a3cfeae9e7\"},{\"name\":\"client-192-0-14-220\",\"id\":\"0021f2bb-5933-4264-9313-ea45651f7030\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"TEZ3Fzz/mCTeWIVvipewpdA/QLRe5yGaxmwIWNTlgHKrsBXbH82YfqnwptKCmC0oe+o94iGHQD7z1iN2wsOtHYSDycqYgHt9zsOvmNFW6BwKDG+UlZDmDGG9ewyJI5CJ90hQVQq6Z2lqlYaDcBO25i0DVuyMyIgSixwiS3kJfIhCM7v1YpzFP/T2rO8pdr9p8C8nExUBjz9I5zoTvsPOslTWc69yWfT6hY9ma1/bEVTg/owfLK4T3OGWSEE5s9va3bWiW1l/C2Q+0L80V0eelvDu0lwcr3mqP7dY4Ox/bfjQuwmvC7OyS0sJbwwqYLrRG43nGOFTd6+6PM9zwFcLVQ==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:02:07.189Z\",\"modified\":\"2017-03-31T07:02:25.600Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/0021f2bb-5933-4264-9313-ea45651f7030\"},{\"name\":\"client-192-0-126-131\",\"id\":\"0029dac8-624b-4689-9838-fedc49f57e1e\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"bdIMpFYOXG9GvNR36n/TeM7WwzcVD0INLI7omuLcmC2e6PpgjPGVzys8qNRIT4hCAcd71AtuByfBVDwCjF1OpnqDN3rcVN4imzBqZn01mgx8Mez5+UbOQnI1r3YiZS7qIuxsv6ePLHBGJiPOf/QhDL4fUXiyzXcUC1ooNAnI70JHXsZRcE4DVcZi2m1driUwV2mRcfcf80R0bMIS620leo+0VJjvEdZEawoZvWt8Pv2z6vYYTUPIX+POz5VIsNUbgw+jVgQcMDTB0XC21jY5M3b3DqIf+ZOYC38veqZ/ElDCaDUOBhmWzLGkgk2bTcfQKxkay9Kw3BPuIiQuRQiVbg==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T06:03:49.653Z\",\"modified\":\"2017-03-31T06:04:08.378Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/0029dac8-624b-4689-9838-fedc49f57e1e\"},{\"name\":\"client-192-0-18-81\",\"id\":\"002b7e8c-39c5-42aa-bc0e-71c33e2a4931\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"no08+6NFln5ruK3yggfPtENGeSEjsCY/vlX1rHsr2cUC+Ks5rfUpPTxASDUV2WvmwrgNkqe2LfdI+HzHuYC1eXx2+OfbZ/hD+RgITR2NugjvMVV6CM4EhFkGicEZpZSay1bp6aSCNLiiLaDAZokJQ9lQP39duRLi5sDe7wJk+rcdFv7gw1nnXSpHgNax0+HuAmsoPuGS1xXBpZhN5rD4bgpe5U3A2UD9KeCkvEz9JUIheP3JGMb9Tgnl2PZPoo+W16NLkDQHEj7CARcuvAHmW6ObjkgUS/fk/kWw6/SinhWru5sqP1Jo5baKZaJ1OY7sSDaPxRBQMBzfEiX3ktN0Rg==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T07:03:02.434Z\",\"modified\":\"2017-03-31T07:03:20.887Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/002b7e8c-39c5-42aa-bc0e-71c33e2a4931\"},{\"name\":\"client-192-0-104-112\",\"id\":\"002a9c7d-e594-4229-88fb-83c6fa26813f\",\"props\":{\"atp_enrollment_info\":{\"encrypted_password\":\"tua2SLO8VBNmp9ycRdgngru4QGhNJyvUMsnyrLTVB5wJ1MDTikvMs3fvssIi0cL7583rx9kwKFXe4OZ98mUf9Knt0iDyboEeWV7Cy2emlHdAoltRmc263nXoVqHWuScD09gqwhRv5xUeqFQCIYid3S5Tsd8QvA1Ue+RbHKaNFVA07W5JXBcjR/3504wGWbjBo/pDuDMy+cHSz9fWmaZ0AKXnpfUko8/i77mwGUeQXg2UU/SIL42lqzRq29KL88aVA9rEKLZulGAr8ZZMySoPuooES5SMYUvinMeKRWQWuXl5soVXRvSCPSKcpmxfuidyrRonXKMynFdrZ4w3OT/Ieg==\",\"publicKey\":\"BgIAAACkAABSU0ExAAgAAAEAAQC9O6wWDqFrMKxr/5C30cFn5bJpoCUBrfJY8yGQPqerecSK4en7RtbQuDue+1f3KZYpmkFkRSFadmTKHueTOh9iD/HCVOgvR7uSYleUH546ohUWPUSPtLM61E+VdFYTBGUeTmS7TfvG6OV3WrAksAMnZ4RjEQHIt1zz8VpAowJIuF9n3yojbm5JDFv5VBI+sUdS4S22ckue+QEJqRNH2jtjaXJ41SgZcrcD+hnyo+Ud8wggHgBbuxxHpim3tT0MKCCJbPntltarROQAJqDcoKjitgnPztGL0MLMPqmzV8kvKR72SUcuM+SXMuZ7CZWrGpJbf8BJg/T5ZBnlhBQ3FFS6\",\"enrollmentStatus\":\"MdrInstalledFeatureCreated\"}},\"created\":\"2017-03-31T05:47:52.942Z\",\"modified\":\"2017-03-31T05:48:11.143Z\",\"obj_classes\":[\"device\"],\"uri\":\"/v1/mdr/devices/002a9c7d-e594-4229-88fb-83c6fa26813f\"}]";
		String pattern = "(\"name\"):(\"\\w+-[\\d\\-]*\").*?(\"encrypted_password\"):(\".*?\").*?(\"publicKey\"):(\".*?\")";
		Pattern mixPattern = Pattern.compile(pattern);

		Matcher m = mixPattern.matcher(str);
		StringBuilder strBuilder = new StringBuilder();
		while (m.find()) {
			m.start();
			strBuilder.append(m.group(2) + ":" + m.group(4) + "\n");
			System.out.println("m2.group(1) " + m.group(1));
			System.out.println("m2.group(2) " + m.group(2));
			System.out.println("m2.group(3) " + m.group(3));
			System.out.println("m2.group(4) " + m.group(4));
			System.out.println("m2.group(5) " + m.group(5));
			System.out.println("m2.group(6) " + m.group(6));
			m.end();
		}
		System.out.println("---------------------------------------------------------------");
		// String pattern2 = "(\"encrypted_password\"):(\"(.*?)\")";
		// Pattern mixPattern2 = Pattern.compile(pattern2);
		// Matcher m2 = mixPattern2.matcher(str);
		//// // System.out.println("m21.find: " + m2.find());
		// while (m2.find()) {
		// m2.start();
		// System.out.println("m21.group(1) " + m2.group(1));
		// System.out.println("m21.group(2) " + m2.group(2));
		// m2.end();
		// }
		////
		// String pattern3 = "(\"publicKey\"):(\"(.*?)\")";
		// Pattern mixPattern3 = Pattern.compile(pattern3);
		// Matcher m3 = mixPattern3.matcher(str);
		//// // System.out.println("m21.find: " + m2.find());
		// while (m3.find()) {
		// m3.start();
		// System.out.println("m31.group(1) " + m3.group(1));
		// System.out.println("m31.group(2) " + m3.group(2));
		// m3.end();
		// }
		appendToFile(strBuilder.toString());
		return strBuilder.toString();
	}

	public static void appendToFile(String str) {
		String fileName = "/Users/junas01/Documents/workspace/practise-java8/src/main/resources/consts.properties";
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
			out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		readFromFile();
	}

	public static void readFromFile() {
		String fileName = "/Users/junas01/Documents/workspace/practise-java8/src/main/resources/consts.properties";
		Properties properties = new Properties();
		try (FileInputStream in = new FileInputStream(fileName)) {
			properties.load(in);
		} catch (IOException ioe) {
		}
		Set<Object> keys = properties.keySet();
		for (Object key : keys) {
			System.out.println((String) key + ":" + (String) properties.getProperty((String) key));
		}
	}

	public static void du9() {
		String jsonStr = "[{\"id\":\"-0jLBqV_R_KsgMoSs8qxVQ\",\"name\":null,\"created\":null,\"modified\":null,\"obj_classes\":null,\"uri\":null,\"feature\":{\"id\":\"{1DF0351C-146D-4F07-B155-BF5C7077FF40}\",\"command_id\":\"{FBABA643-3FAC-436C-8455-10A3A67F1254}\",\"parameters\":{\"0\":\"  {\\\"eoc_scan_command\\\":{\\\"command_id\\\":\\\"671b1fce-5703-4446-94d2-a268fb6bec40\\\",\\\"commands\\\":[{\\\"artifact\\\":{\\\"object_type\\\":\\\"process\\\",\\\"path\\\":{\\\"operator\\\":{\\\"value\\\":\\\"equals\\\",\\\"case_insensitive\\\":true},\\\"value\\\":[\\\"*\\\"]}}}]}}\"}}},{\"id\":\"ddLNQIRjQru1je0MXqY44A\",\"name\":null,\"created\":null,\"modified\":null,\"obj_classes\":null,\"uri\":null,\"feature\":{\"id\":\"{1DF0351C-146D-4F07-B155-BF5C7077FF40}\",\"command_id\":\"{FBABA643-3FAC-436C-8455-10A3A67F1254}\",\"parameters\":{\"0\":\"  {\\\"eoc_scan_command\\\":{\\\"command_id\\\":\\\"671b1fce-5703-4446-94d2-a268fb6bec40\\\",\\\"commands\\\":[{\\\"artifact\\\":{\\\"object_type\\\":\\\"process\\\",\\\"path\\\":{\\\"operator\\\":{\\\"value\\\":\\\"equals\\\",\\\"case_insensitive\\\":true},\\\"value\\\":[\\\"*\\\"]}}}]}}\"}}}]";
		String response = "{\"took\":65,\"timed_out\":false,\"_shards\":{\"total\":1,\"successful\":1,\"failed\":0},\"hits\":{\"total\":100,\"max_score\":1,\"hits\":[{\"_index\":\"epmp_trackdb\",\"_type\":\"endpoint_latest\",\"_id\":\"caAmVkEZl-u3amjvvNX2cPgckOeOJTk0BTHEGMeIua4\",\"_score\":1,\"fields\":{\"latest.device_name\":[\"client-192-0-0-66\"],\"latest.domain_or_workgroup\":[\"seppsr.symc.com\"],\"unique.device_uid\":[\"cb1fe898-9475-4bad-9804-c6ae565235a9\"],\"latest.device_ip\":[\"192.0.0.66\"]}}]}}";
		// String
		// response2="[{\"_index\":\"epmp_trackdb\",\"_type\":\"endpoint_latest\",\"_id\":\"caAmVkEZl-u3amjvvNX2cPgckOeOJTk0BTHEGMeIua4\",\"_score\":1,\"fields\":{\"device_name\":[\"client-192-0-0-66\"],\"latest.domain_or_workgroup\":[\"seppsr.symc.com\"],\"unique.device_uid\":[\"cb1fe898-9475-4bad-9804-c6ae565235a9\"],\"latest.device_ip\":[\"192.0.0.66\"]}}]}}";

		response = "{\"took\":65,\"timed_out\":false,\"_shards\":{\"total\":1,\"successful\":1,\"failed\":0},\"hits\":{\"total\":100,\"max_score\":1,\"hits\":[{\"_index\":\"epmp_trackdb\",\"_type\":\"endpoint_latest\",\"_id\":\"caAmVkEZl-u3amjvvNX2cPgckOeOJTk0BTHEGMeIua4\",\"_score\":1,\"fields\":{\"latest.device_name\":[\"client-192-0-0-66\"],\"latest.domain_or_workgroup\":[\"seppsr.symc.com\"],\"unique.device_uid\":[\"cb1fe898-9475-4bad-9804-c6ae565235a9\"],\"latest.device_ip\":[\"192.0.0.66\"]}},{\"_index\":\"epmp_trackdb\",\"_type\":\"endpoint_latest\",\"_id\":\"caAmVkEZl-u3amjvvNX2cPgckOeOJTk0BTHEGMeIua4\",\"_score\":1,\"fields\":{\"latest.device_name\":[\"client-192-0-0-67\"],\"latest.domain_or_workgroup\":[\"seppsr.symc.com\"],\"unique.device_uid\":[\"cb1fe898-9475-4bad-9804-c6ae565235a9\"],\"latest.device_ip\":[\"192.0.0.67\"]}},{\"_index\":\"epmp_trackdb\",\"_type\":\"endpoint_latest\",\"_id\":\"caAmVkEZl-u3amjvvNX2cPgckOeOJTk0BTHEGMeIua4\",\"_score\":1,\"fields\":{\"latest.device_name\":[\"client-192-0-0-68\"],\"latest.domain_or_workgroup\":[\"seppsr.symc.com\"],\"unique.device_uid\":[\"cb1fe898-9475-4bad-9804-c6ae565235a9\"],\"latest.device_ip\":[\"192.0.0.68\"]}}]}}";
		Configuration conf = Configuration.defaultConfiguration();
		conf = conf.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
		ReadContext ctx = JsonPath.using(conf).parse(response);
		// String fields = ctx.read("$.hits.hits");
		String jsonValue = "$.hits.hits[*].fields.latest.device_name";
		ctx.read("$[?(@.hits)].hits");
		System.out.println("latest " + ctx.read("$[*].hits"));
		System.out.println("fields " + ctx.read("$.hits.hits"));
		System.out.println("example " + ctx.read("$.hits.hits[*].fields.*"));

		System.out.println("read data " + ctx.read(jsonValue));
		// List<String> commandTypeList = ctx.read(jsonValue);
		// System.out.println("commandTypeList " + commandTypeList);

	}

	public static void du10() {
		String str = "kafka_012id=This is kafka id";
		String pattern = "((\\w+)=(\\w+)*)";
		//System.out.println(str.split("=")[0]);

		str = "{\"header¼product_id¼string\":\"PIM_4\",\"header¼tenant_id¼string\":\"Verizon2\",\"header¼doc_type_id¼string\":\"wb\",\"header¼doc_type_version¼string\":\"1.8\",\"body¼names¼array[0]\":\"null\",\"body¼abc¼com.ca.jarvis.abc_name_0¼abc2¼array[0]\":2,\"body¼abc¼com.ca.jarvis.abc_name_0¼abc3¼string\":\"null\",\"body¼abc¼com.ca.jarvis.abc_name_0¼abc2¼array[1]\":3,\"body¼abc¼com.ca.jarvis.abc_name_0¼abc2¼array[2]\":4,\"body¼abc¼com.ca.jarvis.abc_name_0¼abc2¼array[3]\":5,\"body¼abc¼com.ca.jarvis.abc_name_0¼abc2¼array[4]\":6,\"body¼abc¼com.ca.jarvis.abc_name_0¼abc2¼array[5]\":7,\"body¼abc¼com.ca.jarvis.abc_name_0¼abc2¼array[6]\":8";
		 pattern = "([a-zA-Z0-9\\.¼]array\\[0\\]\":\"null\")";

	//	 System.out.println(str.replaceAll("([a-zA-Z0-9\\.¼]array\\[0\\]\":\"null\")", "\":\"null\""));

		 str = "{\"userid\":{\"type\" : \"string\",\"index\" : \"not_analyzed\"},\"GeoLocation233\":{\"type\":\"object\",\"properties\":{\"props\":{\"type\":\"nested\",\"properties\":{\"Key\":{\"type\":\"keyword\"},\"value\":{\"type\":\"text\"}}}}},\"names\":{\"type\":\"string\",\"isArray\":true},\"names2\":{\"type\":\"string\",\"isArray\" : true},\"abc\":{\"type\":\"object\",\"properties\":{\"abc2\":{\"isArray\":true,\"type\":\"long\"},\"abc3\":{\"type\":\"string\"}}}}";
		System.out.println(str);
//		str = str.replaceAll("[\\s+]:[\\s+]", ":");
//		str = str.replaceAll("[\\s+],[\\s+]", ",");
//		str = str.replaceAll(",\"isArray\":(true|false)", "");
//		str = str.replaceAll("\"isArray\":(true|false),", "");
//
//		 System.out.println(str);

		System.out.println(removeIsArrayAttribute(str));
		Pattern mixPattern = Pattern.compile(pattern);
		Matcher m = mixPattern.matcher(str);
		StringBuilder strBuilder = new StringBuilder();
		while (m.find()) {
			m.start();
			System.out.println("m2.group(1) " + m.group(0));


			m.end();
		}

	}
	 private static String removeIsArrayAttribute(String mapping) {
			return mapping.replaceAll("[\\s+]:[\\s+]", ":").replaceAll("[\\s+],[\\s+]", ",").replaceAll(",\"isArray\":(true|false)", "").replaceAll("\"isArray\":(true|false),", "");
	  }
	public static void du8() throws MalformedURLException {
		String url = "http://isl-dsdc.ca.com/artifactory/api/search/latestVersion?g=com.ca.jarvis&a=das&v=1.0.5-publish_release_artifacts-SNAPSHOT&repos=maven-integration-local";
		URL u = new URL(url);
	}

	public static void main(String[] args) {
		String str = "{\"id\":\"3K8wojalQcuei4dnny9gmw\",\"name\":null,\"created\":null,\"modified\":null,\"obj_classes\":null,\"uri\":null,\"feature\":{\"id\":\"{1DF0351C-146D-4F07-B155-BF5C7077FF40}\",\"command_id\":\"{FBABA643-3FAC-436C-8455-10A3A67F1254}\",\"parameters\":{\"0\":\"  {\\\"eoc_scan_command\\\":{\\\"command_id\\\":\\\"671b1fce-5703-4446-94d2-a268fb6bec40\\\",\\\"commands\\\":[{\\\"artifact\\\":{\\\"object_type\\\":\\\"process\\\",\\\"path\\\":{\\\"operator\\\":{\\\"value\\\":\\\"equals\\\",\\\"case_insensitive\\\":true},\\\"value\\\":[\\\"*\\\"]}}}]}}\"}}}";
		JSONObject jsonObj = new JSONObject(str);
		// System.out.println(jsonObj.getString("command_id"));

		int indexOf = str.lastIndexOf("command_id");
		// System.out.println(indexOf);
		int index = str.indexOf(',', indexOf);
		String substring = str.substring(indexOf, index);
		// System.out.println(substring);
		String[] split = substring.split("\\\"");
		// System.out.println(split[0]);
		// System.out.println(split[1]);
		// System.out.println(split[2]);

		// du();
		// du2();
		// du3();
		// du4();
		// du5();
		// du6();
		//du9();
		du10();
	}
}
