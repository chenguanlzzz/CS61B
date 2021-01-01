public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public void resize(int x){
        T[] newItems = (T[]) new Object[x];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    public void addFirst(T x){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextFirst] = x;
        nextFirst -= 1;
        if (nextFirst == -1){
            nextFirst = items.length - 1;
        }
        size += 1;
    }

    public void addLast(T x){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextLast] = x;
        nextLast += 1;
        if (nextLast == items.length){
            nextLast = 0;
        }
        size += 1;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    public int size(){
        return this.size;
    }

    public void printDeque(){
        for (int i = 0; i < size; i++){
            System.out.println(items[i]);
        }
    }

    public T removeFirst(){
        if (size == 0){
            return null;
        }
        nextFirst++;
        size--;
        if (nextFirst == items.length){
            nextFirst = 0;
        }
        T temp = items[nextFirst];
        items[nextFirst] = null;
        if ((int) (0.25 * items.length) > size && items.length > 16){
            resize(items.length/2);
        }
        return temp;
    }

    public T removeLast(){
        if (size == 0){
            return null;
        }
        nextLast--;
        size--;
        if(nextLast == -1){
            nextLast = items.length - 1;
        }
        T temp = items[nextLast];
        items[nextLast] = null;
        if ((int) (0.25 * items.length) > size && items.length > 16){
            resize(items.length/2);
        }
        return null;
    }

    public T get(int x){
        return items[x];
    }

    public static void main(String[] args){
        ArrayDeque<Integer>  a = new ArrayDeque<>();
        a.addFirst(1);
        a.addLast(2);
        a.addFirst(3);
        a.removeFirst();
    }
}
