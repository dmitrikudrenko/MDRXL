package io.github.dmitrikudrenko.sample.utils.ui.view;

public interface INavigationView {
    void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener);

    void setItemSelected(int menuItem);
}
