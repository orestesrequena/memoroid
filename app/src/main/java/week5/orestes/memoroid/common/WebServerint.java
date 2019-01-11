package week5.orestes.memoroid.common;

import retrofit2.Call;
import retrofit2.http.GET;
import week5.orestes.memoroid.model.Exemple;

public interface WebServerint {

    @GET("5c2542a7300000540067f4ec")
    Call<Exemple>getMyExample();
}
