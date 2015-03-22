package pl.michalgorny.wikiatask.pojos;

import lombok.Data;
import lombok.experimental.Builder;

/**
 *  Class handling information about details of wiki item.
 */
@Data
@Builder
public class Wiki {
    Integer id;
    String title;
    String url;
    String urlImage;
}
