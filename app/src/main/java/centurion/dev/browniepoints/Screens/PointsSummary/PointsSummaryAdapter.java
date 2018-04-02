package centurion.dev.browniepoints.Screens.PointsSummary;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import centurion.dev.browniepoints.Services.API.ChangePointsAPIService;
import centurion.dev.browniepoints.DataModel.PointsAccount;
import centurion.dev.browniepoints.DataModel.PointsToChange;
import centurion.dev.browniepoints.R;
import centurion.dev.browniepoints.Util.ClickHandler;

/**
 * Created by rich on 25/11/2017.
 */

//Builds the card deck for the points summary screen creating a PointsSummaryViewHolder per card

public class PointsSummaryAdapter extends RecyclerView.Adapter<PointsSummaryViewHolder> {

    private ArrayList<PointsAccount> pointsAccounts = new ArrayList<PointsAccount>();
    private ClickHandler clickHandler;

    public PointsSummaryAdapter () {

        super();
        clickHandler = new ClickHandler() {
            @Override
            public void componentClicked(int position, int actionToTake, int pointsToChange) {

                switch(actionToTake) {
                    case 0: removePointFromAccount(position, pointsToChange);
                        break;
                    case 1: addPointToAccount(position, pointsToChange);
                        break;
                }
            }
        };

    }

    @Override
    public PointsSummaryViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.points_summary_individual_card_layout, parent, false);

        PointsSummaryViewHolder pointsSummaryViewHolder = new PointsSummaryViewHolder(view);
        return pointsSummaryViewHolder;
    }

    @Override
    public void onBindViewHolder(final PointsSummaryViewHolder pointsSummaryViewHolder, final int listPosition) {

        pointsSummaryViewHolder.pointsAccountNameText.setText(pointsAccounts.get(listPosition).getName());
        pointsSummaryViewHolder.pointsAccountPointsText.setText(Integer.toString(pointsAccounts.get(listPosition).getPoints()));
        pointsSummaryViewHolder.pointsAccountAvatarImage.setImageResource(R.drawable.redsquare);
        //TODO insert in here to turn off clickable when no internet connection
        //POSS wrap return object in api service to be a list that can be extracted and a status
        //POSS if status offline set the clickhandler to "" if not leave to process
        //POSS might want to move the add / remove click handler creation into this class
        pointsSummaryViewHolder.clickHandler = this.clickHandler;
    }

    @Override
    public int getItemCount() {

        return pointsAccounts.size();
    }

    public PointsAccount getItem(int position) {

        return pointsAccounts.get(position);

    }

    //TODO break out the call to the API as a separate function (repeated code in add / remove function)

    public void addPointToAccount(int position, int pointsToChange) {

        pointsAccounts.get(position).setPoints(pointsAccounts.get(position).getPoints() + pointsToChange);

        PointsToChange pointToChange =
                new PointsToChange(pointsAccounts.get(position).getId(), pointsToChange);

        ChangePointsAPIService changePointsAPIService = new ChangePointsAPIService(pointToChange);
        changePointsAPIService.execute();

        dataSetChanged();

    }

    public void removePointFromAccount(int position, int pointsToChange) {

        PointsToChange pointToChange;

        if(pointsAccounts.get(position).getPoints() - pointsToChange >= 0) {

            pointsAccounts.get(position).setPoints(pointsAccounts.get(position).getPoints() - pointsToChange);

            pointToChange =
                    new PointsToChange(pointsAccounts.get(position).getId(), pointsToChange * -1);

        } else {

            pointsAccounts.get(position).setPoints(0);

            pointToChange =
                    new PointsToChange(pointsAccounts.get(position).getId(), pointsAccounts.get(position).getPoints());

        }

        ChangePointsAPIService changePointsAPIService = new ChangePointsAPIService(pointToChange);
        changePointsAPIService.execute();

        dataSetChanged();

    }

    public void upDateEntries(ArrayList<PointsAccount> pointsAccounts){

        this.pointsAccounts = pointsAccounts;
        dataSetChanged();

    }

    //Broke out as a utility in case wanting to take specific common actions on data set changing
    private void dataSetChanged() {notifyDataSetChanged();}

}
