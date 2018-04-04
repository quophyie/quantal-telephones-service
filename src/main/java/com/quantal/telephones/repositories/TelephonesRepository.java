package com.quantal.telephones.repositories;

import com.quantal.telephones.models.Telephone;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by dman on 08/03/2017.
 */
public interface TelephonesRepository extends PagingAndSortingRepository<Telephone, Long> {
}
