package com.example.calendar;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class JumpActivity extends AppCompatActivity {

    private RecyclerView mInstalledRv;
    private List<ApplicationInfo> packageList = new ArrayList<>();
    private EditText mReceiverTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);

        mReceiverTv = (EditText) findViewById(R.id.m_receiver_tv);

//        if (getIntent().getExtras() != null) {
//            String str = getIntent().getExtras().getString("TAG");
//            Log.d("picher", "数据：" + str);
//            mReceiverTv.setText(str);
//        }

        mInstalledRv = (RecyclerView) findViewById(R.id.m_installed_apk);
        mInstalledRv.setLayoutManager(new LinearLayoutManager(this));
        mInstalledRv.setAdapter(new InstalledAdapter());
        //getInstalledPak();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReceiverTv.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(mReceiverTv, InputMethodManager.SHOW_FORCED);
            }
        });


    }

    private void getInstalledPak() {
        // 后台加载下应用列表
        List<ApplicationInfo> listPack = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

        List<ApplicationInfo> removedItems = new ArrayList<>();

        String selfPackage = getPackageName();
        //boolean displaySystemApp = SPService.getBoolean(SPService.KEY_DISPLAY_SYSTEM_APP, false);

        for (ApplicationInfo pack : listPack) {
            if ((pack.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                removedItems.add(pack);
            }

//            // 移除自身
//            if (StringUtil.equals(selfPackage, pack.packageName)) {
//                removedItems.add(pack);
//            }
        }
        // listPack.removeAll(removedItems);
        Log.d("picher", "removeItem:" + removedItems.size());

        // 排序下
        final Comparator c = Collator.getInstance(Locale.CHINA);
        Collections.sort(listPack, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo o1, ApplicationInfo o2) {
                String n1 = o1.loadLabel(getPackageManager()).toString();
                String n2 = o2.loadLabel(getPackageManager()).toString();
                return c.compare(n1, n2);
            }
        });

        packageList.clear();
        packageList.addAll(listPack);
    }

    public class InstalledAdapter extends RecyclerView.Adapter<InstalledAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.installed_item, null, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ApplicationInfo item = packageList.get(position);
            holder.mItemTv.setText(item.packageName);
            Log.d("picher", "pos:" + position + ">>imgPath:package:" + item.packageName);
        }

        @Override
        public int getItemCount() {
            return packageList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mItemIv;
            TextView mItemTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mItemIv = (ImageView) itemView.findViewById(R.id.m_item_iv);
                mItemTv = (TextView) itemView.findViewById(R.id.m_item_tv);
            }
        }
    }
}
