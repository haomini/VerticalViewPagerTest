package fr.castorflex.android.verticalviewpager.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouhao
 * @since 2019/03/07
 */
public class MainFragment extends Fragment {

    private List<String> mListDatas = new ArrayList<>();

    private SmartRefreshLayout mSmartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        mListDatas.addAll(getTestList());
        recyclerView.setAdapter(new CommonAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, mListDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(android.R.id.text1, s);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSmartRefreshLayout = view.findViewById(R.id.srl);
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSmartRefreshLayout.finishLoadMore();
                        mListDatas.addAll(getTestList());
                        recyclerView.getAdapter().notifyDataSetChanged();
                        mSmartRefreshLayout.setEnableLoadMore(false);
                    }
                }, 2000);
            }
        });

    }


    public List<String> getTestList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }
}
