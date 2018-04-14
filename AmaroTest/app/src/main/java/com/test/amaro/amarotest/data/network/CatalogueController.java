package com.test.amaro.amarotest.data.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.amaro.amarotest.data.model.CatalogueResponse;
import com.test.amaro.amarotest.data.network.interfaces.NetworkResponse;
import com.test.amaro.amarotest.data.network.interfaces.UiController;
import com.test.amaro.amarotest.utils.C;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodri on 13/04/2018.
 */

public class CatalogueController implements Callback<CatalogueResponse> {

    private UiController mUiController;

    public void start(UiController uiController) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(C.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NetworkResponse networkResponse = retrofit.create(NetworkResponse.class);

        Call<CatalogueResponse> call = networkResponse.loadCatalogue();
        call.enqueue(this);
        mUiController = uiController;
    }

    @Override
    public void onResponse(Call<CatalogueResponse> call, Response<CatalogueResponse> response) {
        if (response.isSuccessful()) {
            mUiController.onResponseOK(response.body().getProducts());
        } else {
            Log.e(C.LOG_TAG, String.valueOf(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Call<CatalogueResponse> call, Throwable t) {
        t.printStackTrace();
        mUiController.onResponseFail(t);
    }
}
