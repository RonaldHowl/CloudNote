package com.example.ronaldhowl.cloudnote.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ronaldhowl.cloudnote.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * 文本页面
 * Created by RonaldHowl on 2018/1/31.
 */

@ContentView(R.layout.fragment_note)
public class NoteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        initEvent();
        return view;}

    private void initEvent() {
    }
}
