package br.com.stanzione.uoltest.placar;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.copa.CopaFragment;
import br.com.stanzione.uoltest.jogos.JogosFragment;
import br.com.stanzione.uoltest.noticias.NoticiasFragment;
import br.com.stanzione.uoltest.placar.adapter.ViewPagerAdapter;
import br.com.stanzione.uoltest.videos.VideosFragment;

public class PlacarFragment extends Fragment {


    public PlacarFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placar, container, false);
        setupUi(view);
        return view;
    }

    private void setupUi(View view) {

        getActivity().setTitle(R.string.title_placar);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addOption(new CopaFragment(), getString(R.string.tab_title_worldcup));
        adapter.addOption(new JogosFragment(), getString(R.string.tab_title_games));
        adapter.addOption(new NoticiasFragment(), getString(R.string.tab_title_news));
        adapter.addOption(new VideosFragment(), getString(R.string.tab_title_videos));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager, true);
    }

}
