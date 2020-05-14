# XLDrawBoard

详细内容博客地址:[自定义View-XLDrawBoard](https://fanandjiu.com/%E8%87%AA%E5%AE%9A%E4%B9%89View-XLDrawBoard/#more)

简介：

画板常规功能的集成。可以切换画笔的粗细，画笔的颜色，还可以切换为橡皮擦。编辑功能包括


app模块是使用例子，其运行效果：

![](https://github.com/xiaoshitounen/XLDrawBoard/blob/master/XLDrawBoard.gif)


### 1. 添加依赖

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
~~~
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
~~~

Step 2. Add the dependency
~~~
dependencies {
    implementation 'com.github.xiaoshitounen:XLDrawBoard:1.0.0'
}
~~~

### 2. Xml文件中静态添加使用

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:background="#696969">

    <swu.xl.drawboard.XLDrawBoard
        android:id="@+id/board"
        android:layout_width="match_parent"
        android:layout_height="500dp"
        app:bg_color="#ffffff" />

</RelativeLayout>
~~~

#### ① 属性

- bg_color: 画板的背景颜色，同时决定了橡皮擦状态时，画笔的颜色。

#### ② 画笔颜色以及粗细的设置
~~~java
public void setLineColor(int lineColor)；

public void setLineWidth(int lineWidth)：
~~~

#### ③ 画笔，橡皮擦切换
~~~java
//BOARD_STATE_PEN    画笔状态
//BOARD_STATE_ERASER 橡皮擦状态
public void setBoard_state(int board_state)
~~~

#### ④ 画板的编辑操作实现
~~~java
/**
 * 管理的方法-撤销功能的实现核心
 * @return
 */
public void removeLast();

/**
 * 管理的方法-反撤销功能的实现
 */
public void resumeLast();

/**
 * 管理的方法-清空功能的实现核心
 * @return
 */
public void removeAll();
~~~


### 3. Java代码中动态添加

和Xml代码一样，需要传入背景颜色的参数。

