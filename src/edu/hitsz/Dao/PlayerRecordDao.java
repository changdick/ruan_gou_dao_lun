package edu.hitsz.Dao;

import java.util.List;

public interface PlayerRecordDao {
    List<PlayerRecord> getAllRecords();
    void addRecord(PlayerRecord record);
    void saveRecords();

    void deleteRecord(int index);

}
