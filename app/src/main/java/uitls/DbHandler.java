package uitls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    public static final String DbName       = "dbNoti.db";
    private static final String Id          = "id";
    private static final String TblName     = "notifications";
    private static final String message       = "description";
    private static final String title       = "title";
    private static final String timestamp   = "timestamp";

    ArrayList<HashMap<String, String>> arrayList = new ArrayList();

    public DbHandler(Context context) {
        super(context, DbName, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table "+TblName+"(id INTEGER PRIMARY KEY  AUTOINCREMENT," +
                "title text," +
                "description text," +
                "timestamp text)");
    }

    public void deleteAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TblName); //delete all rows in a table
        db.close();
    }

    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("Drop table if exists "+TblName+" ");
        onCreate(db);
    }

    public int getTotalCount() {
        return (int) DatabaseUtils.queryNumEntries(getWritableDatabase(), TblName);
    }

/*    public int checkAvailable(String id) {
        int size = 0;
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM cartitems WHERE id=" + id, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            String data = cursor.getString(cursor.getColumnIndex(count));
            Log.e(count, "SqliteCart" + data);
            size = Integer.parseInt(data);
        }
        cursor.close();
        return size;
    }*/


    public ArrayList<HashMap<String, String>> getNotifications() {
        Cursor c = null;
        try {
            arrayList.clear();
            c = getReadableDatabase().rawQuery("select * from "+TblName+" ORDER by id DESC", null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
//                int size = checkAvailable(c.getString(c.getColumnIndex(Id)));
                HashMap<String, String> data = new HashMap<>();
                data.put("id", c.getString(c.getColumnIndex(Id)));
                data.put("title", c.getString(c.getColumnIndex(title)));
                data.put("message", c.getString(c.getColumnIndex(message)));
//                data.put("size", "" + size);

                arrayList.add(data);
                c.moveToNext();
            }
            c.close();
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            c.close();
        }
    }

    public void addValues(String Message, String Title) {
        try {
//            int size = checkAvailable(id);
            ContentValues cv;
//            if (size == 0) {
                cv = new ContentValues();
//                cv.put(Id, id);
                cv.put(message, Message);
                cv.put(title, Title);
//                cv.put(timestamp, Timestamp);
//                cv.put(count, "1");
                getWritableDatabase().insert(TblName, null, cv);
                return;
//            }
           /* SQLiteDatabase db = getWritableDatabase();
            size++;
            cv = new ContentValues();
//            cv.put(Id, id);
            cv.put(title, Title);
            cv.put(description, Description);
            cv.put(service_id, Service_id);
            cv.put(type, Type);
            cv.put(timestamp, Timestamp);
            cv.put(count, Integer.valueOf(size));
            db.update(TblName, cv, "id=" + id, null);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}