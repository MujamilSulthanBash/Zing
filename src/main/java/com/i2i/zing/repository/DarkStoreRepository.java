package com.i2i.zing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.zing.model.DarkStore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DarkStoreRepository extends JpaRepository<DarkStore, String> {
    /**
     * This method return the list of DarkStores
     * from the Database if the DarkStore not deleted.
     * @return List of DarkStores
     */
    List<DarkStore> findByIsDeletedFalse();

    /**
     * This method return a specific DarkStore by DarkStore
     * ID given by the User
     * @param darkStoreId - To Identify DarkStore
     * @return DarkStore as Entity Object
     */
    DarkStore findByIsDeletedFalseAndDarkStoreId(String darkStoreId);
}
