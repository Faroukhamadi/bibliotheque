package org.farouk_maram.interfaces;

public interface HomeCRUD<T> {
  public void fetchAll();

  public int addOne(T t);

  public void updateOne(T t);

  public void deleteOne(int id);

}
