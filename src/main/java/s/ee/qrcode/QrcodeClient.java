package s.ee.qrcode;

import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.Response;
import s.ee.common.SeeException;
import s.ee.qrcode.model.QrcodeModels;

import java.util.Map;

public class QrcodeClient extends Client {
    public QrcodeClient(Config config) {
        super(config);
    }

    public QrcodeModels.CreateResponse create(QrcodeModels.CreateRequest request) throws SeeException {
        return post("/qrcode", request, QrcodeModels.CreateResponse.class);
    }

    public Response delete(String domain, String slug) throws SeeException {
        return delete("/qrcode", new QrcodeModels.DeleteRequest(domain, slug), Response.class);
    }

    public QrcodeModels.HistoryResponse getHistory(Integer page) throws SeeException {
        return get("/qrcodes", Map.of("page", pageOrDefault(page)), QrcodeModels.HistoryResponse.class);
    }
}
