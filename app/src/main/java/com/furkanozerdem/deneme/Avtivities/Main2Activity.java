package com.furkanozerdem.deneme.Avtivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.furkanozerdem.deneme.Adapters.MyViewPagerAdapter;
import com.furkanozerdem.deneme.Avtivities.Main2ActivityFragments.KisiListesiActivity;
import com.furkanozerdem.deneme.Avtivities.Main2ActivityFragments.RandevuActivity;
import com.furkanozerdem.deneme.R;
import com.furkanozerdem.deneme.deneme;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class Main2Activity extends AppCompatActivity {
    private BottomNavigationView bottom;
    private Fragment tempFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_tutucu,new KisiListesiActivity()).commit();

   /*     fm = getSupportFragmentManager();


        MyViewPagerAdapter adapter = new MyViewPagerAdapter(fm);
        adapter.fragmentEkle(new KisiListesiActivity(),"Oyuncu Durumu");
        adapter.fragmentEkle(new RandevuActivity(),"Oyun Tarihi Belirle!");
       // adapter.fragmentEkle(new deneme(),"asdas");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
*/
   bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
       @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           switch (item.getItemId()) {
               case R.id.action_birinci:
                    tempFragment = new KisiListesiActivity();
                   break;
               case R.id.action_ikinci:
                    tempFragment = new RandevuActivity();
                   break;
           }
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu,tempFragment).commit();

        return true;
       }
   });



    }
    public void init() {
    bottom=findViewById(R.id.bottom_navigation);


    }

}
