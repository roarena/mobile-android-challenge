package com.test.amaro.amarotest.utils;

import com.test.amaro.amarotest.data.model.SizesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 15/04/2018.
 */

public class Utils {

    public static List<SizesItem> clearNotAvailableSizes(List<SizesItem> sizesItems) {
        List<SizesItem> availableSizes = new ArrayList<>();

        for (SizesItem size : sizesItems) {
            if (size.isAvailable())
                availableSizes.add(size);
        }
        return availableSizes;
    }
}
