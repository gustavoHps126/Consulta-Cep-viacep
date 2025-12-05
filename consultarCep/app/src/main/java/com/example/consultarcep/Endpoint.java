package com.example.consultarcep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface Endpoint {
    @GET("/ws/{cep}/json")
    Call<CepModel> get(@Path("cep") String cep);
}
