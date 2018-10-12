
glide370android
======================

my application used glide to load bitmap from file but it seems not work as expected. see below :

![Problem](problem_show.png)

that bitmap doesn't load fully.

in my application, I have a GridView that show the game list with icon&name, first time I decode the icon from external storage my self, then I start a Thread to async load all games and refresh with **notifyDatasetChanged()**, this refresh time I let glide to load&show the game's icon by pass them a File, which the problem happend in this time. you can experience the problem by the video **problem_video.mp4** from my project.

I decide to work into the source code, ended up I found a workaround solution by added an option called [decodeByOriginalIns], so I use this to load my game's icon :

```java
Glide.with(context).load(cacheIconFile).dontAnimate().decodeByOriginalIns().into(new GlideDrawableImageViewTarget(imageView) {
    @Override
    public void onLoadCleared(Drawable placeholder) {
        // prevent set a null image drawable
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        // prevent set a null image drawable
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        // prevent set a null image drawable
    }
});
```

Just see my project's commit to check what I've done.

I think this problem happend only because the application icon's dimension never aligned, the problem icon dimension was **256x256** and others almost 96x96, it cause Glide to prevent allocating more buffer to decode bitmap.

To be honest, my solution was ugly, it can be others.

In this project I tried to reproduce my problem in app module but I failed, it also included that icons in **assets** directory.

I upgrade glide to **4.8.0** and the problem still exists.

also see [stackoverflow question](https://stackoverflow.com/questions/49009670) [stackoverflow question](https://stackoverflow.com/questions/39524604)

