package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote;

import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model.Photos;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model.Woman;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model.Women;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Single;

public interface WomenApi {
    @GET("women.json")
    Single<Women> getWomen();

    @GET("photos/{woman_id}.json")
    Single<Photos> getPhotos(@Path("woman_id") long womanId);

    @GET("women/{woman_id}.json")
    Single<Woman> getWoman(@Path("woman_id") long womanId);
}
