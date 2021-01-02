package com.example.filemanagment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AddNewFolderDialog.AddNewFolderCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        File file = getExternalFilesDir(null);
        String path = file.getPath();

        listFiles(path,false);

        View addNewFolder = findViewById(R.id.iv_main_addNewFolder);
        addNewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewFolderDialog().show(getSupportFragmentManager(),null);
            }
        });

    }

    public void listFiles(String path,boolean addToBackStack){
        FileListFragment fileListFragment = new FileListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path" , path);
        fileListFragment.setArguments(bundle);


        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        if(addToBackStack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.replace(R.id.frame_main_fragmentContainer , fileListFragment);
        fragmentTransaction.commit();

    }
    public void listFiles(String path){
        this.listFiles(path,true);
    }

    @Override
    public void onCreateFolderButtonClick(String folderName) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main_fragmentContainer);
        if(fragment instanceof FileListFragment){
            ((FileListFragment) fragment).createNewFolder(folderName);
        }
    }
}