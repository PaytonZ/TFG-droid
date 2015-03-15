package com.bsod.tfg.utils;

import com.bsod.tfg.modelo.chat.ChatServerBean;
import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Proudly created by Payton on 14/03/2015.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[]{
            MessageBoard.class, User.class, ChatServerBean.class
    };

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile(new File("app/src/main/res/raw/ormlite_config.txt"), classes);
    }
}


