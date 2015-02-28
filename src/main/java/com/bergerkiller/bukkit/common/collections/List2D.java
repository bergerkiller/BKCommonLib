package com.bergerkiller.bukkit.common.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A list implementation that uses a backing array of lists. The amount of
 * stored lists is NEVER changed.
 *
 * @param <E> - List element type
 */
public class List2D<E> implements List<E> {

    private final Collection<List<E>> lists;

    public List2D(List<E>[] lists) {
        this(Arrays.asList(lists));
    }

    public List2D(Collection<List<E>> lists) {
        if (lists.isEmpty()) {
            throw new IllegalArgumentException("Can not use an empty collection of lists");
        }
        this.lists = lists;
    }

    @Override
    public boolean add(E e) {
        Iterator<List<E>> iter = lists.iterator();
        List<E> rval = null;
        while (iter.hasNext()) {
            rval = iter.next();
        }
        return rval.add(e);
    }

    @Override
    public int size() {
        int size = 0;
        for (List<E> list : lists) {
            size += list.size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (List<E> list : lists) {
            if (!list.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        for (List<E> list : lists) {
            if (list.contains(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new List2DIterator<E>(lists);
    }

    @Override
    public Object[] toArray() {
        return CollectionBasics.toArray(this);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return CollectionBasics.toArray(this, array);
    }

    @Override
    public boolean remove(Object o) {
        for (List<E> list : lists) {
            if (list.remove(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return CollectionBasics.getEntry(lists, index).addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (List<E> list : lists) {
            changed |= list.removeAll(c);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return CollectionBasics.retainAll(this, c);
    }

    @Override
    public void clear() {
        for (List<E> list : lists) {
            list.clear();
        }
    }

    @Override
    public E get(int index) {
        return CollectionBasics.getEntry(lists, index).get();
    }

    @Override
    public E set(int index, E element) {
        return CollectionBasics.getEntry(lists, index).set(element);
    }

    @Override
    public void add(int index, E element) {
        CollectionBasics.getEntry(lists, index).add(element);
    }

    @Override
    public E remove(int index) {
        return CollectionBasics.getEntry(lists, index).remove();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E element : c) {
            changed |= add(element);
        }
        return changed;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        int subIndex;
        for (List<E> list : lists) {
            subIndex = list.indexOf(o);
            if (subIndex == -1) {
                index += list.size();
            } else {
                return index + subIndex;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int rval = -1;
        int index = 0;
        int subIndex;
        for (List<E> list : lists) {
            subIndex = list.lastIndexOf(o);
            if (subIndex == -1) {
                index += list.size();
            } else {
                rval = index + subIndex;
            }
        }
        return rval;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new List2DListIterator<E>(lists);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new List2DListIterator<E>(lists, index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Unimplemented feature");
    }
}
