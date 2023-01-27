package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Client;

import java.util.List;


public interface ClientRepository extends CrudRepository<Client, Long> {


    @Override
    @Query(value = """
            SELECT client.id          AS client_id,
                   client.name        AS client_name,
                   address.id         AS address_id,
                   address.street     AS street,
                   address.zip_code   AS zip_code,
                   phone.id           AS phone_id,
                   phone.phone_number AS phone_number
            FROM client
                        
                     LEFT OUTER JOIN address ON client.id = address.id
                     LEFT OUTER JOIN phone ON client.id = phone.client_id
                        
            ORDER BY client_id, phone.id
            """,
            resultSetExtractorClass = ClientResultSetExtractorClass.class)
    List<Client> findAll();
}
