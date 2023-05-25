package com.example.agroapp.http.base;


import com.example.agroapp.R;
import com.example.agroapp.http.HttpCode;
import com.google.gson.JsonParseException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.ResourceObserver;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.ToastUtils;
import retrofit2.HttpException;

/**
 * Observer的基类
 *
 * @param <T> 具体的网络请求响应model
 */
public abstract class BaseObserver<T> extends ResourceObserver<BaseResponse<T>> implements ObserverX<T> {

 

    private boolean mShowError;

    public BaseObserver() {

    }

 

    @Override
    public final void onNext(BaseResponse<T> baseResponse) {
        int errorCode = baseResponse.getErrorCode();
        if (errorCode == HttpCode.SUCCESS) {
            // 服务端返回成功
            onNextX(baseResponse.getData());
        } else {
            String errorMsg = baseResponse.getErrorMsg();
            ToastUtils.showShort(errorMsg);



           // Logger.i("errorCode: %d, errorMsg: %s", errorCode, errorMsg);

            // 服务端返回错误
            onErrorX(errorCode, errorMsg);
        }
    }

    @Override
    public final void onError(Throwable e) {
        int errorCode = HttpCode.ERROR_UNKNOWN;
        String errorMsg = e.getMessage();

        // 公共错误逻辑处理
        if (e instanceof HttpException) {
            errorCode = HttpCode.ERROR_HTTP;
            ToastUtils.showShort(R.string.error_http);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            errorCode = HttpCode.ERROR_NETWORK;
            ToastUtils.showShort(R.string.error_network);

        } else if (e instanceof JsonParseException || e instanceof JSONException) {
            errorCode = HttpCode.ERROR_PARSE;
            ToastUtils.showShort(R.string.error_parse);

        } else if (e instanceof SSLHandshakeException) {
            errorCode = HttpCode.ERROR_SSL;
            ToastUtils.showShort(R.string.error_ssl);

        } else {
            ToastUtils.showShort(R.string.error_unknown);

        }

        com.orhanobut.logger.Logger.i("errorCode: %d, errorMsg: %s", errorCode, errorMsg);

        onErrorX(errorCode, errorMsg);
    }

    @Override
    public final void onComplete() {
        onCompleteX();
    }

    @Override
    public void onErrorX(int errorCode, String errorMsg) {

    }

    @Override
    public void onCompleteX() {

    }
}
