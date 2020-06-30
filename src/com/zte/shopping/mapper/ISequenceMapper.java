package com.zte.shopping.mapper;

import com.zte.shopping.entity.Sequence;

/**
 * Created by Eytins
 */

public interface ISequenceMapper {
    Sequence selectByName(String name);

    void insertSequence(Sequence sequ);

    void updateSequenceValue(String productNoPrefix, String value);
}
