package sunpointed.lqy.dicttest.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by lqy on 16/5/26.
 */
public class NetUtils {

    public interface ItemsService {
        @GET("/rr/index_word.php")
        Call<ResponseBody> getItems();
    }

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://91dict.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public static ItemsService itemsService = retrofit.create(ItemsService.class);

    public interface Img{
        @GET(" ")
        Call<ResponseBody> getImg();
    }

    public static void getImage(String url, final ImageView iv){
        Retrofit r = new Retrofit.Builder().baseUrl(url).build();
        Img img = r.create(Img.class);
        Call<ResponseBody> call = img.getImg();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    if(response.body() == null){
                        return;
                    }
                    InputStream is= response.body().byteStream();
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    byte[] bf = new byte[1024];
                    byte[] bmpBf;
                    int len = 0;
                    while( (len=is.read(bf)) != -1){
                        os.write(bf, 0, len);
                    }
                    os.close();
                    is.close();
                    bmpBf=os.toByteArray();
                    Bitmap b = BitmapFactory.decodeByteArray(bmpBf, 0, bmpBf.length);

//                    byte[] data = getBytes(response.body().byteStream());
//                    Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length);

//                    InputStream is = response.body().byteStream();
//                    Bitmap b = BitmapFactory.decodeStream(response.body().byteStream());
//                    is.close();
//                    if(response.isSuccess()) {
//                    }
                    iv.setImageBitmap(b);
//                    iv.setImageResource(R.drawable.abc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // 用数据装
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        return outstream.toByteArray();
    }

}
