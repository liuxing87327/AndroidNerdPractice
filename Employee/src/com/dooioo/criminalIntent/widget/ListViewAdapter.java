package com.dooioo.criminalIntent.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dooioo.criminalIntent.R;
import com.dooioo.criminalIntent.model.Employee;

import java.util.List;

/**
 * 功能说明：ListViewAdapter
 * 作者：liuxing(2014-12-05 02:30)
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;                        //运行上下文
    private List<Employee> listItems;               //商品信息集合
    private LayoutInflater listContainer;           //视图容器

    //自定义控件集合
    public final class ListItemView {
        public TextView userCode;
        public TextView userName;
        public TextView orgName;
        public TextView position;
        public TextView status;
    }

    public ListViewAdapter(Context context, List<Employee> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;
    }

    public int getCount() {
        return listItems.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    /**
     * ListView Item设置
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        int selectID = position;

        //自定义视图
        ListItemView listItemView = null;

        if (convertView == null) {
            listItemView = new ListItemView();

            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.fragment_employee_list, null);
            //获取控件对象
            listItemView.userCode = (TextView) convertView.findViewById(R.id.userCode);
            listItemView.userName = (TextView) convertView.findViewById(R.id.userName);
            listItemView.orgName = (TextView) convertView.findViewById(R.id.orgName);
            listItemView.position = (TextView) convertView.findViewById(R.id.position);
            listItemView.status = (TextView) convertView.findViewById(R.id.status);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        //设置文字和图片
        listItemView.userCode.setText(listItems.get(position).getUserCode());
        listItemView.userName.setText(listItems.get(position).getUserName());
        listItemView.orgName.setText(listItems.get(position).getOrgName());
        listItemView.position.setText(listItems.get(position).getPosition());
        listItemView.status.setText(listItems.get(position).getStatus());

//        //注册按钮点击时间爱你
//        listItemView.userCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return convertView;
    }
}