package pl.michalgorny.wikiatask.api.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 *  Class corresponding server getList response
 */
@Data
public class WikiGetListResponse {
    private List<WikiItemResponse> items = new ArrayList<>();
    private Integer next;
    private Integer total;
    private Integer batches;
    private Integer currentBatch;
}
