package mobile.com.prototype_socialapp.Singleton;

import android.graphics.Bitmap;
import android.content.Context;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {
    private static VolleySingleton mInstance=null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public VolleySingleton(Context context){
        mRequestQueue= Volley.newRequestQueue(context);

        mImageLoader=new ImageLoader(this.mRequestQueue,new ImageLoader.ImageCache(){
            private final LruCache mCache=new LruCache(10);
            public void putBitmap(String url, Bitmap bitmap){
                mCache.put(url,bitmap);
            }
            public Bitmap getBitmap(String url){
                return (Bitmap)mCache.get(url);
            }
        });
    }

    public static VolleySingleton getmInstance(Context context){
        if(mInstance==null){
            mInstance=new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }
}
