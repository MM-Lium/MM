package com.example.a0912;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class money2 extends Fragment {

    DBService myDb;
    private ListView lv_note2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.activity_money2, container, false);

        myDb = new DBService(getActivity());

        lv_note2 = rootView.findViewById(R.id.lv_note2);
        List<Values> valuesList = new ArrayList<>();
        SQLiteDatabase db = myDb.getReadableDatabase();

        Cursor cursor = db.query(DBService.TABLE_4, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            Values values;
            while (!cursor.isAfterLast()) {

                values = new Values();

                values.setId4((Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBService.ID4)))));
                values.setItitle(cursor.getString(cursor.getColumnIndex(DBService.TITLE)));
                values.setIcontent(cursor.getString(cursor.getColumnIndex(DBService.CONTENT)));
                values.setItime((cursor.getString(cursor.getColumnIndex(DBService.TIME))));
                values.setIyear(cursor.getString(cursor.getColumnIndex(DBService.YEAR)));
                values.setIday(cursor.getString(cursor.getColumnIndex(DBService.DAY)));
                values.setIday2(cursor.getString(cursor.getColumnIndex(DBService.DAY2)));
                values.setIpapa(cursor.getString(cursor.getColumnIndex(DBService.PAPA)));
                values.settype(cursor.getString(cursor.getColumnIndex(DBService.TYPE)));
                values.setImoney(cursor.getString(cursor.getColumnIndex(DBService.MONEY)));
                values.setImoney2(cursor.getString(cursor.getColumnIndex(DBService.LASTMONEY)));
                values.setIname(cursor.getString(cursor.getColumnIndex(DBService.NAME)));
                values.setInum(cursor.getString(cursor.getColumnIndex(DBService.NUM)));
                values.settotal(cursor.getString(cursor.getColumnIndex(DBService.TOTAL)));

                valuesList.add(values);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        final MyBaseAdapter myBaseAdapter = new MyBaseAdapter(valuesList, getActivity(), R.layout.money_item);
        lv_note2.setAdapter(myBaseAdapter);

        lv_note2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), money2_showedit.class);

                Values values = (Values) lv_note2.getItemAtPosition(position);
                intent.putExtra(DBService.TITLE, values.getItitle().trim());
                intent.putExtra(DBService.MONEY, values.getImoney().trim());
                intent.putExtra(DBService.DAY, values.getIday().trim());
                intent.putExtra(DBService.DAY2, values.getIday2().trim());
                intent.putExtra(DBService.PAPA, values.getIpapa().trim());
                intent.putExtra(DBService.TYPE, values.gettype().trim());
                intent.putExtra(DBService.LASTMONEY, values.getImoney2().trim());
                intent.putExtra(DBService.CONTENT, values.getIcontent().trim());
                intent.putExtra(DBService.NUM, values.getInum().trim());
                intent.putExtra(DBService.NAME, values.getIname().trim());
                intent.putExtra(DBService.TOTAL, values.gettotal().trim());
                intent.putExtra(DBService.ID4, values.getId4().toString().trim());
                startActivity(intent);
            }
        });

        lv_note2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // TODO Auto-generated method stub
                final Values values = (Values) lv_note2.getItemAtPosition(position);
                new AlertDialog.Builder(getActivity())
                        .setItems(new String[]{"刪除", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    SQLiteDatabase db = myDb.getWritableDatabase();
                                    db.delete(DBService.TABLE_4, DBService.ID4 + "=?", new String[]{String.valueOf(values.getId4())});
                                    db.close();
                                    myBaseAdapter.removeItem(position);
                                    lv_note2.post(new Runnable() {
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
            money2.ViewHolder viewHolder2;
            LayoutInflater inflater = getActivity().getLayoutInflater();
//            if (convertView == null) {
//                convertView = layoutInflater.inflate(R.layout.money_item2, parent,
//                        false);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.money_item, parent,
                        false);
                viewHolder2 = new money2.ViewHolder();
                viewHolder2.Ititle = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder2.name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder2.Iday = (TextView) convertView.findViewById(R.id.tv_day);
                viewHolder2.Imoney = (TextView) convertView.findViewById(R.id.tv_money);

                convertView.setTag(viewHolder2);
            } else {
                viewHolder2 = (money2.ViewHolder) convertView.getTag();
            }
            String Ititle = valuesList.get(position).getItitle();
            String Imoney = valuesList.get(position).getImoney();
            String Iday = valuesList.get(position).getIday2();
            String name = valuesList.get(position).getIname();
            viewHolder2.Ititle.setText(Ititle);
            viewHolder2.Imoney.setText(Imoney);
            viewHolder2.Iday.setText(Iday);
            viewHolder2.name.setText(name);
            return convertView;
        }

        public void removeItem(int position) {
            this.valuesList.remove(position);
        }
    }

    public class ViewHolder {
        TextView Ititle;
        TextView Iday;
        TextView Imoney;
        TextView name;
    }
}

