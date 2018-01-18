package io.github.dmitrikudrenko.mdrxl.commands;

import android.annotation.SuppressLint;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class CommandStarter {
    private static final String TAG = "CommandStarter";

    private final Map<Class<? extends CommandRequest>, CommandWithScheduler> commands;

    @Inject
    CommandStarter(final Map<Class<? extends CommandRequest>, CommandWithScheduler> commands) {
        this.commands = commands;
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("RxLeakedSubscription")
    public <R extends CommandRequest> void execute(final R request) {
        final Class<? extends CommandRequest> requestClass = request.getClass();
        final String requestName = requestClass.getSimpleName();
        final CommandWithScheduler commandWithScheduler = commands.get(requestClass);

        if (commandWithScheduler != null) {
            commandWithScheduler
                    .getCommand()
                    .execute(request)
                    .subscribeOn(commandWithScheduler.getScheduler())
                    .subscribe(
                            () -> Log.d(TAG, "requestName"),
                            error -> Log.e(TAG, error.getMessage(), error)
                    );
        } else {
            throw new IllegalArgumentException("No command for the request " + requestName);
        }
    }
}
