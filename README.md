# LifeHelper
集成现有的各大框架和模块，创建对应的app

## fmblzf-network网络模块

网络模块实现了Okhttp3+RxJava+Retrofit2组合使用；
在mvp项目中有对应的实例。
## fmblzf-annotion注解模块

注解模块，自定义注解类实现初始化view方式，通过运行时反射机制实现。
在app项目中有对应的实例。

## fmblzf-picture 图片处理模块

该模块封装了[Glide](http://bumptech.github.io/glide/)图片处理框架实现了验证了Glide的扩展功能和自定义GlideAppModule功能的实现；
在app项目中有对应的实例。
+ 生成API
通过编译期注解的处理生成对应的java源文件，达到扩展的效果；
 
  + 创建自定义module继承AppGlideModule并且标注@GlideModule即可生成GlideApp(这个名称可以自定义设置)的api引用，同时将RequestOptions的引用由apply()变为直接链式调用；
  
    * 1.默认使用Glide的代码方式：
      ```  
        Glide.with(this).load(url)
            .apply(new RequestOptions()
            .override(128)         
            .placeholder(com.fmblzf.lifehelper.R.mipmap.placeholder)
            .error(com.fmblzf.lifehelper.R.mipmap.error)
            .fallback(com.fmblzf.lifehelper.R.mipmap.fallback)
            ).into(imageView);
      ```
    * 2.自定义后的代码使用方式：
      ```
        GlideApp.with(this).load(url) .override(128)
           .placeholder(com.fmblzf.lifehelper.R.mipmap.placeholder)
           .error(com.fmblzf.lifehelper.R.mipmap.error)
           .fallback(com.fmblzf.lifehelper.R.mipmap.fallback)
           .into(imageView);
      ```
  + 创建扩展选项RequestOptions
  自定义类被@GlideExtension注解标注：
  >
    *       1.有私有并且空构造器；
    *       2.类是不可被继承的final
    *       3.所有的方法都是静态的
    *       4.可能包含静态变量和引用其他类或者对象
    *       5.类必须被@GlideExtension标记，作为自定义扩展类
    *       6.静态方法必须被@GlideOption标记，作为自定义扩展选项RequestOptions
    *       7.@GlideType可以增加支持的资源类型（gif,svc etc）
    
+ RequestOptions设置

    + 配置项：
        
        1.占位符（Placeholders）配置        
        2.位置变化（Transformations）配置        
        3.缓存策略（Caching Strategies）配置        
        4.特定于组件的选项，如编码质量或解码位图配置（Component specific options, like encode quality, or decode Bitmap configurations.）
        ```
        import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
        import static com.bumptech.glide.request.RequestOptions.centerInsideTransform;
        
        Glide.with(this).load(url)
                        .apply(centerCropTransform())
                        .apply(centerInsideTransform())
                        .into(imageView)
        ```
        
+ TransitionOptions图片过渡或者变化时的动画效果设置

    + 配置项：
        
        1.视图的淡入配置（view fade in）  
        2.从占位符的交叉淡入淡出配置（cross fade from the placeholder）  
        3.没有动画效果（no transition）
        
        ```
        import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
        
        Glide.with(fragment)
            .load(url)
            .transition(withCrossFade())
            .into(view);
        ```
        注意：当请求一个位图（Bitmap）,你需要用BitmapTransitionOptions，而不是DrawableTransitionOptions；总之，如果你请求一个位图，那么你只能设置简单的淡入效果，而不是交叉淡入淡出效果。



## mvp应用

通过mvp架构创建对应的app，model+view+presenter过程中为了解耦Activity，防止Activity过重，使用Fragment来实现各种model和presenter的交互；