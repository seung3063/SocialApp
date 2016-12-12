package mobile.com.prototype_socialapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobile.com.prototype_socialapp.Bean.Comment;
import mobile.com.prototype_socialapp.Bean.Notice;
import mobile.com.prototype_socialapp.R;

/**
 * Created by SeungJun on 2016. 12. 4..
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    Context context;
    List<Notice> items;

    public NoticeAdapter(Context context, List<Notice> items) {
        this.context=context;
        this.items=items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Notice item=items.get(position);

        holder.notice_content.setText(item.getUser_name()+"님이 댓글을 달았습니다");

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setItems(List<Notice> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView notice_content;


        public ViewHolder(View itemView) {

            super(itemView);

            notice_content=(TextView)itemView.findViewById(R.id.notice_content);

        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

}
