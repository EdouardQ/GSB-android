package fr.gsb.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PractitionerAdapter extends BaseAdapter {

    private Context context;
    private List<Practitioner> datas;

    public PractitionerAdapter(Context content, List<Practitioner> datas) {
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
            convertView = inflater.inflate(R.layout.visitor_practitioners_row, null);
        }

        TextView tv_name = convertView.findViewById(R.id.tv_row_name);
        TextView tv_firstName = convertView.findViewById(R.id.tv_row_firstName);

        Practitioner current = (Practitioner) getItem(position);


        tv_name.setText(current.getName());
        tv_firstName.setText(current.getFirstName());

        return convertView;
    }
}
