package com.zte.shopping.dao;

import com.zte.shopping.entity.Sequence;

/**
 * Created by Eytins
 */

public interface ISequenceDao {

    /**
     * 根据name查询对应的Sequence信息
     */
    Sequence selectByName(String name);

    void insertSequence(Sequence sequ);

    void updateSquenceValue(String productNoPrefix, String value);

}
