package io.github.dmitrikudrenko.core.local.database.contract;

import android.provider.BaseColumns;

public interface Contract extends BaseColumns {
    String tableName();

    String createTable();

    String selectAll();

    String selectById();
}
