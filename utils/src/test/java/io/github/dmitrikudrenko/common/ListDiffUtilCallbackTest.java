package io.github.dmitrikudrenko.common;

import io.github.dmitrikudrenko.utils.common.ListDiffUtilCallback;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ListDiffUtilCallbackTest {
    private ListDiffUtilCallback<Object> callback;

    @Test
    public void shouldOldItemsSizeBeZero() {
        final List<Object> oldItems = null;
        final List<Object> newItems = mock(List.class);
        callback = createCallback(newItems, oldItems);
        assertEquals(0, callback.getOldListSize());
    }

    @Test
    public void shouldReturnListsSizes() {
        final List<Object> oldItems = mock(List.class);
        final List<Object> newItems = mock(List.class);

        final int oldItemsCount = 1;
        final int newItemsCount = 2;

        when(oldItems.size()).thenReturn(oldItemsCount);
        when(newItems.size()).thenReturn(newItemsCount);

        callback = createCallback(newItems, oldItems);

        assertEquals(oldItemsCount, callback.getOldListSize());
        assertEquals(newItemsCount, callback.getNewListSize());
    }

    @Test
    public void shouldReturnCorrectItems() {
        final List<Object> oldItems = mock(List.class);
        final List<Object> newItems = mock(List.class);

        final Object oldItem = new Object();
        final Object newItem = new Object();

        when(oldItems.get(anyInt())).thenReturn(oldItem);
        when(newItems.get(anyInt())).thenReturn(newItem);

        callback = spy(createCallback(newItems, oldItems));

        callback.areItemsTheSame(0, 0);
        verify(callback).areItemsTheSame(oldItem, newItem);

        callback.areContentsTheSame(0, 0);
        verify(callback).areContentsTheSame(oldItem, newItem);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowErrorIfOldItemsAreNull() {
        callback = createCallback(mock(List.class), null);
        callback.areItemsTheSame(0, 0);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowErrorIfOldItemsAreNull2() {
        callback = createCallback(mock(List.class), null);
        callback.areContentsTheSame(0, 0);
    }

    private ListDiffUtilCallback<Object> createCallback(
            final List<Object> newItems, final List<Object> oldItems) {
        return new ListDiffUtilCallback<Object>(newItems, oldItems) {
            @Override
            protected boolean areItemsTheSame(final Object oldItem, final Object newItem) {
                return false;
            }

            @Override
            protected boolean areContentsTheSame(final Object oldItem, final Object newItem) {
                return false;
            }
        };
    }
}
