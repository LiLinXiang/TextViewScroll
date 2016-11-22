# TextViewScroll
# 简单的自定义View——TextView实现文本滚动效果类似于弹幕，跑马灯 各方位滚动

**先看看效果**
---------


![这里写图片描述](http://img.blog.csdn.net/20161122224152213)
*****
#先缕一下思路，看下绘制过程
- **先获取TextView的宽度以及高度**
- **再获取文本的宽度以及高度**
- **接着使用scrollTo(int x,int y)方法**
- **最后处理逻辑代码**
- **接着用postInvalidate()刷新View**

>关键点，使用scrollTo方法来滚动视图

#scrollTo(int mScrollX,int mScrollY)原理
这两个变量分别是视图在水平和垂直方向的偏移量，

-    mScrollX:  该视图内容相当于视图起始坐标的偏移量， X轴方向
-    mScrollY:  该视图内容相当于视图起始坐标的偏移量， Y轴方向


分别通过getScrollX() 和getScrollY()方法获得。

  我们知道Android的坐标体系是这样的：
![这里写图片描述](http://img.blog.csdn.net/20161122225238181)

（ps：相对于父类视图的左上角坐标为坐标原点（0，0），而不是整体ViewGroup的左上角为原点。）


   scrollTo()方法就是将一个视图移动到指定位置，偏移量 mScrollX、mScrollY就是视图初始位置的距离，默认是情况下当然是0。
如果视图要发生移动，比如要移动到（x，y），首先要检查这个点的坐标是否和偏移量一样，因为 scrollTo()是移动到指定的点，如果这次移动的点的坐标和上次偏移量一样，也就是说这次移动和上次移动的坐标是同一个，那么就没有必要进行移动了。



#绘制过程onDraw方法如下
``` java
	@Override
    protected void onDraw(Canvas canvas) {
        scrollType(scrollType);
        super.onDraw(canvas);
    }
```

#获取字体行宽度

``` java
	// 获取字体行宽度
    private int getTextWidth() {
        int mTextWidth;
        Paint mPaint = getPaint();
        if (getLineCount() > 1) {
            // 如果有多行文字，则获取最长的一行文字宽度
            String[] lineContent = getText().toString().split("\n");
            int maxLine = 0, maxLineNumber = 0;
            for (int i = 0; i < lineContent.length; i++) {
                if (lineContent[i].length() > maxLine) {
                    maxLine = lineContent[i].length();
                    maxLineNumber = i;
                }
            }
            mTextWidth = (int) mPaint.measureText(lineContent[maxLineNumber]);
        } else {
            mTextWidth = (int) mPaint.measureText(getText().toString());
        }
        return mTextWidth;
    }
```

>需要注意如果有多行文字，获取文本长度应该是获取的最长的一行的长度
>paint对象一定不能new Paint() 不然获取的文字宽度会是错误的，要通过TextView的getPaint()方法获取paint对象

#获取字体总高度

```java
	// 获取字体总高度
    private int getTextHeight() {
        return getLineHeight() * getLineCount();
    }
```

#Dp像素转化



```java
	// 滚动
	private void scrollType(int type) {
        if (scrollStatus) {
            switch (type) {
                case FROM_RIGHT:
                    // 右到左
                    if (x >= getTextWidth()) {
                        x = -getWidth();
                    }
                    scrollTo(x, 0);
                    x = x + speed;
                    postInvalidate();
                    break;
                case FROM_LEFT:
                    // 左到右
                    if (x <= -getWidth()) {
                        x = getTextWidth();
                    }
                    scrollTo(x, 0);
                    x = x - speed;
                    postInvalidate();
                    break;
                case FROM_TOP:
                    // 上到下
                    if (y <= -getHeight()) {
                        y = getTextHeight();
                    }
                    scrollTo(0, y);
                    y = y - speed;
                    postInvalidate();
                    break;
                case FROM_BOTTOM:
                    // 下到上
                    if (y >= getTextHeight()) {
                        y = -getHeight();
                    }
                    scrollTo(0, y);
                    y = y + speed;
                    postInvalidate();
                    break;

                default:
                    break;
            }
        }

    }
```

# 最后布局当普通的TextView用即可

``` xml
    <com.example.lincoln.textviewscroll.TextViewScroll
    android:id="@+id/tv_scroll"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:background="#25d9e2"
    android:text="Hello World!哈哈哈哈哈哈嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿\nHello World!哈哈哈哈哈哈嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿\nHello World!哈哈哈哈哈哈嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿"/>
```

****
完整源码下载地址 [https://github.com/LiLinXiang/TextViewScroll](https://github.com/LiLinXiang/TextViewScroll)

**欢迎star**
