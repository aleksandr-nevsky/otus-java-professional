package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    T model = entityClassMetaData.getConstructor().newInstance();
                    List<Field> fields = entityClassMetaData.getAllFields();

                    for (Field field : fields) {
                        field.setAccessible(true);
                        field.set(model, rs.getObject(field.getName()));
                    }

                    return model;
                }
                throw new RuntimeException("Object not found.");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    T model = entityClassMetaData.getConstructor().newInstance();
                    List<Field> fields = entityClassMetaData.getAllFields();

                    for (Field field : fields) {
                        field.setAccessible(true);
                        field.set(model, rs.getObject(field.getName()));
                    }

                    clientList.add(model);
                }
                return clientList;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                params.add(field.get(client));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                params.add(field.get(client));
            }

            Field fieldId = entityClassMetaData.getIdField();
            fieldId.setAccessible(true);
            params.add(fieldId.get(client));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }
}
