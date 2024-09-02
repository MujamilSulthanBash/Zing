package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.DarkStore;

/**
 * <p>
 *     This interface has Abstract methods for
 *     Dark Store Operations like get all the DarkStores
 *     and DarkStore Object
 * </p>
 */
@Repository
public interface DarkStoreRepository extends JpaRepository<DarkStore, String> {

    /**
     * <p>
     *     This method return the list of DarkStores
     *     from the Database if the DarkStore not deleted.
     * </p>
     * @return List of DarkStores
     */
    List<DarkStore> findByIsDeletedFalse();

    /**
     * <p>
     *     This method return a specific DarkStore by DarkStore
     *     ID given by the User
     * </p>
     * @param darkStoreId - To Identify DarkStore
     * @return DarkStore as Entity Object
     */
    DarkStore findByIsDeletedFalseAndDarkStoreId(String darkStoreId);

}
