package io.github.dmitrikudrenko.sample.ui.women.list.adapter;

import io.github.dmitrikudrenko.utils.ui.ViewHolder;

public interface GeraltWomanHolder extends ViewHolder {
    void showPhoto(String value, String placeholder);
    void showName(String value);
    void showProfession(String value);
    void setSelected(boolean selected);
}
