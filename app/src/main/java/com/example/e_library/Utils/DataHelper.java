package com.example.e_library.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.e_library.Model.ModelBokkmark;
import com.example.e_library.Model.ModelFavorit;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "elibrary.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table favorit(id integer primary key, cover text null, content text null, judul text null, deskripsi text null);";
        Log.d("Data", "onCreate: " + sql);
        sqLiteDatabase.execSQL(sql);
//        sql = "INSERT INTO favorit (id, cover, content, judul, deskripsi) VALUES ('1', 'Darsiwan', '1996-07-12', 'Laki-laki','Indramayu');";
//        sqLiteDatabase.execSQL(sql);

        String sql2 = "create table bookmarks(id integer primary key, cover text null, content text null, judul text null, deskripsi text null);";
        Log.d("Data", "onCreate: " + sql2);
        sqLiteDatabase.execSQL(sql2);
//        sql = "INSERT INTO bookmarks (id, cover, content, judul, deskripsi) VALUES ('1', 'Darsiwan', '1996-07-12', 'Laki-laki','Indramayu');";
//        sqLiteDatabase.execSQL(sql);

    }

    // FUNGSI UNTUK AMBIL SEMUA DATA MAHASISWA
    public List<ModelFavorit> getFavoritList(){
        List<ModelFavorit> modelFavoritList = new ArrayList<>();
        modelFavoritList.clear();
        String selectQuery = "SELECT * FROM favorit";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                ModelFavorit modelFavorit = new ModelFavorit();
                modelFavorit.setId(cursor.getInt(0));
                modelFavorit.setCover(cursor.getString(1));
                modelFavorit.setContent(cursor.getString(2));
                modelFavorit.setJudul(cursor.getString(3));
                modelFavorit.setDeskripsi(cursor.getString(4));
                modelFavoritList.add(modelFavorit);
            } while (cursor.moveToNext());
        }
        return modelFavoritList;
    }

    public List<ModelFavorit> getFavoritListCari(String judul){
        List<ModelFavorit> modelFavoritList = new ArrayList<>();
        modelFavoritList.clear();
        String selectQuery = "SELECT * FROM favorit WHERE judul LIKE '%"+judul+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                ModelFavorit modelFavorit = new ModelFavorit();
                modelFavorit.setId(cursor.getInt(0));
                modelFavorit.setCover(cursor.getString(1));
                modelFavorit.setContent(cursor.getString(2));
                modelFavorit.setJudul(cursor.getString(3));
                modelFavorit.setDeskripsi(cursor.getString(4));
                modelFavoritList.add(modelFavorit);
            } while (cursor.moveToNext());
        }
        return modelFavoritList;
    }

    // FUNGSI MENGHITUNG ADA BEBERAPA DATA
    public int getIdCount(String id){
        String countQuery = "SELECT * FROM favorit WHERE id = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        return cursor.getCount();
    }

    // FUNGSI HAPUS DATA 1 MAHASISWA
    public void hapusDataFavorit(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favorit", "id = ?",
                new String[]{id});
//        db.close();
    }

    public List<ModelBokkmark> getBookmarkList(){
        List<ModelBokkmark> modelBokkmarkList = new ArrayList<>();
        modelBokkmarkList.clear();
        String selectQuery = "SELECT * FROM bookmarks";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                ModelBokkmark modelBokkmark = new ModelBokkmark();
                modelBokkmark.setId(cursor.getInt(0));
                modelBokkmark.setCover(cursor.getString(1));
                modelBokkmark.setContent(cursor.getString(2));
                modelBokkmark.setJudul(cursor.getString(3));
                modelBokkmark.setDeskripsi(cursor.getString(4));
                modelBokkmarkList.add(modelBokkmark);
            } while (cursor.moveToNext());
        }
        return modelBokkmarkList;
    }

    // FUNGSI MENGHITUNG ADA BEBERAPA DATA
    public int getIdCountBookmark(String id){
        String countQuery = "SELECT * FROM bookmarks WHERE id = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        return cursor.getCount();
    }

    // FUNGSI HAPUS DATA 1 MAHASISWA
    public void hapusDataBookmark(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("bookmarks", "id = ?",
                new String[]{id});
//        db.close();
    }

    public List<ModelBokkmark> getBookmarkListCari(String judul){
        List<ModelBokkmark> modelBokkmarkList = new ArrayList<>();
        modelBokkmarkList.clear();
        String selectQuery = "SELECT * FROM bookmarks WHERE judul LIKE '%"+judul+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                ModelBokkmark modelBokkmark = new ModelBokkmark();
                modelBokkmark.setId(cursor.getInt(0));
                modelBokkmark.setCover(cursor.getString(1));
                modelBokkmark.setContent(cursor.getString(2));
                modelBokkmark.setJudul(cursor.getString(3));
                modelBokkmark.setDeskripsi(cursor.getString(4));
                modelBokkmarkList.add(modelBokkmark);
            } while (cursor.moveToNext());
        }
        return modelBokkmarkList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
