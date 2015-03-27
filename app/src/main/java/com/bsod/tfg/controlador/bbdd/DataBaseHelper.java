package com.bsod.tfg.controlador.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.chat.ChatServerBean;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Proudly created by Payton on 14/03/2015.
 */

public abstract class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    protected static final String TAG = "DataBaseHelper";
    private static DataBaseHelper instance;
    protected final String DB_PATH;
    protected Context myContext;
    protected SQLiteDatabase myDataBase;

    protected DataBaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DATABASE_VERSION, R.raw.ormlite_config);
        myContext = context;
        DB_PATH = context.getDatabasePath(Constants.DB_NAME).getPath();
        //context.deleteDatabase(Constants.DB_NAME);
        Log.i(DataBaseHelper.class.getName(), "onCreate");
        try {
            //openDataBase();
            //SQLiteDatabase myDataBase = null;
            onCreate(myDataBase, connectionSource);
            /* Chat Tables destroyed at start */
            TableUtils.dropTable(connectionSource, ChatServerBean.class, true);
            TableUtils.createTableIfNotExists(connectionSource, ChatServerBean.class);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Must be created first! *
     */
    public static DataBaseHelper getInstance() {
        return (instance == null) ? null : instance;
    }

    public static DataBaseHelper getInstance(Context context) {
        createDataBaseHelper(context);
        return instance;
    }

    private static void createDataBaseHelper(Context context) {
        if (instance == null) {
            instance = new DataBaseHelperImp(context);
        }
    }

    protected abstract void openDataBase() throws SQLException;

    public abstract Dao<MessageBoard, Integer> getDAOMessageBoard();

    public abstract Dao<User, Integer> getDAOUser();

    public abstract Dao<ChatServerBean, Integer> getDAOChatServer();
}
