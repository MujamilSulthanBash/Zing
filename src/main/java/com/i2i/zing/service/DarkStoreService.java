package com.i2i.zing.service;

import com.i2i.zing.dto.DarkStoreRequestDto;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;

/**
 * <p>
 *     This interface has Abstract methods to implements the
 *     methods for DarkStore Operations like
 *     add, get all the Details, get Details by Id and Delete
 * </p>
 */
@Service
public interface DarkStoreService {

    /**
     * <p>
     *     This method add the Store Details to the Database
     * </p>
     * @param darkStoreRequestDto {@link DarkStoreRequestDto} - Store Details as Dto Object
     * @return APIResponse Details like Status, Data.
     */
    APIResponse addDarkStore(DarkStoreRequestDto darkStoreRequestDto);

    /**
     * <p>
     *     This method retrieve all the Store Details
     *     from the Database
     * </p>
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getDarkStores();

    /**
     * <p>
     *     This method retrieve a specific Store Details by Id
     * </p>
     * @param darkStoreId - To Identify the Store
     * @return APIResponse Details like Status, Data.
     */
    APIResponse getDarkStoreById(String darkStoreId);

    /**
     * <p>
     *     This method delete the Dark Store in the Database
     * </p>
     * @param darkStoreId - To Identify the Store
     * @return APIResponse Details like Status, Data.
     */
    APIResponse deleteDarkStore(String darkStoreId);
}
