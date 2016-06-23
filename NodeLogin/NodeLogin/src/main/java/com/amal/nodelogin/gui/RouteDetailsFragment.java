package com.amal.nodelogin.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.amal.nodelogin.R;
import com.amal.nodelogin.model.db.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by adrian on 23/06/16.
 */
public class RouteDetailsFragment extends Fragment {

    private static final String ROUTE_ID = "route_id";

    @BindView(R.id.name_card)
    CardViewNative nameCard;
    @BindView(R.id.distance_card)
    CardViewNative distanceCard;
    @BindView(R.id.time_card)
    CardViewNative timeCard;
    @BindView(R.id.map_card)
    CardViewNative mapCard;

    Route route;

    public static RouteDetailsFragment newInstance(Long id) {
        RouteDetailsFragment fragment = new RouteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ROUTE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            long id = getArguments().getLong(ROUTE_ID);
            route = new Select().from(Route.class).where("Id=?", id).executeSingle();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_details, container, false);
        ButterKnife.bind(this, view);
        nameCard.setCard(createCard(getString(R.string.name), route.getName()));
        distanceCard.setCard(createCard(getString(R.string.distance), route.getDistance() + " m"));
        timeCard.setCard(createCard(getString(R.string.time), route.getTime() + " s"));
        mapCard.setCard(createMapCard(route));
        return view;
    }

    private Card createCard(String title, String content) {
        Card card = new Card(getActivity());
        CardHeader header = new CardHeader(getContext());
        header.setTitle(title);
        card.addCardHeader(header);
        card.setTitle(content);
        return card;
    }

    private MapCard createMapCard(Route route) {
        MapCard card = new MapCard(getActivity(), route);
        CardHeader header = new CardHeader(getContext());
        header.setTitle(getString(R.string.map));
        card.addCardHeader(header);
        return card;
    }
}
