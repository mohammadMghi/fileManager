package com.example.filemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> implements PopupMenu.OnMenuItemClickListener {
    private List<File> files;
    private FileItemEventListener fileItemEventListener;
    private Context context;
    private File onClickFile;
    public FileAdapter(List<File> files, FileItemEventListener fileItemEventListener, Context context){
        this.files = files;

        this.fileItemEventListener = fileItemEventListener;
        this.context = context;
    }
    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_file , parent , false
        );
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.bindFile(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }



    public class FileViewHolder extends RecyclerView.ViewHolder{
        private TextView fileNameTv;
        private ImageView fileIconIv;
        private ImageView ivMore;
        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTv = itemView.findViewById(R.id.tv_file_name);
            fileIconIv = itemView.findViewById(R.id.iv_file);
            ivMore = itemView.findViewById(R.id.iv_file_more);

        }

        public void bindFile(File file){
            if(file.isDirectory()){
                fileIconIv.setImageResource(R.drawable.ic_baseline_folder_24);
            }else
                fileIconIv.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);

            fileNameTv.setText(file.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fileItemEventListener.onFileItemClick(file);
                }
            });

            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickFile = file;
                    showPopup(v);
                }
            });
        }

    }

    public void addFile(File file){
        files.add(0 , file);
        notifyItemChanged(0);
    }


    public void removeItem(File file){
        int index = files.indexOf(file);
        if(index > -1){
            files.remove(index);
            notifyItemChanged(index);
        }

    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_file_item, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_delete:
                fileItemEventListener.onDeleteItemClick(onClickFile);
                break;
            case R.id.menuItem_copy:

                break;
            case R.id.menuItem_move:

                return true;
            default:
                return false;
        }
        return false;
    }


    public interface FileItemEventListener{
        void onFileItemClick(File file);
        void onDeleteItemClick(File file);
    }
}
