package mobile.com.prototype_socialapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobile.com.prototype_socialapp.Bean.BoardTable;
import mobile.com.prototype_socialapp.Bean.Comment;
import mobile.com.prototype_socialapp.R;

/**
 * Created by SeungJun on 2016. 12. 4..
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<Comment> items;

    public CommentAdapter(Context context, List<Comment> items) {
        this.context=context;
        this.items=items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Comment item=items.get(position);

        holder.user_name.setText(item.getUser_name());
        holder.comment_content.setText(item.getComment_content());

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setItems(List<Comment> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name;
        TextView comment_content;

        public ViewHolder(View itemView) {

            super(itemView);

            user_name=(TextView)itemView.findViewById(R.id.comment_name);
            comment_content = (TextView)itemView.findViewById(R.id.comment_content);
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

}
