package db02;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DBContext<E> {
    public static final String INSERT_SQL = "INSERT INTO %s (%s) VALUES (%s)";
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        int idValue = getIdValue(entity);
        if (idValue == 0){
            doInsert(entity);
        }
        return false;
    }

    private boolean doInsert(E entity) throws IllegalAccessException, SQLException {
        //find table name
        String tableName = getTableName(entity.getClass());
        //find column names
        List<String> columnNames= findEntityColumns(entity);
        //find column values
        List<String> columnValues = findEntityValues(entity);
        //generate + execute sql
        String insertSQL = String.format(INSERT_SQL,
                tableName,
                String.join(",", columnNames),
                String.join("'",columnValues));

        int updateCount = connection.prepareStatement(insertSQL).executeUpdate();

        return updateCount == 1;
    }

    private List<String> findEntityValues(E entity) throws IllegalAccessException {
        List<String> result = new ArrayList<>();

        for(Field field: entity.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(Column.class)){
                continue;
            }
            field.setAccessible(true);
            field.get(entity);
            Object value = field.get(entity).toString();
            result.add("'" + value + "'");
        }
        return result;
    }


    private List<String> findEntityColumns(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getAnnotation(Column.class))
                .map(ann -> ann.name())
                .collect(Collectors.toList());
    }

    private String getTableName(Class<E> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);

        if (annotation == null){
            throw new IllegalArgumentException("Entity annotation missing!");
        }
        return annotation.name();
    }

    private int getIdValue(E entity) throws IllegalAccessException {
        // get all fields
        List<Field> idFields = Arrays.stream(entity.getClass().getDeclaredFields()).
                filter(field -> field.isAnnotationPresent(Id.class)).
                collect(Collectors.toList());
        if (idFields.size() != 1){
            throw new IllegalArgumentException("Entity should have one ID field.");
        }
        // find field with @Id
        Field idField = idFields.get(0);
        // Get value from field
        idField.setAccessible(true);

        return (int) idField.get(entity);
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table);

        String selectSingleSQL = String.format("SELECT * FROM %s %s %s",
                tableName,
                where == null ? "" : where,
                "");
        ResultSet resultSet = connection.prepareStatement(selectSingleSQL).executeQuery();

        List<E> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(mapEntity(table, resultSet));
        }
        return result;
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //SELECT * FROM TABLE WHERE ... LIMIT 1;
        String tableName = getTableName(table);

        String selectSingleSQL = String.format("SELECT * FROM %s %s %s",
                tableName,
                where == null ? "" : where,
                "LIMIT 1");
        ResultSet resultSet = connection.prepareStatement(selectSingleSQL).executeQuery();
        if (resultSet.next()){
            return mapEntity(table, resultSet);
        }
        return null;
    }

    private E mapEntity(Class<E> type, ResultSet dbResult) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        E result = type.getDeclaredConstructor().newInstance();

        for(Field field: type.getDeclaredFields()){
            if (!field.isAnnotationPresent(Id.class) &&
            !field.isAnnotationPresent(Column.class)) {
                continue;
            }
            result = mapField(result, field, dbResult);
        }

        return result;
    }

    private E mapField(E object, Field field, ResultSet dbResult) throws IllegalAccessException, SQLException {
        String column = "id";

        if (field.isAnnotationPresent(Column.class)){
            column = field.getAnnotation(Column.class).name();
        }
        Object dbValue = mapValue(field, column, dbResult);

        field.set(object, dbValue);
        return object;
    }

    private Object mapValue(Field field, String column, ResultSet dbResult) throws SQLException {
        if (field.getType() == int.class || field.getType() == Integer.class) {
            return dbResult.getInt(column);
        } else if (field.getType() == String.class){
            return dbResult.getString(column);
        } else if (field.getType() == LocalDate.class) {
            String date = dbResult.getString(column);
            return LocalDate.parse(date);
        }
        throw new IllegalArgumentException("Unsupported type " + field.getType());
    }
}
