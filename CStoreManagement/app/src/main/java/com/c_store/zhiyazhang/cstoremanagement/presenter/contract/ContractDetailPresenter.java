package com.c_store.zhiyazhang.cstoremanagement.presenter.contract;

import android.os.Handler;

import com.c_store.zhiyazhang.cstoremanagement.bean.UserBean;
import com.c_store.zhiyazhang.cstoremanagement.model.MyListener;
import com.c_store.zhiyazhang.cstoremanagement.model.contractDetail.ContractDetailInterface;
import com.c_store.zhiyazhang.cstoremanagement.model.contractDetail.ContractDetailModel;
import com.c_store.zhiyazhang.cstoremanagement.utils.ConnectionDetector;
import com.c_store.zhiyazhang.cstoremanagement.view.interfaceview.ContractDetailView;

/**
 * Created by zhiya.zhang
 * on 2017/5/12 11:05.
 */

public class ContractDetailPresenter {
    private ContractDetailInterface anInterface;
    private ContractDetailView cView;
    private Handler mHandler = new Handler();
    private ConnectionDetector cd = ConnectionDetector.getConnectionDetector();

    public ContractDetailPresenter(ContractDetailView cv) {
        this.cView = cv;
        this.anInterface = new ContractDetailModel();
    }

    public void updateCB() {
        if (!cd.isOnline()){
            cView.hideLoading();
            return;
        }
        cView.showLoading();
        anInterface.updateCB(UserBean.getUser(), cView.getNowContractBean(), new MyListener() {
            @Override
            public void contractSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        cView.toActivity();
                        cView.hideLoading();
                    }
                });
            }

            @Override
            public void contractSuccess(Object object) {

            }

            @Override
            public void contractFailed(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        cView.hideLoading();
                        cView.showFailedError(errorMessage);
                    }
                });
            }
        });
    }
}
