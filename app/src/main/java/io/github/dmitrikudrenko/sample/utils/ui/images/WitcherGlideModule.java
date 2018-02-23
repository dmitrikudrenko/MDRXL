package io.github.dmitrikudrenko.sample.utils.ui.images;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;

//@GlideModule
public class WitcherGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(final Context context, final Glide glide, final Registry registry) {
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
