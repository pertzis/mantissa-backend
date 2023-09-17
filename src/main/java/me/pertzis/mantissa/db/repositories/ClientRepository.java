package me.pertzis.mantissa.db.repositories;

import me.pertzis.mantissa.db.entities.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {

}
