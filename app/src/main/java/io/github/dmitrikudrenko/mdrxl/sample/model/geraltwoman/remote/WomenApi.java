package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote;

import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model.Photos;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model.Women;
import retrofit2.http.GET;
import rx.Single;

public interface WomenApi {
    @GET("women.json")
    Single<Women> getWomen();

    @GET("photos/{womanId}.json")
    Single<Photos> getPhotos(long womanId);
}
