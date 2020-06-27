package com.zte.shopping.mapper;

import com.zte.shopping.entity.SysSequence;

/**
 * Created by Eytins
 */

public interface ISequenceMapper {
    SysSequence selectByName(String name);

    void insertSequence(SysSequence sequ);

    void updateSequenceValue(String productNoPrefix, String value);
}
