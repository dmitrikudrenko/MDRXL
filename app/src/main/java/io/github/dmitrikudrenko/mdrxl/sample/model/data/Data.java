package io.github.dmitrikudrenko.mdrxl.sample.model.data;

public final class Data {
    private final int id;
    private final String name;

    public static Data create(final int id, final String name) {
        return new Data(id, name);
    }

    private Data(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
