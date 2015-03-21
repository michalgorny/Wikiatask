package pl.michalgorny.wikiatask.services.responses;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class corresponding response from retrieving wiki items
 */
public class WikiGetListResponse {
    private List<WikiItemResponse> items = new ArrayList<>();
    private Integer next;
    private Integer total;
    private Integer batches;
    private Integer currentBatch;

    public List<WikiItemResponse> getItems() {
        return items;
    }

    public void setItems(List<WikiItemResponse> items) {
        this.items = items;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getBatches() {
        return batches;
    }

    public void setBatches(Integer batches) {
        this.batches = batches;
    }

    public Integer getCurrentBatch() {
        return currentBatch;
    }

    public void setCurrentBatch(Integer currentBatch) {
        this.currentBatch = currentBatch;
    }
}
