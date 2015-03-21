package pl.michalgorny.wikiatask.services;

import pl.michalgorny.wikiatask.services.responses.WikiGetListResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 *  Service to download wiki
 */
public interface WikiService {

    @GET("/wikia.php?controller=WikisApi&method=getList")
    void getWikis(@Query("batch") Integer batch, Callback<WikiGetListResponse> callback);
}
