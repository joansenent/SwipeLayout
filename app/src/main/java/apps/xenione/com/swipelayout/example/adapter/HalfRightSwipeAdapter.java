package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import apps.xenione.com.swipelayout.example.swipe.HalfRightCoordinatorLayout;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class HalfRightSwipeAdapter extends RecyclerView.Adapter<HalfRightSwipeAdapter.ViewHolder> {

    public interface OnItemDismissListener {
        void onItemDismissed(int position);
    }

    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }

    private List<Album> mAlbums;
    private OnItemDismissListener mOnItemDismissListener;
    private OnItemSelectListener mOnItemSelectListener;

    public HalfRightSwipeAdapter(List<Album> albums) {
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_half_right_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Album album = getItem(position);
        holder.coordinatorLayout.sync();
        holder.foreground.setOnClickListener(new OnItemSelectedClick(position));
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
        holder.delete.setOnClickListener(new OnItemDismiss(position));
        ResourcesCompat.getDrawable(context.getResources(), album.getResource(), context.getTheme());
        holder.discImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), album.getResource(), context.getTheme()));
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public void deleteItem(int position) {
        mAlbums.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }


    private Album getItem(int position) {
        return mAlbums.get(position);
    }

    public void setOnItemDismissListener(OnItemDismissListener listener) {
        mOnItemDismissListener = listener;
    }

    public void setOnItemItemSelectListener(OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }

    public class OnItemDismiss implements View.OnClickListener {

        private int position;

        public OnItemDismiss(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemDismissListener != null) {
                mOnItemDismissListener.onItemDismissed(position);
            }
        }
    }

    private class OnItemSelectedClick implements View.OnClickListener{

        private int position;

        public OnItemSelectedClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemSelectListener != null) {
                mOnItemSelectListener.onItemSelected(position);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView bandName;
        public ImageView discImage;
        public View delete;
        public HalfRightCoordinatorLayout coordinatorLayout;
        public View foreground;

        public ViewHolder(View view) {
            super(view);
            coordinatorLayout = (HalfRightCoordinatorLayout) view;
            title = (TextView) view.findViewById(R.id.title);
            bandName = (TextView) view.findViewById(R.id.bandName);
            discImage = (ImageView) view.findViewById(R.id.bg_disc);
            delete= view.findViewById(R.id.backgroundView);
            foreground= view.findViewById(R.id.foregroundView);
        }
    }
}