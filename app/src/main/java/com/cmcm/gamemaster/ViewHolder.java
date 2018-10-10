package com.cmcm.gamemaster;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cmcm.glide370.R;

class ViewHolder {
    private ImageView img;

    void bindView(View convertView) {
        img = (ImageView) convertView.findViewById(R.id.game_icon);
    }

    void bindData(Activity activity, Item sample) {
        if (sample.icon != null) {
            img.setImageDrawable(new BitmapDrawable(sample.icon));
            Log.d("vintst", "show with bitmap");
            return;
        }

        Log.d("vintst", "show with glide");
        Glide.with(activity).load(sample.file.getAbsolutePath()).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).into(new GlideDrawableImageViewTarget(img) {
            @Override
            public void onLoadCleared(Drawable placeholder) {
                // prevent set A null place holder cause ImageView change with not sense
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                // prevent set A null place holder cause ImageView change with not sense
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                // prevent set A null place holder cause ImageView change with not sense
            }
        });
    }
}
