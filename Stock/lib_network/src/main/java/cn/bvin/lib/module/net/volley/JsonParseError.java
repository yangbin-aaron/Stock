package cn.bvin.lib.module.net.volley;

import com.android.volley.VolleyError;

/**
 * json解析异常，JsonSyntaxException触发
 * @author bvin
 *
 */
public class JsonParseError extends VolleyError{
    
    private static final long serialVersionUID = -606838074878317350L;
    
    private final String originJson;

    
    public JsonParseError(String originJson, Throwable cause) {
        super(cause);
        this.originJson = originJson;
    }

    public String getOriginJson() {
        return originJson;
    }

}
