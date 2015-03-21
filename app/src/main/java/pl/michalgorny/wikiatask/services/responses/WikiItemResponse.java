package pl.michalgorny.wikiatask.services.responses;


import lombok.Data;

/**
 *  Class corresponding one item from response wiki list items
 */
@Data
public class WikiItemResponse {
    private Long id;
    private String name;
    private String hub;
    private String language;
    private String topic;
    private String domain;
}
