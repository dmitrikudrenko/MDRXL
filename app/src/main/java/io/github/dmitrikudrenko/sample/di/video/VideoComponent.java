package io.github.dmitrikudrenko.sample.di.video;

import dagger.Subcomponent;
import io.github.dmitrikudrenko.sample.ui.video.player.VideoPlayerFragment;

@VideoScope
@Subcomponent(modules = VideoModule.class)
public interface VideoComponent {
    @VideoId
    long id();

    VideoPlayerFragment.Component plus(VideoPlayerFragment.Module module);
}
