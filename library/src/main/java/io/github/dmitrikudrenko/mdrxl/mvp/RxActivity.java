package io.github.dmitrikudrenko.mdrxl.mvp;

import android.os.Bundle;
import com.arellomobile.mvp.MvpAppCompatActivity;

public class RxActivity extends MvpAppCompatActivity implements RxView {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    protected void beforeOnCreate(final Bundle savedInstanceState) {
        //to override
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
