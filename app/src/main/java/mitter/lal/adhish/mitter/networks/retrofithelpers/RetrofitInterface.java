package mitter.lal.adhish.mitter.networks.retrofithelpers;

import mitter.lal.adhish.mitter.networks.retrofithelpers.responsemodels.RetrofitResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Adhish on 11/03/18.
 */

public interface RetrofitInterface {

    @POST(EndPoints.mySampleEndPoint)
    Call<RetrofitResponse> hitApi(@Query("message") String message);

}
