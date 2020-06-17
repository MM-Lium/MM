package com.example.a0912;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class money extends Fragment {

    DBService myDb;
    private ListView lv_note;
    String[] date;
    ArrayList<String> list = new ArrayList<>();
    String str;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_money, container, false);
        myDb = new DBService(getActivity());
        lv_note = rootView.findViewById(R.id.lv_note);
        List<Values> valuesList = new ArrayList<>();
        SQLiteDatabase db = myDb.getReadableDatabase();

        //查尋資料庫資料
        Cursor cursor = db.query(DBService.TABLE_1, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            Values values;
            while (!cursor.isAfterLast()) {
                //實體化
                values = new Values();
                //把數據庫中的一個表的數據賦值给values
                values.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBService.ID))));
                values.setTitle(cursor.getString(cursor.getColumnIndex(DBService.TITLE)));
                values.setContent(cursor.getString(cursor.getColumnIndex(DBService.CONTENT)));
                values.setTime(cursor.getString(cursor.getColumnIndex(DBService.TIME)));
                values.setYear(cursor.getString(cursor.getColumnIndex(DBService.YEAR)));
                values.setYear(cursor.getString(cursor.getColumnIndex(DBService.YEAR)));
                values.setIname(cursor.getString(cursor.getColumnIndex(DBService.NAME)));
                values.settype(cursor.getString(cursor.getColumnIndex(DBService.TYPE)));
                values.setDay(cursor.getString(cursor.getColumnIndex(DBService.DAY)));
                values.setDay2(cursor.getString(cursor.getColumnIndex(DBService.DAY2)));
                values.setPa(cursor.getString(cursor.getColumnIndex(DBService.PAPA)));
                values.setMoney2(cursor.getString(cursor.getColumnIndex(DBService.LASTMONEY)));
                values.setMoney(cursor.getString(cursor.getColumnIndex(DBService.MONEY)));
                //將values對象存入list中
                valuesList.add(values);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        //設置list组件adapter
        final MyBaseAdapter myBaseAdapter = new MyBaseAdapter(valuesList, getActivity(), R.layout.money_item);
        lv_note.setAdapter(myBaseAdapter);

        //單擊修改
        lv_note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), money_showedit.class);
                Values values = (Values) lv_note.getItemAtPosition(position);
                intent.putExtra(DBService.TITLE, values.getTitle().trim());
                intent.putExtra(DBService.MONEY, values.getMoney().trim());
                intent.putExtra(DBService.DAY, values.getDay().trim());
                intent.putExtra(DBService.DAY2, values.getDay2().trim());
                intent.putExtra(DBService.TYPE, values.gettype().trim());
                intent.putExtra(DBService.NAME, values.getIname().trim());
                intent.putExtra(DBService.PAPA, values.getPa().trim());
                intent.putExtra(DBService.LASTMONEY, values.getMoney2().trim());
                intent.putExtra(DBService.CONTENT, values.getContent().trim());
                intent.putExtra(DBService.ID, values.getId().toString().trim());
                startActivity(intent);
            }
        });

        lv_note.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // TODO Auto-generated method stub
                final Values values = (Values) lv_note.getItemAtPosition(position);
                new AlertDialog.Builder(getActivity())
                        .setItems(new String[]{"刪除", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    SQLiteDatabase db = myDb.getWritableDatabase();
                                    db.delete(DBService.TABLE_1, DBService.ID + "=?", new String[]{String.valueOf(values.getId())});
                                    db.close();
                                    myBaseAdapter.removeItem(position);
                                    lv_note.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            myBaseAdapter.notifyDataSetChanged();
                                        }
                                    });
                                    //MainActivity.this.onResume();
                                } else {
                                }
                            }

                        }).show();
                return true;
            }
        });
        return rootView;
    }

    class MyBaseAdapter extends BaseAdapter {

        private List<Values> valuesList;
        private Context context;
        private int layoutId;
        private LayoutInflater layoutInflater;

        public MyBaseAdapter(List<Values> valuesList, Context context, int layoutId) {
            this.valuesList = valuesList;
            this.context = context;
            this.layoutId = layoutId;
        }

        @Override
        public int getCount() {
            if (valuesList != null && valuesList.size() > 0)
                return valuesList.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            if (valuesList != null && valuesList.size() > 0)
                return valuesList.get(position);
            else
                return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.money_item, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.day = (TextView) convertView.findViewById(R.id.tv_day);
                viewHolder.money = (TextView) convertView.findViewById(R.id.tv_money);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String title = valuesList.get(position).getTitle();
            String money = valuesList.get(position).getMoney();
            String day = valuesList.get(position).getDay2();
            String name = valuesList.get(position).getIname();
            viewHolder.title.setText(title);
            viewHolder.money.setText(money);
            viewHolder.day.setText(day);
            viewHolder.name.setText(name);
            return convertView;
        }

        public void removeItem(int position) {
            this.valuesList.remove(position);
        }
    }

    class ViewHolder {
        TextView title;
        TextView day;
        TextView money;
        TextView name;
    }
}


