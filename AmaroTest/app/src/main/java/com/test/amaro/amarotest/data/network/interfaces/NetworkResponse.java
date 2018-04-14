package com.test.amaro.amarotest.data.network.interfaces;

import com.test.amaro.amarotest.data.model.CatalogueResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rodri on 13/04/2018.
 */

public interface NetworkResponse {
    //@GET("v2/59b6a65a0f0000e90471257d/")
    @GET("things/KzmM.json")
    Call<CatalogueResponse> loadCatalogue();
}
