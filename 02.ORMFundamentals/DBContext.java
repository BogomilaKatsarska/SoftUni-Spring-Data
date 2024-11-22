package db02;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DBContext<E> {
    boolean persist(E entity) throws IllegalAccessException, SQLException; // INSERT or UPDATE

    Iterable<E> find(Class<E> table) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException; //SELECT
    Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;//SELECT WHERE

    E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException; // SELECT LIMIT 1
    E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException; // SELECT WHERE LIMIT 1
}
