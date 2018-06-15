package wizrole.hosadmin.util.http;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.security.spec.MGF1ParameterSpec.SHA1;


/**
 * Created by Administrator on 2017/2/22.
 * 网络请求
 * okhttp+rxjava
 */

public class RxJavaOkPotting {

    private Request request;
    private OkHttpClient client;
    private Call call;
    public static final String NET_ERR="net_err";
    private static RxJavaOkPotting okPotting;
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String base_url;


    private RxJavaOkPotting(String base_url) {
        this.base_url = base_url;
        client = new OkHttpClient();
        client.newBuilder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public void OnCancel() {
        call.cancel();
    }

    public static synchronized RxJavaOkPotting getInstance(String base_url) {
        if (okPotting == null) {
            okPotting = new RxJavaOkPotting(base_url);
        }
        RxJavaOkPotting.base_url = base_url;
        return okPotting;
    }

    private Request get(String url) {
        request = new Request.Builder()
                .url(base_url + url)
                .get()
                .build();
        return request;
    }

    private Request ComGet(String url) {
        request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return request;
    }

    public void ComAsk(String url, Callback callback) {
        call = client.newCall(ComGet(url));
        call.enqueue(callback);
    }

    private Request post(String url, FormBody formBody) {
        //随机数
        Random rand = new Random();
        int i = rand.nextInt(100);
        //时间戳
        String sjc=String.valueOf(System.currentTimeMillis());// 这个语句可转为以下的型式:
        String s="0ZwOSoNSnQ"+i+sjc;
        request = new Request.Builder()
                .url(base_url)
                .addHeader("App-Key","pkfcgjstpoj28")
                .addHeader("Nonce",i+"")
                .addHeader("Timestamp",sjc)
                .addHeader("Signature",sha1(s))
                .post(formBody)
                .build();
        return request;
    }



    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private Request json(String base_url, String json) {
        RequestBody formBody = new FormBody.Builder()
                .add("param", json)
                .build();
        request = new Request.Builder()
                .url(base_url)
                .post(formBody)
                .build();
        return request;
    }

    /**
     * get
     * @param url
     * @param subscriber
     */
    public void Ask(String url, Subscriber subscriber) {
        call = client.newCall(get(url));
        Asynchronous(call, subscriber);
    }

    /**
     * json
     * @param url
     * @param json
     * @param subscriber
     */
    public void Ask(String url, String json, Subscriber subscriber) {
        call = client.newCall(json(url, json));
        Asynchronous(call, subscriber);
    }

    /**
     * post 请求
     * @param url
     * @param formBody
     * @param subscriber
     */
    public void Ask(String url, FormBody formBody, Subscriber subscriber) {
        call = client.newCall(post(url, formBody));
        Asynchronous(call, subscriber);
    }


    private void Asynchronous(Call call, final Subscriber subscriber) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                subscriber.onNext(NET_ERR);
                subscriber.onCompleted();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
            }
        });
    }
}