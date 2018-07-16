package id.tempayan.activity.surat.frag_skkb;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import id.tempayan.R;


public class SkkbDetail extends AppCompatActivity {


    private TabLayout tablayout;
    private ViewPager viewpager;

    private String tabNames[] = {"pemohon", "dokumen", "keterangan"};


    public static Drawable setDrawableSelector(Context context, int normal, int selected) {

        Drawable state_normal = ContextCompat.getDrawable(context, normal);

        Drawable state_pressed = ContextCompat.getDrawable(context, selected);

        StateListDrawable drawable = new StateListDrawable();


        drawable.addState(new int[]{android.R.attr.state_selected},
                state_pressed);
        drawable.addState(new int[]{android.R.attr.state_enabled},
                state_normal);

        return drawable;
    }

    public static ColorStateList setTextselector(int normal, int pressed) {
        ColorStateList colorStates = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_selected},
                        new int[]{}
                },
                new int[]{
                        pressed,
                        normal});
        return colorStates;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skkb_detil);

        initSetTitle();

        initView();

        setupViewPager(viewpager);

        setupTabLayout();

        initTab();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void setupTabLayout() {
        tablayout.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                String id_dokumen = getIntent().getStringExtra("ID_SURAT");

                switch (position) {

                    case 0:
                        String nik = getIntent().getStringExtra("NIK");
                        return PemohonFragment.newInstance(nik);
                    case 1:
                        return DokumenFragment.newInstance(id_dokumen);
                    case 2:
                        return KeteranganFragment.newInstance(id_dokumen);

                }
                return null;
            }

            @Override
            public CharSequence getPageTitle(int position) {
               return tabNames[position];

            }

            @Override
            public int getCount() {
                return tabNames.length;
            }
        });
    }

    private void initView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

    }

    private void initTab() {
        tablayout.getTabAt(0).setCustomView(getTabView(0));
        tablayout.getTabAt(1).setCustomView(getTabView(1));
        tablayout.getTabAt(2).setCustomView(getTabView(2));


    }


    private View getTabView(int position) {
        View view = LayoutInflater.from(SkkbDetail.this).inflate(R.layout.view_tabs, null);

        TextView text = (TextView) view.findViewById(R.id.tab_text);
        //AppBarLayout appbar = (AppBarLayout) view.findViewById(R.id.appbar);

        text.setText(tabNames[position]);
        text.setTextColor(setTextselector(Color.parseColor("#ffbf7a"), Color.parseColor("#FFFFFF")));

        //appbar.setTargetElevation(0);

        return view;
    }


    private void initSetTitle() {

        String id_pelayanan = getIntent().getStringExtra("ID_pelayanan");
        String nama_surat = getIntent().getStringExtra("NAMA_SURAT");
        ActionBar menu = getSupportActionBar();
        assert menu != null;
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setElevation(0);
        menu.setTitle(Html.fromHtml("<small>"+nama_surat+"</small>"));
        menu.setSubtitle(Html.fromHtml("<small>"+id_pelayanan+"</small>"));


    }


}
