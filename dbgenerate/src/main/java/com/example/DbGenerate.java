package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DbGenerate {

    public static void main(String args[]) {
        Schema schema = new Schema(1, "rn.heruijun.com.filedownload.db");
        Entity entitiy = schema.addEntity("DownloadEntity");
        entitiy.addLongProperty("start_position");
        entitiy.addLongProperty("end_position");
        entitiy.addLongProperty("progress_position");
        entitiy.addStringProperty("download_url");
        entitiy.addIntProperty("thread_id");
        entitiy.addIdProperty().autoincrement();

        try {
            new DaoGenerator().generateAll(schema, "dbgenerate/src-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
