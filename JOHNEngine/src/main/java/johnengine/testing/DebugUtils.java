package johnengine.testing;

import org.json.JSONObject;

public class DebugUtils {

    public static void log(Object me, Object... message) {
        System.out.println(me + ":");

        for (Object m : message)
            System.out.println(m);
    }
    
    public static JSONObject createDefaultFontJson() {
        return new JSONObject(
            /*"{\r\n" + 
            "  \"name\": \"Arial\",\r\n" + 
            "  \"size\": 32,\r\n" + 
            "  \"bold\": false,\r\n" + 
            "  \"italic\": false,\r\n" + 
            "  \"width\": 350,\r\n" + 
            "  \"height\": 125,\r\n" + 
            "  \"characters\": {\r\n" + 
            "    \"0\":{\"x\":116,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"1\":{\"x\":107,\"y\":81,\"width\":12,\"height\":25,\"originX\":-2,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"2\":{\"x\":40,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"3\":{\"x\":134,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"4\":{\"x\":59,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"5\":{\"x\":152,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"6\":{\"x\":78,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"7\":{\"x\":170,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"8\":{\"x\":188,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"9\":{\"x\":206,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \" \":{\"x\":272,\"y\":106,\"width\":3,\"height\":3,\"originX\":1,\"originY\":1,\"advance\":9},\r\n" + 
            "    \"!\":{\"x\":130,\"y\":81,\"width\":7,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"\\\"\":{\"x\":178,\"y\":106,\"width\":12,\"height\":10,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"#\":{\"x\":312,\"y\":31,\"width\":20,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"$\":{\"x\":117,\"y\":0,\"width\":19,\"height\":29,\"originX\":1,\"originY\":25,\"advance\":18},\r\n" + 
            "    \"%\":{\"x\":194,\"y\":0,\"width\":28,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":28},\r\n" + 
            "    \"&\":{\"x\":97,\"y\":31,\"width\":22,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"'\":{\"x\":197,\"y\":106,\"width\":6,\"height\":10,\"originX\":0,\"originY\":24,\"advance\":6},\r\n" + 
            "    \"(\":{\"x\":59,\"y\":0,\"width\":11,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \")\":{\"x\":70,\"y\":0,\"width\":11,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"*\":{\"x\":164,\"y\":106,\"width\":14,\"height\":12,\"originX\":1,\"originY\":24,\"advance\":12},\r\n" + 
            "    \"+\":{\"x\":109,\"y\":106,\"width\":19,\"height\":17,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \",\":{\"x\":190,\"y\":106,\"width\":7,\"height\":10,\"originX\":-1,\"originY\":4,\"advance\":9},\r\n" + 
            "    \"-\":{\"x\":231,\"y\":106,\"width\":12,\"height\":5,\"originX\":1,\"originY\":11,\"advance\":11},\r\n" + 
            "    \".\":{\"x\":243,\"y\":106,\"width\":7,\"height\":5,\"originX\":-1,\"originY\":4,\"advance\":9},\r\n" + 
            "    \"/\":{\"x\":68,\"y\":81,\"width\":13,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":9},\r\n" + 
            "    \":\":{\"x\":64,\"y\":106,\"width\":7,\"height\":19,\"originX\":-1,\"originY\":18,\"advance\":9},\r\n" + 
            "    \";\":{\"x\":158,\"y\":81,\"width\":7,\"height\":24,\"originX\":-1,\"originY\":18,\"advance\":9},\r\n" + 
            "    \"<\":{\"x\":71,\"y\":106,\"width\":19,\"height\":18,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \"=\":{\"x\":145,\"y\":106,\"width\":19,\"height\":12,\"originX\":0,\"originY\":17,\"advance\":19},\r\n" + 
            "    \">\":{\"x\":90,\"y\":106,\"width\":19,\"height\":18,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \"?\":{\"x\":224,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"@\":{\"x\":0,\"y\":0,\"width\":33,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":32},\r\n" + 
            "    \"A\":{\"x\":222,\"y\":0,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"B\":{\"x\":0,\"y\":56,\"width\":20,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"C\":{\"x\":50,\"y\":31,\"width\":24,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"D\":{\"x\":119,\"y\":31,\"width\":22,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"E\":{\"x\":20,\"y\":56,\"width\":20,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"F\":{\"x\":97,\"y\":56,\"width\":19,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"G\":{\"x\":247,\"y\":0,\"width\":25,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"H\":{\"x\":207,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"I\":{\"x\":137,\"y\":81,\"width\":7,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"J\":{\"x\":52,\"y\":81,\"width\":16,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":16},\r\n" + 
            "    \"K\":{\"x\":141,\"y\":31,\"width\":22,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"L\":{\"x\":18,\"y\":81,\"width\":17,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"M\":{\"x\":272,\"y\":0,\"width\":25,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":27},\r\n" + 
            "    \"N\":{\"x\":228,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"O\":{\"x\":297,\"y\":0,\"width\":25,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"P\":{\"x\":249,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Q\":{\"x\":136,\"y\":0,\"width\":25,\"height\":26,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"R\":{\"x\":74,\"y\":31,\"width\":23,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"S\":{\"x\":270,\"y\":31,\"width\":21,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"T\":{\"x\":163,\"y\":31,\"width\":22,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"U\":{\"x\":291,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"V\":{\"x\":322,\"y\":0,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"W\":{\"x\":161,\"y\":0,\"width\":33,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":30},\r\n" + 
            "    \"X\":{\"x\":0,\"y\":31,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Y\":{\"x\":25,\"y\":31,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Z\":{\"x\":185,\"y\":31,\"width\":22,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"[\":{\"x\":81,\"y\":0,\"width\":10,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"\\\\\":{\"x\":81,\"y\":81,\"width\":13,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"]\":{\"x\":91,\"y\":0,\"width\":10,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"^\":{\"x\":128,\"y\":106,\"width\":17,\"height\":15,\"originX\":1,\"originY\":24,\"advance\":15},\r\n" + 
            "    \"_\":{\"x\":250,\"y\":106,\"width\":22,\"height\":4,\"originX\":2,\"originY\":-3,\"advance\":18},\r\n" + 
            "    \"`\":{\"x\":222,\"y\":106,\"width\":9,\"height\":6,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"a\":{\"x\":218,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"b\":{\"x\":242,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"c\":{\"x\":0,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"d\":{\"x\":260,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"e\":{\"x\":237,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"f\":{\"x\":94,\"y\":81,\"width\":13,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"g\":{\"x\":278,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"h\":{\"x\":35,\"y\":81,\"width\":17,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"i\":{\"x\":144,\"y\":81,\"width\":7,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"j\":{\"x\":101,\"y\":0,\"width\":10,\"height\":31,\"originX\":3,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"k\":{\"x\":296,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":16},\r\n" + 
            "    \"l\":{\"x\":151,\"y\":81,\"width\":7,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"m\":{\"x\":192,\"y\":81,\"width\":26,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":27},\r\n" + 
            "    \"n\":{\"x\":17,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"o\":{\"x\":256,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"p\":{\"x\":314,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"q\":{\"x\":332,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"r\":{\"x\":51,\"y\":106,\"width\":13,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":11},\r\n" + 
            "    \"s\":{\"x\":275,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"t\":{\"x\":119,\"y\":81,\"width\":11,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"u\":{\"x\":34,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"v\":{\"x\":293,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"w\":{\"x\":165,\"y\":81,\"width\":27,\"height\":19,\"originX\":2,\"originY\":18,\"advance\":23},\r\n" + 
            "    \"x\":{\"x\":311,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"y\":{\"x\":0,\"y\":81,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"z\":{\"x\":329,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"{\":{\"x\":33,\"y\":0,\"width\":13,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"|\":{\"x\":111,\"y\":0,\"width\":6,\"height\":31,\"originX\":-1,\"originY\":24,\"advance\":8},\r\n" + 
            "    \"}\":{\"x\":46,\"y\":0,\"width\":13,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"~\":{\"x\":203,\"y\":106,\"width\":19,\"height\":7,\"originX\":0,\"originY\":15,\"advance\":19}\r\n" + 
            "  }\r\n" + 
            "}"*/
            "{\r\n" + 
            "  \"name\": \"Arial\",\r\n" + 
            "  \"size\": 20,\r\n" + 
            "  \"bold\": false,\r\n" + 
            "  \"italic\": false,\r\n" + 
            "  \"width\": 230,\r\n" + 
            "  \"height\": 89,\r\n" + 
            "  \"characters\": {\r\n" + 
            "    \"0\":{\"x\":70,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"1\":{\"x\":114,\"y\":55,\"width\":9,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"2\":{\"x\":83,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"3\":{\"x\":96,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"4\":{\"x\":0,\"y\":38,\"width\":14,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"5\":{\"x\":109,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"6\":{\"x\":122,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"7\":{\"x\":135,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"8\":{\"x\":148,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"9\":{\"x\":161,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \" \":{\"x\":30,\"y\":85,\"width\":3,\"height\":3,\"originX\":1,\"originY\":1,\"advance\":6},\r\n" + 
            "    \"!\":{\"x\":140,\"y\":55,\"width\":6,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":6},\r\n" + 
            "    \"\\\"\":{\"x\":187,\"y\":72,\"width\":9,\"height\":7,\"originX\":1,\"originY\":16,\"advance\":7},\r\n" + 
            "    \"#\":{\"x\":99,\"y\":21,\"width\":15,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"$\":{\"x\":84,\"y\":0,\"width\":13,\"height\":20,\"originX\":1,\"originY\":17,\"advance\":11},\r\n" + 
            "    \"%\":{\"x\":136,\"y\":0,\"width\":19,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":18},\r\n" + 
            "    \"&\":{\"x\":51,\"y\":21,\"width\":16,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"'\":{\"x\":196,\"y\":72,\"width\":6,\"height\":7,\"originX\":1,\"originY\":16,\"advance\":4},\r\n" + 
            "    \"(\":{\"x\":22,\"y\":0,\"width\":9,\"height\":21,\"originX\":1,\"originY\":16,\"advance\":7},\r\n" + 
            "    \")\":{\"x\":49,\"y\":0,\"width\":8,\"height\":21,\"originX\":0,\"originY\":16,\"advance\":7},\r\n" + 
            "    \"*\":{\"x\":177,\"y\":72,\"width\":10,\"height\":8,\"originX\":1,\"originY\":16,\"advance\":8},\r\n" + 
            "    \"+\":{\"x\":107,\"y\":72,\"width\":13,\"height\":12,\"originX\":1,\"originY\":13,\"advance\":12},\r\n" + 
            "    \",\":{\"x\":202,\"y\":72,\"width\":6,\"height\":7,\"originX\":0,\"originY\":3,\"advance\":6},\r\n" + 
            "    \"-\":{\"x\":15,\"y\":85,\"width\":9,\"height\":4,\"originX\":1,\"originY\":7,\"advance\":7},\r\n" + 
            "    \".\":{\"x\":24,\"y\":85,\"width\":6,\"height\":4,\"originX\":0,\"originY\":3,\"advance\":6},\r\n" + 
            "    \"/\":{\"x\":105,\"y\":55,\"width\":9,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":6},\r\n" + 
            "    \":\":{\"x\":146,\"y\":72,\"width\":6,\"height\":12,\"originX\":0,\"originY\":11,\"advance\":6},\r\n" + 
            "    \";\":{\"x\":162,\"y\":55,\"width\":6,\"height\":15,\"originX\":0,\"originY\":11,\"advance\":6},\r\n" + 
            "    \"<\":{\"x\":120,\"y\":72,\"width\":13,\"height\":12,\"originX\":1,\"originY\":13,\"advance\":12},\r\n" + 
            "    \"=\":{\"x\":164,\"y\":72,\"width\":13,\"height\":8,\"originX\":1,\"originY\":11,\"advance\":12},\r\n" + 
            "    \">\":{\"x\":133,\"y\":72,\"width\":13,\"height\":12,\"originX\":1,\"originY\":13,\"advance\":12},\r\n" + 
            "    \"?\":{\"x\":174,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"@\":{\"x\":0,\"y\":0,\"width\":22,\"height\":21,\"originX\":1,\"originY\":16,\"advance\":20},\r\n" + 
            "    \"A\":{\"x\":155,\"y\":0,\"width\":17,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"B\":{\"x\":14,\"y\":38,\"width\":14,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"C\":{\"x\":67,\"y\":21,\"width\":16,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":14},\r\n" + 
            "    \"D\":{\"x\":114,\"y\":21,\"width\":15,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":14},\r\n" + 
            "    \"E\":{\"x\":28,\"y\":38,\"width\":14,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"F\":{\"x\":187,\"y\":38,\"width\":13,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":12},\r\n" + 
            "    \"G\":{\"x\":172,\"y\":0,\"width\":17,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":16},\r\n" + 
            "    \"H\":{\"x\":129,\"y\":21,\"width\":15,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":14},\r\n" + 
            "    \"I\":{\"x\":146,\"y\":55,\"width\":6,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":6},\r\n" + 
            "    \"J\":{\"x\":84,\"y\":55,\"width\":11,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":10},\r\n" + 
            "    \"K\":{\"x\":144,\"y\":21,\"width\":15,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"L\":{\"x\":0,\"y\":55,\"width\":12,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"M\":{\"x\":189,\"y\":0,\"width\":17,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":17},\r\n" + 
            "    \"N\":{\"x\":159,\"y\":21,\"width\":15,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":14},\r\n" + 
            "    \"O\":{\"x\":206,\"y\":0,\"width\":17,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":16},\r\n" + 
            "    \"P\":{\"x\":42,\"y\":38,\"width\":14,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"Q\":{\"x\":97,\"y\":0,\"width\":18,\"height\":18,\"originX\":1,\"originY\":16,\"advance\":16},\r\n" + 
            "    \"R\":{\"x\":83,\"y\":21,\"width\":16,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":14},\r\n" + 
            "    \"S\":{\"x\":174,\"y\":21,\"width\":15,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"T\":{\"x\":189,\"y\":21,\"width\":15,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":12},\r\n" + 
            "    \"U\":{\"x\":204,\"y\":21,\"width\":15,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":14},\r\n" + 
            "    \"V\":{\"x\":0,\"y\":21,\"width\":17,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"W\":{\"x\":115,\"y\":0,\"width\":21,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":19},\r\n" + 
            "    \"X\":{\"x\":17,\"y\":21,\"width\":17,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"Y\":{\"x\":34,\"y\":21,\"width\":17,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":13},\r\n" + 
            "    \"Z\":{\"x\":56,\"y\":38,\"width\":14,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":12},\r\n" + 
            "    \"[\":{\"x\":65,\"y\":0,\"width\":7,\"height\":21,\"originX\":0,\"originY\":16,\"advance\":6},\r\n" + 
            "    \"\\\\\":{\"x\":123,\"y\":55,\"width\":9,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":6},\r\n" + 
            "    \"]\":{\"x\":72,\"y\":0,\"width\":7,\"height\":21,\"originX\":1,\"originY\":16,\"advance\":6},\r\n" + 
            "    \"^\":{\"x\":152,\"y\":72,\"width\":12,\"height\":10,\"originX\":1,\"originY\":16,\"advance\":9},\r\n" + 
            "    \"_\":{\"x\":0,\"y\":85,\"width\":15,\"height\":4,\"originX\":2,\"originY\":-2,\"advance\":11},\r\n" + 
            "    \"`\":{\"x\":222,\"y\":72,\"width\":7,\"height\":5,\"originX\":1,\"originY\":15,\"advance\":7},\r\n" + 
            "    \"a\":{\"x\":217,\"y\":55,\"width\":13,\"height\":13,\"originX\":1,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"b\":{\"x\":12,\"y\":55,\"width\":12,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"c\":{\"x\":0,\"y\":72,\"width\":13,\"height\":13,\"originX\":1,\"originY\":12,\"advance\":10},\r\n" + 
            "    \"d\":{\"x\":24,\"y\":55,\"width\":12,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"e\":{\"x\":13,\"y\":72,\"width\":13,\"height\":13,\"originX\":1,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"f\":{\"x\":95,\"y\":55,\"width\":10,\"height\":17,\"originX\":2,\"originY\":16,\"advance\":6},\r\n" + 
            "    \"g\":{\"x\":200,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"h\":{\"x\":36,\"y\":55,\"width\":12,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":11},\r\n" + 
            "    \"i\":{\"x\":152,\"y\":55,\"width\":5,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":4},\r\n" + 
            "    \"j\":{\"x\":57,\"y\":0,\"width\":8,\"height\":21,\"originX\":3,\"originY\":16,\"advance\":4},\r\n" + 
            "    \"k\":{\"x\":48,\"y\":55,\"width\":12,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":10},\r\n" + 
            "    \"l\":{\"x\":157,\"y\":55,\"width\":5,\"height\":17,\"originX\":0,\"originY\":16,\"advance\":4},\r\n" + 
            "    \"m\":{\"x\":186,\"y\":55,\"width\":17,\"height\":13,\"originX\":0,\"originY\":12,\"advance\":17},\r\n" + 
            "    \"n\":{\"x\":39,\"y\":72,\"width\":12,\"height\":13,\"originX\":0,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"o\":{\"x\":26,\"y\":72,\"width\":13,\"height\":13,\"originX\":1,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"p\":{\"x\":60,\"y\":55,\"width\":12,\"height\":17,\"originX\":0,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"q\":{\"x\":72,\"y\":55,\"width\":12,\"height\":17,\"originX\":1,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"r\":{\"x\":98,\"y\":72,\"width\":9,\"height\":13,\"originX\":0,\"originY\":12,\"advance\":7},\r\n" + 
            "    \"s\":{\"x\":51,\"y\":72,\"width\":12,\"height\":13,\"originX\":1,\"originY\":12,\"advance\":10},\r\n" + 
            "    \"t\":{\"x\":132,\"y\":55,\"width\":8,\"height\":17,\"originX\":1,\"originY\":16,\"advance\":6},\r\n" + 
            "    \"u\":{\"x\":87,\"y\":72,\"width\":11,\"height\":13,\"originX\":0,\"originY\":12,\"advance\":11},\r\n" + 
            "    \"v\":{\"x\":63,\"y\":72,\"width\":12,\"height\":13,\"originX\":1,\"originY\":12,\"advance\":10},\r\n" + 
            "    \"w\":{\"x\":168,\"y\":55,\"width\":18,\"height\":13,\"originX\":2,\"originY\":12,\"advance\":14},\r\n" + 
            "    \"x\":{\"x\":203,\"y\":55,\"width\":14,\"height\":13,\"originX\":2,\"originY\":12,\"advance\":10},\r\n" + 
            "    \"y\":{\"x\":213,\"y\":38,\"width\":13,\"height\":17,\"originX\":1,\"originY\":12,\"advance\":10},\r\n" + 
            "    \"z\":{\"x\":75,\"y\":72,\"width\":12,\"height\":13,\"originX\":1,\"originY\":12,\"advance\":10},\r\n" + 
            "    \"{\":{\"x\":31,\"y\":0,\"width\":9,\"height\":21,\"originX\":1,\"originY\":16,\"advance\":7},\r\n" + 
            "    \"|\":{\"x\":79,\"y\":0,\"width\":5,\"height\":21,\"originX\":0,\"originY\":16,\"advance\":5},\r\n" + 
            "    \"}\":{\"x\":40,\"y\":0,\"width\":9,\"height\":21,\"originX\":1,\"originY\":16,\"advance\":7},\r\n" + 
            "    \"~\":{\"x\":208,\"y\":72,\"width\":14,\"height\":5,\"originX\":1,\"originY\":10,\"advance\":12}\r\n" + 
            "  }\r\n" + 
            "}"
        );
    }
    
    public static JSONObject createDefaultFontJsonBig() {
        return new JSONObject(
            "{\r\n" + 
            "  \"name\": \"Arial\",\r\n" + 
            "  \"size\": 32,\r\n" + 
            "  \"bold\": false,\r\n" + 
            "  \"italic\": false,\r\n" + 
            "  \"width\": 350,\r\n" + 
            "  \"height\": 125,\r\n" + 
            "  \"characters\": {\r\n" + 
            "    \"0\":{\"x\":116,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"1\":{\"x\":107,\"y\":81,\"width\":12,\"height\":25,\"originX\":-2,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"2\":{\"x\":40,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"3\":{\"x\":134,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"4\":{\"x\":59,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"5\":{\"x\":152,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"6\":{\"x\":78,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"7\":{\"x\":170,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"8\":{\"x\":188,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"9\":{\"x\":206,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \" \":{\"x\":272,\"y\":106,\"width\":3,\"height\":3,\"originX\":1,\"originY\":1,\"advance\":9},\r\n" + 
            "    \"!\":{\"x\":130,\"y\":81,\"width\":7,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"\\\"\":{\"x\":178,\"y\":106,\"width\":12,\"height\":10,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"#\":{\"x\":312,\"y\":31,\"width\":20,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"$\":{\"x\":117,\"y\":0,\"width\":19,\"height\":29,\"originX\":1,\"originY\":25,\"advance\":18},\r\n" + 
            "    \"%\":{\"x\":194,\"y\":0,\"width\":28,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":28},\r\n" + 
            "    \"&\":{\"x\":97,\"y\":31,\"width\":22,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"'\":{\"x\":197,\"y\":106,\"width\":6,\"height\":10,\"originX\":0,\"originY\":24,\"advance\":6},\r\n" + 
            "    \"(\":{\"x\":59,\"y\":0,\"width\":11,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \")\":{\"x\":70,\"y\":0,\"width\":11,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"*\":{\"x\":164,\"y\":106,\"width\":14,\"height\":12,\"originX\":1,\"originY\":24,\"advance\":12},\r\n" + 
            "    \"+\":{\"x\":109,\"y\":106,\"width\":19,\"height\":17,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \",\":{\"x\":190,\"y\":106,\"width\":7,\"height\":10,\"originX\":-1,\"originY\":4,\"advance\":9},\r\n" + 
            "    \"-\":{\"x\":231,\"y\":106,\"width\":12,\"height\":5,\"originX\":1,\"originY\":11,\"advance\":11},\r\n" + 
            "    \".\":{\"x\":243,\"y\":106,\"width\":7,\"height\":5,\"originX\":-1,\"originY\":4,\"advance\":9},\r\n" + 
            "    \"/\":{\"x\":68,\"y\":81,\"width\":13,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":9},\r\n" + 
            "    \":\":{\"x\":64,\"y\":106,\"width\":7,\"height\":19,\"originX\":-1,\"originY\":18,\"advance\":9},\r\n" + 
            "    \";\":{\"x\":158,\"y\":81,\"width\":7,\"height\":24,\"originX\":-1,\"originY\":18,\"advance\":9},\r\n" + 
            "    \"<\":{\"x\":71,\"y\":106,\"width\":19,\"height\":18,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \"=\":{\"x\":145,\"y\":106,\"width\":19,\"height\":12,\"originX\":0,\"originY\":17,\"advance\":19},\r\n" + 
            "    \">\":{\"x\":90,\"y\":106,\"width\":19,\"height\":18,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \"?\":{\"x\":224,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"@\":{\"x\":0,\"y\":0,\"width\":33,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":32},\r\n" + 
            "    \"A\":{\"x\":222,\"y\":0,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"B\":{\"x\":0,\"y\":56,\"width\":20,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"C\":{\"x\":50,\"y\":31,\"width\":24,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"D\":{\"x\":119,\"y\":31,\"width\":22,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"E\":{\"x\":20,\"y\":56,\"width\":20,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"F\":{\"x\":97,\"y\":56,\"width\":19,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"G\":{\"x\":247,\"y\":0,\"width\":25,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"H\":{\"x\":207,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"I\":{\"x\":137,\"y\":81,\"width\":7,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"J\":{\"x\":52,\"y\":81,\"width\":16,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":16},\r\n" + 
            "    \"K\":{\"x\":141,\"y\":31,\"width\":22,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"L\":{\"x\":18,\"y\":81,\"width\":17,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"M\":{\"x\":272,\"y\":0,\"width\":25,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":27},\r\n" + 
            "    \"N\":{\"x\":228,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"O\":{\"x\":297,\"y\":0,\"width\":25,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"P\":{\"x\":249,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Q\":{\"x\":136,\"y\":0,\"width\":25,\"height\":26,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"R\":{\"x\":74,\"y\":31,\"width\":23,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"S\":{\"x\":270,\"y\":31,\"width\":21,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"T\":{\"x\":163,\"y\":31,\"width\":22,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"U\":{\"x\":291,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"V\":{\"x\":322,\"y\":0,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"W\":{\"x\":161,\"y\":0,\"width\":33,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":30},\r\n" + 
            "    \"X\":{\"x\":0,\"y\":31,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Y\":{\"x\":25,\"y\":31,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Z\":{\"x\":185,\"y\":31,\"width\":22,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"[\":{\"x\":81,\"y\":0,\"width\":10,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"\\\\\":{\"x\":81,\"y\":81,\"width\":13,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"]\":{\"x\":91,\"y\":0,\"width\":10,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"^\":{\"x\":128,\"y\":106,\"width\":17,\"height\":15,\"originX\":1,\"originY\":24,\"advance\":15},\r\n" + 
            "    \"_\":{\"x\":250,\"y\":106,\"width\":22,\"height\":4,\"originX\":2,\"originY\":-3,\"advance\":18},\r\n" + 
            "    \"`\":{\"x\":222,\"y\":106,\"width\":9,\"height\":6,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"a\":{\"x\":218,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"b\":{\"x\":242,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"c\":{\"x\":0,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"d\":{\"x\":260,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"e\":{\"x\":237,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"f\":{\"x\":94,\"y\":81,\"width\":13,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"g\":{\"x\":278,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"h\":{\"x\":35,\"y\":81,\"width\":17,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"i\":{\"x\":144,\"y\":81,\"width\":7,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"j\":{\"x\":101,\"y\":0,\"width\":10,\"height\":31,\"originX\":3,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"k\":{\"x\":296,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":16},\r\n" + 
            "    \"l\":{\"x\":151,\"y\":81,\"width\":7,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"m\":{\"x\":192,\"y\":81,\"width\":26,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":27},\r\n" + 
            "    \"n\":{\"x\":17,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"o\":{\"x\":256,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"p\":{\"x\":314,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"q\":{\"x\":332,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"r\":{\"x\":51,\"y\":106,\"width\":13,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":11},\r\n" + 
            "    \"s\":{\"x\":275,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"t\":{\"x\":119,\"y\":81,\"width\":11,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"u\":{\"x\":34,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"v\":{\"x\":293,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"w\":{\"x\":165,\"y\":81,\"width\":27,\"height\":19,\"originX\":2,\"originY\":18,\"advance\":23},\r\n" + 
            "    \"x\":{\"x\":311,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"y\":{\"x\":0,\"y\":81,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"z\":{\"x\":329,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"{\":{\"x\":33,\"y\":0,\"width\":13,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"|\":{\"x\":111,\"y\":0,\"width\":6,\"height\":31,\"originX\":-1,\"originY\":24,\"advance\":8},\r\n" + 
            "    \"}\":{\"x\":46,\"y\":0,\"width\":13,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"~\":{\"x\":203,\"y\":106,\"width\":19,\"height\":7,\"originX\":0,\"originY\":15,\"advance\":19}\r\n" + 
            "  }\r\n" + 
            "}"
        );
    }
}
