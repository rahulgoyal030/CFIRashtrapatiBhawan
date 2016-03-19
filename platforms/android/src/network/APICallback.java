package network;

/**
 * Created by Raja on 3/19/2016.
 */
import org.json.JSONObject;

public interface APICallback {

    void onResponse(JSONObject response);

    void onError(JSONObject response);
}
