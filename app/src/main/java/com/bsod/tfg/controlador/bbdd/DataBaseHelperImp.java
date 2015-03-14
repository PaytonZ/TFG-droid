package com.bsod.tfg.controlador.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Proudly created by Payton on 14/03/2015.
 */
public class DataBaseHelperImp extends DataBaseHelper {


    protected DataBaseHelperImp(Context context) {
        super(context);
    }

    @Override
    protected void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH; //+ DB_NAME;
        // myDataBase = SQLiteDatabase.openOrCreateDatabase(myPath,SQLiteDatabase.OPEN_READWRITE, null);
    }


    /**
     * What to do when your database needs to be created. Usually this entails creating the tables and loading any
     * initial data.
     * <p/>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being created.
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, MessageBoard.class);
            TableUtils.createTable(connectionSource, User.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * What to do when your database needs to be updated. This could mean careful migration of old data to new data.
     * Maybe adding or deleting database columns, etc..
     * <p/>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being upgraded.
     * @param connectionSource To use get connections to the database to be updated.
     * @param oldVersion       The version of the current database so we can know what to do to the database.
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    @Override
    public Dao<MessageBoard, Integer> getDAOMessageBoard() {
        Dao<MessageBoard, Integer> result = null;
        try {
            result = DaoManager.createDao(connectionSource, MessageBoard.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Dao<User, Integer> getDAOUser() {
        Dao<User, Integer> result = null;
        try {
            result = DaoManager.createDao(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
