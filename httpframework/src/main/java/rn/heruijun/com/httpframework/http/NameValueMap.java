package rn.heruijun.com.httpframework.http;

import java.util.Map;

/**
 * Created by heruijun on 2017/9/9.
 */

public interface NameValueMap extends Map {

    boolean get(String key);

    void set(String key, String value);

    void setAll(Map<String, String> map);
    
}
