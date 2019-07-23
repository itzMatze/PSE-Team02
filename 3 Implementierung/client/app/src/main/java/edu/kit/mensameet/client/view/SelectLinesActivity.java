package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.kit.mensameet.client.view.databinding.ActivitySelectLinesBinding;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;

public class SelectLinesActivity extends MensaMeetActivity {

    private ActivitySelectLinesBinding binding;
    private SelectLinesViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fragment: binding = ActivitySelectLinesBinding.inflate(inflater,container,false);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_lines);
        viewModel = ViewModelProviders.of(this).get(SelectLinesViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        adapter = new SelectLinesAdapter(dataset);
        //tood: necessary?
        recyclerView.setAdapter(adapter);

        /*
        TODO: eddit it after get MMItem and MMList
        viewModel.lineList.observe(this, new Observer{
            adapter.setLines(it)
        }
         */

        final SelectLinesActivity context = this;
        final Intent backToHome = new Intent(this, HomeActivity.class);
        final Intent chooseTime = new Intent(this, SetTimeActivity.class);
        /*
        todo edit it later when other relevant activity
        decide which activity to start
         */
        viewModel.getUiEventLiveData().observe(this, new Observer<Pair<SelectLinesViewModel, String>>() {
            @Override
            public void onChanged(@Nullable Pair<SelectLinesViewModel, String> it) {
                switch (it.second) {
                    default:
                        break;
                }

            }
        });
    }

}
