package com.fmblzf.mvp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fmblzf.mvp.base.inter.IAction;
import com.fmblzf.mvp.model.entry.NewInfo;
import com.fmblzf.mvp.presenter.INewInfoPrecenter;
import com.fmblzf.mvp.presenter.impl.NewInfoPrecenter;
import com.fmblzf.mvp.view.IProgressView;
import com.fmblzf.mvp.view.NewFragment;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private NewFragment newFragment = new NewFragment();

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        //添加Fragment
        addFragment(newFragment);
        super.onCreate(savedInstanceState);
    }

    private void addFragment(Fragment fragment) {
        if (fragmentManager == null){
            fragmentManager = getFragmentManager();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_content,fragment).commit();
    }
}
