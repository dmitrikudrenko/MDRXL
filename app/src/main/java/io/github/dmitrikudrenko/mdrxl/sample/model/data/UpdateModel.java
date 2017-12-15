package io.github.dmitrikudrenko.mdrxl.sample.model.data;

import android.content.ContentValues;

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

    public void fill(final ContentValues contentValues) {
        for (final Map.Entry<String, Object> entry : model.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            if (Integer.class.equals(value.getClass())) {
                contentValues.put(key, (Integer) value);
            } else if (String.class.equals(value.getClass())) {
                contentValues.put(key, (String) value);
            }
            //TODO: support other types
        }
    }

    public Data update(final Data data) {
        for (final Map.Entry<String, Object> entry : model.entrySet()) {
            data.set(entry.getKey(), entry.getValue());
        }
        return data;
    }
}
