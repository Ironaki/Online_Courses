import java.util.*;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    // Recommended: QuadTree instance variable. You'll need to make
    //              your own QuadTree since there is no built-in quadtree in Java.

    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */

    node root = new node(0, -122.2998046875, 37.892195547244356, -122.2119140625, 37.82280243352756);

    public Rasterer(String filename) {
        addChildren();
    }

    public void addChildren() {
        Queue<node> toBePop = new ArrayDeque<>();

        toBePop.add(root);

        while (true) {
            node parent = toBePop.remove();
            if (parent.fileNum == 1111111) {
                break;
            }

            double midLon = (parent.ullon+parent.lrlon)/2;
            double midLat = (parent.ullat+parent.lrlat)/2;

            node c1 = new node(parent.fileNum*10+1, parent.ullon, parent.ullat, midLon, midLat);
            node c2 = new node(parent.fileNum*10+2, midLon, parent.ullat, parent.lrlon, midLat);
            node c3 = new node(parent.fileNum*10+3, parent.ullon, midLat, midLon, parent.lrlat);
            node c4 = new node(parent.fileNum*10+4, midLon, midLat, parent.lrlon, parent.lrlat);
            parent.setChildren(c1, c2, c3, c4);

            toBePop.add(c1);
            toBePop.add(c2);
            toBePop.add(c3);
            toBePop.add(c4);
        }
    }

    public class node {
        int fileNum;
        double ullon;
        double ullat;
        double lrlon;
        double lrlat;
        double lonDPP;

        node upperLeft;
        node upperRight;
        node lowerLeft;
        node lowerRight;

        public node(int num, double ullon, double ullat, double lrlon, double lrlat) {
            fileNum = num;
            this.ullon = ullon;
            this.ullat = ullat;
            this.lrlon = lrlon;
            this.lrlat = lrlat;
            lonDPP = (this.lrlon-this.ullon)/256;
        }

        public void setChildren(node c1, node c2, node c3, node c4) {
            upperLeft = c1;
            upperRight = c2;
            lowerLeft = c3;
            lowerRight = c4;
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     * </p>
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified:
     * "render_grid"   -> String[][], the files to display
     * "raster_ul_lon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     *                    Can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     *                    forget to set this to true! <br>
     * @see #REQUIRED_RASTER_REQUEST_PARAMS
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {

        boolean flag = true;

        double query_lrlon = params.get("lrlon");
        double query_ullon = params.get("ullon");
        double query_w = params.get("w");
        double query_h = params.get("h");
        double query_ullat = params.get("ullat");
        double query_lrlat = params.get("lrlat");

        if(query_ullon > query_lrlon || query_ullat < query_lrlat) {
            flag = false;
        }
        if(query_ullon > root.lrlon || query_lrlon < root.ullon || query_ullat < root.lrlat || query_lrlat > root.ullat) {
            flag = false;
        }

        double total_lon = query_lrlon - query_ullon;
        double total_lat = query_ullat - query_lrlat;
        double query_lonDPP = total_lon/query_w;

        Deque<node> toBeCheck = new ArrayDeque<>();
        toBeCheck.add(root);
        while (true) {
            node test = toBeCheck.peek();

            if(test.fileNum/1000000>0) {
                break;
            } else if (test.lonDPP < query_lonDPP) {
                break;
            }

            node parent = toBeCheck.remove();

            if(checkContained(parent.upperLeft, query_ullon, query_ullat, query_lrlon, query_lrlat)) {
                toBeCheck.addLast(parent.upperLeft);
            }
            if(checkContained(parent.upperRight, query_ullon, query_ullat, query_lrlon, query_lrlat)) {
                toBeCheck.addLast(parent.upperRight);
            }
            if(checkContained(parent.lowerLeft, query_ullon, query_ullat, query_lrlon, query_lrlat)) {
                toBeCheck.addLast(parent.lowerLeft);
            }
            if(checkContained(parent.lowerRight, query_ullon, query_ullat, query_lrlon, query_lrlat)) {
                toBeCheck.addLast(parent.lowerRight);
            }
        }


        double ul_lon = toBeCheck.getFirst().ullon;
        double ul_lat = toBeCheck.getFirst().ullat;
        double lr_lon = toBeCheck.getLast().lrlon;
        double lr_lat = toBeCheck.getLast().lrlat;

        int level = String.valueOf(toBeCheck.getFirst().fileNum).length();
        int depth = level;
        double current_lon = root.lrlon-root.ullon;
        double current_lat = root.ullat-root.lrlat;

        while (level != 0) {
            current_lat /= 2;
            current_lon /= 2;
            level -= 1;
        }

        int col = (int) Math.round((toBeCheck.getLast().lrlon-toBeCheck.getFirst().ullon)/current_lon);
        int row = (int) Math.round((toBeCheck.getFirst().ullat-toBeCheck.getLast().lrlat)/current_lat);

        String[][] grid = new String[row][col];

        for(node n: toBeCheck) {
            int colToBe =(int) Math.round((n.ullon-ul_lon)/current_lon);
            int rowToBe =(int) Math.round((ul_lat-n.ullat)/current_lat);
            grid[rowToBe][colToBe] = "img/" + n.fileNum + ".png";
        }


        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", grid);
        results.put("raster_ul_lon", ul_lon);
        results.put("raster_lr_lon", lr_lon);
        results.put("raster_ul_lat", ul_lat);
        results.put("raster_lr_lat", lr_lat);
        results.put("depth", depth);
        results.put("query_success", flag);
        return results;
    }

    private boolean checkContained(node n, double query_ullon, double query_ullat, double query_lrlon, double query_lrlat) {
        if (n.ullon<query_ullon && n.lrlon>query_ullon) {
            if (n.ullat>query_ullat && n.lrlat<query_ullat) {
                return true;
            }
            if (n.ullat<query_ullat && n.lrlat>query_lrlat) {
                return true;
            }
            if (n.ullat>query_lrlat && n.lrlat<query_lrlat) {
                return true;
            }
        } else if (n.ullon>query_ullon && n.lrlon<query_lrlon) {
            if (n.ullat>query_ullat && n.lrlat<query_ullat) {
                return true;
            }
            if (n.ullat<query_ullat && n.lrlat>query_lrlat) {
                return true;
            }
            if (n.ullat>query_lrlat && n.lrlat<query_lrlat) {
                return true;
            }
        } else if (n.ullon<query_lrlon && n.lrlon>query_lrlon) {
            if (n.ullat>query_ullat && n.lrlat<query_ullat) {
                return true;
            }
            if (n.ullat<query_ullat && n.lrlat>query_lrlat) {
                return true;
            }
            if (n.ullat>query_lrlat && n.lrlat<query_lrlat) {
                return true;
            }
        }
        return false;
    }
}
