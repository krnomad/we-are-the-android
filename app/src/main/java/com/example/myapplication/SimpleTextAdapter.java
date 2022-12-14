package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
    private final List<String> mData;

    @IntDef({VIEW_TEXT, VIEW_VTN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {}

    public static final int VIEW_TEXT = 1;
    public static final int VIEW_VTN = 2;

    public interface OnItemClickListener {
        void onItemClick(@ViewType int viewType, int position);
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    private OnItemClickListener mListener = null;

    public SimpleTextAdapter(List<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        SimpleTextAdapter.ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button itemBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text1);
            itemBtn = itemView.findViewById(R.id.item_btn);

            textView.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION ) {
                    if ( mListener != null ) {
                        mListener.onItemClick(VIEW_TEXT, pos);
                    }
                }
            });

            itemBtn.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION ) {
                    if ( mListener != null ) {
                        mListener.onItemClick(VIEW_VTN, pos);
                    }
                }
            });
        }
    }
}
