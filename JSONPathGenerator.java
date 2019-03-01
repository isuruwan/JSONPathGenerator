import org.json.*;

import java.util.Iterator;

public class JSONPathGenerator {

    public static void main(String [] a) {
        String jsonSample = "{\n" +
                "\t\"id\": \"0001\",\n" +
                "\t\"type\": \"donut\",\n" +
                "\t\"name\": \"Cake\",\n" +
                "\t\"ppu\": 0.55,\n" +
                "\t\"batters\":\n" +
                "\t\t{\n" +
                "\t\t\t\"batter\":\n" +
                "\t\t\t\t[\n" +
                "\t\t\t\t\t{ \"id\": \"1001\", \"type\": \"Regular\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1002\", \"type\": \"Chocolate\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1003\", \"type\": \"Blueberry\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1004\", \"type\": \"Devil's Food\" }\n" +
                "\t\t\t\t]\n" +
                "\t\t},\n" +
                "\t\"topping\":\n" +
                "\t\t[\n" +
                "\t\t\t{ \"id\": \"5001\", \"type\": \"None\" },\n" +
                "\t\t\t{ \"id\": \"5002\", \"type\": \"Glazed\" },\n" +
                "\t\t\t{ \"id\": \"5005\", \"type\": \"Sugar\" },\n" +
                "\t\t\t{ \"id\": \"5007\", \"type\": \"Powdered Sugar\" },\n" +
                "\t\t\t{ \"id\": \"5006\", \"type\": \"Chocolate with Sprinkles\" },\n" +
                "\t\t\t{ \"id\": \"5003\", \"type\": \"Chocolate\" },\n" +
                "\t\t\t{ \"id\": \"5004\", \"type\": \"Maple\" }\n" +
                "\t\t]\n" +
                "}";
        JSONObject obj = new JSONObject(jsonSample);
        printJPath(obj, "$");
    }

    private static void printJPath(JSONObject obj, String path) {
        JSONArray keys = obj.names ();
        for (int i = 0; i < keys.length (); ++i) {
            String key = keys.getString (i);
            if (obj.get(key) instanceof JSONObject) {
                String newPath = path + "." + key;
                printJPath((JSONObject)obj.get(key), newPath);
            } else if (obj.get(key) instanceof JSONArray) {
                JSONArray arr = (JSONArray) obj.get(key);
                int j = 0;
                for (Iterator iter = arr.iterator(); iter.hasNext(); ) {
                    JSONObject o = (JSONObject)iter.next();
                    String newPath = path + "." + key + "[" + j + "]";
                    printJPath(o, newPath);
                    j++;
                }
            } else {
                System.out.println(path + "." + key + " : " + obj.get(key));
            }
        }
    }
}
