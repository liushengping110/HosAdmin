package wizrole.hosadmin.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getcompay.CompayList;

/**
 * Created by 何人执笔？ on 2018/4/25.
 * liushengping
 * 公司管理列表
 */

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.CompantViewHolder>{
    public List<CompayList> compayLists;
    public CompanyListAdapter(List<CompayList> compayLists){
        this.compayLists=compayLists;
    }

    @Override
    public CompantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_company_item,null));
    }

    @Override
    public void onBindViewHolder(CompantViewHolder holder, int position) {
        holder.text_name.setText(compayLists.get(position).getCompayName());
        holder.text_address.setText(compayLists.get(position).getCompayAddress());
    }

    @Override
    public int getItemCount() {
        return compayLists.size();
    }

    public class CompantViewHolder extends RecyclerView.ViewHolder{
        TextView text_name;
        TextView text_address;
        public CompantViewHolder(View itemView) {
            super(itemView);
            text_name=(TextView)itemView.findViewById(R.id.text_name);
            text_address=(TextView)itemView.findViewById(R.id.text_address);
        }
    }
}
