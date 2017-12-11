package io.github.dmitrikudrenko.mdrxl.loader;

final class RxLoaderData<D> {
    private final D data;
    private final Throwable error;

    static <D> RxLoaderData<D> result(final D data) {
        return new RxLoaderData<>(data, null);
    }

    static <D> RxLoaderData<D> error(final Throwable error) {
        return new RxLoaderData<>(null, error);
    }

    private RxLoaderData(final D data, final Throwable error) {
        this.data = data;
        this.error = error;
    }

    D getData() {
        return data;
    }

    Throwable getError() {
        return error;
    }

    boolean isSuccess() {
        return error == null;
    }
}
