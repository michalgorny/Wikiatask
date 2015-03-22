package pl.michalgorny.wikiatask.api.responses;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Builder;
import pl.michalgorny.wikiatask.pojos.Wiki;

/**
 *  Response for request getItemDetails and used in DetailsSerializer. It contains list of wiki.
 */

@Data
@Builder
public class GetItemDetailsResponse {
    List<Wiki> wikiList;
}
