import api.GeoLocation;

public class Geo_Location implements GeoLocation {

    double x;
    double y;
    double z;

    // Constructor
    Geo_Location(double a, double b, double c) {
        this.x = a;
        this.y = b;
        this.z = c;
    }

    Geo_Location(Geo_Location g){
        this.x = g.x();
        this.y = g.y();
        this.z = g.z();
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double d_x = Math.pow((g.x() - this.x), 2);
        double d_y = Math.pow((g.y() - this.y), 2);
        double d_z = Math.pow((g.z() - this.z), 2);
        return Math.sqrt(d_x + d_y + d_z);
    }
}
