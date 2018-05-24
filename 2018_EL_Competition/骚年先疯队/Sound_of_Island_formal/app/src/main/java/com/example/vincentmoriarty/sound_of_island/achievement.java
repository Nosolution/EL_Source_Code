package com.example.vincentmoriarty.sound_of_island;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class achievement extends AppCompatActivity {
    private List<Items> itemsList = new ArrayList<>();
    Boolean fish_1_bool,fish_2_bool,fish_3_bool,fish_4_bool,fish_5_bool,fish_6_bool,
            apple_1_bool,apple_2_bool,apple_3_bool,apple_4_bool,apple_5_bool,apple_6_bool,
            book_1_bool,book_2_bool,book_3_bool,book_4_bool,book_5_bool,book_6_bool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        SharedPreferences reader = getSharedPreferences("data_achievement", MODE_PRIVATE);
        fish_1_bool = reader.getBoolean("fish_1",false);
        fish_2_bool = reader.getBoolean("fish_2",false);
        fish_3_bool = reader.getBoolean("fish_3",false);
        fish_4_bool = reader.getBoolean("fish_4",false);
        fish_5_bool = reader.getBoolean("fish_5",false);
        fish_6_bool = reader.getBoolean("fish_6",false);
        apple_1_bool = reader.getBoolean("apple_1",false);
        apple_2_bool = reader.getBoolean("apple_2",false);
        apple_3_bool = reader.getBoolean("apple_3",false);
        apple_4_bool = reader.getBoolean("apple_4",false);
        apple_5_bool = reader.getBoolean("apple_5",false);
        apple_6_bool = reader.getBoolean("apple_6",false);
        book_1_bool = reader.getBoolean("book_1",false);
        book_2_bool = reader.getBoolean("book_2",false);
        book_3_bool = reader.getBoolean("book_3",false);
        book_4_bool = reader.getBoolean("book_4",false);
        book_5_bool = reader.getBoolean("book_5",false);
        book_6_bool = reader.getBoolean("book_6",false);
        initItems();
        ItemAdapter adapter = new ItemAdapter(achievement.this,R.layout.items_layout,itemsList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void initItems(){
        Items fish_1 = new Items(R.mipmap.fish,"钓鱼新手","钓到10条鱼",image_bool(fish_1_bool));
        itemsList.add(fish_1);
        Items fish_2 = new Items(R.mipmap.fish,"钓鱼专家","钓到100条鱼",image_bool(fish_2_bool));
        itemsList.add(fish_2);
        Items fish_3 = new Items(R.mipmap.fish,"钓鱼大师","钓到500条鱼",image_bool(fish_3_bool));
        itemsList.add(fish_3);
        Items fish_4 = new Items(R.mipmap.fish,"钓鱼爱好者","连续钓鱼5次",image_bool(fish_4_bool));
        itemsList.add(fish_4);
        Items fish_5 = new Items(R.mipmap.fish,"钓鱼狂热者","连续钓鱼10次",image_bool(fish_5_bool));
        itemsList.add(fish_5);
        Items fish_6 = new Items(R.mipmap.fish,"沉迷钓鱼，无法自拔","连续钓鱼50次",image_bool(fish_6_bool));
        itemsList.add(fish_6);
        Items book_1 = new Items(R.mipmap.book,"读书寥寥","读书10本",image_bool(book_1_bool));
        itemsList.add(book_1);
        Items book_2 = new Items(R.mipmap.book,"略通诗书","读书100本",image_bool(book_2_bool));
        itemsList.add(book_2);
        Items book_3 = new Items(R.mipmap.book,"博览群书","读书500本",image_bool(book_3_bool));
        itemsList.add(book_3);
        Items book_4 = new Items(R.mipmap.book,"咖啡馆生客","连续泡咖啡馆5次",image_bool(book_4_bool));
        itemsList.add(book_4);
        Items book_5 = new Items(R.mipmap.book,"咖啡馆熟客","连续泡咖啡馆10次",image_bool(book_5_bool));
        itemsList.add(book_5);
        Items book_6 = new Items(R.mipmap.book,"沉迷咖啡，无法自拔","连续泡咖啡馆50次",image_bool(book_6_bool));
        itemsList.add(book_6);
        Items apple_1 = new Items(R.mipmap.apple,"苹果收集新手","收集10个苹果",image_bool(apple_1_bool));
        itemsList.add(apple_1);
        Items apple_2 = new Items(R.mipmap.apple,"苹果收集专家","收集100个苹果",image_bool(apple_2_bool));
        itemsList.add(apple_2);
        Items apple_3 = new Items(R.mipmap.apple,"苹果收集大师","收集500个苹果",image_bool(apple_3_bool));
        itemsList.add(apple_3);
        Items apple_4 = new Items(R.mipmap.apple,"林中散步爱好者","连续散步5次",image_bool(apple_4_bool));
        itemsList.add(apple_4);
        Items apple_5 = new Items(R.mipmap.apple,"林中散步狂热者","连续散步10次",image_bool(apple_5_bool));
        itemsList.add(apple_5);
        Items apple_6 = new Items(R.mipmap.apple,"沉迷散步，无法自拔","连续散步50次",image_bool(apple_6_bool));
        itemsList.add(apple_6);
    }

    private int image_bool(boolean finish_not){
        if (finish_not){
            return R.mipmap.yes;
        }else{
            return R.mipmap.no;
        }
    }
}
