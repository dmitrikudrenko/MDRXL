package io.github.dmitrikudrenko.core.remote;

import io.github.dmitrikudrenko.core.remote.model.video.Videos;
import io.github.dmitrikudrenko.core.remote.model.woman.Woman;
import io.github.dmitrikudrenko.core.remote.model.woman.Women;
import io.github.dmitrikudrenko.core.remote.model.woman.photo.Photos;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Single;

public interface WitcherApi {
    @GET("women.json")
    Single<Women> getWomen();

    @GET("photos/{woman_id}.json")
    Single<Photos> getPhotos(@Path("woman_id") long womanId);

    @GET("women/{woman_id}.json")
    Single<Woman> getWoman(@Path("woman_id") long womanId);

    @GET("video.json")
    Single<Videos> getVideos();
}
