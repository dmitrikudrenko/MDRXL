package io.github.dmitrikudrenko.sample.ui.video.list;

import io.github.dmitrikudrenko.sample.ui.base.BaseFragmentHolderRxActivity;

public class WitcherVideosActivity extends BaseFragmentHolderRxActivity<WitcherVideosFragment> {

    @Override
    protected WitcherVideosFragment createFragment() {
        return new WitcherVideosFragment();
    }
}
