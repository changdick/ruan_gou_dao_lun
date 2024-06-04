package edu.hitsz.application.gui;

import edu.hitsz.Dao.PlayerRecord;
import edu.hitsz.application.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RankBoardFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JScrollPane midPanel;
    private JPanel buttomPanel;
    private JTable rankTable;
    private JButton deletButton;
    private JLabel difficaltyLable1;
    private JLabel difficaltyLable2;


    /**
     * 构造方法
     */
    public RankBoardFrame() {
        difficaltyLable2.setText(Main.getDifficulty());

        showRankTable();

        deletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = rankTable.getSelectedRow();
                System.out.println(row);
                if (row != -1) {
                    int result = JOptionPane.showConfirmDialog(deletButton, "是否确定中删除？");
                    // 因为本身会排序，所以row就是records里面的下标
                    if (result == JOptionPane.YES_OPTION) {
                        // 用户点击了确认按钮，执行删除操作
                        Main.playerRecordDao.deleteRecord(row);
                        showRankTable();
                    }
                }
        }});
    }

    /**
     * 在构造方法内调用此方法，用于显示排行榜。要做的事情是把数据输入到表格模型，然后再设置显示出来。需要获取Dao并进行填表。
     */
//  DAO传到此处的逻辑： 本来DAO在game里面定义并且初始化，把DAO在Main里面定义，在main内初始化，这样排行榜在main里调用的时候就可以获得DAO里的数据
//    本来实验4的时候在game里定义每次游戏的单条记录playerRecord，替换了本来的score，现在实验5决定改回本来的，playerRecord不在game里定义，
//    而是从game里用get方法获取到此次game的分数信息，在main里维护playerRecord并添加到DAO中
    public void showRankTable() {
        // 需要载入数据，dao现在定义在game里，这边用一个List把dao里面的数据列表取出
        //TODO  数据怎么来的问题
        List<PlayerRecord> records = Main.playerRecordDao.getAllRecords();
        String[][] tableData;
        /*
         * @1 columnNames 定义了表格每一列的名称  制表是使用JTable类的setModel方法，读入数据数组(二维)和columnNames得到表格模型。
         * @2 表格模型存在JTable类，也就是这个类的rankTable里，表格在midPanel中显示出来还需要一次midPanel的方法
         */
        String[] columnNames = {"名次", "玩家名", "得分", "记录时间"};
        if (records != null) {
            // 表格的数据，宽就是4columnnames一一对应的4个，长度取决于records有多少条
            tableData = new String[records.size()][columnNames.length];
            //从records的数据载入到tableData
            for (int i = 0; i < records.size(); i++) {
                tableData[i][0] = String.valueOf(i+1);
                tableData[i][1] = records.get(i).getUserName();
                tableData[i][2] = String.valueOf(records.get(i).getScore());
                tableData[i][3] = records.get(i).getLocalDateTime();

            }
        } else {
            tableData = null;
        }
        // 表格模型和显示,这两句来自课程提供的示例
        rankTable.setModel(new DefaultTableModel(tableData, columnNames));
        midPanel.setViewportView(rankTable);



    }

    /**
     *
     * @return mainPanel 所有的Frame的类都有的
     * @注 直接敲"getM"后就会自动可以补出这个方法，所有Frame类都有的
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
