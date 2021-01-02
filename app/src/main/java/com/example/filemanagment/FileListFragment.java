package com.example.filemanagment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListFragment extends Fragment implements FileAdapter.FileItemEventListener{
    private String path;
    private FileAdapter fileAdapter;
    private RecyclerView recyclerView;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files , container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.VERTICAL ,false));

        TextView tvFilesPath= view.findViewById(R.id.tv_files_path);
        tvFilesPath.setText(path);


        View back = view.findViewById(R.id.iv_files_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        File currentFolder = new File(path);
        List<File> files = new ArrayList<>();
        for (int i = 0; i < currentFolder.listFiles().length; i++)
            files.add(currentFolder.listFiles()[i]);

        fileAdapter = new FileAdapter(files,this,getContext());
        recyclerView.setAdapter(fileAdapter);

        return view;
    }

    @Override
    public void onFileItemClick(File file) {
       if(file.isDirectory())
           ((MainActivity)getActivity()).listFiles(file.getPath());

    }

    @Override
    public void onDeleteItemClick(File file) {
        if(file.exists())
            if(file.delete())
                fileAdapter.removeItem(file);
    }

    public void createNewFolder(String folderName){
        File newFolder = new File(path+File.separator+folderName);
        if(!newFolder.exists()){
            if(newFolder.mkdir()){
                fileAdapter.addFile(newFolder);
                recyclerView.smoothScrollToPosition(0);
            }
        }
    }
}
