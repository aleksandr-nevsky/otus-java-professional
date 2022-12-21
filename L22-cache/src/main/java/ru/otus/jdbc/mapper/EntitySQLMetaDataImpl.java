package ru.otus.jdbc.mapper;


import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaDataClient;
    private final String fieldsList;
    private final String fieldsListWoId;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
        List<Field> fields = entityClassMetaDataClient.getAllFields();
        this.fieldsList = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        List<Field> fieldsWoId = entityClassMetaDataClient.getFieldsWithoutId();

        this.fieldsListWoId = fieldsWoId.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT " + fieldsList + " FROM " + entityClassMetaDataClient.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT " + fieldsList + " FROM " + entityClassMetaDataClient.getName() + " WHERE " + entityClassMetaDataClient.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO " + entityClassMetaDataClient.getName() + "(" + fieldsListWoId + ") VALUES(" + fieldsListWoId.replaceAll("[^,]", "").replace(",", "?,").concat("?") + ")";
    }

    @Override
    public String getUpdateSql() {
        List<Field> fields = entityClassMetaDataClient.getFieldsWithoutId();

        String fieldsList = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(" = ?, "));
        fieldsList = fieldsList.concat(" = ?");
        return "UPDATE " + entityClassMetaDataClient.getName() + " SET " + fieldsList + " WHERE " + entityClassMetaDataClient.getIdField().getName() + " = ?";
    }
}
