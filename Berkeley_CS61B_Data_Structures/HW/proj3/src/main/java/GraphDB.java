import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    int numN;
    int numE;
    Map<Long, ArrayList<Long>> adj = new HashMap<>();
    Map<Long, node> nodes = new HashMap<>();

    public class node {
        long id;
        double lon;
        double lat;
        String name;

        public node(long id, double lon, double lat) {
            this.id = id;
            this.lon = lon;
            this.lat = lat;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        ArrayList<Long> toBeRemoved = new ArrayList<>();

        for (long id: adj.keySet()) {
            if (adj.get(id).size() == 0) {
                toBeRemoved.add(id);
            }
        }

        for (long toRemove: toBeRemoved) {
            removeNode(toRemove);
        }
    }

    /** Returns an iterable of all vertex IDs in the graph. */
    Iterable<Long> vertices() {
        return nodes.keySet();
    }

    /** Returns ids of all vertices adjacent to v. */
    Iterable<Long> adjacent(long v) {
        return adj.get(v);
    }

    /** Returns the Euclidean distance between vertices v and w, where Euclidean distance
     *  is defined as sqrt( (lonV - lonV)^2 + (latV - latV)^2 ). */
    double distance(long v, long w) {
        double lonDiff = nodes.get(v).lon-nodes.get(w).lon;
        double latDiff = nodes.get(v).lat-nodes.get(w).lat;
        double res = Math.sqrt(lonDiff*lonDiff+latDiff*latDiff);
        return res;
    }

    /** Returns the vertex id closest to the given longitude and latitude. */
    long closest(double lon, double lat) {
        node virtual = new node(-1, lon, lat);
        nodes.put((long)-1, virtual);

        long res = -1;
        double dist = 0.0;
        for(long id: nodes.keySet()) {
            if (dist == 0.0) {
                dist = distance(id, -1);
                res = id;
            } else {
                double newDist = distance(id,-1);
                if (newDist < dist) {
                    dist = newDist;
                    res = id;
                }
            }
        }
        nodes.remove((long)-1);
        return res;
    }

    /** Longitude of vertex v. */
    double lon(long v) {
        return nodes.get(v).lon;
    }

    /** Latitude of vertex v. */
    double lat(long v) {
        return nodes.get(v).lat;
    }

    public void addNode(long id, double lon, double lat) {
        node newNode = new node(id, lon, lat);
        nodes.put(id, newNode);
        adj.put(id, new ArrayList<>());
        numN += 1;
    }

    public void removeNode(long id) {
        nodes.remove(id);
        ArrayList<Long> adjToBeRemoved = adj.get(id);
        numE -= adj.get(id).size();
        for (long edgeToBeRemoved: adjToBeRemoved) {
            adj.get(edgeToBeRemoved).remove(id);
        }
        adj.remove(id);
        numN -= 1;
    }

    public void addEdge(long n1, long n2) {
        adj.get(n1).add(n2);
        adj.get(n2).add(n1);
        numE += 1;
    }
}
