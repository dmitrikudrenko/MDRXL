package io.github.dmitrikudrenko.mdrxl.sample.model.data;

import java.util.HashMap;
import java.util.Map;

public class UpdateModel {
    private final Map<String, Object> model = new HashMap<>();

    public static UpdateModel create(final String key, final Object value) {
        final UpdateModel model = new UpdateModel();
        model.add(key, value);
        return model;
    }

    public void add(final String key, final Object value) {
        model.put(key, value);
    }

    public Data update(final Data data) {
        for (final Map.Entry<String, Object> entry : model.entrySet()) {
            data.set(entry.getKey(), entry.getValue());
        }
        return data;
    }
}
