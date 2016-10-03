package com.zaozao.vultrManager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zaozao.vultrManager.R;
import com.zaozao.vultrManager.data.AccountInfo;
import com.zaozao.vultrManager.data.Instance;
import com.zaozao.vultrManager.utils.SharedPreferenceHelper;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqisheng on 14/11/15.
 */
public class InfoFragment extends Fragment {
    @InjectView(R.id.label)
    TextView label;
    @InjectView(R.id.run_status)
    TextView runStatus;
    @InjectView(R.id.main_ip)
    TextView mainIp;
    @InjectView(R.id.os)
    TextView os;
    @InjectView(R.id.Bandwidth_Usage)
    TextView bandwidth;
    @InjectView(R.id.Initial_Password)
    TextView password;
    @InjectView(R.id.Initial_Username)
    TextView username;
    @InjectView(R.id.Memory)
    TextView memory;
    @InjectView(R.id.CPU_Count)
    TextView cpuCount;
    @InjectView(R.id.Storage)
    TextView storage;
    @InjectView(R.id.charge)
    TextView charge;
    @InjectView(R.id.balance)
    TextView balance;

    Instance instance;
    SharedPreferenceHelper mSpHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.inject(this, view);
        mSpHelper = new SharedPreferenceHelper(getActivity());
        return view;
    }

    public void setServerInfo(Instance instance) {
        this.instance = instance;
        refresh();
    }

    public void refresh() {
        label.setText(instance.getLabel());
        runStatus.setText(instance.getPowerStatus());
        mainIp.setText(instance.getIp());
        os.setText(instance.getOs());
        charge.setText(instance.getPendingCharges() + " $");
        bandwidth.setText(String.format("%s Gb used of %s Gb", instance.getCurrentBandwidth(), instance.getAllowedBandwidth()));
        password.setText(instance.getDefaultPassword());
        memory.setText(instance.getRam());
        cpuCount.setText(instance.getCpuCount());
        storage.setText(instance.getDisk());

        String str = mSpHelper.getKeyStr("account_info");
        balance.setText(null);
        if (!TextUtils.isEmpty(str)) {
            AccountInfo info = JSON.parseObject(str, AccountInfo.class);
            if (null != info) {
                balance.setText(info.getBalance() + " $");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (instance != null) {
            refresh();
        }
    }
}
