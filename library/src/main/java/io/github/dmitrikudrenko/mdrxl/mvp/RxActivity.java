package io.github.dmitrikudrenko.mdrxl.mvp;

import android.os.Bundle;

public abstract class RxActivity extends MvpAppCompatActivity implements RxView {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    protected void beforeOnCreate(final Bundle savedInstanceState) {
        //to override
    }
}
