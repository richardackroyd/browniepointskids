package centurion.dev.browniepoints.DataModel;

/**
 * Created by rich on 31/12/2017.
 */

public class PointsToChange {

    public Long pointsAccountID;
    public int pointsToChangeBy;

    public PointsToChange (Long pointsAccountID, int pointsToChangeBy) {

        this.pointsAccountID = pointsAccountID;
        this.pointsToChangeBy = pointsToChangeBy;

    }

}
