package service;

import com.jsunsoft.http.HttpRequest;
import com.jsunsoft.http.HttpRequestBuilder;
import com.jsunsoft.http.ResponseHandler;
import com.jsunsoft.http.TypeReference;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TimerTask;

import static util.Constant.*;

/**
 * @author Yulya Kukareko
 * @version 1.0 01 Jul 2019
 */
public class ScheduledTask extends TimerTask {

    private static final Logger LOGGER;

    private String message;

    static {
        LOGGER = LoggerFactory.getLogger(SCHEDULED_TASK_LOG);
    }

    public ScheduledTask(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        try {

            HttpRequest<Map<String, String>> HTTP_REQUEST = HttpRequestBuilder.createGet(URL_REQUEST,
                    new TypeReference<Map<String, String>>() {
                    }).build();

            ResponseHandler<Map<String, String>> responseHandler = HTTP_REQUEST.execute(GRANT_TYPE_FIELD_REQ,
                    GRANT_TYPE, CLIENT_ID_FIELD_REQ, CLIENT_ID, CLIENT_SECRET_FIELD_REQ, CLIENT_SECRET, USERNAME_FIELD_REQ,
                    USERNAME, PASSWORD_FIELD_REQ, PASSWORD, VK_API_VERSION_FIELD_REQ, VK_API_VERSION,
                    FA_SUPPORTED_FIELD_REQ, FA_SUPPORTED);
            Map<String, String> response = responseHandler.get();

            UserActor actor = new UserActor(Integer.parseInt(response.get(USER_ID_FILED_RESP)), response.get(ACCESS_TOKEN_FILED_RESP));
            vk.messages().send(actor).peerId(PEER_ID).message(message).execute();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
