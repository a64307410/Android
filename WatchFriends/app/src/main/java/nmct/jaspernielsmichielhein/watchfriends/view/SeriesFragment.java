package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeriesFragmentViewModel;

public class SeriesFragment extends Fragment {
    private static final String ARG_series = "nmct.jaspernielsmichielhein.watchfriends.series";

    private SeriesFragmentViewModel seriesFragmentViewModel;

    private Series series = null;

    public SeriesFragment() {
    }

    public static SeriesFragment newInstance(Series series) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_series, series);

        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            series = arguments.getParcelable(ARG_series);
            series.initExtraFields();
            if (series.getStatus() != null && series.getSeasons().size() != 0 && series.getSeasons().get(0).getSeason_number() == 0) {
                series.getSeasons().remove(0);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSeriesBinding fragmentSeriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false);
        fragmentSeriesBinding.rvSimilar.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentSeriesBinding.rvSimilar.setItemAnimator(new DefaultItemAnimator());
        seriesFragmentViewModel = new SeriesFragmentViewModel(getActivity(), fragmentSeriesBinding, series);
        return fragmentSeriesBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        seriesFragmentViewModel.loadSeries();
    }
}