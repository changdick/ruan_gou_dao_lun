package edu.hitsz.Dao;

import java.io.*;
import java.util.*;

public class PlayerRecordDaoImpl implements PlayerRecordDao{
    private final String FILENAME;
    private List<PlayerRecord> records;

    Comparator<PlayerRecord> compareScore = (o1, o2) -> Integer.compare(o2.getScore(), o1.getScore());

    public PlayerRecordDaoImpl(String difficulty){
        FILENAME = STR."RecordRankBoard\{difficulty}.txt";

         records = new ArrayList<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILENAME))) {
            records = (List<PlayerRecord>) inputStream.readObject();
            System.out.println("排行榜加载成功！");
        } catch (FileNotFoundException e) {
            System.out.println("找不到保存的排行榜文件：" + e.getMessage());
        } catch (IOException e) {
            System.out.println("加载排行榜时出错：" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("无法解析保存的排行榜文件：" + e.getMessage());
        }
    }
    @Override
    public List<PlayerRecord> getAllRecords(){
        return records;
    }

    @Override
    public void addRecord(PlayerRecord record) {
        records.add(record);
        rankRecords();
    }

    @Override
    public void saveRecords() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            outputStream.writeObject(records);
            outputStream.close();
            System.out.println("排行榜保存成功！");
        } catch (IOException e) {
            System.out.println("保存排行榜时出错：" + e.getMessage());
        }
    }

    private void rankRecords() {
        records.sort(compareScore);
    }

    @Override
    public void deleteRecord(int index) {
        records.remove(records.get(index));
        saveRecords();
    }
}
