package pl.michalgorny.wikiatask.services.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Builder;

/**
 *  Class corresponding response from retrieving wiki items
 */
@Data
public class WikiGetListResponse {
    private List<WikiItemResponse> items = new ArrayList<>();
    private Integer next;
    private Integer total;
    private Integer batches;
    private Integer currentBatch;
}
