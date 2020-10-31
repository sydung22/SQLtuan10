package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView listViewmh;
    ArrayList<monhoc> arrayList;
    monhocadapter monhocadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewmh = (ListView) findViewById(R.id.listviewmh);
        arrayList = new ArrayList<>();
        monhocadapter = new monhocadapter(this, R.layout.listmonhoc, arrayList);
        listViewmh.setAdapter(monhocadapter);
        //tạo database
        database = new Database(this,"testthu.sqlite",null,1);
        // tạo bảng test bởi vì mới học mà kaka.
        database.QueryData("CREATE TABLE IF NOT EXISTS hocne(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenMH VARCHAR(100))");
        getDatamh();

    }
    public void removeconfim(final String mh, final int id){
        AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setTitle("Thông Báo!");
        aler.setMessage("Bạn có chắc chắn xóa môn học "+mh+"?");
        aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM hocne WHERE Id='"+ id +"'");
                Toast.makeText(MainActivity.this,"Đã xóa thành công "+ mh,Toast.LENGTH_SHORT).show();
                getDatamh();
            }
        });
        aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        aler.setIcon(R.mipmap.ic_android);
        aler.show();
    }
    public void adddiaglogrepair(String mh, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogrepair);
        final EditText editTextmh = (EditText) dialog.findViewById(R.id.edittextupdate);
        editTextmh.setText(mh);
        Button btncapnhap = (Button) dialog.findViewById(R.id.btncapnhap);
        Button btncancel = (Button) dialog.findViewById(R.id.btnhuybo);
        btncapnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mhnew = editTextmh.getText().toString().trim();
                database.QueryData("UPDATE hocne SET TenMH = '"+mhnew+"' WHERE Id='"+ id +"'");
                Toast.makeText(MainActivity.this, "Cập Nhập Thành Công", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                getDatamh();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void getDatamh(){
        // select data
        Cursor datamh = database.GetData("select * from hocne");
        arrayList.clear();
        while (datamh.moveToNext()){
            String t = datamh.getString(1);
            int id = datamh.getInt(0);
            arrayList.add(new monhoc(id,t));
        }
        monhocadapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmonhoc, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnthemmhmenu){
            addialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogaddmh);
        final EditText editTextmh = (EditText) dialog.findViewById(R.id.edittextadd);
        Button btnadd = (Button) dialog.findViewById(R.id.btnThem);
        Button btnclose = (Button) dialog.findViewById(R.id.btnhuy);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mh = editTextmh.getText().toString();
                if (mh.equals("")){
                    Toast.makeText(MainActivity.this,"Vui Lòng Nhập Tên Môn Học",Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO hocne VALUES (null, '"+ mh +"')");
                    Toast.makeText(MainActivity.this,"Đã Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatamh();
                }
            }
        });
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}