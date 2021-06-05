package fr.gsb.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AgendaAdapter extends BaseAdapter {

    private Context context;
    private List<Agenda> datas;

    public AgendaAdapter(Context content, List<Agenda> datas) {
        this.context = content;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return this.datas.size(); //method size retourne le nb d'élements (int)
    }

    @Override
    public Object getItem(int position) {
        return this.datas.get(position);
    }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.visitor_agenda_row, null);
        }

        TextView tv_date = convertView.findViewById(R.id.tv_row_date);
        TextView tv_user = convertView.findViewById(R.id.tv_row_user);

        Agenda current = (Agenda) getItem(position);

        tv_date.setText(String.format("%1$td-%1$tm-%1$tY", current.getRdv()));
        tv_user.setText(current.getUserName());

        return convertView;
    }
}
