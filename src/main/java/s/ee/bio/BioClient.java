package s.ee.bio;

import s.ee.bio.model.BioModels;
import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.Response;
import s.ee.common.SeeException;

import java.util.Map;

public class BioClient extends Client {
    public BioClient(Config config) {
        super(config);
    }

    public BioModels.CreateResponse create(BioModels.CreateRequest request) throws SeeException {
        return post("/bio", request, BioModels.CreateResponse.class);
    }

    public Response update(BioModels.UpdateRequest request) throws SeeException {
        return put("/bio", request, Response.class);
    }

    public Response delete(long id) throws SeeException {
        return delete("/bio", new BioModels.DeleteRequest(id), Response.class);
    }

    public BioModels.HistoryResponse getHistory(Integer page) throws SeeException {
        return get("/bios", Map.of("page", page == null ? 1 : page), BioModels.HistoryResponse.class);
    }
}
