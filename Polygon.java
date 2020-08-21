

/**
 * A polygon is a finite ordered set of points
 * We will consider points in R2
 * Points should be oriented in an anticlockwise fashion, otherwise we get wacky errors
 * TODO: use abstract vector space points
 */
class Polygon {

    double[][] points; ///The set of points making up this polygon
    int[] borderType; ///The border type, can be one of a few options

    public Polygon(double[][] points) {
        this.points = points;
    }

    public double[] getSegmentAt(int index) {
        double[] seg = new double[2];
        seg[0] = this.points[(index + 1) % this.points.length][0] - this.points[index % this.points.length][0];
        seg[1] = this.points[(index + 1) % this.points.length][1] - this.points[index % this.points.length][1];
        return seg;
    }

    /**
     * Gets the length of the given segment
     * The index 0 gives the segment from point 0 to point 1
     * Simply uses the defined distance between the two points
     */
    public double getSegmentLength(int index) {
        double[] seg = this.getSegmentAt(index);
        return this.getSegmentLength(seg);
    }

    /**
     * Actually returns the length of a segment
     * Takes the form of an overload
     */
    public double getSegmentLength(double[] seg) {
        return Math.sqrt(seg[0] * seg[0] + seg[1] * seg[1]);
    }

    /**
     * Gets the angle between two segments
     * Index of 0 gives angle between segments 0 and 1, and so on
     * Uses the cross product for the calculation, so that we take orientation into account
     */
    public double getAngle(int index) {
        double[] seg1 = this.getSegmentAt(index);
        double[] seg2 = this.getSegmentAt(index + 1);
        return Math.asin(Math.abs((seg1[0] * seg2[1] - seg1[1] * seg2[0]) / this.getSegmentLength(seg1) / this.getSegmentLength(seg2)));
    }

    /**
     * Gets the centroid of the polygon
     * Simply averages the positions of all vertices
     */
    public double[] getCentroid() {
        double[] point = new double[2];
        for (int i = 0; i < this.points.length; i++) {
            point[0] += this.points[i][0];
            point[1] += this.points[i][1];
        }
        point[0] /= this.points.length;
        point[1] /= this.points.length;
        return point;
    }

    /**
     * Gets the area of the polygon
     * Uses the cross product to get the area of triangles connecting
     * verticles to the centroid
     */
    public double getArea() {
        double[] centroid = this.getCentroid();
        double total = 0;
        for (int i = 0; i < this.points.length - 1; i++) {
            total += Math.abs((centroid[0] - this.points[i][0]) * (centroid[1] - this.points[i + 1][1]) 
                    - (centroid[1] - this.points[i][1]) * (centroid[0] - this.points[i + 1][0])) / 2;
        }
        return total;
    }

    /**
     * Gets the total "bordering land" of the given type
     * If a negative land type is given, all bordering land types are considered
     * Uses a triangular approximation; see something for an example i guess
     * TODO: use a more efficient cross product algorithm for triangle bits
     */
    public double getBordering(int type, double reachDistance) {
        double total = 0;
        for (int i = 1; i <= this.points.length; i++) {
            if (type < 0 || type == this.borderType[i]) {
                total += reachDistance * getSegmentLength(i);
                total += getSegmentLength(i - 1) * getSegmentLength(i) * Math.sin(getAngle(i - 1)) / 4;
                total += getSegmentLength(i) * getSegmentLength(i + 1) * Math.sin(getAngle(i)) / 4;
            }
        }
        return total;
    }

}