package io.github.dmitrikudrenko.mdrxl.mvp;

import android.os.Bundle;

public class RxFragment extends MvpCompatFragment implements RxView {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    protected void beforeOnCreate(final Bundle savedInstanceState) {
        //to override
    }
}
