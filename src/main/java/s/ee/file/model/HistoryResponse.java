package s.ee.file.model;

import java.util.List;

public record HistoryResponse(int code, List<FileResponse.Data> data, String message, boolean success) {}
