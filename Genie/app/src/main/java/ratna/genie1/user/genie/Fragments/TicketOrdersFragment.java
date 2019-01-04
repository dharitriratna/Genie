package ratna.genie1.user.genie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ratna.genie1.user.genie.R;

public class TicketOrdersFragment extends Fragment {

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        AllOrdersFragment fragment = new AllOrdersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(ratna.genie1.user.genie.R.layout.ticketordersfragment, container, false);
        return v;
    }

}
