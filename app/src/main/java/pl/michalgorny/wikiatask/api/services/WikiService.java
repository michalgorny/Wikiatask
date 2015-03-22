package pl.michalgorny.wikiatask.api.services;

import pl.michalgorny.wikiatask.api.responses.GetItemDetailsResponse;
import pl.michalgorny.wikiatask.api.responses.WikiGetListResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 *  Service to download wiki
 */
public interface WikiService {

    @GET("/wikia.php?controller=WikisApi&method=getList")
    void getWikis(@Query("batch") Integer batch, Callback<WikiGetListResponse> callback);

    @GET("/wikia.php?controller=WikisApi&method=getDetails&snippet=0")
    void getDetails(@Query("ids") String ids, Callback<GetItemDetailsResponse> callback);
}
