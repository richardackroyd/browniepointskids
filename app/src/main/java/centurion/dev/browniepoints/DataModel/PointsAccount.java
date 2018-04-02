package centurion.dev.browniepoints.DataModel;

public class PointsAccount implements Comparable<PointsAccount> {
//force
    Long id;

    int points;

    String name;

    public PointsAccount(Long id, int points, String name) {

        this.id = id;
        this.points = points;
        this.name = name;

    }

    public void setId (Long id) {

        this.id = id;
    }

    public Long getId () {

        return id;
    }

    public void setPoints (int points) {

        this.points = points;
    }

    public int getPoints () {

        return points;
    }

    public void setName (String name) {

        this.name = name;
    }

    public String getName () {
        return name;
    }

    @Override
    public int compareTo(PointsAccount pointsAccount) {
        long compareID=((PointsAccount)pointsAccount).getId();
        long delta = this.id - compareID;
        int result = (int) delta;
        return result;
    }

}
