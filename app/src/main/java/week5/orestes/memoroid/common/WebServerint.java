package week5.orestes.memoroid.common;

import retrofit2.Call;
import retrofit2.http.GET;
import week5.orestes.memoroid.model.Exemple;
import week5.orestes.memoroid.model.UserJava;
import week5.orestes.memoroid.model.UserKt;

public interface WebServerint {

    @GET("5c2542a7300000540067f4ec")
    Call<Exemple>getMyExample();
//
//    @GET("5c3860083100006900a98f9f")
//    Call<UserJava> getUser();

    //kotlin
    @GET("5c3860083100006900a98f9f")
    Call<UserKt> getUser();

}
