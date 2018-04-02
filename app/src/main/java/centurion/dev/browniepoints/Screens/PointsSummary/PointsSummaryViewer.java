package centurion.dev.browniepoints.Screens.PointsSummary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.util.Timer;
import java.util.TimerTask;

import centurion.dev.browniepoints.Services.API.PointsSummaryAPIService;
import centurion.dev.browniepoints.R;
import centurion.dev.browniepoints.Util.ClickHandler;

//Composes and triggers the setup and build of the points summary screen

public class PointsSummaryViewer extends AppCompatActivity {

    PointsSummaryAdapter pointsSummaryAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.points_summary_cards_container);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.points_summary_cards_recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //TODO sort out the decision making for actions - try to move closer to the button and not have the logic here
        pointsSummaryAdapter = new PointsSummaryAdapter();

        recyclerView.setAdapter(pointsSummaryAdapter);
        //TODO add context in here to draw shared preference capability for offline data store
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myfile", getApplicationContext().MODE_PRIVATE);
        PointsSummaryAPIService pointsSummaryAPIService = new PointsSummaryAPIService(pointsSummaryAdapter, sharedPreferences);
        pointsSummaryAPIService.execute();

        //Set time to check data changes intermittently - currently every 5 minutes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myfile", getApplicationContext().MODE_PRIVATE);
                        PointsSummaryAPIService pointsSummaryAPIService = new PointsSummaryAPIService(pointsSummaryAdapter, sharedPreferences);
                        pointsSummaryAPIService.execute();
                    }
                });
            }
        }, 0, 300000);

        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myfile", getApplicationContext().MODE_PRIVATE);
                PointsSummaryAPIService pointsSummaryAPIService = new PointsSummaryAPIService(pointsSummaryAdapter, sharedPreferences);
                pointsSummaryAPIService.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
