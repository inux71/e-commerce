package com.grabieckacper.ecommerce.shared.repository;

import com.grabieckacper.ecommerce.shared.model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {

}
