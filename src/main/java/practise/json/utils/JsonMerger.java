package practise.json.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonMerger {
  /**
   *
   * @param toBeMergedInto
   * @param toBeMerged
   * @throws UpdateDocTypeException
   */
  public JSONObject mergeJson(JSONObject toBeMergedInto, JSONObject toBeMerged) {
    System.out.println("Initial: " + toBeMergedInto);

    Set<String> oldKeys = toBeMergedInto.keySet();
    Set<String> newKeys = toBeMerged.keySet();

    for (String newKey : newKeys) {
      if (oldKeys.contains(newKey)) {
        // if (TYPE.equals(newKey)) {
        // String oldType = toBeMergedInto.getString(newKey);
        // String newType = toBeMerged.getString(newKey);
        // if (oldType == null || oldType.isEmpty()) {
        // throw new IllegalArgumentException(
        // "There is no field type specified in old mapping. Cannot update it.");
        // }
        //
        // if (!oldType.equals(newType)) {
        // throw new IllegalArgumentException(
        // "Cannot change the field type value for field. Make sure field types are same.");
        // }
        // continue;
        // }
        Object oldVal = toBeMergedInto.get(newKey);
        Object newVal = toBeMerged.get(newKey);
        if (isJSONObject(oldVal) && isJSONObject(newVal)) {
          JSONObject oldValAsJsonObject = (JSONObject) oldVal;
          JSONObject newValAsJsonObject = (JSONObject) newVal;
          if (!oldValAsJsonObject.similar(newValAsJsonObject)) {
            mergeJson(oldValAsJsonObject, newValAsJsonObject);
          }
        } else if (oldVal instanceof JSONArray && newVal instanceof JSONArray) {
          JSONArray oldVal1 = toBeMergedInto.getJSONArray(newKey);
          JSONArray newVal1 = toBeMerged.getJSONArray(newKey);
          oldVal1 = mergeJson(oldVal1, newVal1);
        } else {
          toBeMergedInto.put(newKey, newVal);
        }

      } else {
        toBeMergedInto.put(newKey, toBeMerged.get(newKey));
      }
    }
    return toBeMergedInto;

  }

  private JSONArray mergeJson(JSONArray oldVal1, JSONArray newVal1) {
    if (oldVal1.get(0) == null && newVal1.get(0) == null) {
      return oldVal1;
    }
    if (oldVal1.get(0) != null && newVal1.get(0) == null) {
      return oldVal1;
    }
    if (oldVal1.get(0) == null && newVal1.get(0) != null) {
      oldVal1.put(newVal1);
      return oldVal1;
    }
    Object firstOldVal = oldVal1.get(0);
    Object firstNewVal1 = newVal1.get(0);

    int newVal1Size = newVal1.length();
    Set<Integer> newValIndices = new HashSet<>(newVal1Size);
    for (int i = 0; i < newVal1Size; i++) {
      newValIndices.add(i);
    }
    if (firstOldVal instanceof JSONObject && firstNewVal1 instanceof JSONObject) {
      List<Integer> matchingIndices = getMatchingIndicesOfJSONObjects(oldVal1, newVal1);
      for (int i = 0; i < oldVal1.length(); i++) {
        JSONObject toBeMergedInto = oldVal1.getJSONObject(i);
        JSONObject toBeMerged = newVal1.getJSONObject(matchingIndices.get(i));
        mergeJson(toBeMergedInto, toBeMerged);
        newValIndices.remove(matchingIndices.get(i));
      }
      if (!newValIndices.isEmpty()) {
        for (Integer index : newValIndices) {
          oldVal1.put(newVal1.getJSONObject(index));
        }
      }
    } else {
      oldVal1.put(newVal1);
    }
    return oldVal1;
  }

  private List<Integer> getMatchingIndicesOfJSONObjects(JSONArray oldVal1, JSONArray newVal1) {
    Object firstOldVal = oldVal1.get(0);
    Object firstNewVal1 = newVal1.get(0);
    if (!(firstOldVal instanceof JSONObject) || !(firstNewVal1 instanceof JSONObject)) {
      throw new IllegalArgumentException("Passed fields are not an array of JSONObject.");
    }
    List<Integer> matchingIndices = new ArrayList<Integer>(oldVal1.length());
    int oldSize = oldVal1.length();
    int newSize = newVal1.length();
    for (int i = 0; i < oldSize; i++) {
      int maxMatchingIndex = 0;
      JSONObject obj = oldVal1.getJSONObject(i);
      Set<String> keys1 = obj.keySet();
      int maxMatchCount = 0;
      for (int j = 0; j < newSize; j++) {
        JSONObject obj2 = newVal1.getJSONObject(j);
        int matchCount = 0;
        Set<String> keys2 = obj2.keySet();
        for (String key : keys1) {
          if (keys2.contains(key)) {
            matchCount++;
          }
        }
        if (matchCount > maxMatchCount) {
          maxMatchingIndex = j;
          maxMatchCount = matchCount;
        }
      }
      matchingIndices.add(maxMatchingIndex);
    }
    return matchingIndices;
  }

  boolean isJSONObject(Object value) {
    return value instanceof JSONObject;
  }

  public static void main(String[] args) {
    // String json1 =
    // "{\"template_mtext_\":{\"mapping\":{\"type\":\"string\", \"abc\":
    // \"val\"},\"match_mapping_type\":\"date\",\"match\":\"mtext_*\"}}";
    //
    // String json2 =
    // "{\"template_mtext_\":{\"mapping\":{\"type\":\"string\", \"abc2\":
    // \"val2\"},\"match_mapping_type\":\"string\",\"match\":\"mtext_*\"}}";
    //
    // JSONObject toBeMergedInto = new JSONObject(json1);
    // JSONObject toBeMerged = new JSONObject(json2);
    // JSONObject merged = new UpdateMappingService().mergeJSON(toBeMergedInto, toBeMerged);
    // System.out.println(merged);

    String json1 =
        "[{\"integers\":{\"match_mapping_type\":\"long\",\"mapping\":{\"type\":\"integer\"}},\"abc\":\"val1\",\"abc2\":\"val2\"},{\"strings\":{\"match_mapping_type\":\"string\",\"mapping\":{\"type\":\"text\",\"fields\":{\"raw\":{\"type\":\"keyword\",\"ignore_above\":256}}}}},{\"integers\":{\"match_mapping_type\":\"long\",\"mapping\":{\"type\":\"integer\"}},\"abc3\":\"val1\",\"abc2\":\"val2\"}]";
    String json2 =
        "[{\"integers\":{\"match_mapping_type\":\"long\",\"mapping\":{\"type\":\"integer\"}},\"abc3\":\"abc3\",\"abc2\":\"abc2\"},{\"integers\":{\"match_mapping_type\":\"long\",\"mapping\":{\"type\":\"integer\"}},\"abc\":\"abc\",\"abc2\":\"abc2\"},{\"strings\":{\"match_mapping_type\":\"string\",\"mapping\":{\"type\":\"text\",\"fields\":{\"raw\":{\"type\":\"keyword\",\"ignore_above\":256}}}}}]";
    json1 =
        "{\"mappings\":{\"data\":{\"dynamic\":\"strict\",\"_all\":{\"enabled\":false},\"properties\":{\"country\":{\"type\":\"keyword\"},\"app_version\":{\"type\":\"keyword\"},\"pipeline_custatt_string\":{\"type\":\"nested\",\"properties\":{\"value\":{\"index\":\"false\",\"isArray\":true,\"type\":\"keyword\",\"doc_values\":true},\"key\":{\"index\":\"false\",\"type\":\"keyword\",\"doc_values\":true}}},\"pipeline_session_length\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"session_start\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"pipeline_cpu\":{\"index\":\"false\",\"isArray\":true,\"type\":\"float\",\"doc_values\":true},\"screen_height\":{\"index\":\"false\",\"type\":\"integer\"},\"platform_version\":{\"type\":\"keyword\"},\"is_multi_pf\":{\"type\":\"boolean\"},\"app_flow\":{\"index\":\"false\",\"type\":\"keyword\"},\"model\":{\"type\":\"keyword\"},\"state\":{\"type\":\"keyword\"},\"app_id\":{\"type\":\"keyword\"},\"events\":{\"type\":\"nested\",\"properties\":{\"bigvalue\":{\"index\":\"false\",\"type\":\"text\"},\"event_id\":{\"index\":\"false\",\"type\":\"integer\"},\"name\":{\"analyzer\":\"keyword_lowercase\",\"type\":\"text\"},\"attributes\":{\"type\":\"nested\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"value\":{\"type\":\"keyword\"}}},\"type\":{\"analyzer\":\"keyword_lowercase\",\"type\":\"text\"},\"value\":{\"analyzer\":\"keyword_lowercase\",\"type\":\"text\"},\"timestamp\":{\"format\":\"epoch_millis\",\"index\":\"false\",\"type\":\"date\"}}},\"session_end\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"payload_type\":{\"type\":\"keyword\"},\"screen_width\":{\"index\":\"false\",\"type\":\"integer\"},\"last_updated\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"device_id\":{\"type\":\"keyword\"},\"pipeline_data_out\":{\"index\":\"false\",\"isArray\":true,\"type\":\"integer\",\"doc_values\":true},\"pipeline_custatt_double\":{\"type\":\"nested\",\"properties\":{\"value\":{\"index\":\"false\",\"isArray\":true,\"type\":\"double\",\"doc_values\":true},\"key\":{\"index\":\"false\",\"type\":\"keyword\",\"doc_values\":true}}},\"pipeline_network_errors\":{\"index\":false,\"type\":\"integer\",\"doc_values\":true},\"chunk_id\":{\"index\":\"false\",\"type\":\"long\"},\"is_last_chunk\":{\"type\":\"boolean\"},\"user_id\":{\"type\":\"keyword\"},\"pipeline_disk\":{\"index\":\"false\",\"isArray\":true,\"type\":\"float\",\"doc_values\":true},\"is_feedback\":{\"type\":\"boolean\"},\"pipeline_network_calls\":{\"index\":\"false\",\"type\":\"long\",\"doc_values\":true},\"pipeline_slow_network_calls\":{\"index\":\"false\",\"type\":\"long\",\"doc_values\":true},\"tenant_id\":{\"type\":\"keyword\"},\"startup_time\":{\"type\":\"integer\",\"doc_values\":true},\"city\":{\"type\":\"keyword\"},\"platform\":{\"type\":\"keyword\"},\"performance_events\":{\"type\":\"nested\",\"properties\":{\"disk\":{\"type\":\"float\"},\"event_id\":{\"index\":\"false\",\"type\":\"integer\"},\"memory\":{\"type\":\"float\"},\"cpu\":{\"type\":\"float\"},\"battery\":{\"type\":\"float\"},\"timestamp\":{\"format\":\"epoch_millis\",\"type\":\"date\"}}},\"pipeline_memory\":{\"index\":\"false\",\"isArray\":true,\"type\":\"float\",\"doc_values\":true},\"pipeline_data_in\":{\"index\":\"false\",\"isArray\":true,\"type\":\"integer\",\"doc_values\":true},\"pipeline_response_time\":{\"index\":\"false\",\"isArray\":true,\"type\":\"integer\",\"doc_values\":true},\"sdk_version\":{\"type\":\"keyword\"},\"pipeline_battery\":{\"index\":\"false\",\"isArray\":true,\"type\":\"float\",\"doc_values\":true},\"make\":{\"type\":\"keyword\"},\"user_agent\":{\"type\":\"keyword\"},\"is_alerted\":{\"type\":\"boolean\"},\"js_errors\":{\"type\":\"nested\",\"properties\":{\"src\":{\"type\":\"keyword\"},\"line\":{\"index\":\"false\",\"type\":\"integer\"},\"strace\":{\"index\":\"false\",\"type\":\"text\"},\"txn_name\":{\"type\":\"keyword\"},\"column\":{\"index\":\"false\",\"type\":\"integer\"},\"page\":{\"type\":\"keyword\"},\"error\":{\"type\":\"keyword\"},\"txn_start\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"timestamp\":{\"format\":\"epoch_millis\",\"type\":\"date\"}}},\"src\":{\"type\":\"keyword\"},\"session_id\":{\"type\":\"keyword\"},\"carrier\":{\"type\":\"keyword\"},\"app_screens\":{\"type\":\"nested\",\"properties\":{\"portrait_taps\":{\"index\":\"false\",\"isArray\":true,\"type\":\"keyword\",\"doc_values\":true},\"network_count\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"interactions\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"high_spin_time_count\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"duration\":{\"index\":\"false\",\"isArray\":true,\"type\":\"integer\",\"doc_values\":true},\"network_error_count\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"network_response_time\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"load_time\":{\"index\":\"false\",\"isArray\":true,\"type\":\"integer\",\"doc_values\":true},\"high_load_time_count\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"name\":{\"type\":\"keyword\"},\"landscape_taps\":{\"index\":\"false\",\"type\":\"keyword\",\"doc_values\":true},\"is_crashed\":{\"type\":\"boolean\",\"doc_values\":true},\"tap_contexts\":{\"isArray\":true,\"type\":\"keyword\"},\"view_count\":{\"index\":\"false\",\"type\":\"integer\",\"doc_values\":true},\"spin_time\":{\"index\":\"false\",\"isArray\":true,\"type\":\"integer\",\"doc_values\":true},\"txns\":{\"isArray\":true,\"type\":\"keyword\",\"doc_values\":true}}},\"is_screen_shot\":{\"type\":\"boolean\"},\"network_events\":{\"type\":\"nested\",\"properties\":{\"connect_end\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"request_start\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"status_code\":{\"type\":\"integer\"},\"resource_type\":{\"type\":\"keyword\"},\"ajax_error_trace\":{\"index\":\"false\",\"type\":\"text\"},\"dom_complete\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"url\":{\"type\":\"keyword\"},\"dom_loading\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"response_start\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"response_end\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"domain_lookup_start\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"domain_lookup_end\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"event_id\":{\"index\":\"false\",\"type\":\"integer\"},\"parent_url\":{\"type\":\"keyword\"},\"ajax_error_type\":{\"type\":\"keyword\"},\"response_time\":{\"type\":\"integer\"},\"attributes\":{\"type\":\"nested\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"value\":{\"type\":\"keyword\"}}},\"ajax_error_msg\":{\"index\":\"false\",\"type\":\"text\"},\"data_out\":{\"type\":\"integer\"},\"connect_start\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"timestamp\":{\"format\":\"epoch_millis\",\"type\":\"date\"},\"data_in\":{\"type\":\"integer\"}}},\"location\":{\"type\":\"geo_point\"},\"is_crashed\":{\"type\":\"boolean\"},\"attributes\":{\"type\":\"nested\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"data_type\":{\"type\":\"keyword\"},\"value\":{\"type\":\"keyword\"}}},\"last_event_timestamp\":{\"format\":\"epoch_millis\",\"type\":\"date\"}}}}}";
    json2 =
        "{\"mappings\":{\"data\":{\"dynamic\":\"strict\",\"_all\":{\"enabled\":false},\"properties\":{\"sdk_version\":{\"type\":\"keyword\"},\"session_id\":{\"type\":\"keyword\"},\"session_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"session_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"user_id\":{\"type\":\"keyword\"},\"tenant_id\":{\"type\":\"keyword\"},\"app_id\":{\"type\":\"keyword\"},\"app_version\":{\"type\":\"keyword\"},\"platform\":{\"type\":\"keyword\"},\"platform_version\":{\"type\":\"keyword\"},\"device_id\":{\"type\":\"keyword\"},\"make\":{\"type\":\"keyword\"},\"model\":{\"type\":\"keyword\"},\"screen_width\":{\"type\":\"integer\",\"index\":\"false\"},\"screen_height\":{\"type\":\"integer\",\"index\":\"false\"},\"carrier\":{\"type\":\"keyword\"},\"country\":{\"type\":\"keyword\"},\"state\":{\"type\":\"keyword\"},\"city\":{\"type\":\"keyword\"},\"src\":{\"type\":\"keyword\"},\"location\":{\"type\":\"geo_point\"},\"payload_type\":{\"type\":\"keyword\"},\"startup_time\":{\"type\":\"integer\",\"doc_values\":true},\"last_updated\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"last_event_timestamp\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"is_alerted\":{\"type\":\"boolean\"},\"is_crashed\":{\"type\":\"boolean\"},\"flag\":{\"type\":\"keyword\"},\"is_screen_shot\":{\"type\":\"boolean\"},\"is_feedback\":{\"type\":\"boolean\"},\"is_multi_pf\":{\"type\":\"boolean\"},\"chunk_id\":{\"type\":\"long\",\"index\":\"false\"},\"is_last_chunk\":{\"type\":\"boolean\"},\"user_agent\":{\"type\":\"keyword\"},\"app_flow\":{\"type\":\"keyword\",\"index\":\"false\"},\"attributes\":{\"type\":\"nested\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"value\":{\"type\":\"keyword\"},\"data_type\":{\"type\":\"keyword\"}}},\"events\":{\"type\":\"nested\",\"properties\":{\"timestamp\":{\"type\":\"date\",\"format\":\"epoch_millis\",\"index\":\"false\"},\"event_id\":{\"type\":\"integer\",\"index\":\"false\"},\"type\":{\"type\":\"text\",\"analyzer\":\"keyword_lowercase\"},\"name\":{\"type\":\"text\",\"analyzer\":\"keyword_lowercase\"},\"value\":{\"type\":\"text\",\"analyzer\":\"keyword_lowercase\"},\"bigvalue\":{\"type\":\"text\",\"index\":\"false\"},\"attributes\":{\"type\":\"nested\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"value\":{\"type\":\"keyword\"}}}}},\"network_events\":{\"type\":\"nested\",\"properties\":{\"timestamp\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"event_id\":{\"type\":\"integer\",\"index\":\"false\"},\"url\":{\"type\":\"keyword\"},\"response_time\":{\"type\":\"integer\"},\"status_code\":{\"type\":\"integer\"},\"data_in\":{\"type\":\"integer\"},\"data_out\":{\"type\":\"integer\"},\"parent_url\":{\"type\":\"keyword\"},\"parent_timestamp\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"resource_type\":{\"type\":\"keyword\"},\"navigation_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"unload_event_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"unload_event_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"redirect_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"redirect_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"domain_lookup_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"domain_lookup_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"connect_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"connect_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"secure_connection_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"request_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"response_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"response_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"dom_loading\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"dom_interactive\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"dom_content_loaded_event_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"dom_content_loaded_event_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"dom_complete\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"load_event_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"load_event_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"fetch_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"worker_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"resource_start_time\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"resource_duration\":{\"type\":\"long\"},\"callback_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"callback_end\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"ajax_error_type\":{\"type\":\"keyword\"},\"ajax_error_msg\":{\"type\":\"text\",\"index\":\"false\"},\"ajax_error_trace\":{\"type\":\"text\",\"index\":\"false\"},\"resource_name\":{\"type\":\"keyword\"},\"resource_entry_type\":{\"type\":\"keyword\"},\"resource_initiator_type\":{\"type\":\"keyword\"},\"resource_next_hop_protocol\":{\"type\":\"keyword\"},\"transfer_size\":{\"type\":\"long\"},\"encoded_body_size\":{\"type\":\"long\"},\"decoded_body_size\":{\"type\":\"long\"},\"attributes\":{\"type\":\"nested\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"value\":{\"type\":\"keyword\"}}}}},\"js_errors\":{\"type\":\"nested\",\"properties\":{\"timestamp\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"page\":{\"type\":\"keyword\"},\"src\":{\"type\":\"keyword\"},\"error\":{\"type\":\"keyword\"},\"line\":{\"type\":\"integer\",\"index\":\"false\"},\"column\":{\"type\":\"integer\",\"index\":\"false\"},\"strace\":{\"type\":\"text\",\"index\":\"false\"},\"txn_name\":{\"type\":\"keyword\"},\"txn_start\":{\"type\":\"date\",\"format\":\"epoch_millis\"}}},\"performance_events\":{\"type\":\"nested\",\"properties\":{\"timestamp\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"event_id\":{\"type\":\"integer\",\"index\":\"false\"},\"cpu\":{\"type\":\"float\"},\"memory\":{\"type\":\"float\"},\"disk\":{\"type\":\"float\"},\"battery\":{\"type\":\"float\"}}},\"app_screens\":{\"type\":\"nested\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"duration\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"view_count\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"interactions\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"load_time\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"high_load_time_count\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"spin_time\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"high_spin_time_count\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"txns\":{\"type\":\"keyword\",\"doc_values\":true,\"isArray\":true},\"is_crashed\":{\"type\":\"boolean\",\"doc_values\":true},\"network_count\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"network_error_count\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"network_response_time\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"tap_contexts\":{\"type\":\"keyword\",\"isArray\":true},\"landscape_taps\":{\"type\":\"keyword\",\"index\":\"false\",\"doc_values\":true},\"portrait_taps\":{\"type\":\"keyword\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true}}},\"pipeline_response_time\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"pipeline_network_errors\":{\"type\":\"integer\",\"index\":false,\"doc_values\":true},\"pipeline_data_in\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"pipeline_data_out\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"pipeline_cpu\":{\"type\":\"float\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"pipeline_memory\":{\"type\":\"float\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"pipeline_disk\":{\"type\":\"float\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"pipeline_battery\":{\"type\":\"float\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true},\"pipeline_session_length\":{\"type\":\"integer\",\"index\":\"false\",\"doc_values\":true},\"pipeline_network_calls\":{\"type\":\"long\",\"index\":\"false\",\"doc_values\":true},\"pipeline_slow_network_calls\":{\"type\":\"long\",\"index\":\"false\",\"doc_values\":true},\"pipeline_custatt_double\":{\"type\":\"nested\",\"properties\":{\"key\":{\"type\":\"keyword\",\"index\":\"true\",\"doc_values\":true},\"value\":{\"type\":\"double\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true}}},\"pipeline_custatt_string\":{\"type\":\"nested\",\"properties\":{\"key\":{\"type\":\"keyword\",\"index\":\"true\",\"doc_values\":true},\"value\":{\"type\":\"keyword\",\"index\":\"false\",\"doc_values\":true,\"isArray\":true}}}}}}}";
    System.out.println(json1);
    System.out.println(json2);
    // JSONArray arr1 = new JSONArray(json1);
    // JSONArray arr2 = new JSONArray(json2);
    // JSONArray json = new UpdateMappingService().mergeJson(arr1, arr2);
    JSONObject toBeMergedInto = new JSONObject(json1);
    JSONObject toBeMerged = new JSONObject(json2);
    JSONObject merged = new JsonMerger().mergeJson(toBeMergedInto, toBeMerged);
    System.out.println(merged);

  }
}

