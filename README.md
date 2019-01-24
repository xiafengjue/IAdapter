# IAapter

这时一个针对RecyclerView.Adapter进行封装的简易Adapter。通过DataBinding进行View的数据加载和绑定。

build

    compile 'com.sora:IAdapter:1.0.0'

使用方法
1、Item布局文件请使用layout节点：
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout>
    <data>
        <variable
            name="item"
            type="String" />

        <variable
            name="itemNavigator"
            type="com.sora.iadapter.library.ItemNavigator" />
    </data>

    <TextView xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="72dp"
        android:gravity="center"
        android:onClick="@{()->itemNavigator.itemDetail(item)}"
        android:text="@{item}" />
</layout>
```
2、RecyclerView绑定Adapter
使用DataBinding可以在xml中使用app:setData进行数据的绑定
```
//需要使用多类型的View，将ItemMainBinding修改为ViewDataBiding
recyclerView.adapter = object : IAdapter<String, ItemMainBinding>() {
            override fun getLayoutId(viewType: Int): Int {
			   //返回item id，使用viewType可以进行多类型的处理
                return R.layout.item_main
            }

            override fun getDataBRId(itemViewType: Int): Int {
			//返回ViewDataBinding中默认数据的BR ID
                return BR.item
            }
			//功能类似onBindViewHolder
            override fun convert(binding: ItemMainBinding, position: Int,t:String?){
                super.convert(binding, position, t)
            }
			//功能类似onCreateViewHolder
            override fun convertListener(binding: ItemMainBinding) {
                super.convertListener(binding)
                binding.itemNavigator = this@MainActivity
            }

        }
```
3、设置数据
可以在xml中直接对RecyclerView进行数据的绑定
```xml
      <android.support.v7.widget.RecyclerView
                android:id="@+id/recyclerView"
                android:layout_width="0dp"
                android:layout_height="0dp"
                app:data="@{activity.data}"/>
```
也可以使用
```Java
      IAdapter.setData(List<T> data);
```
使用上面两种方式的时候，会自动显示Item的动画。

4、其它API
```Java
//设置header view的数量。有时候List中并没有header的相关数据
public int getHeadCount();
//设置最大显示数
public int getMaxSize();
/**
   * 判断Item类型是否相等。
   * @param oldData 旧数据
   * @param data 新数据
   * @param oldItemPosition 旧数据的游标
   * @param newItemPosition 新数据的游标
   * @return 比较结果，默认比较采用Adapter.getItemViewType()
   */
protected boolean areItemsTheSame(List<T> oldData, List<T> data, int oldItemPosition, int newItemPosition);
/**
 * 比较两个item的内容是否相等。
 * @param oldData 旧数据
 * @param data 新数据
 * @param oldItemPosition 旧数据的游标
 * @param newItemPosition 新数据的游标
 * @return 比较结果，默认比较采用Object.equels()
 */
protected boolean areContentsTheSame(List<T> oldData, List<T> data, int oldItemPosition, int newItemPosition)；
```

Copyright 2018 zhengyang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

