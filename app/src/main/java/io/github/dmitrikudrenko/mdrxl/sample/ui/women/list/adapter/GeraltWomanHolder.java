package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter;

import io.github.dmitrikudrenko.ui.ViewHolder;

public interface GeraltWomanHolder extends ViewHolder {
    void showPhoto(String value);
    void showName(String value);
    void showProfession(String value);
    void setSelected(boolean selected);
}
