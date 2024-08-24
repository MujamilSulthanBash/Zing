package com.i2i.zing.repository;

import com.i2i.zing.model.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, String> {

    List<DeliveryPerson> findAllByLocation(String location);

}
