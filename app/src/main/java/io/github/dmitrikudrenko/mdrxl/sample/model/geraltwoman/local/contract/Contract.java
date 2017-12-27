package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.contract;

import android.provider.BaseColumns;

public interface Contract extends BaseColumns {
    String tableName();

    String createTable();

    String selectAll();

    String selectById();
}
