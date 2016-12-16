package mobile.com.prototype_socialapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobile.com.prototype_socialapp.Bean.BoardTable;
import mobile.com.prototype_socialapp.R;

/**
 * Created by SeungJun on 2016. 11. 28..
 */
public class MainRecycleviewAdapter extends RecyclerView.Adapter<MainRecycleviewAdapter.ViewHolder> {

    Context context;
    List<BoardTable> items;

    public MainRecycleviewAdapter(Context context, List<BoardTable> items) {
        this.context=context;
        this.items=items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recycle,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final BoardTable item=items.get(position);

        String role="";
        switch (item.getUser_role()){
            case ""+0:
                role="개발자";
                break;
            case ""+1:
                role="디자이너";
                break;
            case ""+2:
                role="기획자";
                break;
            case ""+3:
                role="배우";
                break;
            case ""+4:
                role="카메라감";
                break;
            case ""+5:
                role="조명감독";
                break;
            case ""+6:
                role="애니메이터";
                break;
            case ""+7:
                role="작가";
                break;
            case ""+8:
                role="감독";
                break;
            case ""+9:
                role="성우";
                break;
        }

        holder.title.setText("[ "+role+" ] "+item.getTitle());
        holder.comment_num.setText(item.getNum_of_comment());

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setItems(List<BoardTable> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView comment_num;

        public ViewHolder(View itemView) {

            super(itemView);

            title=(TextView)itemView.findViewById(R.id.main_list_title);
            comment_num = (TextView)itemView.findViewById(R.id.main_list_comment);
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

}
