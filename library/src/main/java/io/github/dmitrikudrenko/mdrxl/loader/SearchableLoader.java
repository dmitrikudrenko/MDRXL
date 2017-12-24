package io.github.dmitrikudrenko.mdrxl.loader;

public interface SearchableLoader {
    void setSearchQuery(String query);

    void flushSearch();
}
