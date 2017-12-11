package io.github.dmitrikudrenko.mdrxl.sample.model;

public final class Data {
    private final int id;

    public static Data create(final int id) {
        return new Data(id);
    }

    private Data(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                '}';
    }
}
